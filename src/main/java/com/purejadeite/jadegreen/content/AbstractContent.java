package com.purejadeite.jadegreen.content;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import com.purejadeite.jadegreen.AbstractToMap;
import com.purejadeite.jadegreen.definition.MappingDefinition;

/**
 * 値を保持する抽象クラス
 * @author mitsuhiroseino
 */
abstract public class AbstractContent<D extends MappingDefinition<?>> extends AbstractToMap implements Content<D>, Serializable {

	private static final long serialVersionUID = 760790316639278651L;

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

	/**
	 * コンストラクター
	 * @param parent 親要素
	 * @param definition 値を取得する定義
	 */
	public AbstractContent(Content<?> parent, D definition) {
		this.parent = parent;
		this.definition = definition;
		this.closed = false;
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
	public Object getRawValues(MappingDefinition<?>... ignore) {
		if (ArrayUtils.contains(ignore, this.getDefinition())) {
			return SpecificValue.UNDEFINED;
		} else {
			return getRawValuesImpl(ignore);
		}
	}

	/**
	 * 編集していない値を取得する実装です
	 * @param ignore 取得対象外とする子要素の定義
	 * @return 値
	 */
	abstract public Object getRawValuesImpl(MappingDefinition<?>... ignore);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValues(MappingDefinition<?>... ignore) {
		if (ArrayUtils.contains(ignore, this.getDefinition())) {
			return SpecificValue.UNDEFINED;
		} else {
			return getValuesImpl(ignore);
		}
	}

	/**
	 * 編集した値を取得する実装です
	 * @param ignore
	 * @return
	 */
	abstract public Object getValuesImpl(MappingDefinition<?>... ignore);

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
		Map<String, Object> map = super.toMap();
		map.put("definition", definition.getFullId());
		if (parent != null) {
			map.put("parent", parent.getFullId());
		} else {
			map.put("parent", null);
		}
		map.put("closed", closed);
		return map;
	}

}
