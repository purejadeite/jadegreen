package purejadeite.jadegreen.definition;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import purejadeite.core.JsonUtils;
import purejadeite.core.ToJson;

/**
*
* Excelファイル読み込みの定義情報抽象クラス
* @author mitsuhiroseino
*
*/
public abstract class AbstractDefinition implements Definition, ToJson, Serializable {

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
	 * データの読み込みのみ行うか
	 * true: 読み込みのみ
	 * false: 読み込みおよび出力
	 * </pre>
	 */
	protected boolean stuff = false;

	public boolean isStuff() {
		return stuff;
	}

	/**
	 * デフォルトコンストラクタ
	 */
	protected AbstractDefinition() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param id 定義ID
	 */
	protected AbstractDefinition(String id) {
		super();
		this.id = id;
	}

	/**
	 * コンストラクタ
	 * @param parent 親定義
	 * @param id 定義ID
	 * @param stuff データの読み込みのみ行うか
	 */
	protected AbstractDefinition(Definition parent, String id, boolean stuff) {
		super();
		this.parent = parent;
		this.id = id;
		this.stuff = stuff;
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
	public Definition getParent() {
		return parent;
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
		return getJson("id", id) + "," + getJson("stuff", stuff) + "," + getJson("parent", parent.getFullId());

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "id=" + id + ", stuff=" + stuff + ", parent=" + parent.getFullId();
	}

}
