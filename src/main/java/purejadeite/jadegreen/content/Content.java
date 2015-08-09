package purejadeite.jadegreen.content;

import purejadeite.jadegreen.definition.Definition;

import java.util.List;

/**
 * 値の読み込み処理を
 * @author mitsuhiroseino
 */
public interface Content {

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
	public Definition getDefinition();

	/**
	 * 取得可能な状態か判定します
	 * @return true:取得可能, false:取得不可
	 */
	public boolean isClosed();

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
	 * @param ignore 取得対象外とする子要素
	 * @return 値
	 */
	public Object getRawValues(Definition... ignore);

	/**
	 * 最終的な値を取得します
	 * @param ignore 取得対象外とする子要素
	 * @return 値
	 */
	public Object getValues(Definition... ignore);

	/**
	 * 指定の定義を持ったContentを取得します
	 * @param key
	 * @return
	 */
	public List<Content> searchContents(Definition key);

	/**
	 * 自身より上位にある指定のクラスのContentを取得します
	 * @param contentClazz Contentのクラス
	 * @return Content
	 */
	public Content getUpperContent(Class<? extends Content> contentClazz);

}
