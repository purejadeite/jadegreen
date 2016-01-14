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
	private List<C> children = new ArrayList<>();

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
	 * @param parent
	 *            親定義
	 * @param id
	 *            定義ID
	 */
	protected AbstractParentDefinition(P parent, String id) {
		super(parent, id);
	}

	/**
	 * 配下の定義を取得します
	 */
	@Override
	public List<C> getChildren() {
		return children;
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
	@SuppressWarnings("unchecked")
	@Override
	public C get(String id) {
		return (C) definitions.get(id);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		List<Map<String, Object>> childMaps = new ArrayList<>();
		for (C child : children) {
			childMaps.add(child.toMap());
		}
		map.put("children", childMaps);
		return map;
	}

}
