package com.purejadeite.jadegreen.option.sheet;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.content.ContentInterface;
import com.purejadeite.jadegreen.content.SpecificValue;
import com.purejadeite.jadegreen.definition.DefinitionInterface;
import com.purejadeite.jadegreen.option.AbstactIf;
import com.purejadeite.jadegreen.option.Options;
import com.purejadeite.jadegreen.option.book.BookOptionManager;
import com.purejadeite.util.SimpleValidator;

/**
 * オプション実行条件オプション
 *
 * @author mitsuhiroseino
 *
 */
public class If extends AbstactIf implements SheetOptionInterface, Serializable {

	protected static final String CFG_CELL_ID = "cellId";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_CELL_ID };

	/**
	 * セルID
	 */
	protected String cellId;

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

	@SuppressWarnings("unchecked")
	@Override
	public Object apply(Object sheetValues, ContentInterface<?, ?> content) {
		if (sheetValues == SpecificValue.UNDEFINED) {
			return sheetValues;
		}
		return applyImpl((Map<String, Object>) sheetValues, content);
	}

	protected Object applyImpl(Map<String, Object> sheetValues, ContentInterface<?, ?> content) {
		// 仮
		if (evaluate(sheetValues)) {
			return thenOptions.apply(sheetValues, content);
		} else {
			if (thenOptions == null) {
				return sheetValues;
			} else {
				return elseOptions.apply(sheetValues, content);
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
		return BookOptionManager.build(definition, options);
	}

}