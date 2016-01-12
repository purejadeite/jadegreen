package com.purejadeite.util;

import java.util.Map;

/**
 * 値のチェック用ユーティリティです
 *
 * @author mitsuhiroseino
 *
 */
public class ValidationUtils {

	/**
	 * コンフィグの必須チェック
	 *
	 * @param config
	 *            コンフィグ
	 * @param persistence
	 *            必須項目名称
	 */
	public static <K> K exist(Map<K, ?> map, @SuppressWarnings("unchecked") K... keys) {
		for (K key : keys) {
			if (!map.containsKey(key)) {
				return key;
			}
		}
		return null;
	}

}
