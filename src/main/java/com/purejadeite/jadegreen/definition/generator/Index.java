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

	private int from = 0;

	private int increment = 1;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	@SuppressWarnings("unchecked")
	public Index(Map<String, Object> config) {
		super();
		from = MapUtils.getIntValue(config, "from");
		Object increment = config.get("increment");
		if (increment != null) {
			this.increment = Integer.parseInt(increment.toString());
		}
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
			int index = from;
			for (int i = 0; i < values.size(); i++) {
				vals.add(Integer.valueOf(index));
				index += increment;
			}
			return vals;
		} else {
			return Integer.valueOf(from);
		}
	}

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("from", from);
		map.put("increment", increment);
		return map;
	}
}
