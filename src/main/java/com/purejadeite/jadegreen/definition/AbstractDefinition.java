package com.purejadeite.jadegreen.definition;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.purejadeite.jadegreen.ToMap;
import com.purejadeite.util.SimpleValidator;
import com.purejadeite.util.collection.RoughlyMapUtils;

/**
*
* Excelファイル読み込みの定義情報抽象クラス
* @author mitsuhiroseino
*
*/
abstract public class AbstractDefinition<P extends ParentDefinition<?, ?>> implements Definition<P>, ToMap, Serializable {

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
	 * 同シート配下の全定義
	 */
	protected Map<String, Definition<?>> definitions;

	/**
	 * 同シート配下の全定義を設定します
	 * @param definitions シート配下の全定義
	 */
	public void setDefinitions(Map<String, Definition<?>> definitions) {
		this.definitions = definitions;
	}

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
		SimpleValidator.containsKey(config, CONFIG);
		this.parent = parent;
		this.id = RoughlyMapUtils.getString(config, "id");
	}

	/**
	 * コンストラクタ
	 * @param parent 親定義
	 * @param id 定義ID
	 * @param noOutput 値の出力有無
	 */
	protected AbstractDefinition(P parent, String id) {
		super();
		this.parent = parent;
		this.id = id;
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
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("id", id);
		if (parent != null) {
			map.put("parent", parent.getFullId());
		} else {
			map.put("parent", null);
		}
		return map;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return toMap().toString();
	}

}
