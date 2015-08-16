package com.purejadeite.jadegreen.definition.converter.cell;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.CaseFormat;

/**
 * <pre>
 * 文字列の形式を変換する抽象クラスです
 * 以下の形式の文字列を相互変換します
 *・LOWER_CAMEL:		'abcDef'
 *・UPPER_CAMEL:		'AbcDef'
 *・LOWER_UNDERSCORE:	'abc_def'
 *・UPPER_UNDERSCORE:	'ABC_DEF'
 *・LOWER_HYPHEN:		'abc-def'
 * </pre>
 * @author mitsuhiroseino
 *
 */
public abstract class AbstractCaseFormatConverter extends AbstractStringCellConverter {

	private static final long serialVersionUID = -8513734342418090089L;

	/**
	 * コンストラクタ
	 * @param cell Cell読み込み定義
	 */
	public AbstractCaseFormatConverter() {
		super();
	}

	/**
	 * 形式の変換処理
	 * @param value 値
	 * @param to 変換後の形式
	 * @return 変換された値
	 */
	protected Object format(String value, CaseFormat to) {
		if (StringUtils.isEmpty(value)) {
			return value;
		}
		CaseFormat underscore = null;
		if (to == CaseFormat.LOWER_UNDERSCORE) {
			underscore = CaseFormat.UPPER_UNDERSCORE;
		} else {
			underscore = CaseFormat.LOWER_UNDERSCORE;
		}
		CaseFormat camel = null;
		if (to == CaseFormat.LOWER_CAMEL) {
			camel = CaseFormat.UPPER_CAMEL;
		} else {
			camel = CaseFormat.LOWER_CAMEL;
		}
		if (StringUtils.contains(value, "-")) {
			// ハイフン区切りだと思う
			return CaseFormat.LOWER_HYPHEN.to(to, value);
		} else if (StringUtils.contains(value, "_")) {
			// アンダーバー区切りだと思う
			return underscore.to(to, value);
		} else {
			// キャメルケースだと思う
			return camel.to(to, value);
		}
	}

	public List<Map<String, Object>> toList() {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("name", this.getClass().getSimpleName());
		List<Map<String, Object>> list = super.toList();
		list.add(map);
		return list;
	}

}
