package com.purejadeite.jadegreen.definition.cell;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.Map;

import com.purejadeite.jadegreen.content.Content;
import com.purejadeite.jadegreen.definition.ParentDefinition;
import com.purejadeite.util.collection.Table;

/**
 * 固定値の定義です
 *
 * @author mitsuhiroseino
 */
public class ValueDefinitionImpl extends AbstractNoAdressCellDefinition<ParentDefinition<?, ?>> {

	private static final long serialVersionUID = 7280801241651790531L;

	private Object value;

	private boolean list;

	/**
	 * 値
	 */
	public static final String CFG_VALUE = "value";

	/**
	 * unionした際にリスト化するか
	 */
	public static final String CFG_LIST = "list";

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親定義
	 * @param config
	 *            コンフィグ
	 */
	public ValueDefinitionImpl(ParentDefinition<?, ?> parent, Map<String, Object> config) {
		super(parent, config);
		this.value = config.get(CFG_VALUE);
		this.list = getBooleanValue(config, CFG_LIST);
	}

	public static boolean assess(Map<String, Object> config, ParentDefinition<?, ?> table) {
		return config.containsKey(CFG_VALUE);
	}

	@Override
	public Object applyOptions(Object value, Content<?, ?> content) {
		return super.applyOptions(value, content);
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

	@Override
	public Object capture(Table<String> table) {
		return value;
	}

}
