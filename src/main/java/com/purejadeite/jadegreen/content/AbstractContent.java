package com.purejadeite.jadegreen.content;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.Definition;

/**
 * 値を保持する抽象クラス
 *
 * @author mitsuhiroseino
 */
abstract public class AbstractContent<D extends Definition<?>> implements Content<D>, Serializable {

	private static final long serialVersionUID = 760790316639278651L;

	protected String uuid;

	/**
	 * 親要要素
	 */
	protected Content<?> parent;

	/**
	 * 値を取得する定義
	 */
	protected D definition;

	/**
	 * 値の取得完了を表わすフラグ
	 */
	protected boolean closed;

	// 出力した値のキャッシュ
	private Object valuesCache;

	/**
	 * コンストラクター
	 *
	 * @param parent
	 *            親要素
	 * @param definition
	 *            値を取得する定義
	 */
	public AbstractContent(String uuid, Content<?> parent, D definition) {
		this.uuid = uuid;
		this.parent = parent;
		this.definition = definition;
		this.closed = false;
	}

	public List<String> getKey() {
		List<String> key = new ArrayList<>();
		key.add(getFullId());
		key.add(uuid);
		return key;
	}

	public String getUuid() {
		return uuid;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getId() {
		return definition.getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getFullId() {
		return definition.getFullId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public D getDefinition() {
		return definition;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getRawValues() {
		return getRawValuesImpl();
	}

	/**
	 * 編集していない値を取得する実装です
	 *
	 * @param ignore
	 *            取得対象外とする子要素の定義
	 * @return 値
	 */
	abstract public Object getRawValuesImpl();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValues() {
		if (valuesCache == null) {
			valuesCache = getValuesImpl();
		}
		return valuesCache;
	}

	/**
	 * 編集した値を取得する実装です
	 *
	 * @param ignore
	 * @return
	 */
	abstract public Object getValuesImpl();

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <C extends Content<?>> C getUpperContent(Class<C> contentClazz) {
		if (parent == null) {
			return null;
		}
		if (parent.getClass() == contentClazz) {
			return (C) parent;
		}
		return parent.getUpperContent(contentClazz);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isClosed() {
		return closed;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() {
		closed = true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("definition", definition.getFullId());
		if (parent != null) {
			map.put("parent", parent.getFullId());
		} else {
			map.put("parent", null);
		}
		map.put("closed", closed);
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
