package com.purejadeite.jadegreen.definition.option.range;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.util.RoughlyConverter;
import com.purejadeite.util.RoughlyMapUtils;

/**
 * 削除コンバーター
 *
 * @author mitsuhiroseino
 *
 */
public class Eliminate extends AbstractRangeConverter {

	private static final long serialVersionUID = -7665794968023216952L;

	private static final String CFG_KEY_ID = "keyId";

	private static final String CFG_OPERATOR = "operator";

	private static final String CFG_VALUE = "value";

	private static final String EQ = "==";

	private static final String NE = "!=";

	private static final String GT = ">";

	private static final String LT = "<";

	private static final String GE = ">=";

	private static final String LE = "<=";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_KEY_ID, CFG_VALUE };

	/**
	 * 削除キー
	 */
	protected String keyId;

	/**
	 * 比較演算子
	 */
	protected String operator;

	/**
	 * 削除条件値
	 */
	protected String value;

	/**
	 * コンストラクタ
	 *
	 * @param range
	 *            変換元の値を持つ定義
	 * @param config
	 *            コンバーターのコンフィグ
	 */
	public Eliminate(Map<String, Object> config) {
		super();
		this.validateConfig(config, CONFIG);
		this.keyId = RoughlyMapUtils.getString(config, CFG_KEY_ID);
		this.operator = RoughlyMapUtils.getString(config, CFG_OPERATOR, EQ);
		this.value = RoughlyMapUtils.getString(config, CFG_VALUE, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object applyImpl(List<Map<String, Object>> values) {
		List<Map<String, Object>> rows = new ArrayList<>();
		for (Map<String, Object> row : values) {
			Object value = row.get(keyId);
			if (EQ.equals(operator)) {
				if (value == this.value || (this.value != null && this.value.equals(value))) {
					continue;
				}
			} else if (NE.equals(operator)) {
				if ((this.value != null && !this.value.equals(value)) || (value != null && !value.equals(this.value))) {
					continue;
				}
			} else if (GT.equals(operator)) {
				BigDecimal num = RoughlyConverter.intoBigDecimal(value);
				BigDecimal thisNum = RoughlyConverter.intoBigDecimal(this.value);
				if (num != null && thisNum != null && num.compareTo(thisNum) > 0) {
					continue;
				}
				String str = RoughlyConverter.intoString(value);
				String thisStr = RoughlyConverter.intoString(this.value);
				if (str != null && thisStr != null && str.compareTo(thisStr) > 0) {
					continue;
				}
			} else if (GE.equals(operator)) {
				BigDecimal num = RoughlyConverter.intoBigDecimal(value);
				BigDecimal thisNum = RoughlyConverter.intoBigDecimal(this.value);
				if (num != null && thisNum != null && num.compareTo(thisNum) >= 0) {
					continue;
				}
				String str = RoughlyConverter.intoString(value);
				String thisStr = RoughlyConverter.intoString(this.value);
				if (str != null && thisStr != null && str.compareTo(thisStr) >= 0) {
					continue;
				}
			} else if (LT.equals(operator)) {
				BigDecimal num = RoughlyConverter.intoBigDecimal(value);
				BigDecimal thisNum = RoughlyConverter.intoBigDecimal(this.value);
				if (num != null && thisNum != null && num.compareTo(thisNum) < 0) {
					continue;
				}
				String str = RoughlyConverter.intoString(value);
				String thisStr = RoughlyConverter.intoString(this.value);
				if (str != null && thisStr != null && str.compareTo(thisStr) < 0) {
					continue;
				}
			} else if (LE.equals(operator)) {
				BigDecimal num = RoughlyConverter.intoBigDecimal(value);
				BigDecimal thisNum = RoughlyConverter.intoBigDecimal(this.value);
				if (num != null && thisNum != null && num.compareTo(thisNum) <= 0) {
					continue;
				}
				String str = RoughlyConverter.intoString(value);
				String thisStr = RoughlyConverter.intoString(this.value);
				if (str != null && thisStr != null && str.compareTo(thisStr) <= 0) {
					continue;
				}
			}
			rows.add(row);
		}
		return rows;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("keyId", keyId);
		map.put("operator", operator);
		map.put("value", value);
		return map;
	}

}