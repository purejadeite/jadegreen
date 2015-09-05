package com.purejadeite.jadegreen.definition;

import java.io.Serializable;

import com.purejadeite.jadegreen.AbstractToJson;

/**
 *
 * オプション定義情報抽象クラス
 *
 * @author mitsuhiroseino
 *
 */
public abstract class AbstractOption extends AbstractToJson implements Option, Serializable {

	/**
	 * デフォルトコンストラクタ
	 */
	protected AbstractOption() {
		super();
	}

}
