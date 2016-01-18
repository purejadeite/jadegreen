package com.purejadeite.jadegreen.definition.option.table;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.option.AbstractOption;

/**
 * Tableの値を変換する抽象クラス
 *
 * @author mitsuhiroseino
 */
abstract public class AbstractTableOption extends AbstractOption implements TableOption, Serializable {

	private static final long serialVersionUID = -2315365183856254349L;

	/**
	 * コンストラクタ
	 */
	public AbstractTableOption(String id) {
		super(id);
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
