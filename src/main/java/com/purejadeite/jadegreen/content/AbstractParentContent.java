package com.purejadeite.jadegreen.content;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.DefinitionInterface;
import com.purejadeite.jadegreen.definition.ParentDefinitionInterface;
import com.purejadeite.util.collection.Table;

/**
 * 親の抽象クラス
 *
 * @author mitsuhiroseino
 */
abstract public class AbstractParentContent<P extends ParentContentInterface<?, ?, ?>, C extends ContentInterface<?, ?>, D extends ParentDefinitionInterface<?, ?>>
		extends AbstractContent<P, D>implements ParentContentInterface<P, C, D>, Serializable {

	protected List<C> children;

	protected Map<String, ContentInterface<?, ?>> cells = new HashMap<>();

	public AbstractParentContent(String uuid, P parent, D definition) {
		super(uuid, parent, definition);
		children = new ArrayList<>();
	}

	public AbstractParentContent(P parent, D definition) {
		super(parent, definition);
		children = new ArrayList<>();
	}

	public AbstractParentContent(P parent, List<C> children, D definition) {
		super(parent, definition);
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
		if (child instanceof ParentContentInterface) {
			cells.putAll(((ParentContentInterface<?, ?, ?>) child).getCells());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ContentInterface<?, ?> getCell(DefinitionInterface<?> cellDefinition) {
		return cells.get(cellDefinition.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ContentInterface<?, ?> getCell(String id) {
		return cells.get(id);
	}

	@Override
	public Map<String, ContentInterface<?, ?>> getCells() {
		return cells;
	}

	@Override
	public int capture(Table<String> table) {
		int size = 0;
		for (C child : children) {
			size += child.capture(table);
		}
		return size;
	}

}
