package com.purejadeite.jadegreen.definition;

import static com.purejadeite.util.RoughlyConverter.*;
import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.purejadeite.jadegreen.definition.option.Options;

/**
*
* Excelファイル読み込みの定義情報抽象クラス
* @author mitsuhiroseino
*
*/
abstract public class AbstractDefinition<P extends ParentDefinition<?, ?>> implements Definition<P>, Serializable {

	private static final long serialVersionUID = -847224181929765049L;

	/**
	 * 定義ID
	 */
	protected String id;

	/**
	 * 親定義
	 */
	protected P parent;

	/**
	 * オプション
	 */
	protected Options options;

	/**
	 * デフォルトコンストラクタ
	 */
	protected AbstractDefinition() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param id 定義ID
	 * @param noOutput 値の出力有無
	 */
	protected AbstractDefinition(Map<String, ? extends Object> config) {
		super();
		this.id = getString(config, CFG_ID);
		if (id == null) {
			id = UUID.randomUUID().toString();
		}
		// オプションの元になる値は親が取得して、子がOptionsのインスタンスを作る
		List<Map<String, Object>> opts = intoList(get(config, CFG_OPTIONS));
		if (opts == null) {
			Map<String, Object> opt = intoMap(get(config, CFG_OPTIONS));
			if (opt != null) {
				opts = new ArrayList<>();
				opts.add(opt);
			}
		}
		buildOptions(opts);
	}

	/**
	 * コンストラクタ
	 * @param parent 親定義
	 * @param id 定義ID
	 * @param noOutput 値の出力有無
	 */
	protected AbstractDefinition(P parent, Map<String, ? extends Object> config) {
		this(config);
		this.parent = parent;
	}

	/**
	 * コンストラクタ
	 * @param parent 親定義
	 * @param id 定義ID
	 * @param noOutput 値の出力有無
	 */
//	protected AbstractDefinition(P parent, String id) {
//		super();
//		this.parent = parent;
//		this.id = id;
//	}

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
	public Options getOptions() {
		return options;
	}

	abstract protected void buildOptions(List<Map<String, Object>> options);

	@Override
	public Object applyOptions(Object value) {
		if (options == null) {
			return value;
		} else {
			return options.apply(value);
		}
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
		if (options != null) {
			map.put("options", options.toMap());
		} else {
			map.put("options", null);
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
