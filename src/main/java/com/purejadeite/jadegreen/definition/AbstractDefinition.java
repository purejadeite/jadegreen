package com.purejadeite.jadegreen.definition;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.purejadeite.jadegreen.AbstractToJson;

/**
*
* Excelファイル読み込みの定義情報抽象クラス
* @author mitsuhiroseino
*
*/
public abstract class AbstractDefinition extends AbstractToJson implements Definition, Serializable {

	/**
	 * 定義ID
	 */
	protected String id;

	/**
	 * 親定義
	 */
	protected Definition parent;

	/**
	 * <pre>
	 * データの出力を行うか
	 * true: 行わない
	 * false: 行う
	 * </pre>
	 */
	protected boolean noOutput = false;

	/**
	 * デフォルトコンストラクタ
	 */
	protected AbstractDefinition() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param parent 親定義
	 * @param id 定義ID
	 * @param noOutput 値の出力有無
	 */
	protected AbstractDefinition(Definition parent, String id, boolean noOutput) {
		super();
		this.parent = parent;
		this.id = id;
		this.noOutput = noOutput;
	}

	/**
	 * Mapへの出力を行わないか
	 */
	public boolean isNoOutput() {
		return noOutput;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getFullId() {
		String fullId = id;
		if (this.parent != null && !StringUtils.isEmpty(this.parent.getFullId())) {
			fullId += this.parent.getFullId();
		}
		return fullId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Definition getParent() {
		return parent;
	}

	/**
	 * サポートされていないオペレーションです
	 */
	@Override
	public List<? extends Definition> getChildren() {
		throw new UnsupportedOperationException();
	}

	/**
	 * サポートされていないオペレーションです
	 */
	@Override
	public void addChild(Definition child) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Definition get(String fullId) {
		String[] ids = StringUtils.split(fullId, ".");
		return get(ids);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Definition get(String... ids) {
		if (ids.length == 0) {
			return null;
		}
		List<? extends Definition> children = getChildren();
		if (children == null) {
			return null;
		}
		String id = ids[0];
		for (Definition child : children) {
			if (child.getId().equals(id)) {
				if (ids.length == 1) {
					return child;
				} else {
					String[] subIds = ArrayUtils.subarray(ids, 1, ids.length);
					return child.get(subIds);
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
		map.put("id", id);
		map.put("noOutput", noOutput);
		if (parent != null) {
			map.put("parent", parent.getFullId());
		} else {
			map.put("parent", null);
		}
		return map;
	}

}
