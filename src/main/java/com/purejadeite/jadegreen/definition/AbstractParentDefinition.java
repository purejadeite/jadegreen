package com.purejadeite.jadegreen.definition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

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
	 * @param noOutput
	 *            値の出力有無
	 */
	protected AbstractParentDefinition(P parent, String id, boolean noOutput) {
		super(parent, id, noOutput);
	}

	/**
	 * サポートされていないオペレーションです
	 */
	@Override
	public List<C> getChildren() {
		return children;
	}

	/**
	 * サポートされていないオペレーションです
	 */
	@Override
	public void addChild(C child) {
		children.add(child);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Definition<?> get(String fullId) {
		String[] ids = StringUtils.split(fullId, ".");
		return get(ids);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Definition<?> get(String... ids) {
		if (ids.length == 0) {
			return null;
		}
		List<C> children = getChildren();
		if (children == null) {
			return null;
		}
		String id = ids[0];
		for (Definition<?> child : children) {
			if (child.getId().equals(id)) {
				if (ids.length == 1) {
					return child;
				} else if (child instanceof ParentDefinition) {
					ParentDefinition<?, ?> pd = (ParentDefinition<?, ?>) child;
					String[] subIds = ArrayUtils.subarray(ids, 1, ids.length);
					return pd.get(subIds);
				}
			}
		}
		return null;
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
