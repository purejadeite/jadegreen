package com.purejadeite.jadegreen.definition.table;

import java.util.Map;

import com.purejadeite.jadegreen.definition.ParentDefinition;
import com.purejadeite.jadegreen.definition.SheetDefinition;
import com.purejadeite.jadegreen.definition.cell.CellDefinition;

/**
 * 任意の集まりを保持するクラスの抽象クラスです
 * @author mitsuhiroseino
 */
public class CategoryDefinitionImpl extends AbstractCategoryDefinition<CellDefinition<?>> {

	public static final String CFG_CELLS = "cells";

	/**
	 * コンストラクタ
	 * @param parent 親の読み込み情報
	 * @param config コンフィグ
	 */
	public CategoryDefinitionImpl(SheetDefinition parent, Map<String, Object> config) {
		super(parent, config);
	}

	public static boolean assess(Map<String, Object> config, ParentDefinition<?, ?> table) {
		return config.containsKey(CFG_CELLS);
	}

}
