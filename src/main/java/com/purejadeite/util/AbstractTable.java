package com.purejadeite.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 2次元リストの抽象クラス
 *
 * @author mitsuhiroseino
 */
abstract public class AbstractTable<E> implements Table<E> {

	/**
	 * ネストされたリスト
	 */
	protected List<List<E>> table;

	/**
	 * 列数の最大値
	 */
	protected int columnSize = 0;

	/**
	 * コンストラクタ
	 */
	public AbstractTable() {
		super();
		table = new ArrayList<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getRowSize() {
		return table.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getColumnSize() {
		return columnSize;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<List<E>> getTable() {
		return table;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<List<E>> getAdjustedTable() {
		List<List<E>> adjustedTable = new ArrayList<>();
		int rowSize = getRowSize();
		for (int rowIndex = 0; rowIndex < rowSize; rowIndex++) {
			adjustedTable.add(getAdjustedRow(rowIndex));
		}
		return adjustedTable;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<E> getRow(int rowIndex) {
		if (rowIndex < table.size()) {
			return table.get(rowIndex);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<E> getAdjustedRow(int rowIndex) {
		List<E> adjustedRow = new ArrayList<>();
		List<E> row = getRow(rowIndex);
		if (row == null) {
			for (int columnIndex = 0; columnIndex < columnSize; columnIndex++) {
				adjustedRow.add(null);
			}
		} else {
			for (int columnIndex = 0; columnIndex < row.size(); columnIndex++) {
				adjustedRow.add(row.get(columnIndex));
			}
			for (int columnIndex = row.size(); columnIndex < columnSize; columnIndex++) {
				adjustedRow.add(null);
			}
		}
		return adjustedRow;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E get(int rowIndex, int columnIndex) {
		List<E> row = getRow(rowIndex);
		if (row != null && columnIndex < row.size()) {
			return row.get(columnIndex);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(E value) {
		List<E> row = table.get(table.size() - 1);
		row.add(value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int addRow() {
		int rowIndex = table.size();
		table.add(new ArrayList<E>());
		return rowIndex;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + ":[");
		boolean firstRow = true;
		for (List<E> row : table) {
			if (firstRow) {
				firstRow = false;
			} else {
				builder.append(",");
			}
			boolean firstValue = true;
			if (row != null) {
				builder.append("[");
				for (E value : row) {
					if (firstValue) {
						firstValue = false;
					} else {
						builder.append(",");
					}
					builder.append(value);
				}
				builder.append("]");
			} else {
				builder.append("null");
			}
		}
		builder.append("]");
		return builder.toString();
	}

}
