package com.purejadeite.jadegreen.definition.option.table;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.content.SpecificValue;
import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.option.AbstactIf;
import com.purejadeite.jadegreen.definition.option.Options;
import com.purejadeite.util.SimpleValidator;

/**
 * オプション実行条件オプション
 *
 * @author mitsuhiroseino
 *
 */
public class If extends AbstactIf implements TableOption, Serializable {

	protected static final String CFG_KEY_ID = "keyId";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_KEY_ID };

	/**
	 * 条件キー
	 */
	protected List<String> keyIds;

	/**
	 * コンストラクタ
	 *
	 * @param config
	 *            コンバーターのコンフィグ
	 */
	public If(Definition<?> definition, Map<String, Object> config) {
		super(definition, config);
		SimpleValidator.containsKey(config, CONFIG);

		this.keyIds = new ArrayList<>();
		List<Map<String, String>> conditions = getList(config, CFG_CONDITIONS);
		if (conditions != null) {
			for (Map<String, String> condition : conditions) {
				this.keyIds.add(getString(condition, CFG_KEY_ID));
			}
		} else {
			this.keyIds.add(getString(config, CFG_KEY_ID));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object apply(Object tableValues) {
		if (tableValues == SpecificValue.UNDEFINED) {
			return tableValues;
		}
		return applyImpl((List<Map<String, Object>>) tableValues);
	}

	protected Object applyImpl(List<Map<String, Object>> tableValues) {
		List<Object> rows = new ArrayList<>();
		for (Map<String, Object> row : tableValues) {
			boolean result = true;
			// 全ての条件にあてはまる場合にのみオプションを適用
			for (int i = 0; i < keyIds.size(); i++) {
				String keyId = keyIds.get(i);
				Object value = row.get(keyId);
				if (!evaluate(value)) {
					result = false;
					break;
				}
			}
			if (result) {
				rows.add(thenOptions.apply(row));
			} else {
				if (thenOptions == null) {
					rows.add(row);
				} else {
					rows.add(elseOptions.apply(row));
				}
			}
		}
		return rows;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("keyIds", keyIds);
		return map;
	}

	@Override
	protected Options buildOptions(Definition<?> definition, List<Map<String, Object>> options) {
		return TableOptionManager.build(definition, options);
	}

}