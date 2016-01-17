package com.purejadeite.jadegreen.definition.cell;

import static com.purejadeite.jadegreen.definition.DefinitionKeys.*;
import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.Map;

import com.purejadeite.jadegreen.definition.table.TableDefinition;

/**
 * 行方向の繰り返しを持つテーブル配下のCell読み込み定義
 * @author mitsuhiroseino
 */
public class RowCellDefinitionImpl extends AbstractTableCellDefinition<TableDefinition<?>> {

	private static final long serialVersionUID = 1338170679621437792L;

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親定義
	 * @param config
	 *            コンフィグ
	 */
	public RowCellDefinitionImpl(TableDefinition<?> parent, Map<String, Object> config) {
		super(parent, config);
	}

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親定義
	 * @param config
	 *            コンフィグ
	 * @param breakId 終了キー項目
	 * @param breakValue 終了キー値
	 */
	private RowCellDefinitionImpl(TableDefinition<?> parent, Map<String, Object> config, boolean breakId, String breakValue) {
		super(parent, config, breakId, breakValue);
	}

	@Override
	protected int toBeginRow(TableDefinition<?> parent, Map<String, Object> config) {
		return parent.getBegin();
	}

	@Override
	protected int toEndRow(TableDefinition<?> parent, Map<String, Object> config) {
		return parent.getEnd();
	}

	@Override
	protected int toBeginCol(TableDefinition<?> parent, Map<String, Object> config) {
		return getIntValue(config, COLUMN);
	}

	@Override
	protected int toEndCol(TableDefinition<?> parent, Map<String, Object> config) {
		return getIntValue(config, COLUMN);
	}

}
