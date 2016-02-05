package com.purejadeite.jadegreen.content;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.Definition;

/**
 * Excelファイルから取得した値
 * @author mitsuhiroseino
 */
public interface Content<P extends Content<?, ?>, D extends Definition<?>> {

	public List<String> getKey();

	public String getUuid();

	/**
	 * 定義IDを取得します
	 * @return 定義ID
	 */
	public String getId();

	/**
	 * 完全な定義IDを取得します
	 * @return 完全な定義ID
	 */
	public String getFullId();

	/**
	 * 定義を取得します
	 * @return 定義
	 */
	public D getDefinition();

	/**
	 * 取得可能な状態か判定します
	 * @return true:取得可能, false:取得不可
	 */
	public boolean isClosed();

	/**
	 * 取得が完了しているか判定します
	 * @return true:取得完了, false:未取得
	 */
	public boolean isAquired();

	/**
	 * 値の取得を終了します
	 */
	public void close();

	/**
	 * 値を追加します
	 * @param row 行番号
	 * @param col 列番号
	 * @param value 値
	 * @return 取得状況
	 */
	public Status addValue(int row, int col, Object value);

	/**
	 * 編集していない値を取得します
	 * @return 値
	 */
	public Object getRawValues();

	/**
	 * 編集した値を取得します
	 * @return 値
	 */
	public Object getValues();

	/**
	 * コンテンツをMap形式で取得します
	 * @return コンテンツ
	 */
	public Map<String,Object> toMap();

}
