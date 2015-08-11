package com.purejadeite.jadegreen.definition.converter.cell;

import java.util.Map;

import com.purejadeite.jadegreen.reader.CellUtils;

/**
 * 文字列を Long へ変換するクラス
 * @author mitsuhiroseino
 *
 */
public class ToLong extends AbstractStringCellConverter {

	private static final long serialVersionUID = -5403625877786973962L;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public ToLong(Map<String, Object> config) {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object convertImpl(String value) {
		return CellUtils.getFloatValue(value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJson() {
		return "{" + super.toJson() + "}";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + super.toString() + "]";
	}

}
