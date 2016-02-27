package com.purejadeite.jadegreen.option.cell;

import java.util.Map;

import com.purejadeite.jadegreen.content.ContentInterface;
import com.purejadeite.jadegreen.definition.DefinitionInterface;
import com.purejadeite.jadegreen.input.sxssf.SxssfUtils;

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
	public ToBoolean(DefinitionInterface<?> definition, Map<String, Object> config) {
		super(definition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value, ContentInterface<?, ?> content) {
		return SxssfUtils.getBoolean(value);
	}

}
