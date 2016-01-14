package com.purejadeite.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 値のチェック用ユーティリティです
 *
 * @author mitsuhiroseino
 *
 */
public class SimpleValidator {

	/**
	 * コンフィグの必須チェック
	 *
	 * @param config
	 *            コンフィグ
	 * @param persistence
	 *            必須項目名称
	 */
	public static <K> void containsKey(Map<K, ?> map, K[] keys) {
		List<String> errors = new ArrayList<>();
		for (K key : keys) {
			if (!map.containsKey(key)) {
				if (key == null) {
					errors.add(null);
				} else {
					errors.add(key.toString());
				}
			}
		}
		if (!errors.isEmpty()) {
			String errorKeys = StringUtils.join(errors.toArray(), ",");
			throw new ValidationException(errorKeys + "に値を設定してください。");
		}
	}

}
