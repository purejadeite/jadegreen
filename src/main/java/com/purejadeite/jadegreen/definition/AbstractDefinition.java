package com.purejadeite.jadegreen.definition;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.purejadeite.jadegreen.content.ContentInterface;
import com.purejadeite.jadegreen.definition.table.CategoryDefinitionInterface;
import com.purejadeite.jadegreen.definition.table.TableDefinitionInterface;
import com.purejadeite.jadegreen.option.Options;

/**
*
* Excelファイル読み込みの定義情報抽象クラス
* @author mitsuhiroseino
*
*/
abstract public class AbstractDefinition<P extends ParentDefinitionInterface<?, ?>> implements DefinitionInterface<P>, Serializable {

	private static final long serialVersionUID = -847224181929765049L;

	/**
	 * 定義ID
	 */
	protected String id;

	/**
	 * 出力時の項目名
	 */
	protected String name;

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
		this.name = getString(config, CFG_NAME, id);

		// オプションの元になる値をここで取得して、継承先でOptionsのインスタンスを作る
		List<Map<String, Object>> opts = getAsList(config, CFG_OPTIONS);
		buildOptions(this, opts);
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
	public String getName() {
		return name;
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

	abstract protected void buildOptions(DefinitionInterface<?> definition, List<Map<String, Object>> options);

	@Override
	public Object applyOptions(Object value, ContentInterface<?, ?> content) {
		if (options == null) {
			return value;
		} else {
			return options.apply(value, content);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BookDefinition getBook() {
		if (parent == null) {
			return null;
		}
		return parent.getBook();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SheetDefinition getSheet() {
		if (parent == null) {
			return null;
		}
		return parent.getSheet();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SheetDefinition getOutputSheet() {
		if (parent == null) {
			return null;
		}
		return parent.getOutputSheet();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SheetDefinition getSheet(String sheetId) {
		if (parent == null) {
			return null;
		}
		return parent.getSheet(sheetId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TableDefinitionInterface<?> getTable() {
		if (parent == null) {
			return null;
		}
		return parent.getTable();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CategoryDefinitionInterface<?> getCategory() {
		if (parent == null) {
			return null;
		}
		return parent.getCategory();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DefinitionInterface<?> getCell(String cellId) {
		if (parent == null) {
			return null;
		}
		return parent.getCell(cellId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DefinitionInterface<?> getCell(String sheetId, String cellId) {
		if (parent == null) {
			return null;
		}
		return parent.getCell(sheetId, cellId);
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
