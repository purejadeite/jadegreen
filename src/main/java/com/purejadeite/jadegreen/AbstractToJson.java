package com.purejadeite.jadegreen;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

/**
*
* Excelファイル読み込みの定義情報抽象クラス
* @author mitsuhiroseino
*
*/
public abstract class AbstractToJson implements ToJson {

	/**
	 * JSON出力用のオブジェクトマッパー
	 */
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	/**
	 * デフォルトコンストラクタ
	 */
	protected AbstractToJson() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJson() {
		try {
			return OBJECT_MAPPER.writeValueAsString(toMap());
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return toMap().toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
//		Map<String, Object> map = new LinkedHashMap<>();
		Map<String, Object> map = new LMap<>();
		map.put("className", this.getClass().getName());
		return map;
	}

	static class LMap<K, V> extends LinkedHashMap<K, V> {
		public V put(K key,V value){
			if (value instanceof String) {
				value = (V)(((String) value) + "\r\n");
			}
			return super.put(key, value);
		}
	}

}
