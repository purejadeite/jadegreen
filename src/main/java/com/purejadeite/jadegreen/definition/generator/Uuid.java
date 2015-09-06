package com.purejadeite.jadegreen.definition.generator;

import java.util.Map;
import java.util.UUID;

/**
 * ランダムなUUIDを生成するクラス
 * @author mitsuhiroseino
 *
 */
public class Uuid extends AbstractUnrelatedValueGenerator {

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public Uuid(Map<String, Object> config) {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyImpl(Object value) {
		return UUID.randomUUID();
	}
}
