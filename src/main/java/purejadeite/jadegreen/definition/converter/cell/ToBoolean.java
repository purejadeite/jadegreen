package purejadeite.jadegreen.definition.converter.cell;

import java.util.Map;

import purejadeite.jadegreen.reader.CellUtils;

/**
 * 文字列を Boolean へ変換するクラス
 * @author mitsuhiroseino
 *
 */
public class ToBoolean extends AbstractCellConverter {

	private static final long serialVersionUID = 2293861590083383337L;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public ToBoolean(Map<String, Object> config) {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object convertImpl(Object value) {
		return CellUtils.getBooleanValue((String) value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJson() {
		return "{" + super.toJson() + "}";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + super.toString() + "]";
	}

}
