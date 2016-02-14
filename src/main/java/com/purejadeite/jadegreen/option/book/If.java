package com.purejadeite.jadegreen.option.book;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.content.Content;
import com.purejadeite.jadegreen.content.SpecificValue;
import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.option.AbstactIf;
import com.purejadeite.jadegreen.option.Options;
import com.purejadeite.util.SimpleValidator;

/**
 * オプション実行条件オプション
 *
 * @author mitsuhiroseino
 *
 */
public class If extends AbstactIf implements BookOption, Serializable {

	protected static final String CFG_SHEET_ID = "sheetId";
	protected static final String CFG_CELL_ID = "cellId";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_SHEET_ID, CFG_CELL_ID };

	/**
	 * シートID
	 */
	protected String sheetId;

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
	public If(Definition<?> definition, Map<String, Object> config) {
		super(definition, config);
		SimpleValidator.containsKey(config, CONFIG);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object apply(Object bookValues, Content<?, ?> content) {
		if (bookValues == SpecificValue.UNDEFINED) {
			return bookValues;
		}
		return applyImpl((List<Map<String, Object>>) bookValues, content);
	}

	protected Object applyImpl(List<Map<String, Object>> bookValues, Content<?, ?> content) {
		// 仮
		if (evaluate(bookValues)) {
			return thenOptions.apply(bookValues, content);
		} else {
			if (thenOptions == null) {
				return bookValues;
			} else {
				return elseOptions.apply(bookValues, content);
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
	protected Options buildOptions(Definition<?> definition, List<Map<String, Object>> options) {
		return BookOptionManager.build(definition, options);
	}

}