package purejadeite.jadegreen.content.cell;

/**
 * 一覧形式子要素のセル読み込み定義のインターフェイス
 * @author mitsuhiroseino
 */
public interface RangeCellContent extends CellContent {

	/**
	 * 指定件数でクローズします
	 * @param size 有効な取得件数
	 */
	public void close(int size);

	/**
	 * 取得した件数を取得します
	 * @return 取得した値の件数
	 */
	public int size();

}
