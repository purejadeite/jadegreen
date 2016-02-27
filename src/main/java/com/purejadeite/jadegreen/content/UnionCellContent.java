package com.purejadeite.jadegreen.content;

import java.util.ArrayList;
import java.util.List;

import com.purejadeite.jadegreen.definition.cell.CellDefinitionInterface;
import com.purejadeite.util.collection.Table;

/**
 * セルの値をList形式で保持するクラス
 * @author mitsuhiroseino
 */
public class UnionCellContent extends CellContent {

	/**
	 * コンストラクタ
	 * @param parent 親コンテンツ
	 * @param definition 定義
	 */
	public UnionCellContent(ParentContentInterface<?, ?, ?> parent, CellDefinitionInterface<?> definition) {
		super(parent, definition);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int capture(Table<String> table) {
		// 行列番号が取得範囲内の場合
		List<Object> vals = null;
		if (this.values == null) {
			vals = new ArrayList<Object>();
			this.values = vals;
		} else {
			vals = (List<Object>) values;
		}
		vals.add(getDefinition().capture(table));
		return 1;
	}

}
