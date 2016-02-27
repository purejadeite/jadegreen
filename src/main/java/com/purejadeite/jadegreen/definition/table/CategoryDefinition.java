package com.purejadeite.jadegreen.definition.table;

import java.util.Map;

import com.purejadeite.jadegreen.definition.DefinitionInterface;
import com.purejadeite.jadegreen.definition.ParentDefinitionInterface;

/**
 * 任意の集まりを保持するクラスの抽象クラスです
 * @author mitsuhiroseino
 */
public class CategoryDefinition extends AbstractCategoryDefinition<DefinitionInterface<?>> {

	public static final String CFG_CELLS = "cells";

	/**
	 * コンストラクタ
	 * @param parent 親の読み込み情報
	 * @param config コンフィグ
	 */
	public CategoryDefinition(ParentDefinitionInterface<?, ?> parent, Map<String, Object> config) {
		super(parent, config);
	}

	public static boolean assess(Map<String, Object> config, ParentDefinitionInterface<?, ?> table) {
		return config.containsKey(CFG_CELLS);
	}

}
