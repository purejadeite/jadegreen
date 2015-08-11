package com.purejadeite.jadegreen.definition;

import java.util.List;

/**
 *
 * Excelファイル読み込みの定義情報インターフェイス
 * @author mitsuhiroseino
 *
 */
public interface Definition {

	/**
	 * 定義IDを取得します
	 * @return
	 */
	public String getId();

	/**
	 * ルート辿った定義IDを取得します
	 * @return
	 */
	public String getFullId();

	public boolean isStuff();

	/**
	 * 親定義を取得します
	 * @return
	 */
	public Definition getParent();

	/**
	 * 子定義を取得します
	 * @return
	 */
	public List<? extends Definition> getChildren();

	/**
	 * 子定義を追加します
	 * @param child
	 */
	public void addChild(Definition child);

	/**
	 * 配下の定義を取得します
	 * @param fullId 当定義から辿った定義ID
	 * @return 対象の定義
	 */
	public Definition get(String fullId);

	/**
	 * 配下の定義を取得します
	 * @param ids 当定義から辿った定義IDの配列
	 * @return 対象の定義
	 */
	public Definition get(String... ids);

	/**
	 * 大まかな定義をJSON形式で取得します
	 * @return JSON
	 */
	public String toJson();

}
