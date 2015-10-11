package com.purejadeite.jadegreen.definition.option.cell;

/**
 * 文字列を変換する抽象クラスです
 * @author mitsuhiroseino
 *
 */
abstract public class AbstractStringCellConverter extends AbstractCellConverter {

	private static final long serialVersionUID = -6680013687885034863L;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 */
	public AbstractStringCellConverter() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyImpl(Object value) {
		if (value == null) {
			return applyToString((String) null);
		} else {
			return applyToString(value.toString());
		}
	}

	/**
	 * 文字列を変換します
	 * @param value 文字列の値
	 * @return 変換された値
	 */
	abstract protected Object applyToString(String value);

}
