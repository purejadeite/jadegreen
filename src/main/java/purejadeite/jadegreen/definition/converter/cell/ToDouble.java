package purejadeite.jadegreen.definition.converter.cell;

import java.util.Map;

import purejadeite.jadegreen.reader.CellUtils;

/**
 * 文字列を Double へ変換するクラス
 * @author mitsuhiroseino
 *
 */
public class ToDouble extends AbstractCellConverter {

	private static final long serialVersionUID = 6095833065569388637L;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public ToDouble(Map<String, Object> config) {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object convertImpl(Object value) {
		return CellUtils.getDoubleValue((String) value);
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
