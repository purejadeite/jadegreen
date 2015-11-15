package com.purejadeite.jadegreen.definition.cell;

import static com.purejadeite.jadegreen.definition.DefinitionKeys.*;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.WorksheetDefinition;
import com.purejadeite.util.RoughlyMapUtils;

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
	 * @param parent シート読み込み定義
	 * @param id 定義ID
	 * @param row 取得対象行
	 * @param col 取得対象列
	 * @param options オプション
	 */
	protected CellDefinitionImpl(WorksheetDefinition parent, String id, boolean noOutput, int row, int col, List<Map<String, Object>> options) {
		super(parent, id, noOutput, options);
		this.row = row;
		this.col = col;
	}

	public static CellDefinition<?> newInstance(WorksheetDefinition parent, Map<String, Object> config) {
		String id = RoughlyMapUtils.getString(config, ID);
		boolean noOutput = RoughlyMapUtils.getBooleanValue(config, NO_OUTPUT);
		int row = RoughlyMapUtils.getIntValue(config, ROW);
		int col = RoughlyMapUtils.getIntValue(config, COLUMN);
		List<Map<String, Object>> options = RoughlyMapUtils.getList(config, OPTIONS);
		return new CellDefinitionImpl(parent, id, noOutput, row, col, options);
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
