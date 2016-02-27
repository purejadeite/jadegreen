package com.purejadeite.jadegreen.definition.table.cell;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.Map;

import com.purejadeite.jadegreen.definition.table.TableDefinitionInterface;

/**
 * 横方向に繰り返すセルの読み込み定義です
 * @author mitsuhiroseino
 */
public class HorizontalTableRangeDefinition extends AbstractTableRangeDefinition<TableDefinitionInterface<?>> {

	public static final String CFG_END_COLUMN = "endColumn";

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親定義
	 * @param config
	 *            コンフィグ
	 */
	public HorizontalTableRangeDefinition(TableDefinitionInterface<?> parent, Map<String, Object> config) {
		super(parent, config);
		this.end = getIntValue(config, CFG_END_COLUMN, -1);
	}

	public static boolean assess(TableDefinitionInterface<?> table, Map<String, Object> config) {
		return !config.containsKey(CFG_ROW) && config.containsKey(CFG_COLUMN)
				&& (config.containsKey(CFG_BREAK_VALUE) || config.containsKey(CFG_END_COLUMN));
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
	protected int toBeginRow(TableDefinitionInterface<?> parent, Map<String, Object> config) {
		return parent.getBegin();
	}

	@Override
	protected int toEndRow(TableDefinitionInterface<?> parent, Map<String, Object> config) {
		return parent.getEnd();
	}

	@Override
	protected int toBeginCol(TableDefinitionInterface<?> parent, Map<String, Object> config) {
		return getIntValue(config, CFG_COLUMN);
	}

	@Override
	protected int toEndCol(TableDefinitionInterface<?> parent, Map<String, Object> config) {
		return getIntValue(config, CFG_END_COLUMN, UNLIMITED);
	}
}
