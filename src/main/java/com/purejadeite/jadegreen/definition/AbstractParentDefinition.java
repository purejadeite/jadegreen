package com.purejadeite.jadegreen.definition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * Excelファイル読み込みの定義情報抽象クラス
 *
 * @author mitsuhiroseino
 *
 */
abstract public class AbstractParentDefinition<P extends ParentDefinition<?, ?>, C extends Definition<?>>
		extends AbstractDefinition<P>implements ParentDefinition<P, C>, Serializable {

	private static final long serialVersionUID = -6619514528554034278L;

	/**
	 * 配下の要素定義
	 */
	protected List<C> children = new ArrayList<>();

	/**
	 * デフォルトコンストラクタ
	 */
	protected AbstractParentDefinition() {
		super();
	}

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親定義
	 * @param id
	 *            定義ID
	 * @param noOutput
	 *            値の出力有無
	 */
	protected AbstractParentDefinition(P parent, Map<String, ? extends Object> config) {
		super(parent, config);
	}

	/**
	 * コンストラクタ
	 *
	 * @param id
	 *            定義ID
	 * @param noOutput
	 *            値の出力有無
	 */
	protected AbstractParentDefinition(Map<String, ? extends Object> config) {
		super(config);
	}

	/**
	 * 配下の定義を取得します
	 */
	@Override
	public List<C> getChildren() {
		return children;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public C getChild(int index) {
		return children.get(index);
	}

	/**
	 * 配下の定義を追加します
	 */
	@Override
	public void addChild(C child) {
		children.add(child);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addChildren(List<C> children) {
		for (C child : children) {
			addChild(child);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		List<Map<String, Object>> childMaps = new ArrayList<>();
		if (children != null) {
			for (C child : children) {
				childMaps.add(child.toMap());
			}
			map.put("children", childMaps);
		}
		return map;
	}

}
