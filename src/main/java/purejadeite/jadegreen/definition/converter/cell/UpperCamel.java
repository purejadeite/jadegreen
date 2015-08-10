package purejadeite.jadegreen.definition.converter.cell;

import java.util.Map;

import com.google.common.base.CaseFormat;

/**
*
* <pre>
* アッパーキャメル形式の文字列へ変換を行うクラス
* 例: "abcDef" -> "AbcDef"
* </pre>
* @author mitsuhiroseino
*
*/
public class UpperCamel extends AbstractCaseFormatConverter {

	private static final long serialVersionUID = 4377003507328772214L;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public UpperCamel(Map<String, Object> config) {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object convertImpl(String value) {
		return format(value, CaseFormat.UPPER_CAMEL);
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
