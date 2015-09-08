package com.purejadeite.jadegreen.definition;

import static com.purejadeite.jadegreen.definition.DefinitionKeys.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.jadegreen.definition.cell.CellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.ColumnCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.LinkCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.LinkRangeCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.ListCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.RowCellDefinitionImpl;
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
	public static WorkbookDefinitionImpl build(Map<String, Object> definition) {

		// bookのビルド
		WorkbookDefinitionImpl book = new WorkbookDefinitionImpl(definition);
		List<Map<String, Object>> sheets = (List<Map<String, Object>>) definition.get(SHEETS);
		for (Map<String, Object> sheetDef : sheets) {

			// sheetのビルド
			String id = MapUtils.getString(sheetDef, ID);
			String name = MapUtils.getString(sheetDef, NAME);
			boolean noOutput = MapUtils.getBooleanValue(sheetDef, NO_OUTPUT);
			WorksheetDefinitionImpl sheet = new WorksheetDefinitionImpl(book, id, name, noOutput);

			// cellのビルド
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> cellDefs = (List<Map<String, Object>>) sheetDef.get(CELLS);
			for (Map<String, Object> cellDef : cellDefs) {
				sheet.addChild(createCell(cellDef, sheet));
			}
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
	private static Definition createCell(Map<String, Object> cellDef, WorksheetDefinitionImpl sheet) {
		return createCell(cellDef, sheet, null);
	}

	/**
	 * Cell定義の生成
	 *
	 * @param cellDef
	 *            Cellひとつ分の定義
	 * @param sheet
	 *            シート読み込み定義
	 * @param range
	 *            複数Cell定義
	 * @return Cellまたは複数Cell読み込み定義
	 */
	private static Definition createCell(Map<String, Object> cellDef, WorksheetDefinitionImpl sheet,
			RangeDefinition range) {
		String id = MapUtils.getString(cellDef, ID);
		boolean noOutput = MapUtils.getBooleanValue(cellDef, NO_OUTPUT);
		int row = MapUtils.getIntValue(cellDef, ROW);
		int beginRow = MapUtils.getIntValue(cellDef, BEGIN_ROW);
		int endRow = MapUtils.getIntValue(cellDef, END_ROW);
		int col = MapUtils.getIntValue(cellDef, COLUMN);
		int beginCol = MapUtils.getIntValue(cellDef, BEGIN_COLUMN);
		int endCol = MapUtils.getIntValue(cellDef, END_COLUMN);
		String endKey = MapUtils.getString(cellDef, END_KEY);
		String endValue = MapUtils.getString(cellDef, END_VALUE);
		String splitter = MapUtils.getString(cellDef, SPLITTER);
		@SuppressWarnings("unchecked")
		Map<String, String> link = MapUtils.getMap(cellDef, LINK);
		@SuppressWarnings("unchecked")
		List<Map<String, String>> options = (List<Map<String, String>>) cellDef.get(OPTIONS);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>) cellDef.get(ROWS);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> columns = (List<Map<String, Object>>) cellDef.get(COLUMNS);

		Definition definition = null;
		if (link != null) {
			// リンクフィールドの場合
			if (range != null) {
				// 親のあるフィールドの場合
				definition = LinkRangeCellDefinitionImpl.getInstance(sheet.getParent(), range, id, noOutput, options,
						link);
			} else {
				// 単独フィールドの場合
				definition = LinkCellDefinitionImpl.getInstance((WorkbookDefinitionImpl) sheet.getParent(), sheet, id,
						noOutput, link);
			}
		} else if (range != null) {
			// 親のあるフィールドの場合
			if (range instanceof RowDefinitionImpl) {
				// 行方向の繰り返し内のフィールドの場合
				definition = RowCellDefinitionImpl.getInstance(range, id, noOutput, col, options);
			} else if (range instanceof ColumnDefinitionImpl) {
				// 列方向の繰り返し内のフィールドの場合
				definition = ColumnCellDefinitionImpl.getInstance(range, id, noOutput, row, options);
			}
		} else if (columns != null) {
			// 行方向の繰り返しの場合
			RangeDefinition rowDifinition = RowDefinitionImpl.getInstance(sheet, id, noOutput, beginRow, endRow, endKey, endValue,
					options);
			rowDifinition.addChildren(createCells(columns, sheet, rowDifinition));
			definition = rowDifinition;
		} else if (rows != null) {
			// 列方向の繰り返しの場合
			RangeDefinition colDifinition = ColumnDefinitionImpl.getInstance(sheet, id, noOutput, beginCol, endCol, endKey,
					endValue, options);
			colDifinition.addChildren(createCells(columns, sheet, colDifinition));
			definition = colDifinition;
		} else {
			// 単独フィールドの場合
			if (splitter == null) {
				definition = CellDefinitionImpl.getInstance(sheet, id, noOutput, row, col, options);
			} else {
				definition = ListCellDefinitionImpl.getInstance(sheet, id, noOutput, row, col, splitter, options);
			}
		}
		if (definition != null) {
			LOGGER.debug(definition.getClass().getSimpleName() + ":" + definition.getId());
		} else {
			LOGGER.warn("定義が不正です:" + id);
		}
		return definition;
	}

	/**
	 * Cell定義のListの生成
	 *
	 * @param cellDefs
	 * @param sheet
	 * @param range
	 * @return
	 */
	private static List<Definition> createCells(List<Map<String, Object>> cellDefs, WorksheetDefinitionImpl sheet,
			RangeDefinition range) {
		List<Definition> definitions = new ArrayList<>();
		for (Map<String, Object> cellDef : cellDefs) {
			definitions.add(createCell(cellDef, sheet, range));
		}
		return definitions;
	}

}
