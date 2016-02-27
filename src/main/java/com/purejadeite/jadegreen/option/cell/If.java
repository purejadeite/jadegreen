package com.purejadeite.jadegreen.option.cell;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.content.ContentInterface;
import com.purejadeite.jadegreen.content.SpecificValue;
import com.purejadeite.jadegreen.definition.DefinitionInterface;
import com.purejadeite.jadegreen.option.AbstactIf;
import com.purejadeite.jadegreen.option.Options;
import com.purejadeite.util.SimpleValidator;

/**
 * オプション実行条件オプション
 *
 * @author mitsuhiroseino
 *
 */
public class If extends AbstactIf implements CellOptionInterface, Serializable {

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = {};

	/**
	 * コンストラクタ
	 *
	 * @param config
	 *            コンバーターのコンフィグ
	 */
	public If(DefinitionInterface<?> definition, Map<String, Object> config) {
		super(definition, config);
		SimpleValidator.containsKey(config, CONFIG);
	}

	@Override
	public Object apply(Object cellValue, ContentInterface<?, ?> content) {
		if (cellValue == SpecificValue.UNDEFINED) {
			return cellValue;
		} else {
			return applyImpl(cellValue, content);
		}
	}

	protected Object applyImpl(Object cellValue, ContentInterface<?, ?> content) {
		if (evaluate(cellValue)) {
			return thenOptions.apply(cellValue, content);
		} else {
			if (thenOptions == null) {
				return cellValue;
			} else {
				return elseOptions.apply(cellValue, content);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		return map;
	}

	@Override
	protected Options buildOptions(DefinitionInterface<?> definition, List<Map<String, Object>> options) {
		return CellOptionManager.build(definition, options);
	}

}