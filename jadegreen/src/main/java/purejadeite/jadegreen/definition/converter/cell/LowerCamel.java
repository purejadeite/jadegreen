package purejadeite.jadegreen.definition.converter.cell;

import java.util.Map;

import com.google.common.base.CaseFormat;

/**
*
* <pre>
* ロウアーキャメル形式の文字列へ変換を行うクラス
* 例: "ABC_DEF" -> "abcDef"
* </pre>
* @author mitsuhiroseino
*
*/
public class LowerCamel extends AbstractCaseFormatConverter {

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public LowerCamel(Map<String, Object> config) {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object convertImpl(String value) {
		return format(value, CaseFormat.LOWER_CAMEL);
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
