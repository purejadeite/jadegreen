package com.purejadeite.jadegreen.definition.table.cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.cell.AbstractCellDefinition;
import com.purejadeite.jadegreen.definition.table.TableDefinition;

/**
 * Tableの構成要素となるCell読み込み定義
 *
 * @author mitsuhiroseino
 */
abstract public class AbstractTableCellDefinition<P extends TableDefinition<?>> extends AbstractCellDefinition<P>
		implements TableCellDefinition<P> {

	private static final long serialVersionUID = -7367543678923800631L;

	/**
	 * 開始行
	 */
	protected int beginRow = 0;

	/**
	 * 終了行
	 */
	protected int endRow = 0;

	/**
	 * 開始列
	 */
	protected int beginCol = 0;

	/**
	 * 終了列
	 */
	protected int endCol = 0;

	/**
	 * 開始キー項目 ※現在未使用
	 */
	protected boolean beginKeyId = false;

	/**
	 * 開始キー値 ※現在未使用
	 */
	protected String beginValue = null;

	/**
	 * 終了キー項目
	 */
	protected boolean breakId = false;

	/**
	 * 終了キー値
	 */
	protected List<String> breakValues = null;

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親定義
	 * @param config
	 *            コンフィグ
	 */
	protected AbstractTableCellDefinition(P parent, Map<String, Object> config) {
		super(parent, config);
		this.beginRow = toBeginRow(parent, config);
		this.endRow = toEndRow(parent, config);
		this.beginCol = toBeginCol(parent, config);
		this.endCol = toEndCol(parent, config);
	}

	abstract protected int toBeginRow(P parent, Map<String, Object> config);

	abstract protected int toEndRow(P parent, Map<String, Object> config);

	abstract protected int toBeginCol(P parent, Map<String, Object> config);

	abstract protected int toEndCol(P parent, Map<String, Object> config);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMinRow() {
		return beginRow;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMaxRow() {
		return endRow;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMinCol() {
		return beginCol;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMaxCol() {
		return endCol;
	}

	public void setBreakId(boolean breakId) {
		this.breakId = breakId;
	}

	public void setBreakValues(List<String> breakValues) {
		this.breakValues = breakValues;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEndValue(Object value) {
		if (breakId) {
			// 自身にクローズ条件の値が設定されている場合
			if (breakValues == null) {
				if (value == null) {
					return true;
				}
			} else {
				for (String breakValue : breakValues) {
					if (breakValue == value || (breakValue != null && breakValue.equals(value))) {
						return true;
					}
				}
			}
		}
		// クローズの状態を返す
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isIncluded(int row, int col) {
		if (beginRow <= row && row <= endRow && beginCol <= col && col <= endCol) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public Object applyValue(Object value, Object appliedValues) {
		List<Object> vals = null;
		if (appliedValues == null) {
			vals = new ArrayList<Object>();
		} else {
			vals = (List<Object>) appliedValues;
		}
		vals.add(value);
		return vals;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("beginRow", beginRow);
		map.put("endRow", endRow);
		map.put("beginCol", beginCol);
		map.put("endCol", endCol);
		map.put("beginKeyId", beginKeyId);
		map.put("beginValue", beginValue);
		map.put("endKeyId", breakId);
		map.put("endValue", breakValues);
		return map;
	}

}
