package com.purejadeite.jadegreen.option.cell;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.purejadeite.jadegreen.content.ContentInterface;
import com.purejadeite.jadegreen.definition.DefinitionInterface;

/**
*
* <pre>
* 大文字へ変換を行うクラス
* 例: "abcdef" -> "ABCDEF"
* </pre>
* @author mitsuhiroseino
*
*/
public class Upper extends AbstractStringCellOption {

	private static final long serialVersionUID = 2045257264059932768L;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public Upper(DefinitionInterface<?> definition, Map<String, Object> config) {
		super(definition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value, ContentInterface<?, ?> content) {
		return StringUtils.upperCase(value);
	}
}
