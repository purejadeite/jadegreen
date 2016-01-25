package com.purejadeite.jadegreen.definition.option.cell;

import java.util.Map;
import java.util.UUID;

import com.purejadeite.jadegreen.definition.Definition;

/**
 * ランダムなUUIDを生成するクラス
 * @author mitsuhiroseino
 *
 */
public class Uuid extends AbstractUnrelatedValueGenerator {

	private static final long serialVersionUID = 1577740202379006372L;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public Uuid(Definition<?> definition, Map<String, Object> config) {
		super(definition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyImpl(Object value) {
		return UUID.randomUUID();
	}
}
