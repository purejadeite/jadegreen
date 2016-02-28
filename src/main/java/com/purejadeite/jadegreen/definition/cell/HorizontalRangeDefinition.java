package com.purejadeite.jadegreen.definition.cell;

import java.util.Map;

import com.purejadeite.jadegreen.definition.SheetDefinition;
import com.purejadeite.jadegreen.definition.table.TableDefinitionInterface;
import com.purejadeite.util.collection.Table;

/**
 * 横方向に繰り返すセルの読み込み定義です
 * @author mitsuhiroseino
 */
public class HorizontalRangeDefinition extends AbstractRangeDefinition {

	/**
	 * 終了列
	 */
	public static final String CFG_END_COLUMN = "endColumn";

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親定義
	 * @param config
	 *            コンフィグ
	 */
	public HorizontalRangeDefinition(SheetDefinition parent, Map<String, Object> config) {
		super(parent, config);
	}

	public static boolean assess(TableDefinitionInterface<?> table, Map<String, Object> config) {
		return config.containsKey(CFG_ROW) && config.containsKey(CFG_COLUMN)
				&& (config.containsKey(CFG_BREAK_VALUE) || config.containsKey(CFG_END_COLUMN));
	}

	protected String getEndId() {
		return CFG_END_COLUMN;
	}

	protected int getEndRow(Table<String> table) {
		return row;
	}

	protected int getEndCol(Table<String> table) {
		if (end == -1) {
			return table.getColumnSize();
		} else {
			return Math.min(end, table.getColumnSize());
		}
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
