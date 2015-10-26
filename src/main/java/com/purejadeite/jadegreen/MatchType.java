package com.purejadeite.jadegreen;

/**
 * 文字列の一致種別
 * @author mitsuhiroseino
 *
 */
enum MatchType {

	/**
	 * 無条件
	 */
	ANY,

	/**
	 * 完全一致
	 */
	EQUALS,

	/**
	 * 前方一致
	 */
	START_WITH,

	/**
	 * 後方一致
	 */
	END_WITH,

	/**
	 * 部分一致
	 */
	LIKE,

	/**
	 * 正規表現
	 */
	REGEX

}