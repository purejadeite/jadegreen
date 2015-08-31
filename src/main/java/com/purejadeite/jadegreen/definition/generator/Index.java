package com.purejadeite.jadegreen.definition.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ランダムなUUIDを生成するクラス
 * @author mitsuhiroseino
 *
 */
public class Index extends AbstractValueGenerator {

	private static final Logger LOGGER = LoggerFactory.getLogger(Index.class);

	private int start = 0;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	@SuppressWarnings("unchecked")
	public Index(Map<String, Object> config) {
		super();
		start = MapUtils.getIntValue(config, "start");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object apply(Object value) {
		if (value instanceof Collection) {
			@SuppressWarnings("unchecked")
			Collection<Object> values = (Collection<Object>) value;
			Collection<Object> vals = new ArrayList<>();
			for (int i = start; i < values.size() + start; i++) {
				vals.add(Integer.valueOf(i));
			}
			return vals;
		} else {
			return Integer.valueOf(start);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + super.toString() + "]";
	}

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("name", this.getClass().getSimpleName());
		return map;
	}
}
