package com.purejadeite.jadegreen;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.xerces.impl.xpath.regex.RegularExpression;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

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
	private String matchSheetName;

	/**
	 * シート名と一致した場合に取得対象とする正規表現
	 */
	private RegularExpression reqex;

	/**
	 * 一致タイプ
	 */
	private MatchType type;

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
	 *   "sheet": 完全一致
	 *   "sheet*": 前方一致
	 *   "*sheet": 後方一致
	 *   "*sheet*": 部分一致
	 *   "/sheet/": 正規表現
	 * </pre>
	 */
	public SxssfWorkbookHandler(String sheetName) {
		String str = sheetName;
		MatchType type = MatchType.EQUALS;

		if (StringUtils.endsWith(str, "*")) {
			str = StringUtils.substring(str, 0, str.length() - 1);
			// 前方一致
			type = MatchType.START_WITH;
		}

		if (StringUtils.startsWith(str, "*")) {
			str = StringUtils.substring(str, 1);
			if (type == MatchType.EQUALS) {
				// 後方一致
				type = MatchType.END_WITH;
			} else {
				// 部分一致
				type = MatchType.LIKE;
			}
		}

		if (StringUtils.startsWith(str, "/") && StringUtils.endsWith(str, "/")) {
			str = StringUtils.substring(str, 1, str.length() - 1);
			reqex = new RegularExpression(str);
			// 正規表現
			type = MatchType.REGEX;
		}
		this.matchSheetName = str;
		this.type = type;
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
			if (isTarget(sheetNameTemp)) {
				sheetNames.put(rIdTemp, sheetNameTemp);
			}
		}
	}

	/**
	 * 取得対象のシートか判定します。
	 *
	 * @param name シート名
	 * @return true:取得対象,false:取得対象外
	 */
	private boolean isTarget(String name) {
		if (type == MatchType.START_WITH) {
			return StringUtils.startsWith(name, this.matchSheetName);
		} else if (type == MatchType.END_WITH) {
			return StringUtils.endsWith(name, this.matchSheetName);
		} else if (type == MatchType.LIKE) {
			return StringUtils.contains(name, this.matchSheetName);
		} else if (type == MatchType.REGEX) {
			return this.reqex.matches(name);
		}
		return StringUtils.equals(name, this.matchSheetName);

	}

}
