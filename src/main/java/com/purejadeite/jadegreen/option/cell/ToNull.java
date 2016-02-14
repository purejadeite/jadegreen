package com.purejadeite.jadegreen.option.cell;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.purejadeite.jadegreen.content.Content;
import com.purejadeite.jadegreen.definition.Definition;

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
	public ToNull(Definition<?> definition, Map<String, Object> config) {
		super(definition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value, Content<?, ?> content) {
		return StringUtils.defaultIfEmpty(value, null);
	}

}
