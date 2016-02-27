package com.purejadeite.jadegreen.content;

import java.util.ArrayList;
import java.util.List;

import com.purejadeite.jadegreen.definition.table.cell.TableCellDefinitionInterface;

/**
 * Tableの構成要素となるCellコンテンツ
 * @author mitsuhiroseino
 */
public class StaticTableCellContent extends AbstractTableCellContent<TableCellDefinitionInterface<?>> {

	/**
	 * コンストラクタ
	 * @param parent 親コンテンツ
	 * @param definition 定義
	 */
	public StaticTableCellContent(TableContentInterface parent, TableCellDefinitionInterface<?> definition) {
		super(parent, definition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValuesImpl() {
		int size = parent.size();
		List<Object> newValues = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			newValues.add(definition.applyOptions(values, this));
		}
		return newValues;
	}

}
