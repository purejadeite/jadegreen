package com.purejadeite.jadegreen.definition.cell;

import static com.purejadeite.jadegreen.definition.DefinitionKeys.*;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.ParentMappingDefinition;
import com.purejadeite.util.RoughlyMapUtils;

/**
 * 固定値の定義です
 *
 * @author mitsuhiroseino
 */
public class ValueDefinitionImpl<P extends ParentMappingDefinition<?, ?>> extends AbstractNoAdressCellDefinition<P> {

	private static final long serialVersionUID = 7280801241651790531L;

	private String value;

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            シート読み込み定義
	 * @param id
	 *            定義ID
	 * @param row
	 *            取得対象行
	 * @param col
	 *            取得対象列
	 * @param options
	 *            コンバーター
	 */
	private ValueDefinitionImpl(P parent, String id, String value, List<Map<String, Object>> options) {
		super(parent, id, options);
		this.value = value;
	}

	/**
	 * インスタンスを取得します
	 *
	 * @param parent
	 *            シート読み込み定義
	 * @param id
	 *            定義ID
	 * @param row
	 *            取得対象行
	 * @param col
	 *            取得対象列
	 * @param options
	 *            コンバーター
	 * @return ラップされたCell読み込み定義
	 */
	public static <P extends ParentMappingDefinition<?, ?>> CellDefinition<P> newInstance(P parent, Map<String, Object> config) {
		String id = RoughlyMapUtils.getString(config, ID);
		String value = RoughlyMapUtils.getString(config, VALUE);
		List<Map<String, Object>> options = RoughlyMapUtils.getList(config, OPTIONS);
		return new ValueDefinitionImpl<P>(parent, id, value, options);
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
