package purejadeite.jadegreen.definition.converter.cell;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * 文字列を変換する抽象クラスです
 * @author mitsuhiroseino
 *
 */
public abstract class AbstractNumberCellConverter extends AbstractCellConverter {

	private static final long serialVersionUID = 8428819225468846834L;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 */
	public AbstractNumberCellConverter() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object convertImpl(Object value) {
		if (value == null) {
			return convertImpl((Number) null);
		} else {
			return convertImpl(NumberUtils.createBigDecimal((String) value));
		}
	}

	/**
	 * 数値を変換します
	 * @param value 数値
	 * @return 変換された数値
	 */
	abstract protected Object convertImpl(Number value);

}
