package com.purejadeite.jadegreen.option.cell;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.Map;

import com.purejadeite.jadegreen.content.ContentInterface;
import com.purejadeite.jadegreen.content.SpecificValue;
import com.purejadeite.jadegreen.definition.DefinitionInterface;
import com.purejadeite.util.EvaluationUtils;

/**
 * 条件に一致する場合に項目を削除するクラス
 * @author mitsuhiroseino
 *
 */
public class Remove extends AbstractStringCellOption {

	protected static final String CFG_OPERATOR = "operator";

	protected static final String CFG_VALUE = "value";

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
	public Remove(DefinitionInterface<?> definition, Map<String, Object> config) {
		super(definition);
		this.operator = getString(config, CFG_OPERATOR, "==");
		this.value = getString(config, CFG_VALUE, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value, ContentInterface<?, ?> content) {
		if (EvaluationUtils.evaluate(value, operator, this.value)) {
			return SpecificValue.UNDEFINED;
		}
		return value;
	}

}
