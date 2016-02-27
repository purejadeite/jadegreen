package com.purejadeite.jadegreen.option.table;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.content.ContentInterface;
import com.purejadeite.jadegreen.definition.DefinitionInterface;
import com.purejadeite.util.SimpleValidator;
import com.purejadeite.util.collection.StringKeyNestedMap;

/**
 * Branchテーブルコンバーター
 *
 * @author mitsuhiroseino
 *
 */
public class Branch extends AbstractTableOption {

	protected static final String CFG_SEPARATOR = "separator";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = {};

	/**
	 * キーを分割する文字列
	 */
	protected String separator;

	/**
	 * コンストラクタ
	 *
	 * @param config
	 *            コンバーターのコンフィグ
	 */
	public Branch(DefinitionInterface<?> definition, Map<String, Object> config) {
		super(definition);
		SimpleValidator.containsKey(config, CONFIG);
		this.separator = getString(config, CFG_SEPARATOR, ".");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object applyImpl(List<Map<String, Object>> values, ContentInterface<?, ?> content) {
		// フラットなMapを階層構造に変換する
		List<Map<String, Object>> newValues = new ArrayList<>();
		for (Map<String, Object> line : values) {
			StringKeyNestedMap newLine = new StringKeyNestedMap(new LinkedHashMap<String, Object>());
			for (Map.Entry<String, Object> entry : line.entrySet()) {
				newLine.put(entry.getKey(), entry.getValue());
			}
			newValues.add(newLine.getMap());
		}
		return newValues;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("separator", separator);
		return map;
	}
}