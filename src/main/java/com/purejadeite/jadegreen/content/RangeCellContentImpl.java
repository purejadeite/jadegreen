package com.purejadeite.jadegreen.content;

import com.purejadeite.jadegreen.definition.cell.RangeCellDefinition;

/**
 * Rangeの構成要素となるCell読み込み定義
 * @author mitsuhiroseino
 */
public class RangeCellContentImpl extends AbstractRangeCellContent<RangeCellDefinition<?>> {

	/**
	 * コンストラクタ
	 */
	public RangeCellContentImpl(Content<?> parent, RangeCellDefinition<?> definition) {
		super(parent, definition);
	}

}
