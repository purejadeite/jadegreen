package com.purejadeite.jadegreen.definition;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.jadegreen.DefinitionException;
import com.purejadeite.jadegreen.definition.cell.CellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.JoinedCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.ValueDefinitionImpl;
import com.purejadeite.jadegreen.definition.table.CategoryDefinitionImpl;
import com.purejadeite.jadegreen.definition.table.ColumnRepeatDefinitionImpl;
import com.purejadeite.jadegreen.definition.table.RowRepeatDefinitionImpl;
import com.purejadeite.jadegreen.definition.table.TableDefinition;
import com.purejadeite.jadegreen.definition.table.cell.ColumnCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.table.cell.JoinedTableCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.table.cell.RowCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.table.cell.TableCellDefinition;
import com.purejadeite.jadegreen.definition.table.cell.TableValueDefinitionImpl;

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
	public static BookDefinition build(Map<String, Object> config) {
		// bookのビルド
		BookDefinition book = new BookDefinition(config);
		DefinitionManager.getInstance().register(book);
		List<Map<String, Object>> sheetConfigs = getList(config, BookDefinition.CFG_SHEETS);
		for (Map<String, Object> sheetConfig : sheetConfigs) {
			// sheetのビルド
			SheetDefinition sheet = new SheetDefinition(book, sheetConfig);
			// cellのビルド
			List<Map<String, Object>> cellConfigs = getList(sheetConfig, SheetDefinition.CFG_CELLS);
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
	private static Definition<?> createCell(Map<String, Object> cellDef, SheetDefinition sheet) {
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
	private static Definition<?> createCell(Map<String, Object> config, SheetDefinition sheet,
			TableDefinition<?> table) {
		Definition<?> definition = null;

		if (JoinedTableCellDefinitionImpl.assess(table, config)) {
			// 親のあるJOINフィールドの場合
			definition = new JoinedTableCellDefinitionImpl(sheet, table, config);
		} else if (JoinedCellDefinitionImpl.assess(table, config)) {
			// 単独のJOINフィールドの場合
			definition = new JoinedCellDefinitionImpl(sheet, config);
		} else if (TableValueDefinitionImpl.assess(table, config)) {
			// 親のある値フィールドの場合
			definition = new TableValueDefinitionImpl<TableDefinition<?>>(table, config);
		} else if (ValueDefinitionImpl.assess(table, config)) {
			// 単独の値フィールドの場合
			definition = new ValueDefinitionImpl<SheetDefinition>(sheet, config);
		} else if (RowCellDefinitionImpl.assess(table, config)) {
			// 親が行方向の繰り返しの場合
			definition = new RowCellDefinitionImpl(table, config);
		} else if (ColumnCellDefinitionImpl.assess(table, config)) {
			// 親が列方向の繰り返しの場合
			definition = new ColumnCellDefinitionImpl(table, config);
		} else if (CategoryDefinitionImpl.assess(table, config)) {
			// 配下に単独のセルを持っているの場合
			definition = new CategoryDefinitionImpl(sheet, config);
		} else if (RowRepeatDefinitionImpl.assess(table, config)) {
			// 行方向の繰り返しの場合
			TableDefinition<?> tbl = new RowRepeatDefinitionImpl(sheet, config);
			List<Map<String, Object>> columns = getList(config, RowRepeatDefinitionImpl.CFG_COLUMNS);
			tbl.addChildren(createCells(columns, sheet, tbl));
			definition = tbl;
		} else if (ColumnRepeatDefinitionImpl.assess(table, config)) {
			// 列方向の繰り返しの場合
			TableDefinition<?> tbl = new ColumnRepeatDefinitionImpl(sheet, config);
			List<Map<String, Object>> rows = getList(config, ColumnRepeatDefinitionImpl.CFG_ROWS);
			tbl.addChildren(createCells(rows, sheet, tbl));
			definition = tbl;
		} else if (CellDefinitionImpl.assess(table, config)) {
			// 単独のフィールドの場合
			definition = new CellDefinitionImpl(sheet, config);
		}

		if (definition != null) {
			LOGGER.debug(definition.getClass().getSimpleName() + ":" + definition.getId());
			DefinitionManager.getInstance().register(sheet, definition);
		} else {
			String id = getString(config, Definition.CFG_ID);
			throw new DefinitionException("id=" + id + ":定義が不正です");
		}
		return definition;
	}

	/**
	 * Cell定義のListの生成
	 *
	 * @param cells
	 *            JSONから変換したセル定義
	 * @param sheet
	 *            シート定義
	 * @param table
	 *            table定義
	 * @return セルの定義リスト
	 */
	private static List<TableCellDefinition<?>> createCells(List<Map<String, Object>> cells, SheetDefinition sheet,
			TableDefinition<?> table) {
		List<TableCellDefinition<?>> definitions = new ArrayList<>();
		for (Map<String, Object> cell : cells) {
			Definition<?> child = createCell(cell, sheet, table);
			if (child instanceof TableCellDefinition) {
				definitions.add((TableCellDefinition<?>) child);
			} else {
				throw new DefinitionException("table=" + table.getFullId() + "&illegal child=" + child.getFullId()
						+ ":tableの子要素にはcellを定義してください");
			}
		}
		return definitions;
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
	private static Definition<?> createTableCell(Map<String, Object> config, SheetDefinition sheet,
			TableDefinition<?> table) {
		Definition<?> definition = null;

		if (JoinedTableCellDefinitionImpl.assess(table, config)) {
			// 親のあるJOINフィールドの場合
			definition = new JoinedTableCellDefinitionImpl(sheet, table, config);
		} else if (TableValueDefinitionImpl.assess(table, config)) {
			// 親のある値フィールドの場合
			definition = new TableValueDefinitionImpl<TableDefinition<?>>(table, config);
		} else if (RowCellDefinitionImpl.assess(table, config)) {
			// 親が行方向の繰り返しの場合
			definition = new RowCellDefinitionImpl(table, config);
		} else if (ColumnCellDefinitionImpl.assess(table, config)) {
			// 親が列方向の繰り返しの場合
			definition = new ColumnCellDefinitionImpl(table, config);
		} else if (RowRepeatDefinitionImpl.assess(table, config)) {
			// 行方向の繰り返しの場合
			TableDefinition<?> tbl = new RowRepeatDefinitionImpl(sheet, config);
			List<Map<String, Object>> columns = getList(config, RowRepeatDefinitionImpl.CFG_COLUMNS);
			tbl.addChildren(createCells(columns, sheet, tbl));
			definition = tbl;
		} else if (ColumnRepeatDefinitionImpl.assess(table, config)) {
			// 列方向の繰り返しの場合
			TableDefinition<?> tbl = new ColumnRepeatDefinitionImpl(sheet, config);
			List<Map<String, Object>> rows = getList(config, ColumnRepeatDefinitionImpl.CFG_ROWS);
			tbl.addChildren(createCells(rows, sheet, tbl));
			definition = tbl;
		}

		if (definition != null) {
			LOGGER.debug(definition.getClass().getSimpleName() + ":" + definition.getId());
			DefinitionManager.getInstance().register(sheet, definition);
		} else {
			String id = getString(config, Definition.CFG_ID);
			throw new DefinitionException("id=" + id + ":定義が不正です");
		}
		return definition;
	}

}
