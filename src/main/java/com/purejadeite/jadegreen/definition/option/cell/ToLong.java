package com.purejadeite.jadegreen.definition.option.cell;

import java.util.Map;

import com.purejadeite.jadegreen.SxssfUtils;
import com.purejadeite.jadegreen.content.Content;
import com.purejadeite.jadegreen.definition.Definition;

/**
 * 文字列を Long へ変換するクラス
 * @author mitsuhiroseino
 *
 */
public class ToLong extends AbstractStringCellOption {

	private static final long serialVersionUID = -3511954204763743901L;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public ToLong(Definition<?> definition, Map<String, Object> config) {
		super(definition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value, Content<?, ?> content) {
		return SxssfUtils.getFloat(value);
	}

}
