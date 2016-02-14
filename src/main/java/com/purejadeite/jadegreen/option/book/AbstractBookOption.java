package com.purejadeite.jadegreen.option.book;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.content.Content;
import com.purejadeite.jadegreen.content.SpecificValue;
import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.option.AbstractOption;

/**
 * Bookの値を変換する抽象クラス
 *
 * @author mitsuhiroseino
 */
abstract public class AbstractBookOption extends AbstractOption implements BookOption, Serializable {

	/**
	 * コンストラクタ
	 */
	public AbstractBookOption(Definition<?> definition) {
		super(definition);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object apply(Object values, Content<?, ?> content) {
		if (values == SpecificValue.UNDEFINED) {
			return values;
		}
		return applyImpl((List<Map<String, Object>>) values, content);
	}

	/**
	 * テーブルの変換を行います
	 *
	 * @param values
	 *            変換前のテーブル
	 * @return 変換後のテーブル
	 */
	abstract protected Object applyImpl(List<Map<String, Object>> values, Content<?, ?> content);

	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("name", this.getClass().getSimpleName());
		return map;
	}
}