package com.purejadeite.jadegreen.definition.option.table;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.Definition;

/**
 * 削除コンバーター
 * @author mitsuhiroseino
 *
 */
public class Unique extends AbstractTableOption {

	private static final long serialVersionUID = 5701716891112764511L;

	/**
	 * コンストラクタ
	 * @param config コンバーターのコンフィグ
	 */
	public Unique(Definition<?> definition, Map<String, Object> config) {
		super(definition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object applyImpl(List<Map<String, Object>> values) {
		return new ArrayList<>(new LinkedHashSet<>(values));
	}

}