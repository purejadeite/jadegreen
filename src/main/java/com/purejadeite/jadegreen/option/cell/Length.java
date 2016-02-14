package com.purejadeite.jadegreen.option.cell;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.purejadeite.jadegreen.content.Content;
import com.purejadeite.jadegreen.definition.Definition;

/**
 * 文字列の長さを取得するクラス
 * @author mitsuhiroseino
 *
 */
public class Length extends AbstractStringCellOption {

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public Length(Definition<?> definition, Map<String, Object> config) {
		super(definition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value, Content<?, ?> content) {
		return Integer.valueOf(StringUtils.length(value));
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		return map;
	}

}
