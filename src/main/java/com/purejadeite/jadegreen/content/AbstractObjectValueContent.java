package com.purejadeite.jadegreen.content;

import java.util.Map;

import com.purejadeite.jadegreen.definition.cell.CellDefinitionInterface;
import com.purejadeite.util.collection.Table;

/**
 * Objectでセルの値を保持する抽象クラス
 *
 * @author mitsuhiroseino
 */
abstract public class AbstractObjectValueContent<D extends CellDefinitionInterface<?, Object>>
		extends AbstractContent<ParentContentInterface<?, ?, ?>, D>implements CellContentInterface<ParentContentInterface<?, ?, ?>, D> {

	/**
	 * セルの値
	 */
	protected Object value;

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親コンテンツ
	 * @param definition
	 *            定義
	 */
	public AbstractObjectValueContent(ParentContentInterface<?, ?, ?> parent, D definition) {
		super(parent, definition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getRawValuesImpl() {
		return value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValuesImpl() {
		return definition.applyOptions(value, this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("value", value);
		return map;
	}

	@Override
	public int capture(Table<String> table) {
		value = getDefinition().capture(table);
		return 1;
	}

}
