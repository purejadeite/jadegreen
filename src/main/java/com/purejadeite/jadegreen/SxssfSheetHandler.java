package com.purejadeite.jadegreen;

import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.purejadeite.jadegreen.content.Content;

/**
 * セルを走査するハンドラ
 * @author mitsuhiroseino
 */
public class SxssfSheetHandler extends DefaultHandler {

	/**
	 * 行のタグ
	 */
	private static final String TAG_ROW = "row";

	/**
	 *
	 */
	private static final String TAG_COL = "c";

	/**
	 *
	 */
	private static final String TAG_VAL = "v";

	/**
	 *
	 */
	private static final String TAG_SHEETDATA = "sheetData";

	/**
	 *
	 */
	private static final String NAME_RNO = "r";

	/**
	 *
	 */
	private static final String NAME_RANGE = "r";

	/**
	 *
	 */
	private static final String NAME_TYPE = "t";

	/**
	 *
	 */
	private static final String TYPE_STRING = "s";

	/**
	 *
	 */
	private final SharedStringsTable sharedStrings;

	/**
	 *
	 */
	private Content sheetContent = null;

	/**
	 *
	 */
	private StringBuilder lastContent;

	/**
	 *
	 */
	private boolean nextIsString;

	/**
	 *
	 */
	private int currentRow = 0;

	/**
	 *
	 */
	private int currentCol = 0;

	/**
	 * コンストラクター
	 * @param sharedStrings ブックで共有されている文字列テーブル
	 * @param sheetContent シートの値
	 */
	public SxssfSheetHandler(SharedStringsTable sharedStrings, Content sheetContent) {
		this.sharedStrings = sharedStrings;
		this.sheetContent = sheetContent;
	}

	/**
	 * 開始タグを取得した際の処理
	 * {@inheritDoc}
	 */
	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {

		// シートがクローズしているならば処理なし
		if (sheetContent.isClosed()) {
			return;
		}

		// コンテンツ初期化
		lastContent = new StringBuilder();

		// 列開始タグの場合
		if (TAG_COL.equals(name)) {
			// 今回の列の前処理
			// rangeを取得
			String range = attributes.getValue(NAME_RANGE);
			String colStr = CellUtils.getColString(range);
			// 今回の列番号を取得
			currentCol = CellUtils.toColIndex(colStr);

			// 値の取得に向けた前処理
			// セルの型取得
			String cellType = attributes.getValue(NAME_TYPE);
			// セルの型が文字列の場合はフラグを立てる
			if (TYPE_STRING.equals(cellType)) {
				nextIsString = true;
			} else {
				nextIsString = false;
			}
		}

		// 行開始の場合
		if (TAG_ROW.equals(name)) {
			// 今回の行の前処理
			// 行番号
			currentRow = Integer.parseInt(attributes.getValue(NAME_RNO));
			// 列番号クリア
			currentCol = 0;
		}

		return;
	}

	/**
	 * 終了タグを取得した際の処理
	 * {@inheritDoc}
	 */
	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		// クローズしているならば終わり
		if (sheetContent.isClosed()) {
			return;
		}

		// 文字列の場合は、今持っているlastContentsはSharedStringsTableのインデックスなので、文字列に置き換え。
		if (nextIsString) {
			// 直前の値を数値(参照先のインデックス)に変換
			int idx = Integer.parseInt(lastContent.toString());
			// 文字列の場合はSharedStringsTableから取得
			lastContent = new StringBuilder();
			lastContent.append(new XSSFRichTextString(sharedStrings.getEntryAt(idx)).toString());
			// 文字列フラグをクリア
			nextIsString = false;
		}

		// 値の終了タグの場合
		if (TAG_VAL.equals(name)) {
			// シートへ値を追加
			sheetContent.addValue(currentRow, currentCol, lastContent.toString());
		}

		// シートデータの場合
		if (TAG_SHEETDATA.equals(name)) {
			// コンテンツを閉じる
			sheetContent.close();
		}

		return;
	}

	/**
	 * 要素の値を保持するためのメソッド
	 * {@inheritDoc}
	 */
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// シートがクローズしているならば処理なし
		if (sheetContent.isClosed()) {
			return;
		}
		// 現在の値を取得する
		lastContent.append(new String(ch, start, length));
		return;
	}

}