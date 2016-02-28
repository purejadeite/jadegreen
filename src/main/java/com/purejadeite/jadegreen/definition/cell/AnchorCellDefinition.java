package com.purejadeite.jadegreen.definition.cell;

import java.util.Map;

import com.purejadeite.jadegreen.definition.ParentDefinitionInterface;
import com.purejadeite.util.collection.Table;

/**
 * Optionの作った値の設定先になるCell
 *
 * @author mitsuhiroseino
 */
public class AnchorCellDefinition extends AbstractNoAdressCellDefinition<ParentDefinitionInterface<?,?>, Object> {

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親定義
	 * @param config
	 *            コンフィグ
	 */
	public AnchorCellDefinition(ParentDefinitionInterface<?,?> parent, Map<String, Object> config) {
		super(parent, config);
	}

	public static boolean assess(Map<String, Object> config, ParentDefinitionInterface<?, ?> table) {
		return (config.containsKey(CFG_ID) && config.size() == 1) || (config.containsKey(CFG_ID) && config.containsKey(CFG_NAME) && config.size() == 2);
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
	public Object capture(Table<String> table) {
		return null;
	}

}
