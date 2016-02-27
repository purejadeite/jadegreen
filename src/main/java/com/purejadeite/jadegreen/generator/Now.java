package com.purejadeite.jadegreen.generator;

import java.util.Date;
import java.util.Map;

import com.purejadeite.jadegreen.content.ContentInterface;
import com.purejadeite.jadegreen.definition.DefinitionInterface;

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
	public Now(DefinitionInterface<?> definition, Map<String, Object> config) {
		super(definition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyImpl(Object value, ContentInterface<?, ?> content) {
		return new Date();
	}
}
