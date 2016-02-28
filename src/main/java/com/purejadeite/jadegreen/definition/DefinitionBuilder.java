package com.purejadeite.jadegreen.definition;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.jadegreen.DefinitionException;
import com.purejadeite.jadegreen.definition.cell.AnchorCellDefinition;
import com.purejadeite.jadegreen.definition.cell.CellDefinition;
import com.purejadeite.jadegreen.definition.cell.JoinedCellDefinition;
import com.purejadeite.jadegreen.definition.cell.ListCellDefinition;
import com.purejadeite.jadegreen.definition.cell.ValueDefinition;
import com.purejadeite.jadegreen.definition.table.CategoryDefinition;
import com.purejadeite.jadegreen.definition.table.CategoryDefinitionInterface;
import com.purejadeite.jadegreen.definition.table.HorizontalTableDefinition;
import com.purejadeite.jadegreen.definition.table.TableDefinitionInterface;
import com.purejadeite.jadegreen.definition.table.VerticalTableDefinition;
import com.purejadeite.jadegreen.definition.table.cell.AnchorTableCellDefinition;
import com.purejadeite.jadegreen.definition.table.cell.HorizontalTableCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.table.cell.JoinedTableCellDefinition;
import com.purejadeite.jadegreen.definition.table.cell.TableCellDefinitionInterface;
import com.purejadeite.jadegreen.definition.table.cell.TableValueDefinition;
import com.purejadeite.jadegreen.definition.table.cell.VerticalTableCellDefinition;

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
		List<Map<String, Object>> sheetConfigs = getList(config, BookDefinition.CFG_SHEETS);
		for (Map<String, Object> sheetConfig : sheetConfigs) {
			// sheetのビルド
			SheetDefinition sheet = createSheet(sheetConfig, book);
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
	 * Sheet定義の生成
	 *
	 * @param config
	 *            Sheetひとつ分の定義
	 * @param book
	 *            ブック読み込み定義
	 * @return Sheet読み込み定義
	 */
	private static SheetDefinition createSheet(Map<String, Object> config, BookDefinition book) {
		SheetDefinition definition = null;

		if (UnionSheetDefinition.assess(config, book)) {
			// 集約の場合
			definition = new UnionSheetDefinition(book, config);
		} else if (SheetDefinition.assess(config, book)) {
			// 通常の場合
			definition = new SheetDefinition(book, config);
		}

		if (definition != null) {
			LOGGER.debug(definition.getClass().getSimpleName() + ":" + definition.getId());
		} else {
			String id = getString(config, DefinitionInterface.CFG_ID);
			throw new DefinitionException("id=" + id + ":定義が不正です");
		}
		return definition;
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
	private static DefinitionInterface<?> createCell(Map<String, Object> cellDef, SheetDefinition sheet) {
		return createCell(cellDef, sheet, null);
	}

	/**
	 * Sheet直下のCell定義の生成
	 *
	 * @param config
	 *            Cellひとつ分の定義
	 * @param sheet
	 *            シート読み込み定義
	 * @param parent
	 *            複数Cell定義
	 * @return Cellまたは複数Cell読み込み定義
	 */
	private static DefinitionInterface<?> createCell(Map<String, Object> config, SheetDefinition sheet,
			ParentDefinitionInterface<?, ?> parent) {
		DefinitionInterface<?> definition = null;

		if (AnchorCellDefinition.assess(config, parent)) {
			// アンカーの場合
			definition = new AnchorCellDefinition(sheet, config);
		} else if (JoinedCellDefinition.assess(config, parent)) {
				// 単独のJOINフィールドの場合
				definition = new JoinedCellDefinition(sheet, config);
		} else if (LinkedDefinition.assess(config, parent)) {
			// 単独の値フィールドの場合
			definition = new LinkedDefinition(sheet, config);
		} else if (ValueDefinition.assess(config, parent)) {
			// 単独の値フィールドの場合
			definition = new ValueDefinition(sheet, config);
		} else if (ListCellDefinition.assess(config, parent)) {
			// セルの値を分割する場合
			definition = new ListCellDefinition(sheet, config);
		} else if (CategoryDefinition.assess(config, parent)) {
			// 配下に単独のセルを持っているの場合
			CategoryDefinitionInterface<?> cate = new CategoryDefinition(sheet, config);
			List<Map<String, Object>> columns = getList(config, CategoryDefinition.CFG_CELLS);
			cate.addChildren(createCells(columns, sheet, (ParentDefinitionInterface<?, ?>) cate));
			definition = cate;
		} else if (VerticalTableDefinition.assess(config, parent)) {
			// 行方向の繰り返しの場合
			TableDefinitionInterface<?> tbl = new VerticalTableDefinition(sheet, config);
			List<Map<String, Object>> columns = getList(config, VerticalTableDefinition.CFG_COLUMNS);
			tbl.addChildren(createTableCells(columns, sheet, tbl));
			definition = tbl;
		} else if (HorizontalTableDefinition.assess(config, parent)) {
			// 列方向の繰り返しの場合
			TableDefinitionInterface<?> tbl = new HorizontalTableDefinition(sheet, config);
			List<Map<String, Object>> rows = getList(config, HorizontalTableDefinition.CFG_ROWS);
			tbl.addChildren(createTableCells(rows, sheet, tbl));
			definition = tbl;
		} else if (CellDefinition.assess(config, parent)) {
			// 単独のフィールドの場合
			definition = new CellDefinition(sheet, config);
		}

		if (definition != null) {
			LOGGER.debug(definition.getClass().getSimpleName() + ":" + definition.getId());
		} else {
			String id = getString(config, DefinitionInterface.CFG_ID);
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
	private static List<DefinitionInterface<?>> createCells(List<Map<String, Object>> cells, SheetDefinition sheet,
			ParentDefinitionInterface<?, ?> table) {
		List<DefinitionInterface<?>> definitions = new ArrayList<>();
		for (Map<String, Object> cell : cells) {
			DefinitionInterface<?> child = createCell(cell, sheet, table);
			definitions.add(child);
		}
		return definitions;
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
	private static List<TableCellDefinitionInterface<?, ?>> createTableCells(List<Map<String, Object>> cells, SheetDefinition sheet,
			ParentDefinitionInterface<?, ?> table) {
		List<TableCellDefinitionInterface<?, ?>> definitions = new ArrayList<>();
		for (Map<String, Object> cell : cells) {
			DefinitionInterface<?> child = createTableCell(cell, sheet, table);
			if (child instanceof TableCellDefinitionInterface) {
				definitions.add((TableCellDefinitionInterface<?, ?>) child);
			} else {
				throw new DefinitionException("table=" + table.getFullId() + "&illegal child=" + child.getFullId()
						+ ":tableの子要素にはcellを定義してください");
			}
		}
		return definitions;
	}

	/**
	 * Table, Category配下のCell定義の生成
	 *
	 * @param config
	 *            Cellひとつ分の定義
	 * @param sheet
	 *            シート読み込み定義
	 * @param table
	 *            複数Cell定義
	 * @return Cellまたは複数Cell読み込み定義
	 */
	@SuppressWarnings("unchecked")
	private static DefinitionInterface<?> createTableCell(Map<String, Object> config, SheetDefinition sheet,
			ParentDefinitionInterface<?, ?> table) {
		DefinitionInterface<?> definition = null;

		if (AnchorTableCellDefinition.assess(config, table)) {
			// アンカーの場合
			definition = new AnchorTableCellDefinition((TableDefinitionInterface<?>) table, config);
		} else if (JoinedTableCellDefinition.assess(config, table)) {
			// 親のあるJOINフィールドの場合
			definition = new JoinedTableCellDefinition(sheet, (TableDefinitionInterface<?>) table, config);
		} else if (TableValueDefinition.assess(config, table)) {
			// 親のある値フィールドの場合
			definition = new TableValueDefinition<TableDefinitionInterface<?>>((TableDefinitionInterface<?>) table, config);
		} else if (VerticalTableCellDefinition.assess(config, table)) {
			// 親が行方向の繰り返しの場合
			definition = new VerticalTableCellDefinition((TableDefinitionInterface<?>) table, config);
		} else if (HorizontalTableCellDefinitionImpl.assess(config, table)) {
			// 親が列方向の繰り返しの場合
			definition = new HorizontalTableCellDefinitionImpl((TableDefinitionInterface<?>) table, config);
		} else if (VerticalTableDefinition.assess(config, table)) {
			// 行方向の繰り返しの場合
			TableDefinitionInterface<?> tbl = new VerticalTableDefinition(sheet, config);
			List<Map<String, Object>> columns = getList(config, VerticalTableDefinition.CFG_COLUMNS);
			tbl.addChildren(createTableCells(columns, sheet, tbl));
			definition = tbl;
		} else if (HorizontalTableDefinition.assess(config, table)) {
			// 列方向の繰り返しの場合
			TableDefinitionInterface<?> tbl = new HorizontalTableDefinition(sheet, config);
			List<Map<String, Object>> rows = getList(config, HorizontalTableDefinition.CFG_ROWS);
			tbl.addChildren(createTableCells(rows, sheet, tbl));
			definition = tbl;
		}

		if (definition != null) {
			LOGGER.debug(definition.getClass().getSimpleName() + ":" + definition.getId());
		} else {
			String id = getString(config, DefinitionInterface.CFG_ID);
			throw new DefinitionException("id=" + id + ":定義が不正です");
		}
		return definition;
	}

}
