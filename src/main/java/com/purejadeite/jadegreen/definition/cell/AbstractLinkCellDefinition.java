package com.purejadeite.jadegreen.definition.cell;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.AbstractDefinition;
import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.WorkbookDefinitionImpl;

/**
 * 単一セルのリンク定義です
 * @author mitsuhiroseino
 */
abstract public class AbstractLinkCellDefinition extends AbstractDefinition implements LinkCellDefinition {

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = {"mySheetKeyId", "sheetKeyId", "valueId"};

	/**
	 * 自身のシートのキーになる項目のID
	 */
	protected String mySheetKeyId;

	/**
	 * リンク先のキーになる項目のID
	 */
	protected String sheetKeyId;

	/**
	 * リンク先の値を取得する対象の項目のID
	 */
	protected String valueId;

	/**
	 * Book読み込み定義
	 */
	protected WorkbookDefinitionImpl book;

	/**
	 * コンストラクタ
	 * @param book ブック読み込み定義
	 * @param parent シート読み込み定義
	 * @param id 定義ID
	 * @param options オプション
	 * @param config リンク設定
	 */
	protected AbstractLinkCellDefinition(WorkbookDefinitionImpl book, Definition parent, String id, boolean noOutput, Map<String, String> config) {
		super(parent, id, noOutput);
		this.validateConfig(config, CONFIG);
		this.book = book;
		this.mySheetKeyId = config.get("mySheetKeyId");
		this.sheetKeyId = config.get("sheetKeyId");
		this.valueId = config.get("valueId");
	}

	public String getSheetKeyId() {
		return sheetKeyId;
	}

	/**
	 * {@inheritDoc}
	 */
	public Definition getMySheetKeyDefinition() {
		return book.get(mySheetKeyId);
	}

	/**
	 * {@inheritDoc}
	 */
	public Definition getSheetKeyDefinition() {
		return book.get(sheetKeyId);
	}

	/**
	 * {@inheritDoc}
	 */
	public Definition getValueDefinition() {
		return book.get(valueId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("mySheetKeyId", mySheetKeyId);
		map.put("sheetKeyId", sheetKeyId);
		map.put("valueId", valueId);
		map.put("book", book.getFullId());
		return map;
	}

	/**
	 * サポートされていないオペレーションです
	 */
	@Override
	public List<Definition> getChildren() {
		throw new UnsupportedOperationException();
	}

	/**
	 * サポートされていないオペレーションです
	 */
	@Override
	public void addChild(Definition child) {
		throw new UnsupportedOperationException();
	}

	public Object apply(Object value) {
		return value;
	}

}
