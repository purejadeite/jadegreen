package com.purejadeite.jadegreen.content;

import static com.purejadeite.jadegreen.content.Status.*;

import java.util.ArrayList;
import java.util.List;

import com.purejadeite.jadegreen.definition.cell.TableCellDefinition;

/**
 * Tableの構成要素となるCell読み込み定義
 * @author mitsuhiroseino
 */
public class StaticTableCellContentImpl extends AbstractTableCellContent<TableCellDefinition<?>> {

	private static final long serialVersionUID = -6172471723011313228L;

	/**
	 * コンストラクタ
	 * @param parent 親コンテンツ
	 * @param definition 定義
	 */
	public StaticTableCellContentImpl(String uuid, TableContent parent, TableCellDefinition<?> definition) {
		super(uuid, parent, definition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Status addValue(int row, int col, Object value) {
		return NO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValuesImpl() {
		// 親のtableからsizeを取得してその数分だけvaluesを返すように実装する
		int size = parent.size();
		List<Object> newValues = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			newValues.add(definition.applyOptions(values));
		}
		return newValues;
	}

}
