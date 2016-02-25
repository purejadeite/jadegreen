package com.purejadeite.jadegreen.option.cell;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.purejadeite.jadegreen.content.Content;
import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.util.SimpleValidator;

/**
 * 指定された区切り文字で文字列を分割した最初の要素を取るオプション
 *
 * @author mitsuhiroseino
 *
 */
abstract public class AbstractAtCellOption extends AbstractStringCellOption {

	protected static final String CFG_SPLITTER = "splitter";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_SPLITTER };

	/**
	 * 区切り文字
	 */
	protected String splitter;

	/**
	 * コンストラクタ
	 *
	 * @param cell
	 *            値の取得元Cell読み込み定義
	 * @param config
	 *            コンバーターのコンフィグ
	 */
	public AbstractAtCellOption(Definition<?> definition, Map<String, Object> config) {
		super(definition);
		SimpleValidator.containsKey(config, CONFIG);
		this.splitter = getString(config, CFG_SPLITTER, "\n");
	}

	/**
	 * {@inheritDoc}
	 */
	protected Object applyToString(String value, Content<?, ?> content) {
		if (StringUtils.isEmpty(value)) {
			return value;
		}
		String[] values = StringUtils.split(value, splitter);
		if (values == null) {
			return null;
		} else {
			return getAt(values);
		}
	}

	abstract protected String getAt(String[] values);

	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("splitter", this.splitter);
		return map;
	}
}
