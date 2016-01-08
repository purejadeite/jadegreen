package com.purejadeite.jadegreen;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.jadegreen.content.WorkbookContent;
import com.purejadeite.jadegreen.content.WorksheetContent;
import com.purejadeite.jadegreen.definition.DefinitionBuilder;
import com.purejadeite.jadegreen.definition.WorkbookDefinition;
import com.purejadeite.jadegreen.definition.WorksheetDefinition;
import com.purejadeite.util.Table;

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
	public static List<Map<String, Object>> read(String excelFilePath, Map<String, Object> definitionObj) throws IOException {
		WorkbookDefinition definition = DefinitionBuilder.build(definitionObj);
		return read(excelFilePath, definition);
	}

	/**
	 * Excelファイルとワークブックの値取得定義を基に、Excelから取得した値をMapに設定し返します。
	 * @param excelFilePath Excelファイルのパス
	 * @param definition ワークブックの読み込み定義
	 * @return Excelの値を設定したMap
	 * @throws IOException ファイルの取得に失敗
	 */
	public static List<Map<String, Object>> read(String excelFilePath, WorkbookDefinition definition) throws IOException {
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
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> read(File excelFile, WorkbookDefinition workbookDefinition) throws IOException {
		WorkbookContent workbookContent = new WorkbookContent(workbookDefinition, excelFile.getName());
		Map<String, Table<String>> tables = SxssfTableMapper.read(excelFile);
		for (WorksheetDefinition worksheet : workbookDefinition.getChildren()) {
			for (String name: tables.keySet()) {
				Table<String> table = tables.get(name);
				if (worksheet.match(name, table)) {
					WorksheetContent sheet = toSheetContent(name, table, workbookContent, worksheet);
					workbookContent.addSheet(sheet);
				}
			}
		}
		return (List<Map<String, Object>>) workbookContent.getValues();
	}

	public static WorksheetContent toSheetContent(String sheetName, Table<String> table, WorkbookContent book, WorksheetDefinition definition) {
		WorksheetContent sheet = new WorksheetContent(book, definition, sheetName);
		List<List<String>> rows = table.getAdjustedTable();
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