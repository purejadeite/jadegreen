package com.purejadeite.jadegreen.definition.option.sheet;

import java.io.Serializable;
import java.util.Map;

import com.purejadeite.jadegreen.content.Content;
import com.purejadeite.jadegreen.content.SpecificValue;
import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.option.AbstractOption;

/**
 * Sheetの値を変換する抽象クラス
 *
 * @author mitsuhiroseino
 */
abstract public class AbstractSheetOption extends AbstractOption implements SheetOption, Serializable {

	/**
	 * コンストラクタ
	 */
	public AbstractSheetOption(Definition<?> definition) {
		super(definition);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object apply(Object values, Content<?, ?> content) {
		if (values == SpecificValue.UNDEFINED) {
			return values;
		}
		return applyImpl((Map<String, Object>) values, content);
	}

	/**
	 * テーブルの変換を行います
	 *
	 * @param values
	 *            変換前のテーブル
	 * @return 変換後のテーブル
	 */
	abstract protected Object applyImpl(Map<String, Object> values, Content<?, ?> content);

	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("name", this.getClass().getSimpleName());
		return map;
	}
}
