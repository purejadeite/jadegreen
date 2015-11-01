package com.purejadeite;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;

/**
 * ネストしたmapの値にアクセスしやすくするためのラッパークラス
 * get,put,remove,containsKeyのkeyに配列またはCollectionを含む値を渡した場合に、ネストした値を取得します。
 *
 * @author mitsuhiroseino
 */
public class NestedMap<K> implements Map<K, Object> {

	protected Map<K, Object> map;

	private static Object[] NO_ARGS = new Object[0];

	@SuppressWarnings("rawtypes")
	protected static Class<? extends Map> DEFAULT_MAP = HashMap.class;

	@SuppressWarnings("rawtypes")
	protected static Class<? extends List> DEFAULT_LIST = ArrayList.class;

	protected boolean lazy = false;

	@SuppressWarnings("rawtypes")
	protected Class<? extends Map> mapClass;

	@SuppressWarnings("rawtypes")
	protected Class<? extends List> listClass;

	public NestedMap(Map<K, Object> map) {
		this(map, false, DEFAULT_MAP, DEFAULT_LIST);
	}

	public NestedMap(Map<K, Object> map, boolean lazy) {
		this(map, lazy, DEFAULT_MAP, DEFAULT_LIST);
	}

	@SuppressWarnings("rawtypes")
	public NestedMap(Map<K, Object> map, boolean lazy, Class<? extends Map> mapClass) {
		this(map, lazy, mapClass, DEFAULT_LIST);
	}

