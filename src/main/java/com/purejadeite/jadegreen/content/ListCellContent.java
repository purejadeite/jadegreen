package com.purejadeite.jadegreen.content;

import java.util.List;

import com.purejadeite.jadegreen.definition.cell.ListCellDefinition;
import com.purejadeite.util.collection.Table;

/**
 * セルの値を保持するクラス
 * @author mitsuhiroseino
 */
public class ListCellContent extends AbstractListValueContent<ListCellDefinition> {

	/**
	 * コンストラクタ
	 * @param parent 親コンテンツ
	 * @param definition 定義
	 */
	public ListCellContent(ParentContentInterface<?, ?, ?> parent, ListCellDefinition definition) {
		super(parent, definition);
	}

	@Override
	public int capture(Table<String> table) {
		Object value = getDefinition().capture(table);
		if (value instanceof List) {
			values.addAll((List<?>) value);
		} else {
			values.add(value);
		}
		return 1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValuesImpl() {
		return definition.applyOptions(values, this);
	}

}
