package com.purejadeite.jadegreen.option.cell;

import com.purejadeite.jadegreen.content.ContentInterface;
import com.purejadeite.jadegreen.definition.DefinitionInterface;

/**
 * 文字列を変換する抽象クラスです
 * @author mitsuhiroseino
 *
 */
abstract public class AbstractStringCellOption extends AbstractCellOption {

	private static final long serialVersionUID = -6680013687885034863L;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 */
	public AbstractStringCellOption(DefinitionInterface<?> definition) {
		super(definition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyImpl(Object value, ContentInterface<?, ?> content) {
		if (value == null) {
			return applyToString((String) null, content);
		} else {
			return applyToString(value.toString(), content);
		}
	}

	/**
	 * 文字列を変換します
	 * @param value 文字列の値
	 * @return 変換された値
	 */
	abstract protected Object applyToString(String value, ContentInterface<?, ?> content);

}
