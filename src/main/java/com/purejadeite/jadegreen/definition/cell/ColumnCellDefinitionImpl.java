package com.purejadeite.jadegreen.definition.cell;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.range.RangeDefinition;

/**
 * 列方向の繰り返しを持つテーブル配下のCell読み込み定義
 * @author mitsuhiroseino
 */
public class ColumnCellDefinitionImpl extends AbstractRangeCellDefinition {

	private static final long serialVersionUID = -6991064050234248248L;

	/**
	 * コンストラクタ
	 * @param parent テーブル読み込み定義
	 * @param id 定義ID
	 * @param row 取得対象行
	 * @param beginCol 取得開始列
	 * @param endCol 取得終了列
	 * @param endKey 終了キー項目フラグ
	 * @param endValue 終了キー値
	 * @param options オプション
	 */
	private ColumnCellDefinitionImpl(Definition parent, String id, boolean noOutput, int row, int beginCol, int endCol,
			boolean endKey, String endValue, List<Map<String, String>> options) {
		super(parent, id, noOutput, row, row, beginCol, endCol, endKey, endValue, options);
	}

	/**
	 * インスタンスを取得します
	 * @param range テーブル読み込み定義
	 * @param id 定義ID
	 * @param row 取得対象行
	 * @param options オプション
	 * @return ラップされたCell読み込み定義
	 */
	public static CellDefinition getInstance(RangeDefinition range, String id, boolean noOutput, int row, List<Map<String, String>> options) {
		boolean endKey = false;
		String endValue = null;
		if (id.equals(range.getEndKeyId())) {
			// 終了条件
			endKey = true;
			endValue = range.getEndValue();
		}
		AbstractRangeCellDefinition cell = new ColumnCellDefinitionImpl(range, id, noOutput, row, range.getBegin(), range.getEnd(),
				endKey, endValue,
				options);
		return cell;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJson() {
		return "{" + super.toJson() + "}";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + super.toString() + "]";
	}


}
