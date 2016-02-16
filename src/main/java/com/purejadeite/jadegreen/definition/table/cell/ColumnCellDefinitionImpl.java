package com.purejadeite.jadegreen.definition.table.cell;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.Map;

import com.purejadeite.jadegreen.definition.ParentDefinition;
import com.purejadeite.jadegreen.definition.table.TableDefinition;

/**
 * 列方向の繰り返しを持つテーブル配下のCell読み込み定義
 *
 * @author mitsuhiroseino
 */
public class ColumnCellDefinitionImpl extends AbstractTableCellDefinition<TableDefinition<?>> {

	private static final long serialVersionUID = -1504987549447864687L;

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親定義
	 * @param config
	 *            コンフィグ
	 */
	public ColumnCellDefinitionImpl(TableDefinition<?> parent, Map<String, Object> config) {
		super(parent, config);
	}

	public static boolean assess(Map<String, Object> config, ParentDefinition<?, ?> table) {
		return table != null && config.containsKey(CFG_ROW);
	}

	@Override
	protected int toBeginRow(TableDefinition<?> parent, Map<String, Object> config) {
		return getIntValue(config, CFG_ROW);
	}

	@Override
	protected int toEndRow(TableDefinition<?> parent, Map<String, Object> config) {
		return getIntValue(config, CFG_ROW);
	}

	@Override
	protected int toBeginCol(TableDefinition<?> parent, Map<String, Object> config) {
		return parent.getBegin();
	}

	@Override
	protected int toEndCol(TableDefinition<?> parent, Map<String, Object> config) {
		return parent.getEnd();
	}

}
