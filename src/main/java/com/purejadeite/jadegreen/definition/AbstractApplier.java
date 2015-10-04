package com.purejadeite.jadegreen.definition;

import java.io.Serializable;
import java.util.Map;

import com.purejadeite.jadegreen.AbstractToMap;
import com.purejadeite.jadegreen.DefinitionException;

/**
 *
 * 値に変更を適用する抽象クラス
 *
 * @author mitsuhiroseino
 *
 */
abstract public class AbstractApplier extends AbstractToMap implements Applier, Serializable {

	/**
	 * デフォルトコンストラクタ
	 */
	protected AbstractApplier() {
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
