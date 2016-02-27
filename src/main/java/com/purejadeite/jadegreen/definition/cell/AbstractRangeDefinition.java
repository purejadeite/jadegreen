package com.purejadeite.jadegreen.definition.cell;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.SheetDefinition;

/**
 * 単一セルの繰り返し読み込み定義です
 * @author mitsuhiroseino
 */
abstract public class AbstractRangeDefinition extends CellDefinition {

	/**
	 * 列数/行数の上限なし
	 */
	public static final int UNLIMITED = Integer.MAX_VALUE;

	/**
	 * 繰り返しの取得終了判定値
	 */
	public static final String CFG_BREAK_VALUE = "breakValue";

	/**
	 * 終了行／列
	 */
	protected int end;

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
	public AbstractRangeDefinition(SheetDefinition parent, Map<String, Object> config) {
		super(parent, config);
		breakValues = getAsList(config, CFG_BREAK_VALUE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		return map;
	}

}
