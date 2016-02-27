package com.purejadeite.jadegreen.definition.cell;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.ArrayList;
import java.util.List;
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
		this.end = getIntValue(config, CFG_END_ROW, -1);
	}

	public static boolean assess(TableDefinitionInterface<?> table, Map<String, Object> config) {
		return config.containsKey(CFG_ROW) && config.containsKey(CFG_COLUMN)
				&& (config.containsKey(CFG_BREAK_VALUE) || config.containsKey(CFG_END_ROW));
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
		List<Object> values = new ArrayList<>();
		int x = col - 1;
		int beginY = row - 1;
		if (end == -1) {
			// 終了条件がセルの値
			int size = table.getRowSize() - 1;
			for (int y = beginY; y < size; y++) {
				String value = table.get(y, x);
				if (breakValues != null && breakValues.contains(value)) {
					// 終了条件値の場合
					return values;
				}
				values.add(value);
			}
			return values;
		} else {
			// 終了条件が行番号
			int size = Math.min(end - 1, table.getRowSize() - 1);
			for (int y = beginY; y < size; y++) {
				values.add(table.get(y, x));
			}
			return values;
		}
	}
}
