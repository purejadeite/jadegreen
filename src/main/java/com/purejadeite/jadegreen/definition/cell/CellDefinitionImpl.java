package com.purejadeite.jadegreen.definition.cell;

import static com.purejadeite.jadegreen.definition.DefinitionKeys.*;
import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.Map;

import com.purejadeite.jadegreen.definition.WorksheetDefinition;

/**
 * 単一セルの読み込み定義です
 * @author mitsuhiroseino
 */
public class CellDefinitionImpl extends AbstractCellDefinition<WorksheetDefinition> {

	private static final long serialVersionUID = -6196528307255166352L;

	/**
	 * 取得対象列
	 */
	protected int row = 0;

	/**
	 * 取得対象行
	 */
	protected int col = 0;

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親定義
	 * @param config
	 *            コンフィグ
	 */
	public CellDefinitionImpl(WorksheetDefinition parent, Map<String, Object> config) {
		super(parent, config);
		row = getIntValue(config, ROW);
		col = getIntValue(config, COLUMN);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMinRow() {
		return row;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMaxRow() {
		return row;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMinCol() {
		return col;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMaxCol() {
		return col;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isIncluded(int row, int col) {
		if (this.row == row && this.col == col) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("row", row);
		map.put("col", col);
		return map;
	}

}
