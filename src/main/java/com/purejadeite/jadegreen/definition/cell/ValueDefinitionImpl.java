package com.purejadeite.jadegreen.definition.cell;

import java.util.Map;

import com.purejadeite.jadegreen.content.Content;
import com.purejadeite.jadegreen.definition.ParentDefinition;
import com.purejadeite.jadegreen.definition.table.TableDefinition;

/**
 * 固定値の定義です
 *
 * @author mitsuhiroseino
 */
public class ValueDefinitionImpl<P extends ParentDefinition<?, ?>> extends AbstractNoAdressCellDefinition<P> {

	private static final long serialVersionUID = 7280801241651790531L;

	private Object value;

	/**
	 * 値
	 */
	public static final String CFG_VALUE = "value";

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親定義
	 * @param config
	 *            コンフィグ
	 */
	public ValueDefinitionImpl(P parent, Map<String, Object> config) {
		super(parent, config);
		this.value = config.get(CFG_VALUE);
	}

	public static boolean assess(TableDefinition<?> table, Map<String, Object> config) {
		return table == null && config.containsKey(CFG_VALUE);
	}

	@Override
	public Object applyOptions(Object value, Content<?, ?> content) {
		return super.applyOptions(this.value, content);
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
