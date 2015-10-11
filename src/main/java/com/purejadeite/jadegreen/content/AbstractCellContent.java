package com.purejadeite.jadegreen.content;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.cell.CellDefinition;

/**
 * <pre>
 * 値の取得元セルの情報を保持する抽象クラス
 * </pre>
 * @author mitsuhiroseino
 */
abstract public class AbstractCellContent<D extends CellDefinition<?>> extends AbstractContent<D> implements CellContent<D> {

	private static final long serialVersionUID = 8565694667933995117L;

	protected Object values;

	public AbstractCellContent(Content<?> parent, D definition) {
		super(parent, definition);
	}

	@Override
	public Object getRawValuesImpl(Definition<?>... ignore) {
		return values;
	}

	@Override
	public Object getValuesImpl(Definition<?>... ignore) {
		return definition.apply(values);
	}

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
