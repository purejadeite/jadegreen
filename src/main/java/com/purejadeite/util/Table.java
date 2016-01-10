package com.purejadeite.util;

import java.util.List;

/**
 * 2次元リストのインターフェイス
 *
 * @author mitsuhiroseino
 */
public interface Table<E> extends List<List<E>> {

	/**
	 * 行数を取得します
	 * @return 行数
	 */
	public int getRowSize();

	/**
	 * 列数の最大値を取得します
	 * @return 列数
	 */
	public int getColumnSize();

	/**
	 * リストを持ったリストを取得します
	 * @return リスト
	 */
	public List<List<E>> getTable();

	/**
	 * 全ての行の列数を列数の最大値に揃えた、リストを持ったリストを取得します
	 * @return リスト
	 */
	public List<List<E>> getAdjustedTable();

	/**
	 * 指定行のリストを取得します
	 * @param rowIndex 行インデックス
	 * @return 指定行のリスト
	 */
	public List<E> getRow(int rowIndex);

	/**
	 * 列数を列数の最大値に揃えた、指定行のリストを取得します
	 * @param rowIndex 行インデックス
	 * @return 指定行のリスト
	 */
	public List<E> getAdjustedRow(int rowIndex);

	/**
	 * 指定位置の値を取得します
	 * @param rowIndex 行インデックス
	 * @param columnIndex 列インデックス
	 * @return 値
	 */
	public E get(int rowIndex, int columnIndex);

	/**
	 * 指定位置に値を設定します
	 * @param rowIndex 行インデックス
	 * @param columnIndex 列インデックス
	 * @param value 値
	 * @return 既存の値
	 */
	public E set(int rowIndex, int columnIndex, E value);

	/**
	 * 最終行に値を追加します
	 * @param value 値
	 */
	public void addValue(E value);

	/**
	 * 行を追加します
	 * @return 追加した行のインデックス
	 */
	public int addRow();

}
