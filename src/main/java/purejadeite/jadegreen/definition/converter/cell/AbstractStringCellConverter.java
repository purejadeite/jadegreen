package purejadeite.jadegreen.definition.converter.cell;


/**
 * 文字列を変換する抽象クラスです
 * @author mitsuhiroseino
 *
 */
public abstract class AbstractStringCellConverter extends AbstractCellConverter {

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
