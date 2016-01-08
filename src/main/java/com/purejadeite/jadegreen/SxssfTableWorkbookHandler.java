package com.purejadeite.jadegreen;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * ワークブック用ハンドラ
 *
 * @author mitsuhiroseino
 */
public class SxssfTableWorkbookHandler extends DefaultHandler {

	/**
	 * シートの開始終了を表わすタグ名
	 */
	private static final String TAG_SHEET = "sheet";

	/**
	 * シート名を表わす属性名
	 */
	private static final String NAME_SHEET_NAME = "name";

	/**
	 * r:ID(シートのID)を表わす属性名
	 */
	private static final String NAME_RID = "r:id";

	/**
	 * 一致したシートの名前
	 */
	private Map<String, String> sheetNames = new HashMap<>();

	/**
	 * コンストラクタ
	 */
	public SxssfTableWorkbookHandler() {
	}

	/**
	 * r:IDとシート名のマッピング情報取得します。
	 *
	 * @return シート名
	 */
	public Map<String, String> getSheetNames() {
		return sheetNames;
	}

	/**
	 * <pre>
	 * 開始タグを取得した際に動作するメソッド
	 * 取得対象のシートの場合は、r:IDとシート名をMapに追加します。
	 * </pre>
	 * {@inheritDoc}
	 */
	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		if (TAG_SHEET.equals(name)) {
			// シートの開始タグならばシート名とr:IDを取得
			String sheetNameTemp = attributes.getValue(NAME_SHEET_NAME);
			String rIdTemp = attributes.getValue(NAME_RID);
			sheetNames.put(rIdTemp, sheetNameTemp);
		}
	}

}
