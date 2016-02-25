package com.purejadeite.jadegreen.option.cell;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;

import com.purejadeite.jadegreen.content.Content;
import com.purejadeite.jadegreen.definition.Definition;

/**
 * 文字列を適切な型に変換するクラス
 * @author mitsuhiroseino
 *
 */
public class To extends AbstractStringCellOption {

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public To(Definition<?> definition, Map<String, Object> config) {
		super(definition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value, Content<?, ?> content) {
		if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
			return Boolean.valueOf(value);
		} else if (NumberUtils.isNumber(value)) {
			return new BigDecimal(value);
		} else {
			return value;
		}
	}

}
