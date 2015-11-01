package com.purejadeite;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * ネストしたmapの値にアクセスしやすくするためのラッパークラス
 * get,put,remove,containsKeyのkeyに配列またはCollectionを含む値を渡した場合に、ネストした値を取得します。
 * @author mitsuhiroseino
 */
public class StringKeyNestedMap extends NestedMap<String> {

	private static String DEFAULT_KEY_SEPARATOR = ".";

	private String keySeparator;

	public StringKeyNestedMap(Map<String, Object> map) {
		this(map, DEFAULT_KEY_SEPARATOR, false, DEFAULT_MAP, DEFAULT_LIST);
	}

	public StringKeyNestedMap(Map<String, Object> map, String keySeparator) {
		this(map, keySeparator, false, DEFAULT_MAP, DEFAULT_LIST);
	}

	public StringKeyNestedMap(Map<String, Object> map, boolean lazy) {
		this(map, DEFAULT_KEY_SEPARATOR, lazy, DEFAULT_MAP, DEFAULT_LIST);
	}

	@SuppressWarnings("rawtypes")
	public StringKeyNestedMap(Map<String, Object> map, boolean lazy, Class<? extends Map> mapClass) {
		this(map, DEFAULT_KEY_SEPARATOR, lazy, mapClass, DEFAULT_LIST);
	}

	@SuppressWarnings("rawtypes")
	public StringKeyNestedMap(Map<String, Object> map, boolean lazy, Class<? extends Map> mapClass,
			Class<? extends List> listClass) {
		this(map, DEFAULT_KEY_SEPARATOR, lazy, mapClass, listClass);
	}

	@SuppressWarnings("rawtypes")
	public StringKeyNestedMap(Map<String, Object> map, String keySeparator, boolean lazy, Class<? extends Map> mapClass,
			Class<? extends List> listClass) {
		super(map, lazy, mapClass, listClass);
		this.keySeparator = keySeparator;
	}

	protected List<String> getKeys(Object key) {
		Object keys = null;
		if (key instanceof String) {
			keys = StringUtils.split((String)key, keySeparator);
		} else {
			keys = key;
		}
		return super.getKeys(keys);
	}

}

