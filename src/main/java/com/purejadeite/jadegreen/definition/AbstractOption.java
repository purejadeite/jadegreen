package com.purejadeite.jadegreen.definition;

import java.io.Serializable;
import java.util.Map;

import com.purejadeite.jadegreen.AbstractToMap;

/**
 *
 * オプション定義情報抽象クラス
 *
 * @author mitsuhiroseino
 *
 */
public abstract class AbstractOption extends AbstractToMap implements Option, Serializable {

	/**
	 * デフォルトコンストラクタ
	 */
	protected AbstractOption() {
		super();
	}

	/**
	 * コンフィグのチェック
	 *
	 * @param config
	 *            コンフィグ
	 * @param persistence
	 *            必須項目名称
	 */
	protected void validateConfig(Map<String, ? extends Object> config, String[] persistence) {
		for (String property : persistence) {
			Object value = config.get(property);
			if (value == null) {
				throw new IllegalArgumentException(property + " は必須です");
			}
		}
	}

}
