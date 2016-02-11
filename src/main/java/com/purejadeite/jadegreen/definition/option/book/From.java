package com.purejadeite.jadegreen.definition.option.book;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.content.ContentManager;
import com.purejadeite.jadegreen.content.SheetContent;
import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.DefinitionManager;
import com.purejadeite.jadegreen.definition.SheetDefinition;
import com.purejadeite.util.SimpleValidator;

/**
 * 指定したシートの値をbookとして扱う
 *
 * @author mitsuhiroseino
 *
 */
public class From extends AbstractBookOption {

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { };

	/**
	 * コンストラクタ
	 *
	 * @param config
	 *            コンバーターのコンフィグ
	 */
	public From(Definition<?> definition, Map<String, Object> config) {
		super(definition);
		SimpleValidator.containsKey(config, CONFIG);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object applyImpl(List<Map<String, Object>> values) {
		SheetDefinition outputSheet = DefinitionManager.getInstance().getOutputSheet();
		List<SheetContent> sheetContents = ContentManager.getInstance().getSheets(outputSheet);
		List<Object> newBookValues = new ArrayList<>();
		for (SheetContent sheetContent : sheetContents) {
			Object sheetValues = sheetContent.getValues();
			newBookValues.add(sheetValues);
		}
		if (newBookValues.size() == 1) {
			return newBookValues.get(0);
		} else {
			return newBookValues;
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
}