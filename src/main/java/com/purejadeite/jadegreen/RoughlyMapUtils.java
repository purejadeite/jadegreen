package com.purejadeite.jadegreen;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * 値がObjectのMap用ユーティリティ
 *
 * @author mitsuhiroseino
 *
 */
public class RoughlyMapUtils {

	@SuppressWarnings("unchecked")
	public static <K, V extends Object> Map<K, V> getMap(Map<K, ? extends Object> map, K key) {
		Object value = map.get(key);
		if (value instanceof Map) {
			return (Map<K, V>) value;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <K, V extends Object> List<V> getList(Map<K, ? extends Object> map, K key) {
		Object value = map.get(key);
		if (value instanceof List) {
			return (List<V>) value;
		}
		return null;
	}

	public static <K> String getString(Map<K, ? extends Object> map, K key) {
		Object value = map.get(key);
		if (value instanceof CharSequence) {
			return value.toString();
		}
		return null;
	}

	public static <K> char[] getChar(Map<K, ? extends Object> map, K key) {
		String value = getString(map, key);
		if (value == null) {
			char[] val = {};
			return val;
		}
		return value.toCharArray();
	}

	public static <K> Boolean getBoolean(Map<K, ? extends Object> map, K key) {
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

	public static <K> boolean getBooleanValue(Map<K, ? extends Object> map, K key) {
		Boolean value = getBoolean(map, key);
		if (value == null) {
			return false;
		}
		return value.booleanValue();
	}

	public static <K> Number getNumber(Map<K, ? extends Object> map, K key) {
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

	public static <K> Byte getByte(Map<K, ? extends Object> map, K key) {
		Number value = getNumber(map, key);
		if (value == null) {
			return null;
		}
		return Byte.valueOf(value.byteValue());
	}

	public static <K> byte getByteValue(Map<K, ? extends Object> map, K key) {
		Number value = getNumber(map, key);
		if (value == null) {
			return 0;
		}
		return value.byteValue();
	}

	public static <K> Integer getInteger(Map<K, ? extends Object> map, K key) {
		Number value = getNumber(map, key);
		if (value == null) {
			return null;
		}
		return Integer.valueOf(value.intValue());
	}

	public static <K> int getIntValue(Map<K, ? extends Object> map, K key) {
		Number value = getNumber(map, key);
		if (value == null) {
			return 0;
		}
		return value.intValue();
	}

	public static <K> Short getShort(Map<K, ? extends Object> map, K key) {
		Number value = getNumber(map, key);
		if (value == null) {
			return null;
		}
		return Short.valueOf(value.shortValue());
	}

	public static <K> short getShortValue(Map<K, ? extends Object> map, K key) {
		Number value = getNumber(map, key);
		if (value == null) {
			return 0;
		}
		return value.shortValue();
	}

	public static <K> Long getLong(Map<K, ? extends Object> map, K key) {
		Number value = getNumber(map, key);
		if (value == null) {
			return null;
		}
		return Long.valueOf(value.longValue());
	}

	public static <K> long getLongValue(Map<K, ? extends Object> map, K key) {
		Number value = getNumber(map, key);
		if (value == null) {
			return 0;
		}
		return value.longValue();
	}

	public static <K> Float getFloat(Map<K, ? extends Object> map, K key) {
		Number value = getNumber(map, key);
		if (value == null) {
			return null;
		}
		return Float.valueOf(value.floatValue());
	}

	public static <K> float getFloatValue(Map<K, ? extends Object> map, K key) {
		Number value = getNumber(map, key);
		if (value == null) {
			return 0;
		}
		return value.floatValue();
	}

	public static <K> Double getDouble(Map<K, ? extends Object> map, K key) {
		Number value = getNumber(map, key);
		if (value == null) {
			return null;
		}
		return Double.valueOf(value.doubleValue());
	}

	public static <K> double getDoubleValue(Map<K, ? extends Object> map, K key) {
		Number value = getNumber(map, key);
		if (value == null) {
			return 0;
		}
		return value.doubleValue();
	}

	public static <K> BigDecimal getBigDecimal(Map<K, ? extends Object> map, K key) {
		Object value = map.get(key);
		if (value == null) {
			return null;
		}
		try {
			return new BigDecimal(value.toString());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static <K> BigInteger getBigInteger(Map<K, ? extends Object> map, K key) {
		Object value = map.get(key);
		if (value == null) {
			return null;
		}
		try {
			return new BigInteger(value.toString());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static <K> Date getDate(Map<K, ? extends Object> map, K key) {
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

	public static <K> java.sql.Date getSqlDate(Map<K, ? extends Object> map, K key) {
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

	public static <K> Timestamp getTimestamp(Map<K, ? extends Object> map, K key) {
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
}
