package com.purejadeite.jadegreen.definition.option.cell;

import java.util.Map;

import com.purejadeite.jadegreen.SxssfUtils;
import com.purejadeite.jadegreen.definition.Definition;

/**
 * 文字列を Double へ変換するクラス
 * @author mitsuhiroseino
 *
 */
public class ToDouble extends AbstractStringCellOption {

	private static final long serialVersionUID = 6915138341525556829L;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public ToDouble(Definition<?> definition, Map<String, Object> config) {
		super(definition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value) {
		return SxssfUtils.getDouble(value);
	}

}
