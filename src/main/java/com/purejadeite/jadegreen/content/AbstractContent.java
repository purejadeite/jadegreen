package com.purejadeite.jadegreen.content;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.purejadeite.jadegreen.definition.Definition;

/**
 * 値を保持する抽象クラス
 * @author mitsuhiroseino
 */
public abstract class AbstractContent<D extends Definition> implements Content, Serializable {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	/**
	 * 親要要素
	 */
	protected Content parent;

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
	public AbstractContent(Content parent, D definition) {
		this.parent = parent;
		this.definition = definition;
		this.closed = false;
	}

	@Override
	public String getId() {
		return definition.getId();
	}

	@Override
	public String getFullId() {
		return definition.getFullId();
	}

	@Override
	public Definition getDefinition() {
		return definition;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getRawValues(Definition... ignore) {
		if (ArrayUtils.contains(ignore, this.getDefinition())) {
			return SpecificValue.INVALID;
		} else {
			return getRawValuesImpl(ignore);
		}
	}

	/**
	 * 編集していない値を取得する実装です
	 * @param ignore 取得対象外とする子要素の定義
	 * @return 値
	 */
	abstract public Object getRawValuesImpl(Definition... ignore);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValues(Definition... ignore) {
		if (this.getDefinition().isNoOutput()) {
			return SpecificValue.NO_OUTPUT;
		} else {
			if (ArrayUtils.contains(ignore, this.getDefinition())) {
				return SpecificValue.INVALID;
			} else {
				return getValuesImpl(ignore);
			}
		}
	}

	/**
	 * 編集した値を取得する実装です
	 * @param ignore
	 * @return
	 */
	abstract public Object getValuesImpl(Definition... ignore);

	public Content getUpperContent(Class<? extends Content> contentClazz) {
		if (parent == null) {
			return null;
		}
		if (parent.getClass() == contentClazz) {
			return parent;
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
	public String toJson() {
		try {
			return OBJECT_MAPPER.writeValueAsString(toMap());
		} catch (IOException e) {
			return null;
		}
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("definition", definition.getFullId());
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
		return "definition=" + definition.getFullId() + ", parent=" + parent.getFullId();
	}

}
