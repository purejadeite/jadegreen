package com.purejadeite.jadegreen.option.cell;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.purejadeite.jadegreen.content.Content;
import com.purejadeite.jadegreen.definition.Definition;

/**
*
* <pre>
* 小文字へ変換を行うクラス
* 例: "ABCDEF" -> "abcdef"
* </pre>
* @author mitsuhiroseino
*
*/
public class Lower extends AbstractStringCellOption {

	private static final long serialVersionUID = -4426567676789266496L;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public Lower(Definition<?> definition, Map<String, Object> config) {
		super(definition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value, Content<?, ?> content) {
		return StringUtils.lowerCase(value);
	}
}
