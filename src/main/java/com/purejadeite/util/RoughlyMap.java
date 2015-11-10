package com.purejadeite.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * 値がObjectのMap用ユーティリティ
 *
 * @author mitsuhiroseino
 *
 */
public class RoughlyMap<K> implements Map<K, Object> {

	protected Map<K, Object> map;

	public RoughlyMap(Map<K, Object> map) {
		this.map = map;
	}

	public Map<K, Object> getMap() {
		return map;
	}

	/**
	 * <pre>
	 * Mapから値をMapで取得します。
	 * 値がMapでない場合はnullを返します。
	 * </pre>
	 *
	 * @param map
	 *            対象のMap
	 * @param key
	 *            キー
	 * @return Map形式の値
	 */
	@SuppressWarnings("unchecked")
	public <V extends Object> Map<K, V> getMap(K key) {
		Object value = map.get(key);
		if (value instanceof Map) {
			return (Map<K, V>) value;
		}
		return null;
	}

	/**
	 * <pre>
	 * Mapから値をListで取得します。
	 * 値がListでない場合はnullを返します。
	 * </pre>
	 *
	 * @param map
	 *            対象のMap
	 * @param key
	 *            キー
	 * @return List形式の値
	 */
	@SuppressWarnings("unchecked")
	public <V extends Object> List<V> getList(K key) {
		Object value = map.get(key);
		if (value instanceof List) {
			return (List<V>) value;
		}
		return null;
	}

	/**
	 * <pre>
	 * Mapから値をStringで取得します。
	 * 値がStringでない場合はnullを返します。
	 * </pre>
	 *
	 * @param map
	 *            対象のMap
	 * @param key
	 *            キー
	 * @return String形式の値
	 */
	public String getString(K key) {
		Object value = map.get(key);
		if (value instanceof CharSequence) {
			return value.toString();
		}
		return null;
	}

	/**
	 * <pre>
	 * Mapから値をBooleanで取得します。
	 * 値がBooleanの場合はその値を、
	 * Stringの場合はBoolean.valueOfで取得できる値を、
	 * Numberの場合は0をfalse、それ以外をtrueとした値を、
	 * それ以外はnullを返します。
	 * </pre>
	 *
	 * @param map
	 *            対象のMap
	 * @param key
	 *            キー
	 * @return Boolean形式の値
	 */
	public Boolean getBoolean(K key) {
		Object value = map.get(key);
		if (value instanceof Boolean) {
			return (Boolean) value;
		} else if (value instanceof CharSequence) {
			return Boolean.valueOf(value.toString());
		} else if (value instanceof Number) {
			boolean val = ((Number) value).intValue() == 0 ? false : true;
			return Boolean.valueOf(val);
		}
		return null;
	}

	/**
	 * <pre>
	 * Mapから値をbooleanで取得します。
	 * getBooleanで取得できた値がnullの場合はfalseを、
	 * それ以外の場合は取得できた値をbooleanに変換した値を返します。
	 * </pre>
	 *
	 * @param map
	 *            対象のMap
	 * @param key
	 *            キー
	 * @return boolean形式の値
	 */
	public boolean getBooleanValue(K key) {
		return getBooleanValue(key, false);
	}

	/**
	 * <pre>
	 * Mapから値をNumberで取得します。
	 * Numberの場合はその値を、
	 * CharSequenceの場合はNumberFormatでパースした値を、
	 * パースに失敗した場合および上記以外はnullを返します。
	 * </pre>
	 *
	 * @param map
	 *            対象のMap
	 * @param key
	 *            キー
	 * @return Number形式の値
	 */
	public Number getNumber(K key) {
		Object value = map.get(key);
		if (value instanceof Number) {
			return (Number) value;
		} else if (value instanceof CharSequence) {
			String str = value.toString();
			try {
				return NumberFormat.getInstance().parse(str);
			} catch (ParseException e) {
				return null;
			}
		}
		return null;
	}

