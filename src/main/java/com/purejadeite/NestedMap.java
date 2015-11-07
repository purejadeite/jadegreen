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

	private static final Object[] NO_ARGS = new Object[0];

	@SuppressWarnings("rawtypes")
	protected static final Class<? extends Map> MAP_CLASS = HashMap.class;

	@SuppressWarnings("rawtypes")
	protected static final Class<? extends List> LIST_CLASS = ArrayList.class;

	protected boolean lazy = false;

	@SuppressWarnings("rawtypes")
	protected Class<? extends Map> mapClass;

	@SuppressWarnings("rawtypes")
	protected Class<? extends List> listClass;

	public NestedMap(Map<K, Object> map) {
		this(map, false, MAP_CLASS, LIST_CLASS);
	}

	public NestedMap(Map<K, Object> map, boolean lazy) {
		this(map, lazy, MAP_CLASS, LIST_CLASS);
	}

	@SuppressWarnings("rawtypes")
	public NestedMap(Map<K, Object> map, boolean lazy, Class<? extends Map> mapClass) {
		this(map, lazy, mapClass, LIST_CLASS);
	}

	@SuppressWarnings("rawtypes")
	public NestedMap(Map<K, Object> map, boolean lazy, Class<? extends Map> mapClass, Class<? extends List> listClass) {
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
		if (keys == null || keys.isEmpty()) {
			return null;
		}
		Object parent = get(map, getParentKeys(keys), false);
		return getChild(parent, getLastKey(keys));
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
		if (keys == null || keys.isEmpty()) {
			return null;
		}
		List<K> parentKeys = getParentKeys(keys);
		Object parent = getObject(map, parentKeys);
		if (parent == null) {
			if (lazy) {
				parent = newMap();
				Object grandparent = getObject(map, getParentKeys(parentKeys), false);
				setChild(grandparent, getLastKey(parentKeys), parent);
			} else {
				return null;
			}
		}
		return setChild(parent, getLastKey(keys), value);
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
		if (keys == null || keys.isEmpty()) {
			return false;
		}
		return add(map, keys, value);
	}

	@Override
	public Object remove(Object key) {
		return remove(getKeys(key));
	}

	public Object remove(List<K> keys) {
		if (keys == null || keys.isEmpty()) {
			return null;
		}
		Object parent = getObject(map, getParentKeys(keys), false);
		return removeChild(parent, getLastKey(keys));
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
		Object parent = getObject(obj, getParentKeys(keys), lazy);
		if (parent != null) {
			K key = getLastKey(keys);
			Object child = getChild(parent, key);
			Collection<Object> collection = null;
			if (child instanceof Collection) {
				collection = (Collection<Object>) child;
				collection.add(value);
				return true;
			} else {
				collection = newList();
				collection.add(value);
				setChild(parent, key, collection);
				return true;
			}
		}
		return false;
	}

	public Map<K, Object> getMap() {
		return map;
	}

	protected Object getObject(Object obj, List<K> keys) {
		return getObject(obj, keys, lazy);
	}

	protected Object getObject(Object obj, List<K> keys, boolean lazy) {
		return get(obj, keys, lazy);
	}

	protected Map<K, Object> getMap(Object obj, List<K> keys) {
		return getMap(obj, keys, lazy);
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

	private List<K> getParentKeys(List<K> keys) {
		if (keys.isEmpty()) {
			return null;
		}
		return keys.subList(0, keys.size() - 1);
	}

	private List<K> getChildKeys(List<K> keys) {
		if (keys.isEmpty()) {
			return null;
		}
		return keys.subList(1, keys.size());
	}

	private K getFirstKey(List<K> keys) {
		if (keys.isEmpty()) {
			return null;
		}
		return keys.get(0);
	}

	private K getLastKey(List<K> keys) {
		if (keys.isEmpty()) {
			return null;
		}
		return keys.get(keys.size() - 1);
	}

	protected Object get(Object obj, List<K> keys) {
		return get(obj, keys, lazy);
	}

	/**
	 * objの子要素を取得します
	 *
	 * @param map
	 * @param keys
	 * @return
	 */
	protected Object get(Object obj, List<K> keys, boolean lazy) {
		if (keys.isEmpty()) {
			return obj;
		}
		// 子を取得する為のkey
		K key = getFirstKey(keys);
		// 子要素を取得
		Object child = getChild(obj, key);
		// さらに配下のキー
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
				setChild(obj, childKey, child);
			}
			return get(child, childKeys, lazy);
		}
	}

	protected int getIndex(K key) {
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

	private Object getChild(Object parent, K key) {
		if (parent instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<K, Object> map = (Map<K, Object>) parent;
			return map.get(key);
		} else if (parent instanceof List) {
			int index = getIndex(key);
			if (index != -1) {
				@SuppressWarnings("unchecked")
				List<Object> list = (List<Object>) parent;
				if (index < list.size()) {
					return list.get(index);
				}
			}
		}
		return null;
	}

	private Object setChild(Object parent, K key, Object child) {
		if (parent instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<K, Object> map = (Map<K, Object>) parent;
			return map.put(key, child);
		} else if (parent instanceof List) {
			int index = getIndex(key);
			if (index != -1) {
				@SuppressWarnings("unchecked")
				List<Object> list = (List<Object>) parent;
				while (list.size() < index + 1) {
					list.add(null);
				}
				return list.set(index, child);
			}
		}
		return null;
	}

	private Object removeChild(Object parent, K key) {
		if (parent instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<K, Object> map = (Map<K, Object>) parent;
			return map.remove(key);
		} else if (parent instanceof List) {
			int index = getIndex(key);
			if (index != -1) {
				@SuppressWarnings("unchecked")
				List<Object> list = (List<Object>) parent;
				list.remove(index);
			}
		}
		return null;
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
