package com.purejadeite.jadegreen.definition.cell;

import static com.purejadeite.jadegreen.definition.DefinitionKeys.*;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.RoughlyMapUtils;
import com.purejadeite.jadegreen.definition.range.RangeDefinition;

/**
 * 固定値の定義です
 *
 * @author mitsuhiroseino
 */
public class RangeValueDefinitionImpl<P extends RangeDefinition<?>> extends AbstractNoAdressRangeCellDefinition<P> {

	private String value;

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            シート読み込み定義
	 * @param id
	 *            定義ID
	 * @param noOutput
	 *            出力可否
	 * @param row
	 *            取得対象行
	 * @param col
	 *            取得対象列
	 * @param options
	 *            コンバーター
	 */
	private RangeValueDefinitionImpl(P parent, String id, boolean noOutput, String value,
			List<Map<String, Object>> options) {
		super(parent, id, noOutput, options);
		this.value = value;
	}

	/**
	 * インスタンスを取得します
	 *
	 * @param parent
	 *            シート読み込み定義
	 * @param id
	 *            定義ID
	 * @param noOutput
	 *            出力可否
	 * @param row
	 *            取得対象行
	 * @param col
	 *            取得対象列
	 * @param options
	 *            コンバーター
	 * @return ラップされたCell読み込み定義
	 */
	public static <P extends RangeDefinition<?>> CellDefinition<P> newInstance(P parent, Map<String, Object> config) {
		String id = RoughlyMapUtils.getString(config, ID);
		boolean noOutput = RoughlyMapUtils.getBooleanValue(config, NO_OUTPUT);
		String value = RoughlyMapUtils.getString(config, VALUE);
		List<Map<String, Object>> options = RoughlyMapUtils.getList(config, OPTIONS);
		return new RangeValueDefinitionImpl<P>(parent, id, noOutput, value, options);
	}

	@Override
	public Object apply(Object value) {
		return super.apply(value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("value", value);
		return map;
	}

}
