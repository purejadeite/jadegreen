package com.purejadeite.jadegreen.definition;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.purejadeite.jadegreen.RoughlyMapUtils;

/**
*
* Excelファイル読み込みの定義情報抽象クラス
* @author mitsuhiroseino
*
*/
abstract public class AbstractDefinition<P extends ParentDefinition<?, ?>> extends AbstractApplier implements Definition<P>, Serializable {

	private static final long serialVersionUID = -847224181929765049L;

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = {"id"};

	/**
	 * 定義ID
	 */
	protected String id;

	/**
	 * 親定義
	 */
	protected P parent;

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
	protected AbstractDefinition(P parent, Map<String, ? extends Object> config) {
		super();
		this.validateConfig(config, CONFIG);
		this.parent = parent;
		this.id = RoughlyMapUtils.getString(config, "id");
		this.noOutput = RoughlyMapUtils.getBooleanValue(config, "noOutput");
	}

	/**
	 * コンストラクタ
	 * @param parent 親定義
	 * @param id 定義ID
	 * @param noOutput 値の出力有無
	 */
	protected AbstractDefinition(P parent, String id, boolean noOutput) {
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
			fullId = this.parent.getFullId() + "." + fullId;
		}
		return fullId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public P getParent() {
		return parent;
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
