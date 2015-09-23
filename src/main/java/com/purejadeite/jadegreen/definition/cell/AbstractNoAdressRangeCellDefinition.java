package com.purejadeite.jadegreen.definition.cell;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.range.RangeDefinition;

/**
 * Rangeの構成要素となるCell読み込み定義
 * @author mitsuhiroseino
 */
abstract public class AbstractNoAdressRangeCellDefinition<P extends RangeDefinition<?>> extends AbstractNoAdressCellDefinition<P> implements RangeCellDefinition<P> {


	/**
	 * コンストラクタ
	 * @param parent 範囲定義
	 * @param id 定義ID
	 * @param noOutput 出力対象外フラグ
	 * @param options オプション
	 */
	protected AbstractNoAdressRangeCellDefinition(P parent, String id, boolean noOutput, List<Map<String, Object>> options) {
		super(parent, id, noOutput, options);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEndValue(Object value) {
		return false;
	}

}
