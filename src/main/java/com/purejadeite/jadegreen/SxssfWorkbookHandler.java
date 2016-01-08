package com.purejadeite.jadegreen;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.purejadeite.util.SimpleComparison;

/**
 * ワークブック用ハンドラ
 *
 * @author mitsuhiroseino
 */
public class SxssfWorkbookHandler extends DefaultHandler {

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
	 * シート名と一致した場合に取得対象とする文字列
	 */
	private String sheetName;

	/**
	 * 一致したシートの名前
	 */
	private Map<String, String> sheetNames = new HashMap<>();

	/**
	 * コンストラクタ
	 *
	 * @param sheetName
	 * <pre>
	 * 取得対象のシート名
	 *   "*": 無条件
	 *   "sheet": 完全一致
	 *   "sheet*": 前方一致
	 *   "*sheet": 後方一致
	 *   "*sheet*": 部分一致
	 *   "/sheet/": 正規表現
	 * </pre>
	 */
	public SxssfWorkbookHandler(String sheetName) {
		this.sheetName = sheetName;
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
			if (SimpleComparison.compare(sheetName, sheetNameTemp)) {
				sheetNames.put(rIdTemp, sheetNameTemp);
			}
		}
	}

}
