package com.purejadeite.jadegreen.definition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.option.book.BookOptionManager;

/**
 * Bookの読み込み定義です
 * @author mitsuhiroseino
 *
 */
public class BookDefinition extends AbstractParentDefinition<NoDefinition, SheetDefinition> {

	private static final long serialVersionUID = 1118031049839086924L;

	/**
	 * シート
	 */
	public static final String CFG_SHEETS = "sheets";

	/**
	 * コンストラクタ
	 * @param config コンフィグ
	 */
	public BookDefinition(Map<String, Object> config) {
		super(config);
	}

	/**
	 * インスタンスを取得します
	 * @param config コンフィグ
	 * @return インスタンス
	 */
	public static BookDefinition newInstance(Map<String, Object> config) {
		return new BookDefinition(config);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getFullId() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		List<Map<String, Object>> sheetMaps = new ArrayList<>();
		for(SheetDefinition sheet: getChildren()) {
			sheetMaps.add(sheet.toMap());
		}
		map.put("sheets", sheetMaps);
		return map;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void buildOptions(Definition<?> definition, List<Map<String, Object>> options) {
		this.options = BookOptionManager.build(definition, options);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addChild(SheetDefinition child) {
		super.addChild(child);
	}

}
