package com.purejadeite.jadegreen.definition.range;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.Definition;

/**
 * 列方向の繰り返し範囲の情報を保持するクラスの抽象クラスです
 * @author mitsuhiroseino
 */
public class ColumnDefinitionImpl extends AbstractRangeDefinition {

	private static final long serialVersionUID = -7108734031566256539L;

	/**
	 * コンストラクタ
	 * @param parent 親の読み込み情報
	 * @param id 定義ID
	 * @param stuff データの読み込みのみ行うか
	 * @param beginCol 開始列
	 * @param endCol 終了列
	 * @param endKey 開始キー項目
	 * @param endValue 終了キー値
	 * @param converters コンバーター
	 */
	public ColumnDefinitionImpl(Definition parent, String id, boolean stuff, int beginCol, int endCol, String endKey,
			String endValue,
			List<Map<String, String>> converters) {
		super(parent, id, stuff, beginCol, endCol, endKey, endValue, converters);
	}

	/**
	 * インスタンスを取得します
	 * @param parent 親の読み込み情報
	 * @param id 定義ID
	 * @param stuff データの読み込みのみ行うか
	 * @param beginCol 開始列
	 * @param endCol 終了列
	 * @param endKey 開始キー項目
	 * @param endValue 終了キー値
	 * @param converters コンバーター
	 * @return コンバーターでラップした読み込み定義
	 */
	public static RangeDefinition getInstance(Definition parent, String id, boolean stuff, int beginCol, int endCol,
			String endKey, String endValue,
			List<Map<String, String>> converters) {
		ColumnDefinitionImpl range = new ColumnDefinitionImpl(parent, id, stuff, beginCol, endCol, endKey, endValue,
				converters);
		return range;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isIncluded(int row, int col) {
		return (begin <= col && col <= end);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + super.toString() + "]";
	}

}
