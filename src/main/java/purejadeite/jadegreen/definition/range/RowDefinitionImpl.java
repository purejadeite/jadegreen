package purejadeite.jadegreen.definition.range;

import java.util.List;
import java.util.Map;

import purejadeite.jadegreen.definition.Definition;

/**
 * 行方向の繰り返し範囲の情報を保持するクラスの抽象クラスです
 * @author mitsuhiroseino
 */
public class RowDefinitionImpl extends AbstractRangeDefinition {

	/**
	 * コンストラクタ
	 * @param parent 親の読み込み情報
	 * @param id 定義ID
	 * @param stuff データの読み込みのみ行うか
	 * @param beginRow 開始行
	 * @param endRow 終了行
	 * @param endKey 開始キー項目
	 * @param endValue 終了キー値
	 * @param converters コンバーター
	 */
	public RowDefinitionImpl(Definition parent, String id, boolean stuff, int beginRow, int endRow, String endKey,
			String endValue, List<Map<String, String>> converters) {
		super(parent, id, stuff, beginRow, endRow, endKey, endValue, converters);
	}

	/**
	 * インスタンスを取得します
	 * @param parent 親の読み込み情報
	 * @param id 定義ID
	 * @param stuff データの読み込みのみ行うか
	 * @param beginRow 開始行
	 * @param endRow 終了行
	 * @param endKey 開始キー項目
	 * @param endValue 終了キー値
	 * @param converters コンバーター
	 * @return コンバーターでラップした読み込み定義
	 */
	public static RangeDefinition getInstance(Definition parent, String id, boolean stuff, int beginRow, int endRow,
			String endKey, String endValue, List<Map<String, String>> converters) {
		RowDefinitionImpl range = new RowDefinitionImpl(parent, id, stuff, beginRow, endRow, endKey, endValue,
				converters);
		return range;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isIncluded(int row, int col) {
		return (begin <= row && row <= end);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + super.toString() + "]";
	}
}