	@SuppressWarnings("rawtypes")
	public NestedMap(Map<K, Object> map, boolean lazy, Class<? extends Map> mapClass,
			Class<? extends List> listClass) {
		this.map = map;
		this.lazy = lazy;
		this.mapClass = mapClass;
		this.listClass = listClass;
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public Object get(Object key) {
		return get(getKeys(key));
	}

	public Object get(List<K> keys) {
		Map<K, Object> nestedMap = getParentMap(keys, false);
		if (nestedMap == null) {
			return null;
		} else {
			return nestedMap.get(getLastKey(keys));
		}
	}

	@Override
	public Object put(K key, Object value) {
		return put(getKeys(key), value);
	}

	public Object put(Iterable<K> keys, Object value) {
		return put(getKeys(keys), value);
	}

	public Object put(K[] keys, Object value) {
		return put(getKeys(keys), value);
	}

	public Object put(List<K> keys, Object value) {
		Map<K, Object> nestedMap = getParentMap(keys, lazy);
		if (nestedMap == null) {
			return null;
		} else {
			return nestedMap.put(getLastKey(keys), value);
		}
	}

	public boolean add(K key, Object value) {
		return add(getKeys(key), value);
	}

	public boolean add(Iterable<K> keys, Object value) {
		return add(getKeys(keys), value);
	}

	public boolean add(K[] keys, Object value) {
		return add(getKeys(keys), value);
	}

	public boolean add(List<K> keys, Object value) {
		if (keys == null) {
			return false;
		} else {
			return add(map, keys, value);
		}
	}

	@Override
	public Object remove(Object key) {
		return remove(getKeys(key));
	}

	public Object remove(List<K> keys) {
		Map<K, Object> nestedMap = getParentMap(keys, false);
		if (nestedMap == null) {
			return null;
		} else {
			return nestedMap.remove(getLastKey(keys));
		}
	}

	private Map<K, Object> getParentMap(List<K> keys, boolean lazy) {
		if (keys == null) {
			return null;
		}
		List<K> rootKeys = getParentKeys(keys);
		return getMap(map, rootKeys, lazy);
	}

	private List<K> getParentKeys(List<K> keys) {
		return keys.subList(0, keys.size() - 1);
	}

	private List<K> getChildKeys(List<K> keys) {
		return keys.subList(1, keys.size());
	}

	private K getFirstKey(List<K> keys) {
		if (keys.isEmpty()) {
			return null;
		}
		return keys.get(0);
	}

	private K getLastKey(List<K> keys) {
		return keys.get(keys.size() - 1);
	}

	@Override
	public void putAll(Map<? extends K, ? extends Object> m) {
		map.putAll(m);
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public Set<K> keySet() {
		return map.keySet();
	}

	@Override
	public Collection<Object> values() {
		return map.values();
	}

	@Override
	public Set<java.util.Map.Entry<K, Object>> entrySet() {
		return map.entrySet();
	}

	@SuppressWarnings("unchecked")
	protected List<K> getKeys(Object key) {
		if (key instanceof List) {
			return (List<K>) key;
		} else {
			List<K> keys = new ArrayList<>();
			if (key instanceof Iterable) {
				// Iterableの場合はListに変換
				for (K k : (Iterable<K>) key) {
					keys.add(k);
				}
			} else if (key instanceof Object[]) {
				// 配列の場合はListに変換
				for (K k : (K[]) key) {
					keys.add(k);
				}
			} else if (key == null) {
				// nullの場合は要素がnullのみの配列に変換
				keys.add(null);
			} else {
				// 上記以外はnull
				return null;
			}
			return keys;
		}
	}

	/**
	 * ネストした位置にあるコレクションに値を追加します
	 *
	 * @param map
	 * @param keys
	 * @param value
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	protected boolean add(Object obj, List<K> keys, Object value) {
		Map<K, Object> parent = getMap(obj, getParentKeys(keys), lazy);
		if (parent != null) {
			K key = getLastKey(keys);
			Object child = parent.get(key);
			List<Object> list = null;
			if (child == null) {
				list = newList();
				list.add(value);
				parent.put(key, list);
				return true;
			} else if (child instanceof Collection) {
				list = (List<Object>) obj;
				list.add(value);
				return true;
			}
		}
		return false;
	}

	public Map<K, Object> getMap() {
		return map;
	}

	protected Map<K, Object> getMap(Map<K, Object> map, List<K> keys) {
		return getMap(map, keys, lazy);
	}

	@SuppressWarnings("unchecked")
	protected Map<K, Object> getMap(Object obj, List<K> keys, boolean lazy) {
		return (Map<K, Object>) get(obj, keys, lazy);
	}

	protected List<Object> getList(Object obj, List<K> keys) {
		return getList(obj, keys, lazy);
	}

	@SuppressWarnings("unchecked")
	protected List<Object> getList(Object obj, List<K> keys, boolean lazy) {
		return (List<Object>) get(obj, keys, lazy);
	}

	/**
	 * objの子要素を取得します
	 *
	 * @param map
	 * @param keys
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Object get(Object obj, List<K> keys, boolean lazy) {
		// objがMapだった時用
		Map<K, Object> map = null;
		// objがListだった時用
		List<Object> list = null;

		// 受け取ったObjectは何か？
		if (obj instanceof Map) {
			map = (Map<K, Object>) obj;
		} else if (obj instanceof Collection) {
			list = (List<Object>) obj;
		} else {
			// 上記以外の場合、objの子は無いのでnullを返す
			return null;
		}

		// 子を取得する為のkeyとindexの準備
		K key = getFirstKey(keys);
		int index = getIndex(key);

		// 子要素を取得
		Object child = null;
		if (map != null) {
			child = map.get(key);
		} else {
			if (index < list.size()) {
				child = list.get(index);
			}
		}

		List<K> childKeys = getChildKeys(keys);
		if (childKeys.isEmpty()) {
			// これ以上キーが指定されていないならばchildを返す
			return child;
		} else {
			if (child == null) {
				// 子がnullの場合
				if (lazy) {
					// 子がnullで自動的に要素を作らない場合はnullを返す
					return null;
				}
				// 子要素を自動的に作る場合
				K childKey = getFirstKey(childKeys);
				int childIndex = getIndex(childKey);
				if (childIndex != -1) {
					// 子要素はlistだと思われる
					child = newList();
				} else {
					// 子要素はmapだと思われる
					child = newMap();
				}
				// 親に追加
				if (map != null) {
					map.put(key, child);
				} else {
					list.add(child);
				}
			}
			return get(child, childKeys, lazy);
		}
	}

	private int getIndex(K key) {
		if (key != null && NumberUtils.isNumber(key.toString())) {
			// キーが数値の場合はlistかもしれないのでindex
			return Integer.valueOf(key.toString()).intValue();
		} else {
			return -1;
		}
	}

	@SuppressWarnings("unchecked")
	private Map<K, Object> newMap() {
		try {
			return ConstructorUtils.invokeConstructor(mapClass, NO_ARGS);
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException
				| InstantiationException e) {
			// デフォルトコンストラクタが無い場合など
			throw new IllegalArgumentException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private List<Object> newList() {
		try {
			return ConstructorUtils.invokeConstructor(listClass, NO_ARGS);
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException
				| InstantiationException e) {
			// デフォルトコンストラクタが無い場合など
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public String toString() {
		if (map == null) {
			return "null";
		}
		return map.toString();
	}

	@Override
	public int hashCode() {
		if (map == null) {
			return 0;
		}
		return map.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (map == other) {
			return true;
		} else if (map == null || other == null) {
			return false;
		}
		return map.equals(other);
	}
}
