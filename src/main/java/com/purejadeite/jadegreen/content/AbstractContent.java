package com.purejadeite.jadegreen.content;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.DefinitionInterface;

/**
 * 値を保持する抽象クラス
 *
 * @author mitsuhiroseino
 */
abstract public class AbstractContent<P extends ContentInterface<?, ?>, D extends DefinitionInterface<?>>
		implements ContentInterface<P, D>, Serializable {

	private static final long serialVersionUID = 760790316639278651L;

	protected String uuid;

	/**
	 * 親要要素
	 */
	protected P parent;

	/**
	 * 値を取得する定義
	 */
	protected D definition;

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
	public AbstractContent(String uuid, P parent, D definition) {
		this.uuid = uuid;
		this.parent = parent;
		this.definition = definition;
	}

	/**
	 * コンストラクター
	 *
	 * @param parent
	 *            親要素
	 * @param definition
	 *            値を取得する定義
	 */
	public AbstractContent(P parent, D definition) {
		if (parent != null) {
			this.uuid = parent.getUuid();
			this.parent = parent;
		}
		this.definition = definition;
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
	public String getName() {
		return definition.getName();
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
	public P getParent() {
		return parent;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BookContent getBook() {
		if (parent == null) {
			return null;
		}
		return parent.getBook();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SheetContent getSheet() {
		if (parent == null) {
			return null;
		}
		return parent.getSheet();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<SheetContent> getSheets(DefinitionInterface<?> sheetDefinition) {
		if (parent == null) {
			return null;
		}
		return parent.getSheets(sheetDefinition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<SheetContent> getSheets(DefinitionInterface<?> myKeyDefinition, DefinitionInterface<?> keyDefinition) {
		ContentInterface<?, ?> myKeyContent = this.getSheet().getCell(myKeyDefinition);
		return getSheets(myKeyContent, keyDefinition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<SheetContent> getSheets(ContentInterface<?, ?> myKeyContent, DefinitionInterface<?> keyDefinition) {
		List<SheetContent> sheets = new ArrayList<>();
		List<ContentInterface<?, ?>> contents = getContents(keyDefinition);
		for (ContentInterface<?, ?> content : contents) {
			if (myKeyContent == content) {
				// 自身は対象外
				continue;
			}
			Object myValues = myKeyContent.getValues();
			Object values = content.getValues();
			if (myValues == values || (myValues != null && myValues.equals(values))) {
				sheets.add(content.getSheet());
			}
		}
		return sheets;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TableContentInterface getTable() {
		if (parent == null) {
			return null;
		}
		return parent.getTable();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CategoryContentInterface getCategory() {
		if (parent == null) {
			return null;
		}
		return parent.getCategory();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ContentInterface<?, ?> getCell(DefinitionInterface<?> cellDefinition) {
		if (parent == null) {
			return null;
		}
		return parent.getCell(cellDefinition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ContentInterface<?, ?> getCell(String id) {
		if (parent == null) {
			return null;
		}
		return parent.getCell(id);
	}

	/**
	 * 定義に一致するコンテンツを取得します
	 *
	 * @param definition
	 *            定義
	 * @return コンテンツのリスト
	 */
	@Override
	public List<ContentInterface<?, ?>> getContents(DefinitionInterface<?> definition) {
		if (parent == null) {
			return null;
		}
		return parent.getContents(definition);
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
