package com.purejadeite.core;

/**
 * JSON形式の値出力を行うクラスのインターフェイス
 * @author mitsuhiroseino
 */
public interface ToJson {

	/**
	 * インスタンスの内容をJSON形式で取得します
	 * @return JSON
	 */
	public String toJson();
}
