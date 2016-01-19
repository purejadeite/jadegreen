package com.purejadeite.jadegreen.definition;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.jadegreen.DefinitionException;
import com.purejadeite.jadegreen.definition.cell.CellDefinition;
import com.purejadeite.jadegreen.definition.cell.CellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.ColumnCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.JoinedCellDefinition;
import com.purejadeite.jadegreen.definition.cell.JoinedCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.JoinedTableCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.RowCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.TableCellDefinition;
import com.purejadeite.jadegreen.definition.cell.TableValueDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.ValueDefinitionImpl;
import com.purejadeite.jadegreen.definition.table.ColumnRepeatDefinitionImpl;
import com.purejadeite.jadegreen.definition.table.RowRepeatDefinitionImpl;
import com.purejadeite.jadegreen.definition.table.TableDefinition;

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
	 * @param config
	 *            JSONから変換されたMAPのLIST形式の定義
	 * @return Book読み込み定義
	 */
	public static WorkbookDefinition build(Map<String, Object> config) {
		// bookのビルド
		WorkbookDefinition book = new WorkbookDefinition(config);
		List<Map<String, Object>> sheetConfigs = getList(config, WorkbookDefinition.CFG_SHEETS);
		for (Map<String, Object> sheetConfig : sheetConfigs) {
			// sheetのビルド
			WorksheetDefinition sheet = new WorksheetDefinition(book, sheetConfig);
			// cellのビルド
			List<Map<String, Object>> cellConfigs = getList(sheetConfig, WorksheetDefinition.CFG_CELLS);
			for (Map<String, Object> cellConfig : cellConfigs) {
				sheet.addChild(createCell(cellConfig, sheet));
			}
			// bookに追加
			book.addChild(sheet);
		}
		if (book.getChildren().size() == 1) {
			// シートが一つしか無い場合はそれが出力対象
			book.getChild(0).setOutput(true);
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
	private static Definition<?> createCell(Map<String, Object> cellDef, WorksheetDefinition sheet) {
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
	private static Definition<?> createCell(Map<String, Object> config, WorksheetDefinition sheet,
			TableDefinition<?> table) {
		Definition<?> definition = null;
		if (config.containsKey(JoinedCellDefinition.CFG_JOIN)) {
			// 結合フィールドの場合
			if (table != null) {
				// 親のあるフィールドの場合
				definition = new JoinedTableCellDefinitionImpl(sheet, table, config);
			} else {
				// 単独フィールドの場合
				definition = new JoinedCellDefinitionImpl(sheet, config);
			}
		} else if (config.containsKey(TableValueDefinitionImpl.CFG_VALUE)) {
			// 値フィールドの場合
			if (table != null) {
				// 親のあるフィールドの場合
				definition = new TableValueDefinitionImpl<TableDefinition<?>>(table, config);
			} else {
				// 単独フィールドの場合
				definition = new ValueDefinitionImpl<WorksheetDefinition>(sheet, config);
			}
		} else if (table != null) {
			// 親のあるフィールドの場合
			if (!config.containsKey(RowRepeatDefinitionImpl.CFG_ROW) && !config.containsKey(ColumnCellDefinitionImpl.CFG_COLUMN)) {
				// アドレスなし
				definition = new TableValueDefinitionImpl<TableDefinition<?>>(table, config);
			} else if (table instanceof RowRepeatDefinitionImpl) {
				// 行方向の繰り返し内のフィールドの場合
				definition = new RowCellDefinitionImpl(table, config);
			} else if (table instanceof ColumnRepeatDefinitionImpl) {
				// 列方向の繰り返し内のフィールドの場合
				definition = new ColumnCellDefinitionImpl(table, config);
			}
		} else if (config.containsKey(RowRepeatDefinitionImpl.CFG_COLUMNS)) {
			// 行方向の繰り返しの場合
			List<Map<String, Object>> columns = getList(config, RowRepeatDefinitionImpl.CFG_COLUMNS);
			TableDefinition<?> rowTable = new RowRepeatDefinitionImpl(sheet, config);
			rowTable.addChildren(createCells(columns, sheet, rowTable));
			definition = rowTable;
		} else if (config.containsKey(ColumnRepeatDefinitionImpl.CFG_ROWS)) {
			// 列方向の繰り返しの場合
			// TODO 現在未対応
			List<Map<String, Object>> rows = getList(config, ColumnRepeatDefinitionImpl.CFG_ROWS);
			TableDefinition<?> columnTable = new ColumnRepeatDefinitionImpl(sheet, config);
			columnTable.addChildren(createCells(rows, sheet, columnTable));
			definition = columnTable;
		} else {
			// 単独フィールドの場合
			if (!config.containsKey(CellDefinition.CFG_ROW) && !config.containsKey(CellDefinition.CFG_COLUMN)) {
				// アドレスなし
				definition = new ValueDefinitionImpl<WorksheetDefinition>(sheet, config);
			} else {
				definition = new CellDefinitionImpl(sheet, config);
			}
		}
		if (definition != null) {
			LOGGER.debug(definition.getClass().getSimpleName() + ":" + definition.getId());
			DefinitionManager.register(sheet, definition);
		} else {
			String id = getString(config, Definition.CFG_ID);
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
			Definition<?> child = createCell(cell, sheet, table);
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
