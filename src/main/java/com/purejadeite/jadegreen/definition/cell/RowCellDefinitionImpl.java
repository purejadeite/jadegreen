package com.purejadeite.jadegreen.definition.cell;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.range.RangeDefinition;

/**
 * 行方向の繰り返しを持つテーブル配下のCell読み込み定義
 * @author mitsuhiroseino
 */
public class RowCellDefinitionImpl extends AbstractRangeCellDefinition {

	private static final long serialVersionUID = 8171650318154820322L;

	/**
	 * コンストラクタ
	 * @param parent テーブル読み込み定義
	 * @param id 定義ID
	 * @param beginRow 取得開始行
	 * @param endRow 取得終了行
	 * @param col 取得対象列
	 * @param endKey 終了キー項目フラグ
	 * @param endValue 終了キー値
	 * @param options オプション
	 */
	private RowCellDefinitionImpl(Definition parent, String id, boolean noOutput, int beginRow, int endRow, int col,
			boolean endKey, String endValue, List<Map<String, String>> options) {
		super(parent, id, noOutput, beginRow, endRow, col, col, endKey, endValue, options);
	}

	/**
	 * インスタンスを取得します
	 * @param range テーブル読み込み定義
	 * @param id 定義ID
	 * @param col 取得対象列
	 * @param options オプション
	 * @return ラップされたCell読み込み定義
	 */
	public static CellDefinition getInstance(RangeDefinition range, String id, boolean noOutput, int col, List<Map<String, String>> options) {
		boolean endKey = false;
		String endValue = null;
		if (id.equals(range.getEndKeyId())) {
			// 終了条件
			endKey = true;
			endValue = range.getEndValue();
		}
		AbstractRangeCellDefinition cell = new RowCellDefinitionImpl(range, id, noOutput, range.getBegin(), range.getEnd(), col, endKey, endValue,
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
