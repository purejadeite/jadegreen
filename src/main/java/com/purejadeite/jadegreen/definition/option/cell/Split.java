package com.purejadeite.jadegreen.definition.option.cell;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.purejadeite.jadegreen.content.Content;
import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.util.SimpleValidator;

/**
 * 指定された区切り文字で文字列を分割するクラス
 *
 * @author mitsuhiroseino
 *
 */
public class Split extends AbstractStringCellOption {

	private static final long serialVersionUID = -7086955680521212723L;

	protected static final String CFG_SPLITTER = "splitter";

	protected static final String CFG_ALWAYS = "always";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_SPLITTER };

	/**
	 * 区切り文字
	 */
	protected String splitter;

	/**
	 * 分割を常に行うか
	 */
	protected boolean always;

	/**
	 * コンストラクタ
	 *
	 * @param cell
	 *            値の取得元Cell読み込み定義
	 * @param config
	 *            コンバーターのコンフィグ
	 */
	public Split(Definition<?> definition, Map<String, Object> config) {
		super(definition);
		SimpleValidator.containsKey(config, CONFIG);
		this.splitter = getString(config, CFG_SPLITTER, "\n");
		this.always = getBooleanValue(config, CFG_ALWAYS, true);
	}

	/**
	 * {@inheritDoc}
	 */
	protected Object applyToString(String value, Content<?, ?> content) {
		if (StringUtils.isEmpty(value)) {
			return value;
		}
		String[] values = StringUtils.split(value, splitter);
		if (values.length == 1 && !always) {
			return value;
		} else {
			return (Object) Arrays.asList(values);
		}
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("splitter", this.splitter);
		map.put("always", this.always);
		return map;
	}
}
