package com.purejadeite.jadegreen.content;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.table.cell.TableCellDefinitionInterface;
import com.purejadeite.util.collection.Table;

/**
 * Table配下のCellのコンテンツ
 *
 * @author mitsuhiroseino
 */
abstract public class AbstractTableCellContent<D extends TableCellDefinitionInterface<?>>
		extends AbstractContent<TableContentInterface, D>implements TableCellContentInterface<D> {

	private static final long serialVersionUID = 1420210546938530625L;

	/**
	 * 値
	 */
	protected List<Object> values = new ArrayList<>();

	/**
	 * コンストラクタ
	 */
	public AbstractTableCellContent(TableContentInterface parent, D definition) {
		super(parent, definition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getRawValuesImpl() {
		return values;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValuesImpl() {
		return definition.applyOptions(values, this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		return values.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("values", values);
		return map;
	}

	/**
	 * キー項目の値を取得
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int capture(Table<String> table) {
		List<Object> vals = (List<Object>) getDefinition().capture(table);
		values.addAll(vals);
		return vals.size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public int capture(Table<String> table, int size) {
		List<Object> vals = (List<Object>) getDefinition().capture(table, size);
		values.addAll(vals);
		return vals.size();
	}

}
