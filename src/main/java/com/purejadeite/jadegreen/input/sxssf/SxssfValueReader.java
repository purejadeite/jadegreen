package com.purejadeite.jadegreen.input.sxssf;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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

import com.purejadeite.jadegreen.JadegreenException;
import com.purejadeite.util.collection.LazyTable;
import com.purejadeite.util.collection.Table;

/**
 * <pre>
 * ExcelファイルからSxssf方式で値を読み込み、値のみを取得するクラスです。
 * </pre>
 * @author mitsuhiroseino
 */
public class SxssfValueReader {

	/**
	 * ロガー
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SxssfValueReader.class);

	/**
	 * Excelファイルとワークブックの値取得定義を基に、Excelから取得した値をMapに設定し返します。
	 * @param excelFilePath Excelファイルのパス
	 * @param definition ワークブックの読み込み定義
	 * @return Excelの値を設定したMap
	 * @throws IOException ファイルの取得に失敗
	 */
	public static List<Table<String>> read(String excelFilePath) throws IOException {
		File excelFile = new File(excelFilePath);
		return read(excelFile);
	}

	/**
	 * Excelファイルとワークブックの値取得定義を基に、Excelから取得した値をMapに設定し返します。
	 * @param excelFile Excelファイル
	 * @return Excelの値を設定したMap
	 * @throws IOException ファイルの取得に失敗
	 */
	public static List<Table<String>> read(File excelFile) throws IOException {

		if (!excelFile.isFile()) {
			throw new JadegreenException("excelFile=" + excelFile.getPath() + ":ファイルが存在しません");
		}

		try {
			// ファイルを開く
			OPCPackage pkg = OPCPackage.open(excelFile);
			XSSFReader reader = new XSSFReader(pkg);
			// 値の収集
			return correctValues(excelFile.getPath(), reader, correctSheetIds(reader));
		} catch (OpenXML4JException | IOException e) {
			throw new IOException(e);
		}

	}

	/**
	 * 全シートのr:id&シート名を取得します
	 * @param reader リーダー
	 * @return r:idとシート名を紐付けたMap
	 */
	private static Map<String, String> correctSheetIds(XSSFReader reader) {
		// ブックのパーサーを取得
		XMLReader workbookParser = new SAXParser();
		// ハンドラで対象シートのrIdを収集する
		SxssfWorkbookValueHandler workbookHandler = new SxssfWorkbookValueHandler();
		workbookParser.setContentHandler(workbookHandler);

		InputSource workbookSource = null;
		try {
			workbookSource = new InputSource(reader.getWorkbookData());
			// パース
			workbookParser.parse(workbookSource);
		} catch (InvalidFormatException | SAXException | IOException e) {
			throw new JadegreenException(e);
		}
		// ハンドラが収集したrIdを取得
		Map<String, String> worksheetNames = workbookHandler.getSheetNames();
		return worksheetNames;
	}

	// 全シートのセルの値を取得
	/**
	 * 全シートのセルの値を取得します
	 * @param excelFilePath Excelファイルのパス
	 * @param reader リーダー
	 * @param worksheetNames ワークシート名
	 * @return 全シートの値を持つリスト
	 */
	private static List<Table<String>> correctValues(String excelFilePath, XSSFReader reader, Map<String, String> worksheetNames) {
		// 1ファイル分の情報を集めるインスタンス
		List<Table<String>> workbookContent = new ArrayList<>();

		// ブックでシェアしている値を取得する
		SharedStringsTable sst = null;
		try {
			sst = reader.getSharedStringsTable();
		} catch (InvalidFormatException | IOException e) {
			throw new JadegreenException(e);
		}

		// シートのパーサ
		XMLReader worksheetParser = null;
		Table<String> worksheetContent = null;
		SxssfWorksheetValueHandler worksheetHandler =null;

		InputStream worksheetIs = null;
		for (Entry<String, String> entry : worksheetNames.entrySet()) {
			LOGGER.debug("対象Sheet:" + entry.getValue());
			// シートのパーサを取得
			worksheetContent = new LazyTable<>();
			worksheetContent.setOption("bookName", excelFilePath);
			worksheetContent.setOption("sheetName", entry.getValue());
			worksheetHandler = new SxssfWorksheetValueHandler(sst, worksheetContent);
			worksheetParser = new SAXParser();
			worksheetParser.setContentHandler(worksheetHandler);
			try {
				// rIdでシートのInputStreamを取得
				worksheetIs = reader.getSheet(entry.getKey());
				InputSource sheetSource = new InputSource(worksheetIs);
				// シートをパース
				worksheetParser.parse(sheetSource);
				// パース下結果をbookContentへ追加
				workbookContent.add(worksheetContent);
			} catch (InvalidFormatException | SAXException | IOException e) {
				throw new JadegreenException(e);
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
