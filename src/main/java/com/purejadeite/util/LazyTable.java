package com.purejadeite.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 行列の範囲を自動的に拡張するtable
 *
 * @author mitsuhiroseino
 */
public class LazyTable<E> extends AbstractTable<E> {

	/**
	 * コンストラクタ
	 */
	public LazyTable() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E set(int rowIndex, int columnIndex, E value) {
		expand(table, rowIndex);
		List<E> row = table.get(rowIndex);
		if (row == null) {
			row = new ArrayList<>();
			table.set(rowIndex, row);
		}
		expand(row, columnIndex);
		if (columnSize <= columnIndex) {
			columnSize = columnIndex + 1;
		}
		return row.set(columnIndex, value);
	}

	/**
	 * 指定のindexが設定できるまでlistを拡張します
	 * @param list リスト
	 * @param index インデックス
	 */
	private void expand(List<?> list, int index) {
		if (index < list.size()) {
			return;
		}
		while (list.size() <= index) {
			list.add(null);
		}
	}


}
