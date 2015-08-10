package purejadeite.jadegreen.content;

import purejadeite.core.JsonUtils;
import purejadeite.core.ToJson;
import purejadeite.jadegreen.definition.Definition;

import java.io.Serializable;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Rangeの構成要素となるCell読み込み定義
 * @author mitsuhiroseino
 */
public abstract class AbstractContent<D extends Definition> implements Content, ToJson, Serializable {

	private static final long serialVersionUID = 6746413349706897776L;

	protected Content parent;

	protected D definition;

	protected boolean closed;

	/**
	 * コンストラクタ
	 */
	public AbstractContent(Content parent, D definition) {
		this.parent = parent;
		this.definition = definition;
		this.closed = false;
	}

	@Override
	public Object getRawValues(Definition... ignore) {
		if (ArrayUtils.contains(ignore, this.getDefinition())) {
			return SpecificValue.IGNORE;
		} else {
			return getRawValuesImpl(ignore);
		}
	}

	abstract public Object getRawValuesImpl(Definition... ignore);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValues(Definition... ignore) {
		if (this.getDefinition().isStuff()) {
			return SpecificValue.STUFF;
		} else {
			if (ArrayUtils.contains(ignore, this.getDefinition())) {
				return SpecificValue.IGNORE;
			} else {
				return getValuesImpl(ignore);
			}
		}
	}

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
