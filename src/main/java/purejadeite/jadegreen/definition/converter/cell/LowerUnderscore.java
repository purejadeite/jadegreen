package purejadeite.jadegreen.definition.converter.cell;

import java.util.Map;

import com.google.common.base.CaseFormat;

/**
*
* <pre>
* ロウアーアンダースコア形式の文字列へ変換を行うクラス
* 例: "abcDef" -> "abc_def"
* </pre>
* @author mitsuhiroseino
*
*/
public class LowerUnderscore extends AbstractCaseFormatConverter {

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public LowerUnderscore(Map<String, Object> config) {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object convertImpl(String value) {
		return format(value, CaseFormat.LOWER_UNDERSCORE);
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
