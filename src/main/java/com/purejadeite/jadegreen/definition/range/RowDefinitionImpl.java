package com.purejadeite.jadegreen.definition.range;

import static com.purejadeite.jadegreen.definition.DefinitionKeys.*;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.RoughlyMapUtils;
import com.purejadeite.jadegreen.definition.WorksheetDefinitionImpl;

/**
 * 行方向の繰り返し範囲の情報を保持するクラスの抽象クラスです
 * @author mitsuhiroseino
 */
public class RowDefinitionImpl extends AbstractRangeDefinition {

	/**
	 * コンストラクタ
	 * @param parent 親の読み込み情報
	 * @param id 定義ID
	 * @param noOutput データの読み込みのみ行うか
	 * @param beginRow 開始行
	 * @param endRow 終了行
	 * @param endKey 開始キー項目
	 * @param endValue 終了キー値
	 * @param options オプション
	 */
	public RowDefinitionImpl(WorksheetDefinitionImpl parent, String id, boolean noOutput, int beginRow, int endRow, String endKey,
			String endValue, List<Map<String, Object>> options) {
		super(parent, id, noOutput, beginRow, endRow, endKey, endValue, options);
	}

	public static RangeDefinition newInstance(WorksheetDefinitionImpl parent, Map<String, Object> config) {
		String id = RoughlyMapUtils.getString(config, ID);
		boolean noOutput = RoughlyMapUtils.getBooleanValue(config, NO_OUTPUT);
		int beginRow = RoughlyMapUtils.getIntValue(config, BEGIN_ROW);
		int endRow = RoughlyMapUtils.getIntValue(config, END_ROW);
		String endKey = RoughlyMapUtils.getString(config, END_KEY);
		String endValue = RoughlyMapUtils.getString(config, END_VALUE);
		List<Map<String, Object>> options = RoughlyMapUtils.getList(config, OPTIONS);
		return new RowDefinitionImpl(parent, id, noOutput, beginRow, endRow, endKey, endValue,
				options);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isIncluded(int row, int col) {
		return (begin <= row && row <= end);
	}
}
