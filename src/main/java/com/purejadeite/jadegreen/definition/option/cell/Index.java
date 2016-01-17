package com.purejadeite.jadegreen.definition.option.cell;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Indexを生成するクラス
 * @author mitsuhiroseino
 *
 */
public class Index extends AbstractRelatedValueGenerator {

	private static final long serialVersionUID = -9188313601218449958L;

	private static final String CFG_FROM = "from";

	private static final String CFG_INCREMENT = "increment";

	private int from = 0;

	private int increment = 1;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public Index(Map<String, Object> config) {
		super();
		from = getIntValue(config, CFG_FROM);
		Integer increment = getInteger(config, CFG_INCREMENT);
		if (increment != null) {
			this.increment = increment.intValue();
		}
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
