package com.purejadeite.jadegreen.definition;

import java.io.Serializable;
import java.util.Map;

import com.purejadeite.jadegreen.AbstractToMap;
import com.purejadeite.jadegreen.DefinitionException;

/**
 *
 * 読み込み定義の抽象クラス
 *
 * @author mitsuhiroseino
 *
 */
abstract public class AbstractDefinition extends AbstractToMap implements Definition, Serializable {

	private static final long serialVersionUID = -5944791488682103592L;

	/**
	 * デフォルトコンストラクタ
	 */
	protected AbstractDefinition() {
		super();
	}

	/**
	 * コンフィグの必須チェック
	 *
	 * @param config
	 *            コンフィグ
	 * @param persistence
	 *            必須項目名称
	 */
	protected void validateConfig(Map<String, ? extends Object> config, String... persistence) {
		for (String property : persistence) {
			if (!config.containsKey(property)) {
				throw new DefinitionException(property + "=null:必須項目が設定されていません");
			}
		}
	}

}
