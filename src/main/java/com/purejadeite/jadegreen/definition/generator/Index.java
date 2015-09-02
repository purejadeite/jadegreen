package com.purejadeite.jadegreen.definition.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ランダムなUUIDを生成するクラス
 * @author mitsuhiroseino
 *
 */
public class Index extends AbstractRelatedValueGenerator {

	private static final Logger LOGGER = LoggerFactory.getLogger(Index.class);

	private int startAt = 0;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	@SuppressWarnings("unchecked")
	public Index(Map<String, Object> config) {
		super();
		startAt = MapUtils.getIntValue(config, "startAt");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object generate(Object value) {
		if (value instanceof Collection) {
			@SuppressWarnings("unchecked")
			Collection<Object> values = (Collection<Object>) value;
			Collection<Object> vals = new ArrayList<>();
			for (int i = startAt; i < values.size() + startAt; i++) {
				vals.add(Integer.valueOf(i));
			}
			return vals;
		} else {
			return Integer.valueOf(startAt);
		}
	}

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("startAt", startAt);
		return map;
	}
}
