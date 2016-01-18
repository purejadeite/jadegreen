package com.purejadeite.jadegreen.definition.option.cell;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 文字列が空文字の場合nullへ変換するクラス
 * @author mitsuhiroseino
 *
 */
public class ToNull extends AbstractStringCellOption {

	private static final long serialVersionUID = 7289936917974505093L;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public ToNull(String id, Map<String, Object> config) {
		super(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value) {
		return StringUtils.defaultIfEmpty(value, null);
	}

}
