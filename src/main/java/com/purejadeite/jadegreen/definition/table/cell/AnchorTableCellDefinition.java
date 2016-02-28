package com.purejadeite.jadegreen.definition.table.cell;

import java.util.Map;

import com.purejadeite.jadegreen.definition.table.TableDefinitionInterface;
import com.purejadeite.util.collection.Table;

/**
 * オプションなどが値の作成先にする仮想Cellの定義です
 *
 * @author mitsuhiroseino
 */
public class AnchorTableCellDefinition<P extends TableDefinitionInterface<?>, V> extends AbstractNoAdressTableCellDefinition<P, V> {

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親定義
	 * @param config
	 *            コンフィグ
	 */
	public AnchorTableCellDefinition(P parent, Map<String, Object> config) {
		super(parent, config);
	}

	public static boolean assess(TableDefinitionInterface<?> table, Map<String, Object> config) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		return map;
	}

	@Override
	public V capture(Table<String> table) {
		return null;
	}

	@Override
	public V capture(Table<String> table, int size) {
		return null;
	}

}
