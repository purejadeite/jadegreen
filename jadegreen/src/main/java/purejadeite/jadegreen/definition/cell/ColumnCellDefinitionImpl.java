package purejadeite.jadegreen.definition.cell;

import purejadeite.jadegreen.definition.Definition;
import purejadeite.jadegreen.definition.range.RangeDefinition;

import java.util.List;
import java.util.Map;

/**
 * 列方向の繰り返しを持つテーブル配下のCell読み込み定義
 * @author mitsuhiroseino
 */
public class ColumnCellDefinitionImpl extends AbstractRangeCellDefinition {

	/**
	 * コンストラクタ
	 * @param parent テーブル読み込み定義
	 * @param id 定義ID
	 * @param row 取得対象行
	 * @param beginCol 取得開始列
	 * @param endCol 取得終了列
	 * @param endKey 終了キー項目フラグ
	 * @param endValue 終了キー値
	 * @param converters コンバーター
	 */
	private ColumnCellDefinitionImpl(Definition parent, String id, boolean stuff, int row, int beginCol, int endCol,
			boolean endKey, String endValue, List<Map<String, String>> converters) {
		super(parent, id, stuff, row, row, beginCol, endCol, endKey, endValue, converters);
	}

	/**
	 * インスタンスを取得します
	 * @param range テーブル読み込み定義
	 * @param id 定義ID
	 * @param row 取得対象行
	 * @param converters コンバーター
	 * @return ラップされたCell読み込み定義
	 */
	public static CellDefinition getInstance(RangeDefinition range, String id, boolean stuff, int row, List<Map<String, String>> converters) {
		boolean endKey = false;
		String endValue = null;
		if (id.equals(range.getEndKeyId())) {
			// 終了条件
			endKey = true;
			endValue = range.getEndValue();
		}
		AbstractRangeCellDefinition cell = new ColumnCellDefinitionImpl(range, id, stuff, row, range.getBegin(), range.getEnd(),
				endKey, endValue,
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
