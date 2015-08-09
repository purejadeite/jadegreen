package purejadeite.jadegreen.definition.converter.cell;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 指定された区切り文字で文字列を分割するクラス
 * @author mitsuhiroseino
 *
 */
public class Split extends AbstractStringCellConverter {

	/**
	 * 区切り文字
	 */
	private String splitter;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public Split(Map<String, Object> config) {
		super();
		this.splitter = (String) config.get("splitter");
	}

	/**
	 * {@inheritDoc}
	 */
	protected Object convertImpl(String value) {
		String[] values = StringUtils.split(value, splitter);
		return (Object) Arrays.asList(values);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJson() {
		return "{" + super.toJson() + "," + getJson("splitter", splitter) + "}";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + super.toString() + ", splitter=" + splitter + "]";
	}
}
