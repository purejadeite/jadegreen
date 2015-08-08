package purejadeite.jadegreen.definition.converter.cell;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 文字列の前後に文字列を追加するクラス
 * @author mitsuhiroseino
 *
 */
public class AddText extends AbstractStringCellConverter {

	/**
	 * 接頭語
	 */
	private String prefix;

	/**
	 * 接尾語
	 */
	private String suffix;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public AddText(Map<String, Object> config) {
		super();
		this.prefix = (String) config.get("prefix");
		this.suffix = (String) config.get("suffix");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object convertImpl(String value) {
		if (!StringUtils.isEmpty(prefix)) {
			value = prefix + value;
		}
		if (!StringUtils.isEmpty(suffix)) {
			value = value + suffix;
		}
		return value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJson() {
		return "{" + super.toJson() + "," + getJson("prefix", prefix) + "," + getJson("suffix", suffix) + "}";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + super.toString() + ", prefix=" + prefix + ", suffix=" + suffix
				+ "]";
	}

}
