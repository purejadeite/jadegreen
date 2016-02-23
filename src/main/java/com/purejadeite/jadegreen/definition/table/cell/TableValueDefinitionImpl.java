package com.purejadeite.jadegreen.definition.table.cell;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.content.Content;
import com.purejadeite.jadegreen.definition.ParentDefinition;
import com.purejadeite.jadegreen.definition.table.TableDefinition;
import com.purejadeite.util.collection.Table;

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

	public static boolean assess(Map<String, Object> config, ParentDefinition<?, ?> table) {
		return table != null && config.containsKey(CFG_VALUE);
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
		return null;
	}

	@Override
	public Object capture(Table<String> table, int size) {
		List<Object> values = new ArrayList<>();
		while(values.size() < size) {
			values.add(value);
		}
		return values;
	}

}
