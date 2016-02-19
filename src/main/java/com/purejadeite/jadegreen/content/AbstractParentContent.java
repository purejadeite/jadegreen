package com.purejadeite.jadegreen.content;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.Definition;

/**
 * 親の抽象クラス
 *
 * @author mitsuhiroseino
 */
abstract public class AbstractParentContent<P extends ParentContent<?, ?, ?>, C extends Content<?, ?>, D extends Definition<?>>
		extends AbstractContent<P, D>implements ParentContent<P, C, D>, Serializable {

	protected List<C> children;

	protected Map<String, Content<?, ?>> cells = new HashMap<>();

	public AbstractParentContent(String uuid, P parent, D definition) {
		super(uuid, parent, definition);
		children = new ArrayList<>();
	}

	public AbstractParentContent(String uuid, P parent, List<C> children, D definition) {
		super(uuid, parent, definition);
		this.children = children;
	}

	@Override
	public List<C> getChildren() {
		return children;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addChild(C child) {
		children.add(child);
		cells.put(child.getId(), child);
		if (child instanceof ParentContent) {
			cells.putAll(((ParentContent<?, ?, ?>) child).getCells());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Content<?, ?> getCell(Definition<?> cellDefinition) {
		return cells.get(cellDefinition.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Content<?, ?> getCell(String id) {
		return cells.get(id);
	}

	@Override
	public Map<String, Content<?, ?>> getCells() {
		return cells;
	}

}
