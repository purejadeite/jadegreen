package com.purejadeite.jadegreen.definition.option.cell;

import java.util.Map;

import com.purejadeite.jadegreen.content.SpecificValue;
import com.purejadeite.util.EvaluationUtils;
import com.purejadeite.util.RoughlyMapUtils;

/**
 * 条件に一致する場合に項目を削除するクラス
 * @author mitsuhiroseino
 *
 */
public class Remove extends AbstractStringCellConverter {

	private static final String CFG_OPERATOR = "operator";

	private static final String CFG_VALUE = "value";

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
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public Remove(Map<String, Object> config) {
		super();
		this.operator = RoughlyMapUtils.getString(config, CFG_OPERATOR, "==");
		this.value = RoughlyMapUtils.getString(config, CFG_VALUE, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value) {
		if (EvaluationUtils.evaluate(value, operator, this.value)) {
			return value;
		}
		return SpecificValue.UNDEFINED;
	}

}
