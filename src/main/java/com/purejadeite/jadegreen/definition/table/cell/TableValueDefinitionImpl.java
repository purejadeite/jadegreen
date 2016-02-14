package com.purejadeite.jadegreen.definition.table.cell;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.Map;

import com.purejadeite.jadegreen.definition.table.TableDefinition;

/**
 * 固定値の定義です
 *
 * @author mitsuhiroseino
 */
public class TableValueDefinitionImpl<P extends TableDefinition<?>> extends AbstractNoAdressTableCellDefinition<P> {

	private static final long serialVersionUID = 960962162579025353L;

	/**
	 * 値
	 */
	public static final String CFG_VALUE = "value";

	private String value;

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親定義
	 * @param config
	 *            コンフィグ
	 */
	public TableValueDefinitionImpl(P parent, Map<String, Object> config) {
		super(parent, config);
		this.value = getString(config, CFG_VALUE);
	}

	public static boolean assess(TableDefinition<?> table, Map<String, Object> config) {
		return table != null && config.containsKey(CFG_VALUE);
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
