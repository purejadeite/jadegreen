package com.purejadeite.jadegreen.definition.cell;

import static com.purejadeite.jadegreen.definition.DefinitionKeys.*;
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
		this.value = getString(config, VALUE);
	}

	@Override
	public Object applyOptions(Object value) {
		return super.applyOptions(this.value);
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
