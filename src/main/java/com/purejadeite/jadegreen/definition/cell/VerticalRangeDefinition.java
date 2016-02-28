package com.purejadeite.jadegreen.definition.cell;

import java.util.Map;

import com.purejadeite.jadegreen.definition.SheetDefinition;
import com.purejadeite.jadegreen.definition.table.TableDefinitionInterface;
import com.purejadeite.util.collection.Table;

/**
 * 縦方向に繰り返すセルの読み込み定義です
 * @author mitsuhiroseino
 */
public class VerticalRangeDefinition extends AbstractRangeDefinition {

	/**
	 * 終了行
	 */
	public static final String CFG_END_ROW = "endRow";

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親定義
	 * @param config
	 *            コンフィグ
	 */
	public VerticalRangeDefinition(SheetDefinition parent, Map<String, Object> config) {
		super(parent, config);
	}

	public static boolean assess(TableDefinitionInterface<?> table, Map<String, Object> config) {
		return config.containsKey(CFG_ROW) && config.containsKey(CFG_COLUMN)
				&& (config.containsKey(CFG_BREAK_VALUE) || config.containsKey(CFG_END_ROW));
	}

	protected String getEndId() {
		return CFG_END_ROW;
	}

	protected int getEndRow(Table<String> table) {
		if (end == -1) {
			return table.getRowSize();
		} else {
			return Math.min(end, table.getRowSize());
		}
	}

	protected int getEndCol(Table<String> table) {
		return col;
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
