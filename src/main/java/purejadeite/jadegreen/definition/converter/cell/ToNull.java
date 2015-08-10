package purejadeite.jadegreen.definition.converter.cell;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 文字列が空文字の場合nullへ変換するクラス
 * @author mitsuhiroseino
 *
 */
public class ToNull extends AbstractStringCellConverter {

	private static final long serialVersionUID = -8616341169957203342L;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public ToNull(Map<String, Object> config) {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object convertImpl(String value) {
		return StringUtils.defaultIfEmpty(value, null);
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
