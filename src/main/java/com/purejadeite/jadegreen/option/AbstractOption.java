package com.purejadeite.jadegreen.option;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.purejadeite.jadegreen.definition.DefinitionInterface;

/**
 * Cellの値を変換する抽象クラス
 *
 * @author mitsuhiroseino
 *
 */
abstract public class AbstractOption implements OptionInterface, Serializable {

	protected DefinitionInterface<?> definition;

	/**
	 * コンストラクタ
	 *
	 * @param cell
	 *            値の取得元Cell読み込み定義
	 */
	public AbstractOption(DefinitionInterface<?> definition) {
		this.definition = definition;
	}

	@Override
	public DefinitionInterface<?> getDefinition() {
		return definition;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return toMap().toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = new LinkedHashMap<>();
		return map;
	}

}
