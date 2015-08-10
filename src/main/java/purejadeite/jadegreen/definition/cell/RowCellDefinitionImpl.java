package purejadeite.jadegreen.definition.cell;

import purejadeite.jadegreen.definition.Definition;
import purejadeite.jadegreen.definition.range.RangeDefinition;

import java.util.List;
import java.util.Map;

/**
 * 行方向の繰り返しを持つテーブル配下のCell読み込み定義
 * @author mitsuhiroseino
 */
public class RowCellDefinitionImpl extends AbstractRangeCellDefinition {

	private static final long serialVersionUID = 8171650318154820322L;

	/**
	 * コンストラクタ
	 * @param parent テーブル読み込み定義
	 * @param id 定義ID
	 * @param beginRow 取得開始行
	 * @param endRow 取得終了行
	 * @param col 取得対象列
	 * @param endKey 終了キー項目フラグ
	 * @param endValue 終了キー値
	 * @param converters コンバーター
	 */
	private RowCellDefinitionImpl(Definition parent, String id, boolean stuff, int beginRow, int endRow, int col,
			boolean endKey, String endValue, List<Map<String, String>> converters) {
		super(parent, id, stuff, beginRow, endRow, col, col, endKey, endValue, converters);
	}

	/**
	 * インスタンスを取得します
	 * @param range テーブル読み込み定義
	 * @param id 定義ID
	 * @param col 取得対象列
	 * @param converters コンバーター
	 * @return ラップされたCell読み込み定義
	 */
	public static CellDefinition getInstance(RangeDefinition range, String id, boolean stuff, int col, List<Map<String, String>> converters) {
		boolean endKey = false;
		String endValue = null;
		if (id.equals(range.getEndKeyId())) {
			// 終了条件
			endKey = true;
			endValue = range.getEndValue();
		}
		AbstractRangeCellDefinition cell = new RowCellDefinitionImpl(range, id, stuff, range.getBegin(), range.getEnd(), col, endKey, endValue,
				converters);
		return cell;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJson() {
		return "{" + super.toJson() + "}";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + super.toString() + "]";
	}
}
