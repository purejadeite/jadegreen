package com.purejadeite.jadegreen.definition.option.sheet;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.Map;

import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.util.SimpleValidator;

/**
 * 指定したidの値をsheetとして扱う
 *
 * @author mitsuhiroseino
 *
 */
public class From extends AbstractSheetOption {

	protected static final String CFG_KEY_ID = "keyId";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_KEY_ID };

	/**
	 * Sheetの出力値として扱う項目
	 */
	protected String keyId;

	/**
	 * コンストラクタ
	 *
	 * @param config
	 *            コンバーターのコンフィグ
	 */
	public From(Definition<?> definition, Map<String, Object> config) {
		super(definition);
		SimpleValidator.containsKey(config, CONFIG);
		this.keyId = getString(config, CFG_KEY_ID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object applyImpl(Map<String, Object> values) {
		return values.get(keyId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("keyId", keyId);
		return map;
	}
}