package purejadeite.jadegreen.definition.range;

import purejadeite.jadegreen.definition.Definition;
import purejadeite.jadegreen.definition.cell.CellDefinition;
import purejadeite.jadegreen.definition.converter.range.RangeConverter;

import java.util.List;

/**
 * テーブル形式の範囲の情報を保持するクラスのインターフェイスです
 * @author mitsuhiroseino
 */
public interface RangeDefinition extends Definition {

	/**
	 * テーブルの項目を追加します
	 * @param cell Cell読み込み情報
	 */
	public void add(CellDefinition cell);

	/**
	 * 終了条件になる項目の定義IDを取得します
	 * @return 定義ID
	 */
	public String getEndKeyId();

	/**
	 * 終了条件になる項目の定義IDを設定します
	 * @param endKey 定義ID
	 */
	public void setEndKeyId(String endKey);

	/**
	 * 終了条件になる項目の値を取得します
	 * @return 値
	 */
	public String getEndValue();

	/**
	 * 終了条件になる項目の値を設定します
	 * @param endValue 値
	 */
	public void setEndValue(String endValue);

	/**
	 * 開始行/列を取得します
	 * @return 開始行/列
	 */
	public int getBegin();

	/**
	 * 終了行/列を取得します
	 * @return 終了行/列
	 */
	public int getEnd();

	/**
	 * 指定の行番号、列番号が当セルの範囲内か判定します
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean isIncluded(int row, int col);

	/**
	 * コンバーターでラップされたRange読み込み定義を取得します
	 * @return
	 */
	public RangeConverter getConverter();

	/**
	 * 子要素を追加します
	 * @param children 子要素
	 */
	public void addChildren(List<Definition> children);

	public Object convert(Object value);
}
