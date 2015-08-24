package com.purejadeite.jadegreen;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
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

import com.purejadeite.jadegreen.content.BookContentImpl;
import com.purejadeite.jadegreen.content.Content;
import com.purejadeite.jadegreen.content.SheetContentImpl;
import com.purejadeite.jadegreen.definition.BookDefinitionImpl;
import com.purejadeite.jadegreen.definition.DefinitionBuilder;
import com.purejadeite.jadegreen.definition.SheetDefinitionImpl;

/**
 * <pre>
 * ExcelファイルからSxssf方式で値を読み込み、Mapへマッピングするクラスです。
 * </pre>
 * @author mitsuhiroseino
 */
public class SxssfValueMapper {

	private static final Logger LOGGER = LoggerFactory.getLogger(SxssfValueMapper.class);

	/**
	 * Excelファイルとワークブックの値取得定義を基に、Excelの値を設定したMapを出力対象の中から1シートのみ返します。
	 * @param excelFilePath Excelファイルのパス
	 * @param definitionFilePath ワークブックの値取得定義ファイルのパス
	 * @return Excelの値を設定したMap
	 * @throws IOException ファイルの取得に失敗
	 */
	public static Map<String, Object> readPrimeSheet(String excelFilePath, String definitionFilePath)
			throws IOException {
		BookDefinitionImpl book = DefinitionBuilder.build(definitionFilePath);
		List<Map<String, Object>> valueSheets = read(excelFilePath, book);
		for (Map<String, Object> valueSheet : valueSheets) {
			if (!MapUtils.getBooleanValue(valueSheet, "noOutput")) {
				return valueSheet;
			}
		}
		return null;
	}

	/**
	 * Excelファイルとワークブックの値取得定義を基に、Excelの値を設定したMapを返します。
	 * @param excelFile Excelファイル
	 * @param definitionFile ワークブックの値取得定義ファイル
	 * @return Excelの値を設定したMap
	 * @throws IOException ファイルの取得に失敗
	 */
	public static List<Map<String, Object>> read(File excelFile, File definitionFile) throws IOException {
		BookDefinitionImpl book = DefinitionBuilder.build(definitionFile);
		return read(excelFile, book);
	}

	/**
	 * Excelファイルとワークブックの値取得定義を基に、Excelの値を設定したMapを返します。
	 * @param excelFilePath Excelファイルのパス
	 * @param definitionFilePath ワークブックの値取得定義ファイルのパス
	 * @return Excelの値を設定したMap
	 * @throws IOException ファイルの取得に失敗
	 */
	public static List<Map<String, Object>> read(String excelFilePath, String definitionFilePath) throws IOException {
		BookDefinitionImpl book = DefinitionBuilder.build(definitionFilePath);
		return read(excelFilePath, book);
	}

	/**
	 * Excelファイルとワークブックの値取得定義を基に、Excelの値を設定したMapを返します。
	 * @param excelFilePath Excelファイルのパス
	 * @param book ワークブックの読み込み定義
	 * @return Excelの値を設定したMap
	 * @throws IOException ファイルの取得に失敗
	 */
	public static List<Map<String, Object>> read(String excelFilePath, BookDefinitionImpl book) throws IOException {
		File excelFile = new File(excelFilePath);
		return read(excelFile, book);
	}

	/**
	 * Excelファイルとワークブックの値取得定義を基に、Excelの値を設定したMapを返します。
	 * @param excelFile Excelファイル
	 * @param book ワークブックの読み込み定義
	 * @return Excelの値を設定したMap
	 * @throws IOException ファイルの取得に失敗
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> read(File excelFile, BookDefinitionImpl book) throws IOException {

		OPCPackage pkg = null;
		XSSFReader reader = null;
		SharedStringsTable sst = null;
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
		BookContentImpl bookContent = new BookContentImpl(book, excelFile.getName());
		// ブックのパーサーを取得
		XMLReader bookParser = new SAXParser();
		for (SheetDefinitionImpl sheet : book.getSheets()) {
			// ハンドラで対象シートのrIdを収集する
			SxssfBookHandler bookHandler = new SxssfBookHandler(sheet.getName());
			bookParser.setContentHandler(bookHandler);

			InputSource bookSource = null;
			try {
				bookSource = new InputSource(reader.getWorkbookData());
				// パース
				bookParser.parse(bookSource);
			} catch (InvalidFormatException | SAXException | IOException e) {
				throw new RuntimeException(e);
			}
			// ハンドラが収集したrIdを取得
			Map<String, String> sheetNames = bookHandler.getSheetNames();
			if (sheetNames.isEmpty()) {
				// 対象シートなし
				LOGGER.debug("対象なし:" + sheet.getName());
				continue;
			}

			// シートのパーサ
			XMLReader sheetParser = null;
			Content sheetContent = null;
			SxssfSheetHandler sheetHandler =null;

			InputStream sheetIs = null;
			for (Entry<String, String> entry : sheetNames.entrySet()) {
				LOGGER.debug("対象Sheet:" + entry.getValue());
				// シートのパーサを取得
				sheetContent = new SheetContentImpl(bookContent, sheet, entry.getValue());
				sheetHandler = new SxssfSheetHandler(sst, sheetContent);
				sheetParser = new SAXParser();
				sheetParser.setContentHandler(sheetHandler);
				try {
					// rIdでシートのInputStreamを取得
					sheetIs = reader.getSheet(entry.getKey());
					InputSource sheetSource = new InputSource(sheetIs);
					// シートをパース
					sheetParser.parse(sheetSource);
					// パース下結果をbookContentへ追加
					bookContent.addContent(sheetContent);
				} catch (InvalidFormatException | SAXException | IOException e) {
					throw new RuntimeException(e);
				} finally {
					try {
						sheetIs.close();
					} catch (IOException e) {
						// クローズできない場合は無視
					}
				}
			}
		}
		LOGGER.debug("\r\n\r\n" + bookContent.toJson() + "\r\n\r\n");
		return (List<Map<String, Object>>) bookContent.getValues();
	}

}
