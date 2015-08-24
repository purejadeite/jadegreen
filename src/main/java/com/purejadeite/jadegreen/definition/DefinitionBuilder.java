package com.purejadeite.jadegreen.definition;

import static com.purejadeite.jadegreen.definition.DefinitionKeys.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.jadegreen.definition.cell.CellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.ColumnCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.LinkCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.LinkRangeCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.RowCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.range.ColumnDefinitionImpl;
import com.purejadeite.jadegreen.definition.range.RangeDefinition;
import com.purejadeite.jadegreen.definition.range.RowDefinitionImpl;

/**
 * 定義情報を生成するクラスです
 * @author mitsuhiroseino
 *
 */
public class DefinitionBuilder {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefinitionBuilder.class);

	/**
	 * ファイル(パス指定)から読み込み
	 * @param jsonFilePath JSONファイルパス
	 * @return Book読み込み定義
	 * @throws IOException
	 */
	public static BookDefinitionImpl build(String jsonFilePath) throws IOException {
		File jsonFile = new File(jsonFilePath);
		return build(jsonFile);
	}

	public static BookDefinitionImpl build(File definitionsDirPath, String jsonFileName) throws IOException {
		String json = FileUtils.readFileToString(new File(definitionsDirPath, jsonFileName));
		return buildFromJson(json);
	}

	/**
	 * JSONファイルから読み込み
	 * @param jsonFile JSONファイル
	 * @return Book読み込み定義
	 * @throws IOException
	 */
	public static BookDefinitionImpl build(File jsonFile) throws IOException {
		String json = FileUtils.readFileToString(jsonFile);
		return buildFromJson(json);
	}

	/**
	 * JSON文字列から読み込み
	 * @param json JSON文字列
	 * @return Book読み込み定義
	 * @throws IOException
	 */
	public static BookDefinitionImpl buildFromJson(String json) throws IOException {
		// Mapへのマッピング
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, Object>> definition = null;
		String trimedJson = StringUtils.trim(json);
		if (StringUtils.startsWith(trimedJson, "{") && StringUtils.endsWith(trimedJson, "}")) {
			json = "[" + json + "]";
		}
		definition = mapper.readValue(json, new TypeReference<List<Map<String, Object>>>() {
		});
		return build(definition);
	}

	/**
	 * Mapから読み込み
	 * @param definition JSONから変換されたMAP形式の定義
	 * @return Book読み込み定義
	 */
	public static BookDefinitionImpl build(Map<String, Object> definition) {
		List<Map<String, Object>> def = new ArrayList<>();
		def.add(definition);
		return build(def);
	}

	/**
	 * MapのListから読み込み
	 * @param definition JSONから変換されたMAPのLIST形式の定義
	 * @return Book読み込み定義
	 */
	public static BookDefinitionImpl build(List<Map<String, Object>> definition) {

		// bookのビルド
		BookDefinitionImpl book = new BookDefinitionImpl();
		for (Map<String, Object> sheetDef : definition) {

			// sheetのビルド
			String id = MapUtils.getString(sheetDef, ID);
			String name = MapUtils.getString(sheetDef, NAME);
			boolean stuff = MapUtils.getBooleanValue(sheetDef, NO_OUTPUT);
			SheetDefinitionImpl sheet = new SheetDefinitionImpl(book, id, name, stuff);

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
	 * @param cellDef Cellひとつ分の定義
	 * @param sheet シート読み込み定義
	 * @return Cell読み込み定義
	 */
	private static Definition createCell(Map<String, Object> cellDef, SheetDefinitionImpl sheet) {
		return createCell(cellDef, sheet, null);
	}

	/**
	 * Cell定義の生成
	 * @param cellDef Cellひとつ分の定義
	 * @param sheet シート読み込み定義
	 * @param range 複数Cell定義
	 * @return Cellまたは複数Cell読み込み定義
	 */
	private static Definition createCell(Map<String, Object> cellDef, SheetDefinitionImpl sheet, RangeDefinition range) {
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
		@SuppressWarnings("unchecked")
		Map<String, String> link = MapUtils.getMap(cellDef, LINK);
		@SuppressWarnings("unchecked")
		List<Map<String, String>> converters = (List<Map<String, String>>) cellDef.get(CONVERTERS);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>) cellDef.get(ROWS);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> columns = (List<Map<String, Object>>) cellDef.get(COLUMNS);

		if (link != null) {
			// リンクフィールドの場合
			if (range != null) {
				// 親のあるフィールドの場合
				LOGGER.debug("親のあるリンク:" + id);
				return LinkRangeCellDefinitionImpl.getInstance(sheet.getParent(), range, id, noOutput, converters, link);
			} else {
				// 単独フィールドの場合
				LOGGER.debug("単独のリンク:" + id);
				return LinkCellDefinitionImpl.getInstance((BookDefinitionImpl) sheet.getParent(), sheet, id, noOutput, link);
			}
		} else if (range != null) {
			// 親のあるフィールドの場合
			if (range instanceof RowDefinitionImpl) {
				// 行方向の繰り返し内のフィールドの場合
				LOGGER.debug("行方向の繰り返しの子:" + id);
				return RowCellDefinitionImpl.getInstance(range, id, noOutput, col, converters);
			} else if (range instanceof ColumnDefinitionImpl) {
				// 列方向の繰り返し内のフィールドの場合
				LOGGER.debug("列方向の繰り返しの子:" + id);
				return ColumnCellDefinitionImpl.getInstance(range, id, noOutput, row, converters);
			}
		} else if (columns != null) {
			// 行方向の繰り返しの場合
			LOGGER.debug("行方向の繰り返し:" + id);
			RangeDefinition r =
					RowDefinitionImpl.getInstance(sheet, id, noOutput, beginRow, endRow, endKey, endValue, converters);
			r.addChildren(createCells(columns, sheet, r));
			return r;
		} else if (rows != null) {
			// 列方向の繰り返しの場合
			LOGGER.debug("列方向の繰り返し:" + id);
			RangeDefinition c =
					ColumnDefinitionImpl.getInstance(sheet, id, noOutput, beginCol, endCol, endKey, endValue, converters);
			c.addChildren(createCells(columns, sheet, c));
			return c;
		} else {
			// 単独フィールドの場合
			LOGGER.debug("単独:" + id);
			return CellDefinitionImpl.getInstance(sheet, id, noOutput, row, col, converters);
		}
		return null;
	}

	/**
	 * Cell定義のListの生成
	 * @param cellDefs
	 * @param sheet
	 * @param range
	 * @return
	 */
	private static List<Definition> createCells(List<Map<String, Object>> cellDefs, SheetDefinitionImpl sheet, RangeDefinition range) {
		List<Definition> definitions = new ArrayList<>();
		for (Map<String, Object> cellDef : cellDefs) {
			definitions.add(createCell(cellDef, sheet, range));
		}
		return definitions;
	}

}