	/**
	 * <pre>
	 * Mapから値をByteで取得します。
	 * 値がByteでない場合はnullを返します。
	 * </pre>
	 *
	 * @param map
	 *            対象のMap
	 * @param key
	 *            キー
	 * @return Byte形式の値
	 */
	public Byte getByte(K key) {
		Number value = getNumber(key);
		if (value == null) {
			return null;
		} else if (value instanceof Byte) {
			return (Byte) value;
		}
		return Byte.valueOf(value.byteValue());
	}

	/**
	 * <pre>
	 * Mapから値をbyteで取得します。
	 * getByteで取得できた値がnullの場合は0を、
	 * それ以外の場合は取得できた値をbyteに変換した値を返します。
	 * </pre>
	 *
	 * @param map
	 *            対象のMap
	 * @param key
	 *            キー
	 * @return Byte形式の値
	 */
	public byte getByteValue(K key) {
		return getByteValue(key, (byte) 0);
	}

	public Integer getInteger(K key) {
		Number value = getNumber(key);
		if (value == null) {
			return null;
		} else if (value instanceof Integer) {
			return (Integer) value;
		}
		return Integer.valueOf(value.intValue());
	}

	public int getIntValue(K key) {
		return getIntValue(key, 0);
	}

	public Short getShort(K key) {
		Number value = getNumber(key);
		if (value == null) {
			return null;
		} else if (value instanceof Short) {
			return (Short) value;
		}
		return Short.valueOf(value.shortValue());
	}

	public short getShortValue(K key) {
		return getShortValue(key, (short) 0);
	}

	public Long getLong(K key) {
		Number value = getNumber(key);
		if (value == null) {
			return null;
		} else if (value instanceof Long) {
			return (Long) value;
		}
		return Long.valueOf(value.longValue());
	}

	public long getLongValue(K key) {
		return getLongValue(key, 0L);
	}

	public Float getFloat(K key) {
		Number value = getNumber(key);
		if (value == null) {
			return null;
		} else if (value instanceof Float) {
			return (Float) value;
		}
		return Float.valueOf(value.floatValue());
	}

	public float getFloatValue(K key) {
		return getFloatValue(key, 0F);
	}

	public Double getDouble(K key) {
		Number value = getNumber(key);
		if (value == null) {
			return null;
		} else if (value instanceof Double) {
			return (Double) value;
		}
		return Double.valueOf(value.doubleValue());
	}

	public double getDoubleValue(K key) {
		return getDoubleValue(key, 0D);
	}

