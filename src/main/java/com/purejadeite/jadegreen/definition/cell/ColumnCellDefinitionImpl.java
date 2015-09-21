package com.purejadeite.jadegreen.definition.cell;

import static com.purejadeite.jadegreen.definition.DefinitionKeys.*;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.RoughlyMapUtils;
import com.purejadeite.jadegreen.definition.range.RangeDefinition;

/**
 * 列方向の繰り返しを持つテーブル配下のCell読み込み定義
 *
 * @author mitsuhiroseino
 */
public class ColumnCellDefinitionImpl extends AbstractRangeCellDefinition<RangeDefinition> {

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            テーブル読み込み定義
	 * @param id
	 *            定義ID
	 * @param row
	 *            取得対象行
	 * @param beginCol
	 *            取得開始列
	 * @param endCol
	 *            取得終了列
	 * @param endKey
	 *            終了キー項目フラグ
	 * @param endValue
	 *            終了キー値
	 * @param options
	 *            オプション
	 */
	private ColumnCellDefinitionImpl(RangeDefinition parent, String id, boolean noOutput, int row, int beginCol, int endCol,
			boolean endKey, String endValue, List<Map<String, Object>> options) {
		super(parent, id, noOutput, row, row, beginCol, endCol, endKey, endValue, options);
	}

	public static CellDefinition<RangeDefinition> newInstance(RangeDefinition range, Map<String, Object> config) {
		String id = RoughlyMapUtils.getString(config, ID);
		boolean noOutput = RoughlyMapUtils.getBooleanValue(config, NO_OUTPUT);
		int row = RoughlyMapUtils.getIntValue(config, ROW);
		List<Map<String, Object>> options = RoughlyMapUtils.getList(config, OPTIONS);
		boolean endKey = false;
		String endValue = null;
		if (id.equals(range.getEndKeyId())) {
			// 終了条件
			endKey = true;
			endValue = range.getEndValue();
		}
		return new ColumnCellDefinitionImpl(range, id, noOutput, row, range.getBegin(), range.getEnd(), endKey,
				endValue, options);
	}

}
