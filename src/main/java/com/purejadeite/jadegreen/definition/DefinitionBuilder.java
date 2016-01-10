package com.purejadeite.jadegreen.definition;

import static com.purejadeite.jadegreen.definition.DefinitionKeys.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.jadegreen.DefinitionException;
import com.purejadeite.jadegreen.definition.cell.CellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.ColumnCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.JoinedCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.JoinedTableCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.ListCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.RowCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.TableCellDefinition;
import com.purejadeite.jadegreen.definition.cell.TableValueDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.ValueDefinitionImpl;
import com.purejadeite.jadegreen.definition.table.ColumnRepeatDefinitionImpl;
import com.purejadeite.jadegreen.definition.table.RowRepeatDefinitionImpl;
import com.purejadeite.jadegreen.definition.table.TableDefinition;
import com.purejadeite.util.RoughlyMapUtils;

/**
 * 定義情報を生成するクラスです
 *
 * @author mitsuhiroseino
 *
 */
public class DefinitionBuilder {

	/**
	 * ロガー
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DefinitionBuilder.class);

	/**
	 * MapのListから読み込み
	 *
	 * @param definition
	 *            JSONから変換されたMAPのLIST形式の定義
	 * @return Book読み込み定義
	 */
	public static WorkbookDefinition build(Map<String, Object> definition) {
		// bookのビルド
		WorkbookDefinition book = WorkbookDefinition.newInstance(definition);
		List<Map<String, Object>> sheetConfigs = RoughlyMapUtils.getList(definition, SHEETS);
		for (Map<String, Object> sheetConfig : sheetConfigs) {
			// sheetのビルド
			WorksheetDefinition sheet = WorksheetDefinition.newInstance(book, sheetConfig);
			// cellのビルド
			List<Map<String, Object>> cellConfigs = RoughlyMapUtils.getList(sheetConfig, CELLS);
			for (Map<String, Object> cellConfig : cellConfigs) {
				sheet.addChild(createCell(cellConfig, sheet));
			}
			// bookに追加
			book.addChild(sheet);
		}
		return book;
	}

	/**
	 * Cell定義の生成
	 *
	 * @param cellDef
	 *            Cellひとつ分の定義
	 * @param sheet
	 *            シート読み込み定義
	 * @return Cell読み込み定義
	 */
	private static MappingDefinition<?> createCell(Map<String, Object> cellDef, WorksheetDefinition sheet) {
		return createCell(cellDef, sheet, null);
	}

	/**
	 * Cell定義の生成
	 *
	 * @param config
	 *            Cellひとつ分の定義
	 * @param sheet
	 *            シート読み込み定義
	 * @param table
	 *            複数Cell定義
	 * @return Cellまたは複数Cell読み込み定義
	 */
	private static MappingDefinition<?> createCell(Map<String, Object> config, WorksheetDefinition sheet,
			TableDefinition<?> table) {
		MappingDefinition<?> definition = null;
		if (config.containsKey(JOIN)) {
			// 結合フィールドの場合
			if (table != null) {
				// 親のあるフィールドの場合
				definition = JoinedTableCellDefinitionImpl.newInstance(sheet.getParent(), sheet, table, config);
			} else {
				// 単独フィールドの場合
				definition = JoinedCellDefinitionImpl.newInstance(sheet.getParent(), sheet, config);
			}
		} else if (config.containsKey(VALUE)) {
			// 値フィールドの場合
			if (table != null) {
				// 親のあるフィールドの場合
				definition = TableValueDefinitionImpl.newInstance(sheet, table, config);
			} else {
				// 単独フィールドの場合
				definition = ValueDefinitionImpl.newInstance(sheet, config);
			}
		} else if (table != null) {
			// 親のあるフィールドの場合
			if (!config.containsKey(ROW) && !config.containsKey(COLUMN)) {
				// アドレスなし
				definition = TableValueDefinitionImpl.newInstance(sheet, table, config);
			} else if (table instanceof RowRepeatDefinitionImpl) {
				// 行方向の繰り返し内のフィールドの場合
				definition = RowCellDefinitionImpl.newInstance(sheet, table, config);
			} else if (table instanceof ColumnRepeatDefinitionImpl) {
				// 列方向の繰り返し内のフィールドの場合
				definition = ColumnCellDefinitionImpl.newInstance(sheet, table, config);
			}
		} else if (config.containsKey(COLUMNS)) {
			// 行方向の繰り返しの場合
			List<Map<String, Object>> columns = RoughlyMapUtils.getList(config, COLUMNS);
			TableDefinition<?> rowTable = RowRepeatDefinitionImpl.newInstance(sheet, config);
			rowTable.addChildren(createCells(columns, sheet, rowTable));
			definition = rowTable;
		} else if (config.containsKey(ROWS)) {
			// 列方向の繰り返しの場合
			// TODO 現在未対応
			List<Map<String, Object>> rows = RoughlyMapUtils.getList(config, ROWS);
			TableDefinition<?> columnTable = ColumnRepeatDefinitionImpl.newInstance(sheet, config);
			columnTable.addChildren(createCells(rows, sheet, columnTable));
			definition = columnTable;
		} else {
			// 単独フィールドの場合
			if (!config.containsKey(ROW) && !config.containsKey(COLUMN)) {
				// アドレスなし
				definition = ValueDefinitionImpl.newInstance(sheet, config);
			} else if (config.containsKey(SPLITTER)) {
				definition = ListCellDefinitionImpl.newInstance(sheet, config);
			} else {
				definition = CellDefinitionImpl.newInstance(sheet, config);
			}
		}
		if (definition != null) {
			LOGGER.debug(definition.getClass().getSimpleName() + ":" + definition.getId());
		} else {
			String id = RoughlyMapUtils.getString(config, ID);
			throw new DefinitionException("id=" + id + ":定義が不正です");
		}
		return definition;
	}

	/**
	 * Cell定義のListの生成
	 *
	 * @param cells JSONから変換したセル定義
	 * @param sheet シート定義
	 * @param table table定義
	 * @return セルの定義リスト
	 */
	private static List<TableCellDefinition<?>> createCells(List<Map<String, Object>> cells,
			WorksheetDefinition sheet, TableDefinition<?> table) {
		List<TableCellDefinition<?>> definitions = new ArrayList<>();
		for (Map<String, Object> cell : cells) {
			MappingDefinition<?> child = createCell(cell, sheet, table);
			if (child instanceof TableCellDefinition) {
				definitions.add((TableCellDefinition<?>) child);
			} else {
				throw new DefinitionException("table=" + table.getFullId() + "&illegal child=" + child.getFullId() +
						":tableの子要素にはcellを定義してください");
			}
		}
		return definitions;
	}

}