	public BigDecimal getBigDecimal(K key) {
		Object value = map.get(key);
		if (value == null) {
			return null;
		} else if (value instanceof BigDecimal) {
			return (BigDecimal) value;
		}
		try {
			return new BigDecimal(value.toString());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public BigInteger getBigInteger(K key) {
		Object value = map.get(key);
		if (value == null) {
			return null;
		} else if (value instanceof BigInteger) {
			return (BigInteger) value;
		}
		try {
			return new BigInteger(value.toString());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public Date getDate(K key) {
		Object value = map.get(key);
		if (value == null) {
			return null;
		} else if (value instanceof Date) {
			return (Date) value;
		} else if (value instanceof CharSequence) {
			try {
				return DateFormat.getInstance().parse(value.toString());
			} catch (ParseException e) {
				return null;
			}
		}
		return null;
	}

	public java.sql.Date getSqlDate(K key) {
		Object value = map.get(key);
		if (value == null) {
			return null;
		} else if (value instanceof java.sql.Date) {
			return (java.sql.Date) value;
		} else if (value instanceof CharSequence) {
			try {
				return java.sql.Date.valueOf(value.toString());
			} catch (IllegalArgumentException e) {
				return null;
			}
		}
		return null;
	}

	public Timestamp getTimestamp(K key) {
		Object value = map.get(key);
		if (value == null) {
			return null;
		} else if (value instanceof Timestamp) {
			return (Timestamp) value;
		} else if (value instanceof CharSequence) {
			try {
				return Timestamp.valueOf(value.toString());
			} catch (IllegalArgumentException e) {
				return null;
			}
		}
		return null;
	}

	public <V extends Object> Map<K, V> getMap(K key, Map<K, V> dflt) {
		Map<K, V> value = getMap(key);
		return value == null ? dflt : value;
	}

	public <V extends Object> List<V> getList(K key, List<V> dflt) {
		List<V> value = getList(key);
		return value == null ? dflt : value;
	}

	public String getString(K key, String dflt) {
		String value = getString(key);
		return value == null ? dflt : value;
	}

	public Boolean getBoolean(K key, Boolean dflt) {
		Boolean value = getBoolean(key);
		return value == null ? dflt : value;
	}

	public boolean getBooleanValue(K key, boolean dflt) {
		Boolean value = getBoolean(key);
		if (value == null) {
			return dflt;
		}
		return value.booleanValue();
	}

	public Number getNumber(K key, Number dflt) {
		Number value = getNumber(key);
		return value == null ? dflt : value;
	}

	public Byte getByte(K key, Byte dflt) {
		Byte value = getByte(key);
		return value == null ? dflt : value;
	}

	public byte getByteValue(K key, byte dflt) {
		Number value = getNumber(key);
		if (value == null) {
			return dflt;
		}
		return value.byteValue();
	}

	public Integer getInteger(K key, Integer dflt) {
		Integer value = getInteger(key);
		return value == null ? dflt : value;
	}

	public int getIntValue(K key, int dflt) {
		Number value = getNumber(key);
		if (value == null) {
			return dflt;
		}
		return value.intValue();
	}

	public Short getShort(K key, Short dflt) {
		Short value = getShort(key);
		return value == null ? dflt : value;
	}

	public short getShortValue(K key, Short dflt) {
		Number value = getNumber(key);
		if (value == null) {
			return dflt;
		}
		return value.shortValue();
	}

	public Long getLong(K key, Long dflt) {
		Long value = getLong(key);
		return value == null ? dflt : value;
	}

	public long getLongValue(K key, long dflt) {
		Number value = getNumber(key);
		if (value == null) {
			return dflt;
		}
		return value.longValue();
	}

	public Float getFloat(K key, Float dflt) {
		Float value = getFloat(key);
		return value == null ? dflt : value;
	}

	public float getFloatValue(K key, float dflt) {
		Number value = getNumber(key);
		if (value == null) {
			return dflt;
		}
		return value.floatValue();
	}

	public Double getDouble(K key, Double dflt) {
		Double value = getDouble(key);
		return value == null ? dflt : value;
	}

	public double getDoubleValue(K key, double dflt) {
		Number value = getNumber(key);
		if (value == null) {
			return dflt;
		}
		return value.doubleValue();
	}

	public BigDecimal getBigDecimal(K key, BigDecimal dflt) {
		BigDecimal value = getBigDecimal(key);
		return value == null ? dflt : value;
	}

	public BigInteger getBigInteger(K key, BigInteger dflt) {
		BigInteger value = getBigInteger(key);
		return value == null ? dflt : value;
	}

	public Date getDate(K key, Date dflt) {
		Date value = getDate(key);
		return value == null ? dflt : value;
	}

	public java.sql.Date getSqlDate(K key, java.sql.Date dflt) {
		java.sql.Date value = getSqlDate(key);
		return value == null ? dflt : value;
	}

	public Timestamp getTimestamp(K key, Timestamp dflt) {
		Timestamp value = getTimestamp(key);
		return value == null ? dflt : value;
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
		return map.get(key);
	}

	@Override
	public Object put(K key, Object value) {
		return map.put(key, value);
	}

	@Override
	public Object remove(Object key) {
		return map.remove(key);
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

}
