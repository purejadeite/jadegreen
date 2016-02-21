package com.purejadeite.jadegreen.input.sxssf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.Jadegreen;
import com.purejadeite.jadegreen.definition.BookDefinition;
import com.purejadeite.jadegreen.definition.DefinitionBuilder;
import com.purejadeite.util.collection.Table;

/**
 * <pre>
 * ExcelファイルからSxssf方式で値を読み込み、Mapへマッピングするクラスです。
 * </pre>
 * @author mitsuhiroseino
 */
public class SxssfMapper {

	/**
	 * Excelファイルとワークブックの値取得定義を基に、Excelから取得した値をMapに設定し返します。
	 * @param excelFilePath Excelファイルのパス
	 * @param definitionObj ワークブックの値取得定義
	 * @return Excelの値を設定したMap
	 * @throws IOException ファイルの取得に失敗
	 */
	public static Object read(String excelFilePath, Map<String, Object> definitionObj) throws IOException {
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
		List<Table<String>> worksheets = SxssfValueReader.read(excelFile);
		Jadegreen mapper = new Jadegreen();
		return mapper.map(worksheets, workbookDefinition);
	}

	/**
	 * Excelファイルとワークブックの値取得定義を基に、Excelから取得した値をMapに設定し返します。
	 * @param excelFiles Excelファイル配列
	 * @param workbookDefinition ワークブックの読み込み定義
	 * @return Excelの値を設定したMap
	 * @throws IOException ファイルの取得に失敗
	 */
	public static Object read(File[] excelFiles, BookDefinition workbookDefinition) throws IOException {
		List<Table<String>> worksheets =  new ArrayList<>();
		for (File excelFile : excelFiles) {
			worksheets.addAll(SxssfValueReader.read(excelFile));
		}
		Jadegreen mapper = new Jadegreen();
		return mapper.map(worksheets, workbookDefinition);
	}

}
