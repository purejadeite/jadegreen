package com.purejadeite.jadegreen.content;

import java.io.Serializable;

import org.apache.commons.lang3.ArrayUtils;

import com.purejadeite.core.JsonUtils;
import com.purejadeite.core.ToJson;
import com.purejadeite.jadegreen.definition.Definition;

/**
 * 値を保持する抽象クラス
 * @author mitsuhiroseino
 */
public abstract class AbstractContent<D extends Definition> implements Content, ToJson, Serializable {

	private static final long serialVersionUID = 6746413349706897776L;

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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getRawValues(Definition... ignore) {
		if (ArrayUtils.contains(ignore, this.getDefinition())) {
			return SpecificValue.IGNORE;
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
			return SpecificValue.STUFF;
		} else {
			if (ArrayUtils.contains(ignore, this.getDefinition())) {
				return SpecificValue.IGNORE;
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
	 * JSON形式のnameとvalueの表現を取得します
	 * @param name プロパティ名
	 * @param value 値
	 * @return "name": "value"形式の文字列
	 */
	protected String getJson(String name, Object value) {
		return JsonUtils.getJsonStyle(name, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJson() {
		return getJson("definition", definition.getFullId()) + "," + getJson("parent", parent.getFullId()) + ","
				+ getJson("closed", closed);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "definition=" + definition.getFullId() + ", parent=" + parent.getFullId() + ", closed=" + closed;
	}

}
