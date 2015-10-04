package com.purejadeite.jadegreen.definition;

import static com.purejadeite.jadegreen.definition.DefinitionKeys.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.jadegreen.DefinitionException;
import com.purejadeite.jadegreen.RoughlyMapUtils;
import com.purejadeite.jadegreen.definition.cell.CellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.ColumnCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.LinkCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.LinkRangeCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.ListCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.RangeCellDefinition;
import com.purejadeite.jadegreen.definition.cell.RangeValueDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.RowCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.ValueDefinitionImpl;
import com.purejadeite.jadegreen.definition.range.ColumnDefinitionImpl;
import com.purejadeite.jadegreen.definition.range.RangeDefinition;
import com.purejadeite.jadegreen.definition.range.RowDefinitionImpl;

/**
 * 定義情報を生成するクラスです
 *
 * @author mitsuhiroseino
 *
 */
public class DefinitionBuilder {

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
	 * @param range
	 *            複数Cell定義
	 * @return Cellまたは複数Cell読み込み定義
	 */
	private static Definition<?> createCell(Map<String, Object> config, WorksheetDefinition sheet,
			RangeDefinition<?> range) {
		Definition<?> definition = null;
		if (config.containsKey(LINK)) {
			// リンクフィールドの場合
			if (range != null) {
				// 親のあるフィールドの場合
				definition = LinkRangeCellDefinitionImpl.newInstance(sheet.getParent(), range, config);
			} else {
				// 単独フィールドの場合
				definition = LinkCellDefinitionImpl.newInstance(sheet.getParent(), sheet, config);
			}
		} else if (config.containsKey(VALUE)) {
			// 値フィールドの場合
			if (range != null) {
				// 親のあるフィールドの場合
				definition = RangeValueDefinitionImpl.newInstance(range, config);
			} else {
				// 単独フィールドの場合
				definition = ValueDefinitionImpl.newInstance(sheet, config);
			}
		} else if (range != null) {
			// 親のあるフィールドの場合
			if (!config.containsKey(ROW) && !config.containsKey(COLUMN)) {
				// アドレスなし
				definition = RangeValueDefinitionImpl.newInstance(range, config);
			} else if (range instanceof RowDefinitionImpl) {
				// 行方向の繰り返し内のフィールドの場合
				definition = RowCellDefinitionImpl.newInstance(range, config);
			} else if (range instanceof ColumnDefinitionImpl) {
				// 列方向の繰り返し内のフィールドの場合
				definition = ColumnCellDefinitionImpl.newInstance(range, config);
			}
		} else if (config.containsKey(COLUMNS)) {
			// 行方向の繰り返しの場合
			List<Map<String, Object>> columns = RoughlyMapUtils.getList(config, COLUMNS);
			RangeDefinition<?> rowRange = RowDefinitionImpl.newInstance(sheet, config);
			rowRange.addChildren(createCells(columns, sheet, rowRange));
			definition = rowRange;
		} else if (config.containsKey(ROWS)) {
			// 列方向の繰り返しの場合
			// TODO 現在未対応
			List<Map<String, Object>> rows = RoughlyMapUtils.getList(config, ROWS);
			RangeDefinition<?> columnRange = ColumnDefinitionImpl.newInstance(sheet, config);
			columnRange.addChildren(createCells(rows, sheet, columnRange));
			definition = columnRange;
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
	 * @param cells
	 * @param sheet
	 * @param range
	 * @return
	 */
	private static List<RangeCellDefinition<?>> createCells(List<Map<String, Object>> cells,
			WorksheetDefinition sheet, RangeDefinition<?> range) {
		List<RangeCellDefinition<?>> definitions = new ArrayList<>();
		for (Map<String, Object> cell : cells) {
			Definition<?> child = createCell(cell, sheet, range);
			if (child instanceof RangeCellDefinition) {
				definitions.add((RangeCellDefinition<?>) child);
			} else {
				throw new DefinitionException("range=" + range.getFullId() + "&illegal child=" + child.getFullId() +
						":rangeの子要素にはcellを定義してください");
			}
		}
		return definitions;
	}

}
