package com.purejadeite.jadegreen.option.cell;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.content.Content;
import com.purejadeite.jadegreen.content.SpecificValue;
import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.option.AbstractOption;

/**
 * 値を追加するクラス
 *
 * @author mitsuhiroseino
 *
 */
public class AddValue extends AbstractOption implements CellOption, Serializable {

	protected static final String CFG_UNIQUE = "unique";

	protected static final String CFG_VALUE = "value";

	/**
	 * 一意
	 */
	protected boolean unique;
	protected String value;

	/**
	 * コンストラクタ
	 *
	 * @param cell
	 *            値の取得元Cell読み込み定義
	 * @param config
	 *            コンバーターのコンフィグ
	 */
	public AddValue(Definition<?> definition, Map<String, Object> config) {
		super(definition);
		this.unique = getBooleanValue(config, CFG_UNIQUE);
		this.value = getString(config, CFG_VALUE);
	}

	@SuppressWarnings("unchecked")
	public Object apply(Object value, Content<?, ?> content) {
		if (value == SpecificValue.UNDEFINED) {
			return value;
		}
		List<Object> values = null;
		if (value instanceof List) {
			values = (List<Object>) value;
		} else {
			values = new ArrayList<>();
			values.add(value);
		}
		if (!unique || !values.contains(this.value)) {
			values.add(this.value);
		}
		return values;
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("unique", unique);
		return map;
	}

}
