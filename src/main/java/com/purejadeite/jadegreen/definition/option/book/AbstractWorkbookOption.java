package com.purejadeite.jadegreen.definition.option.book;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.option.AbstractOption;

/**
 * Workbookの値を変換する抽象クラス
 *
 * @author mitsuhiroseino
 */
abstract public class AbstractWorkbookOption extends AbstractOption implements BookOption, Serializable {

	/**
	 * コンストラクタ
	 */
	public AbstractWorkbookOption(Definition<?> definition) {
		super(definition);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object apply(Object values) {
		return applyImpl((List<Map<String, Object>>) values);
	}

	/**
	 * テーブルの変換を行います
	 *
	 * @param values
	 *            変換前のテーブル
	 * @return 変換後のテーブル
	 */
	abstract protected Object applyImpl(List<Map<String, Object>> values);

	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("name", this.getClass().getSimpleName());
		return map;
	}
}
