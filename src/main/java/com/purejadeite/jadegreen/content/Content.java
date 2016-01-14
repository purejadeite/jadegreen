package com.purejadeite.jadegreen.content;

import java.util.List;

import com.purejadeite.jadegreen.ToMap;
import com.purejadeite.jadegreen.definition.Definition;

/**
 * Excelファイルから取得した値
 * @author mitsuhiroseino
 */
public interface Content<D extends Definition<?>> extends ToMap {

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
	 * @param ignore 取得対象外とする子要素の定義
	 * @return 値
	 */
	public Object getRawValues(Definition<?>... ignore);

	/**
	 * 編集した値を取得します
	 * @param ignore 取得対象外とする子要素の定義
	 * @return 値
	 */
	public Object getValues(Definition<?>... ignore);

	/**
	 * 指定の定義を持ったContentを取得します
	 * @param key 取得する子要素の定義
	 * @return 対象のContent
	 */
	public List<Content<?>> searchContents(Definition<?> key);

	/**
	 * 自身より上位にある指定のクラスのContentを取得します
	 * @param contentClazz Contentのクラス
	 * @return 対象のContent
	 */
	public <C extends Content<?>> C getUpperContent(Class<C> contentClazz);

}
