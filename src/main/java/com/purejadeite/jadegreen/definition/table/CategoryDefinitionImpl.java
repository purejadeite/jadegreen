package com.purejadeite.jadegreen.definition.table;

import java.util.Map;

import com.purejadeite.jadegreen.definition.SheetDefinition;
import com.purejadeite.jadegreen.definition.cell.CellDefinition;

/**
 * 任意の集まりを保持するクラスの抽象クラスです
 * @author mitsuhiroseino
 */
public class CategoryDefinitionImpl extends AbstractCategoryDefinition<CellDefinition<?>> {

	/**
	 * コンストラクタ
	 * @param parent 親の読み込み情報
	 * @param config コンフィグ
	 */
	public CategoryDefinitionImpl(SheetDefinition parent, Map<String, Object> config) {
		super(parent, config);
	}

	public static boolean assess(TableDefinition<?> table, Map<String, Object> config) {
		return table == null && !config.containsKey("row") && !config.containsKey("column");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isIncluded(int row, int col) {
		for (CellDefinition<?> cell : cells) {
			if (cell.isIncluded(row, col)) {
				return true;
			}
		}
		return false;
	}
}
