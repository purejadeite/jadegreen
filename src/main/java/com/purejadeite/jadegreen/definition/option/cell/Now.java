package com.purejadeite.jadegreen.definition.option.cell;

import java.util.Date;
import java.util.Map;

/**
 * 現在時刻を生成するクラス
 * @author mitsuhiroseino
 *
 */
public class Now extends AbstractUnrelatedValueGenerator {

	private static final long serialVersionUID = 6703579512400061087L;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public Now(String id, Map<String, Object> config) {
		super(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyImpl(Object value) {
		return new Date();
	}
}
