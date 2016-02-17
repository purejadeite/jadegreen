package com.purejadeite.jadegreen.content;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.purejadeite.jadegreen.definition.Definition;

/**
 * 親の抽象クラス
 *
 * @author mitsuhiroseino
 */
abstract public class AbstractParentContent<P extends ParentContent<?, ?, ?>, C extends Content<?, ?>, D extends Definition<?>>
		extends AbstractContent<P, D>implements ParentContent<P, C, D>, Serializable {

	protected List<C> children;

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

	@Override
	public void addChild(C child) {
		this.children.add(child);
	}

}
