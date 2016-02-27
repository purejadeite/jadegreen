package com.purejadeite.jadegreen.option.cell;

import java.util.Map;

import com.purejadeite.jadegreen.content.ContentInterface;
import com.purejadeite.jadegreen.definition.DefinitionInterface;
import com.purejadeite.jadegreen.input.sxssf.SxssfUtils;

/**
 * 文字列を Integer へ変換するクラス
 * @author mitsuhiroseino
 *
 */
public class ToInteger extends AbstractStringCellOption {

	private static final long serialVersionUID = -1553240654783334091L;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public ToInteger(DefinitionInterface<?> definition, Map<String, Object> config) {
		super(definition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value, ContentInterface<?, ?> content) {
		return SxssfUtils.getInteger(value);
	}

}
