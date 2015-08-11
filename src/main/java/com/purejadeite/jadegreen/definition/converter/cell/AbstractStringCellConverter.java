package com.purejadeite.jadegreen.definition.converter.cell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 文字列を変換する抽象クラスです
 * @author mitsuhiroseino
 *
 */
public abstract class AbstractStringCellConverter extends AbstractCellConverter {

	private static final long serialVersionUID = 6947506412389834748L;

	protected static Logger LOGGER;

	{
		LOGGER = LoggerFactory.getLogger(this.getClass());
	}

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
	public Object convertImpl(Object value) {
		if (value == null) {
			return convertImpl((String) null);
		} else {
			return convertImpl(value.toString());
		}
	}

	/**
	 * 文字列を変換します
	 * @param value 文字列の値
	 * @return 変換された値
	 */
	abstract protected Object convertImpl(String value);

}
