package com.purejadeite.jadegreen.definition.table.cell;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.Map;

import com.purejadeite.jadegreen.definition.ParentDefinitionInterface;
import com.purejadeite.jadegreen.definition.table.TableDefinitionInterface;

/**
 * 行方向の繰り返しを持つテーブル配下のCellをList形式で読み込む定義
 * @author mitsuhiroseino
 */
public class HorizontalTableListCellDefinition extends AbstractTableListCellDefinition {

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親定義
	 * @param config
	 *            コンフィグ
	 */
	public HorizontalTableListCellDefinition(TableDefinitionInterface<?> parent, Map<String, Object> config) {
		super(parent, config);
	}

	public static boolean assess(Map<String, Object> config, ParentDefinitionInterface<?, ?> table) {
		return table != null && config.containsKey(CFG_ROW) && config.containsKey(CFG_SPLITTER);
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
		return getIntValue(config, CFG_ROW);
	}

	@Override
	protected int toEndRow(TableDefinitionInterface<?> parent, Map<String, Object> config) {
		return getIntValue(config, CFG_ROW);
	}

	@Override
	protected int toBeginCol(TableDefinitionInterface<?> parent, Map<String, Object> config) {
		return parent.getBegin();
	}

	@Override
	protected int toEndCol(TableDefinitionInterface<?> parent, Map<String, Object> config) {
		return parent.getEnd();
	}

}
