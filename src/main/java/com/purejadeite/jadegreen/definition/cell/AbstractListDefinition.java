package com.purejadeite.jadegreen.definition.cell;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.ParentDefinitionInterface;

/**
 * 単一セルの繰り返し読み込み定義です
 *
 * @author mitsuhiroseino
 */
abstract public class AbstractListDefinition
		extends AbstractCellDefinition<ParentDefinitionInterface<?, ?>, List<Object>> {

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親定義
	 * @param config
	 *            コンフィグ
	 */
	public AbstractListDefinition(ParentDefinitionInterface<?, ?> parent, Map<String, Object> config) {
		super(parent, config);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		return map;
	}

}
