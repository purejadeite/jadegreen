package com.purejadeite.jadegreen.reader;

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
 * Excelファイルから値を読み込み、Mapへマッピングするクラス
 * </pre>
 * @author mitsuhiroseino
 */
public class XssfValueMapper {

	private static final Logger LOGGER = LoggerFactory.getLogger(XssfValueMapper.class);

	public static Map<String, Object> readPrimeSheet(String excelFilePath, String definitionFilePath)
			throws IOException {
		BookDefinitionImpl book = DefinitionBuilder.build(definitionFilePath);
		List<Map<String, Object>> valueSheets = read(excelFilePath, book);
		for (Map<String, Object> valueSheet : valueSheets) {
			if (!MapUtils.getBooleanValue(valueSheet, "stuff")) {
				return valueSheet;
			}
		}
		return null;
	}

	public static List<Map<String, Object>> read(File excelFile, File definitionFile) throws IOException {
		BookDefinitionImpl book = DefinitionBuilder.build(definitionFile);
		return read(excelFile, book);
	}

	public static List<Map<String, Object>> read(String excelFilePath, String definitionFilePath) throws IOException {
		BookDefinitionImpl book = DefinitionBuilder.build(definitionFilePath);
		return read(excelFilePath, book);
	}

	public static List<Map<String, Object>> read(String excelFilePath, BookDefinitionImpl book) throws IOException {
		File excelFile = new File(excelFilePath);
		return read(excelFile, book);
	}

	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> read(File excelFile, BookDefinitionImpl book) {

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
			throw new RuntimeException(e);
		}

		// 1ファイル分の情報を集めるインスタンス
		BookContentImpl bookContent = new BookContentImpl(book, excelFile.getName());
		// ブックのパーサーを取得
		XMLReader bookParser = new SAXParser();
		for (SheetDefinitionImpl sheet : book.getSheets()) {
			// ハンドラで対象シートのrIdを収集する
			BookHandler bookHandler = new BookHandler(sheet.getName());
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
			SheetHandler sheetHandler =null;

			InputStream sheetIs = null;
			for (Entry<String, String> entry : sheetNames.entrySet()) {
				LOGGER.debug("対象Sheet:" + entry.getValue());
				// シートのパーサを取得
				sheetContent = new SheetContentImpl(bookContent, sheet, entry.getValue());
				sheetHandler = new SheetHandler(sst, sheetContent);
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
