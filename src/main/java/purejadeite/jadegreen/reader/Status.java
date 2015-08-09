package purejadeite.jadegreen.reader;

/**
 * エクセルファイル読み込みステータス
 * @author mitsuhiroseino
 *
 */
public enum Status {
	/**
	 * 読み込みなし
	 */
	NO,
	/**
	 * 読み込み成功
	 */
	SUCCESS,
	/**
	 * 読み込み終了
	 */
	END,
	/**
	 * 読み込み失敗
	 */
	FAILURE;
}
