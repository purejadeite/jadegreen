package com.purejadeite.jadegreen.definition.table.cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.cell.AbstractCellDefinition;
import com.purejadeite.jadegreen.definition.table.TableDefinition;
import com.purejadeite.util.collection.Table;

/**
 * Tableの構成要素となるCell読み込み定義
 *
 * @author mitsuhiroseino
 */
abstract public class AbstractTableCellDefinition<P extends TableDefinition<?>> extends AbstractCellDefinition<P>
		implements TableCellDefinition<P> {

	private static final long serialVersionUID = -7367543678923800631L;

	/**
	 * 開始列
	 */
	protected int beginCol = 0;

	/**
	 * 終了列
	 */
	protected int endCol = 0;

	/**
	 * 開始行
	 */
	protected int beginRow = 0;

	/**
	 * 終了行
	 */
	protected int endRow = 0;

	/**
	 * 終了キー項目
	 */
	protected boolean breakKey = false;

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
		this.beginCol = toBeginCol(parent, config);
		this.endCol = toEndCol(parent, config);
		this.beginRow = toBeginRow(parent, config);
		this.endRow = toEndRow(parent, config);
	}

	abstract protected int toBeginRow(P parent, Map<String, Object> config);

	abstract protected int toEndRow(P parent, Map<String, Object> config);

	abstract protected int toBeginCol(P parent, Map<String, Object> config);

	abstract protected int toEndCol(P parent, Map<String, Object> config);

	public void setBreakKey(boolean breakKey) {
		this.breakKey = breakKey;
	}

	public void setBreakValues(List<String> breakValues) {
		this.breakValues = breakValues;
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
		map.put("beginCol", beginCol);
		map.put("endCol", endCol);
		map.put("beginRow", beginRow);
		map.put("endRow", endRow);
		map.put("endKeyId", breakKey);
		map.put("endValue", breakValues);
		return map;
	}

	@Override
	public boolean isBreakKey() {
		return breakKey;
	}

	@Override
	public List<String> getBreakValues() {
		return breakValues;
	}

	@Override
	public Object capture(Table<String> table) {
		List<Object> values = new ArrayList<>();
		int beginX = beginCol - 1;
		int endX = Math.min(endCol - 1, table.getColumnSize() - 1);
		int beginY = beginRow - 1;
		int endY = Math.min(endRow - 1, table.getRowSize() - 1);
		List<String> breakValues = getBreakValues();

		for (int y = beginY; y < endY + 1; y++) {
			for (int x = beginX; x < endX + 1; x++) {
				String value = table.get(y, x);
				if (breakValues != null && breakValues.contains(value)) {
					// 終了条件値の場合
					return values;
				}
				values.add(value);
			}
		}
		return values;
	}

	@Override
	public Object capture(Table<String> table, int size) {
		@SuppressWarnings("unchecked")
		List<Object> values = (List<Object>) capture(table);

		if (getTable().getBreakId() == null) {
			// 終了位置が指定されている場合(BreakIdが指定されていない場合)
			return values;
		}

		// BreakIdが指定されている場合はsizeに合わせる
		if (size < values.size()) {
			// 超過
			values = values.subList(0, size);
		} else if (values.size() < size) {
			// 不足
			while (values.size() < size) {
				values.add(null);
			}
		}

		return values;
	}

}
