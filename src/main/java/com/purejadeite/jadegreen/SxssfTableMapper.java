package com.purejadeite.jadegreen;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
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

import com.purejadeite.util.collection.LazyTable;
import com.purejadeite.util.collection.Table;

/**
 * <pre>
 * ExcelファイルからSxssf方式で値を読み込み、Mapへマッピングするクラスです。
 * </pre>
 * @author mitsuhiroseino
 */
public class SxssfTableMapper {

	/**
	 * ロガー
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SxssfTableMapper.class);

	/**
	 * Excelファイルとワークブックの値取得定義を基に、Excelから取得した値をMapに設定し返します。
	 * @param excelFilePath Excelファイルのパス
	 * @param definition ワークブックの読み込み定義
	 * @return Excelの値を設定したMap
	 * @throws IOException ファイルの取得に失敗
	 */
	public static Map<String, Table<String>> read(String excelFilePath) throws IOException {
		File excelFile = new File(excelFilePath);
		return read(excelFile);
	}

	/**
	 * Excelファイルとワークブックの値取得定義を基に、Excelから取得した値をMapに設定し返します。
	 * @param excelFile Excelファイル
	 * @return Excelの値を設定したMap
	 * @throws IOException ファイルの取得に失敗
	 */
	public static Map<String, Table<String>> read(File excelFile) throws IOException {

		if (!excelFile.isFile()) {
			throw new JadegreenException("excelFile=" + excelFile.getPath() + ":ファイルが存在しません");
		}

		try {
			// ファイルを開く
			OPCPackage pkg = OPCPackage.open(excelFile);
			XSSFReader reader = new XSSFReader(pkg);
			// 値の収集
			return correctValues(reader, correctSheetIds(reader));
		} catch (OpenXML4JException | IOException e) {
			throw new IOException(e);
		}

	}

	// 全シートのr:id&シート名を取得
	private static Map<String, String> correctSheetIds(XSSFReader reader) throws IOException {
		// ブックのパーサーを取得
		XMLReader workbookParser = new SAXParser();
		// ハンドラで対象シートのrIdを収集する
		SxssfTableWorkbookHandler workbookHandler = new SxssfTableWorkbookHandler();
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
		return worksheetNames;
	}

	// 全シートのセルの値を取得
	private static Map<String, Table<String>> correctValues(XSSFReader reader, Map<String, String> worksheetNames) throws InvalidFormatException, IOException {
		// 1ファイル分の情報を集めるインスタンス
		Map<String, Table<String>> workbookContent = new HashMap<>();

		// ブックでシェアしている値を取得する
		SharedStringsTable sst = reader.getSharedStringsTable();

		// シートのパーサ
		XMLReader worksheetParser = null;
		Table<String> worksheetContent = null;
		SxssfTableWorksheetHandler worksheetHandler =null;

		InputStream worksheetIs = null;
		for (Entry<String, String> entry : worksheetNames.entrySet()) {
			LOGGER.debug("対象Sheet:" + entry.getValue());
			// シートのパーサを取得
			worksheetContent = new LazyTable<>();
			worksheetHandler = new SxssfTableWorksheetHandler(sst, worksheetContent);
			worksheetParser = new SAXParser();
			worksheetParser.setContentHandler(worksheetHandler);
			try {
				// rIdでシートのInputStreamを取得
				worksheetIs = reader.getSheet(entry.getKey());
				InputSource sheetSource = new InputSource(worksheetIs);
				// シートをパース
				worksheetParser.parse(sheetSource);
				// パース下結果をbookContentへ追加
				workbookContent.put(entry.getValue(), worksheetContent);
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
		return workbookContent;
	}

}
