package com.purejadeite.jadegreen.content;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.cell.CellDefinition;

/**
 * セルの値を保持する抽象クラス
 * @author mitsuhiroseino
 */
abstract public class AbstractCellContent<D extends CellDefinition<?>> extends AbstractContent<D> implements CellContent<D> {

	private static final long serialVersionUID = 8565694667933995117L;

	/**
	 * セルの値
	 */
	protected Object values;

	/**
	 * コンストラクタ
	 * @param parent 親コンテンツ
	 * @param definition 定義
	 */
	public AbstractCellContent(String uuid, Content<?> parent, D definition) {
		super(uuid, parent, definition);
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
		return definition.applyOptions(values);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Content<?>> searchContents(Definition<?> key) {
		List<Content<?>> contents = new ArrayList<>();
		if (definition == key) {
			contents.add(this);
		}
		return contents;
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
}
