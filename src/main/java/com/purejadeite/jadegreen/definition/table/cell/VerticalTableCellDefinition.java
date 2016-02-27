package com.purejadeite.jadegreen.definition.table.cell;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.Map;

import com.purejadeite.jadegreen.definition.ParentDefinitionInterface;
import com.purejadeite.jadegreen.definition.table.TableDefinitionInterface;

/**
 * 行方向の繰り返しを持つテーブル配下のCell読み込み定義
 * @author mitsuhiroseino
 */
public class VerticalTableCellDefinition extends AbstractTableCellDefinition<TableDefinitionInterface<?>> {

	private static final long serialVersionUID = 1338170679621437792L;

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親定義
	 * @param config
	 *            コンフィグ
	 */
	public VerticalTableCellDefinition(TableDefinitionInterface<?> parent, Map<String, Object> config) {
		super(parent, config);
	}

	public static boolean assess(Map<String, Object> config, ParentDefinitionInterface<?, ?> table) {
		return table != null && config.containsKey(CFG_COLUMN);
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
		return getIntValue(config, CFG_COLUMN);
	}

}
