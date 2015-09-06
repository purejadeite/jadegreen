package com.purejadeite.jadegreen.definition.cell;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.Definition;

/**
 * Rangeの構成要素となるCell読み込み定義
 * @author mitsuhiroseino
 */
public abstract class AbstractRangeCellDefinition extends AbstractCellDefinition implements RangeCellDefinition {

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
	 * 開始キー項目
	 * ※現在未使用
	 */
	protected boolean beginKeyId = false;

	/**
	 * 開始キー値
	 * ※現在未使用
	 */
	protected String beginValue = null;

	/**
	 * 終了キー項目
	 */
	protected boolean endKeyId = false;

	/**
	 * 終了キー値
	 */
	protected String endValue = null;

	/**
	 * コンストラクタ
	 * @param parent 範囲定義
	 * @param id 定義ID
	 * @param beginRow 開始行
	 * @param endRow 終了行
	 * @param beginCol 開始列
	 * @param endCol 終了列
	 * @param endKeyId 終了キー項目
	 * @param endValue 終了キー値
	 * @param options オプション
	 */
	protected AbstractRangeCellDefinition(Definition parent, String id, boolean noOutput, int beginRow, int endRow,
			int beginCol, int endCol,
			boolean endKeyId, String endValue, List<Map<String, String>> options) {
		super(parent, id, noOutput, options);
		this.beginRow = beginRow;
		this.endRow = endRow <= 0 ? Integer.MAX_VALUE : endRow;
		this.beginCol = beginCol;
		this.endCol = endCol <= 0 ? Integer.MAX_VALUE : endCol;
		this.endKeyId = endKeyId;
		this.endValue = endValue;
	}

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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEndValue(Object value) {
		if (endKeyId) {
			// 自身にクローズ条件の値が設定されている場合
			if (endValue == null) {
				if (value == null) {
					return true;
				}
			} else if (endValue.equals(value)) {
				return true;
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
		map.put("endKeyId", endKeyId);
		map.put("endValue", endValue);
		return map;
	}

}
