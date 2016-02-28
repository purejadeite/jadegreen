package com.purejadeite.jadegreen.content;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.cell.CellDefinitionInterface;
import com.purejadeite.util.collection.Table;

/**
 * セルの値を保持する抽象クラス
 *
 * @author mitsuhiroseino
 */
abstract public class AbstractListValueContent<D extends CellDefinitionInterface<?, ?>>
		extends AbstractContent<ParentContentInterface<?, ?, ?>, D>implements CellContentInterface<ParentContentInterface<?, ?, ?>, D> {

	/**
	 * セルの値
	 */
	protected List<Object> values;

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親コンテンツ
	 * @param definition
	 *            定義
	 */
	public AbstractListValueContent(ParentContentInterface<?, ?, ?> parent, D definition) {
		super(parent, definition);
		values = new ArrayList<>();
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
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("values", values);
		return map;
	}

	@Override
	public int capture(Table<String> table) {
		values.add(getDefinition().capture(table));
		return 1;
	}

}
