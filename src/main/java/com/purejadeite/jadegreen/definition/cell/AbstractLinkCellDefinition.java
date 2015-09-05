package com.purejadeite.jadegreen.definition.cell;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.AbstractDefinition;
import com.purejadeite.jadegreen.definition.BookDefinitionImpl;
import com.purejadeite.jadegreen.definition.Definition;

/**
 * 単一セルのリンク定義です
 * @author mitsuhiroseino
 */
public abstract class AbstractLinkCellDefinition extends AbstractDefinition implements LinkCellDefinition {

	protected String mySheetKeyId;

	/**
	 * リンク先のキーになる定義ID
	 */
	protected String sheetKeyId;

	/**
	 * リンク先取得する値の定義ID
	 */
	protected String valueId;

	/**
	 * Book読み込み定義
	 */
	protected BookDefinitionImpl book;

	/**
	 * コンストラクタ
	 * @param book ブック読み込み定義
	 * @param parent シート読み込み定義
	 * @param id 定義ID
	 * @param options オプション
	 * @param config リンク設定
	 */
	protected AbstractLinkCellDefinition(BookDefinitionImpl book, Definition parent, String id, boolean noOutput, Map<String, String> config) {
		super(parent, id, noOutput);
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

	public Object aplly(Object value) {
		return value;
	}

}