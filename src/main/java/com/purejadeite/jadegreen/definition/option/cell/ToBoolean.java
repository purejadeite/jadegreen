package com.purejadeite.jadegreen.definition.option.cell;

import java.util.Map;

import com.purejadeite.jadegreen.SxssfUtils;
import com.purejadeite.jadegreen.definition.Definition;

/**
 * 文字列を Boolean へ変換するクラス
 * @author mitsuhiroseino
 *
 */
public class ToBoolean extends AbstractStringCellOption {

	private static final long serialVersionUID = 1780810272134810268L;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public ToBoolean(Definition<?> definition, Map<String, Object> config) {
		super(definition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value) {
		return SxssfUtils.getBoolean(value);
	}

}
