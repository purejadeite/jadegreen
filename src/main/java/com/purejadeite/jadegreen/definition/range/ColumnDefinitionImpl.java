package com.purejadeite.jadegreen.definition.range;

import static com.purejadeite.jadegreen.definition.DefinitionKeys.*;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.WorksheetDefinition;
import com.purejadeite.jadegreen.definition.cell.RangeCellDefinition;
import com.purejadeite.util.RoughlyMapUtils;

/**
 * 列方向の繰り返し範囲の情報を保持するクラスの抽象クラスです
 * @author mitsuhiroseino
 */
public class ColumnDefinitionImpl extends AbstractRangeDefinition<RangeCellDefinition<?>> {

	private static final long serialVersionUID = -1028109893285661849L;

	/**
	 * コンストラクタ
	 * @param parent 親の読み込み情報
	 * @param id 定義ID
	 * @param noOutput データの読み込みのみ行うか
	 * @param beginCol 開始列
	 * @param endCol 終了列
	 * @param endKey 開始キー項目
	 * @param endValue 終了キー値
	 * @param options オプション
	 */
	public ColumnDefinitionImpl(WorksheetDefinition parent, String id, boolean noOutput, int beginCol, int endCol, String endKey,
			String endValue,
			List<Map<String, Object>> options) {
		super(parent, id, noOutput, beginCol, endCol, endKey, endValue, options);
	}

	public static RangeDefinition<?> newInstance(WorksheetDefinition parent, Map<String, Object> config) {
		String id = RoughlyMapUtils.getString(config, ID);
		boolean noOutput = RoughlyMapUtils.getBooleanValue(config, NO_OUTPUT);
		int beginCol = RoughlyMapUtils.getIntValue(config, BEGIN_COLUMN);
		int endCol = RoughlyMapUtils.getIntValue(config, END_COLUMN);
		String endKey = RoughlyMapUtils.getString(config, END_KEY);
		String endValue = RoughlyMapUtils.getString(config, END_VALUE);
		List<Map<String, Object>> options = RoughlyMapUtils.getList(config, OPTIONS);
		return new ColumnDefinitionImpl(parent, id, noOutput, beginCol, endCol, endKey, endValue,
				options);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isIncluded(int row, int col) {
		return (begin <= col && col <= end);
	}

}
