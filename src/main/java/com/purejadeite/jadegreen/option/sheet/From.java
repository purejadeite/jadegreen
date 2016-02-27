package com.purejadeite.jadegreen.option.sheet;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.Map;

import com.purejadeite.jadegreen.content.ContentInterface;
import com.purejadeite.jadegreen.definition.DefinitionInterface;
import com.purejadeite.util.SimpleValidator;

/**
 * 指定したセルの値をsheetとして扱う
 *
 * @author mitsuhiroseino
 *
 */
public class From extends AbstractSheetOption {

	protected static final String CFG_CELL_ID = "cellId";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_CELL_ID };

	/**
	 * Sheetの出力値として扱う項目
	 */
	protected String cellId;

	/**
	 * コンストラクタ
	 *
	 * @param config
	 *            コンバーターのコンフィグ
	 */
	public From(DefinitionInterface<?> definition, Map<String, Object> config) {
		super(definition);
		SimpleValidator.containsKey(config, CONFIG);
		this.cellId = getString(config, CFG_CELL_ID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object applyImpl(Map<String, Object> values, ContentInterface<?, ?> content) {
		return values.get(cellId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("cellId", cellId);
		return map;
	}
}