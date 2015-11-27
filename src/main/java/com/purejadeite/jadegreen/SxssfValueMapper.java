package com.purejadeite.jadegreen;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.xerces.parsers.SAXParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.purejadeite.jadegreen.content.WorkbookContent;
import com.purejadeite.jadegreen.content.WorksheetContent;
import com.purejadeite.jadegreen.definition.DefinitionBuilder;
import com.purejadeite.jadegreen.definition.WorkbookDefinition;
import com.purejadeite.jadegreen.definition.WorksheetDefinition;

/**
 * <pre>
 * ExcelファイルからSxssf方式で値を読み込み、Mapへマッピングするクラスです。
 * </pre>
 * @author mitsuhiroseino
 */
public class SxssfValueMapper {

	/**
	 * ロガー
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SxssfValueMapper.class);

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

		OPCPackage pkg = null;
		XSSFReader reader = null;
		SharedStringsTable sst = null;
		if (!excelFile.isFile()) {
			throw new MappingException("excelFile=" + excelFile.getPath() + ":ファイルが存在しません");
		}
		try {
			// ファイルを開く
			pkg = OPCPackage.open(excelFile);
			reader = new XSSFReader(pkg);
			// ブックでシェアしている値を取得する
			sst = reader.getSharedStringsTable();
		} catch (OpenXML4JException | IOException e) {
			throw new IOException(e);
		}

		// 1ファイル分の情報を集めるインスタンス
		WorkbookContent workbookContent = new WorkbookContent(workbookDefinition, excelFile.getName());
		// ブックのパーサーを取得
		XMLReader workbookParser = new SAXParser();
		for (WorksheetDefinition worksheet : workbookDefinition.getChildren()) {
			// ハンドラで対象シートのrIdを収集する
			SxssfWorkbookHandler workbookHandler = new SxssfWorkbookHandler(worksheet.getName());
			workbookParser.setContentHandler(workbookHandler);

			InputSource workbookSource = null;
			try {
				workbookSource = new InputSource(reader.getWorkbookData());
				// パース
				workbookParser.parse(workbookSource);
			} catch (InvalidFormatException | SAXException | IOException e) {
				throw new RuntimeException(e);
			}
			// ハンドラが収集したrIdを取得
			Map<String, String> worksheetNames = workbookHandler.getSheetNames();
			if (worksheetNames.isEmpty()) {
				// 対象シートなし
				LOGGER.debug("対象なし:" + worksheet.getName());
				continue;
			}

			// シートのパーサ
			XMLReader worksheetParser = null;
			WorksheetContent worksheetContent = null;
			SxssfWorksheetHandler worksheetHandler =null;

			InputStream worksheetIs = null;
			for (Entry<String, String> entry : worksheetNames.entrySet()) {
				LOGGER.debug("対象Sheet:" + entry.getValue());
				// シートのパーサを取得
				worksheetContent = new WorksheetContent(workbookContent, worksheet, entry.getValue());
				worksheetHandler = new SxssfWorksheetHandler(sst, worksheetContent);
				worksheetParser = new SAXParser();
				worksheetParser.setContentHandler(worksheetHandler);
				try {
					// rIdでシートのInputStreamを取得
					worksheetIs = reader.getSheet(entry.getKey());
					InputSource sheetSource = new InputSource(worksheetIs);
					// シートをパース
					worksheetParser.parse(sheetSource);
					// パース下結果をbookContentへ追加
					workbookContent.addSheet(worksheetContent);
				} catch (InvalidFormatException | SAXException | IOException e) {
					throw new RuntimeException(e);
				} finally {
					try {
						worksheetIs.close();
					} catch (IOException e) {
						// クローズできない場合は無視
					}
				}
			}
		}
		return (List<Map<String, Object>>) workbookContent.getValues();
	}

}
