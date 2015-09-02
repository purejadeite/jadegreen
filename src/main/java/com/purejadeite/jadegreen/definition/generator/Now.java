package com.purejadeite.jadegreen.definition.generator;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ランダムなUUIDを生成するクラス
 * @author mitsuhiroseino
 *
 */
public class Now extends AbstractUnrelatedValueGenerator {

	private static final Logger LOGGER = LoggerFactory.getLogger(Now.class);

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	@SuppressWarnings("unchecked")
	public Now(Map<String, Object> config) {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyImpl(Object value) {
		return new Date();
	}
}
