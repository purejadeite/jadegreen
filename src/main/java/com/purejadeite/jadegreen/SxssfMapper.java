package com.purejadeite.jadegreen;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.jadegreen.content.BookContent;
import com.purejadeite.jadegreen.content.ContentManager;
import com.purejadeite.jadegreen.content.SheetContent;
import com.purejadeite.jadegreen.definition.BookDefinition;
import com.purejadeite.jadegreen.definition.DefinitionBuilder;
import com.purejadeite.jadegreen.definition.DefinitionManager;
import com.purejadeite.jadegreen.definition.SheetDefinition;
import com.purejadeite.util.collection.Table;

/**
 * <pre>
 * ExcelファイルからSxssf方式で値を読み込み、Mapへマッピングするクラスです。
 * </pre>
 * @author mitsuhiroseino
 */
public class SxssfMapper {

	/**
	 * ロガー
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SxssfMapper.class);

	/**
	 * Excelファイルとワークブックの値取得定義を基に、Excelから取得した値をMapに設定し返します。
	 * @param excelFilePath Excelファイルのパス
	 * @param definitionObj ワークブックの値取得定義
	 * @return Excelの値を設定したMap
	 * @throws IOException ファイルの取得に失敗
	 */
	public static Object read(String excelFilePath, Map<String, Object> definitionObj) throws IOException {
		DefinitionManager.getInstance().init();
		BookDefinition definition = DefinitionBuilder.build(definitionObj);
		return read(excelFilePath, definition);
	}

	/**
	 * Excelファイルとワークブックの値取得定義を基に、Excelから取得した値をMapに設定し返します。
	 * @param excelFile Excelファイル
	 * @param definitionObj ワークブックの値取得定義
	 * @return Excelの値を設定したMap
	 * @throws IOException ファイルの取得に失敗
	 */
	public static Object read(File excelFile, Map<String, Object> definitionObj) throws IOException {
		DefinitionManager.getInstance().init();
		BookDefinition definition = DefinitionBuilder.build(definitionObj);
		return read(excelFile, definition);
	}

	/**
	 * Excelファイルとワークブックの値取得定義を基に、Excelから取得した値をMapに設定し返します。
	 * @param excelFilePath Excelファイルのパス
	 * @param definition ワークブックの読み込み定義
	 * @return Excelの値を設定したMap
	 * @throws IOException ファイルの取得に失敗
	 */
	public static Object read(String excelFilePath, BookDefinition definition) throws IOException {
		File excelFile = new File(excelFilePath);
		return read(excelFile, definition);
	}

	/**
	 * Excelファイルとワークブックの値取得定義を基に、Excelから取得した値をMapに設定し返します。
	 * @param excelFile Excelファイル
	 * @param workbookDefinition ワークブックの読み込み定義
	 * @return Excelの値を設定したMap
	 * @throws IOException ファイルの取得に失敗
	 */
	public static Object read(File excelFile, BookDefinition workbookDefinition) throws IOException {
		ContentManager.getInstance().init();
		List<Sheet> worksheets = SxssfTableMapper.read(excelFile);
		return read(worksheets, workbookDefinition);
	}

	/**
	 * Excelファイルとワークブックの値取得定義を基に、Excelから取得した値をMapに設定し返します。
	 * @param excelFiles Excelファイル配列
	 * @param workbookDefinition ワークブックの読み込み定義
	 * @return Excelの値を設定したMap
	 * @throws IOException ファイルの取得に失敗
	 */
	public static Object read(File[] excelFiles, BookDefinition workbookDefinition) throws IOException {
		ContentManager.getInstance().init();
		List<Sheet> worksheets =  new ArrayList<>();
		for (File excelFile : excelFiles) {
			worksheets.addAll(SxssfTableMapper.read(excelFile));
		}
		return read(worksheets, workbookDefinition);
	}

	@SuppressWarnings("unchecked")
	public static Object read(List<Sheet> sheets, BookDefinition bookDefinition) throws IOException {
		BookContent bookContent = new BookContent(bookDefinition);
		for (SheetDefinition sheetDefinition : bookDefinition.getChildren()) {
			for (Sheet sheet: sheets) {
				String name = sheet.getName();
				if (sheetDefinition.match(name, sheet)) {
					LOGGER.debug("[取得] sheet:" + name + ", type:" + sheetDefinition.getId());
					SheetContent sheetContent;
					sheetContent = toSheetContent(name, sheet, bookContent, sheetDefinition);
					bookContent.addSheet(sheetContent);
				}
			}
		}
		return bookContent.getValues();
	}

	// tableの値をWorksheetContentにマッピングします
	public static SheetContent toSheetContent(String sheetName, Table<String> table, BookContent book, SheetDefinition definition) {
		SheetContent sheet = null;
		if (definition.isUnion()) {
			// 複数のシートの内容を1シートに集約する場合
			List<SheetContent> lastSheets = ContentManager.getInstance().getSheets(definition);
			if (lastSheets == null || lastSheets.isEmpty()) {
				// 対象のシートがまだ無いならば新規作成
				sheet = new SheetContent(book, definition, sheetName);
			} else {
				// 既にあるならばそれを使う
				sheet = lastSheets.iterator().next();
				sheet.open();
			}
		} else {
			sheet = new SheetContent(book, definition, sheetName);
		}
		List<List<String>> rows = table.getAdjustedTable(definition.getMaxRow(), definition.getMaxCol());
		int rowSize = rows.size();
		for (int rowIndex = 0; rowIndex < rowSize; rowIndex++) {
			List<String> row = rows.get(rowIndex);
			int colSize = row.size();
			for (int colIndex = 0; colIndex < colSize; colIndex++) {
				String value = row.get(colIndex);
				sheet.addValue(rowIndex + 1, colIndex + 1, value);
			}
		}
		sheet.close();
		return sheet;
	}
}
