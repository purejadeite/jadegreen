package com.purejadeite.jadegreen.definition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Bookの読み込み定義です
 * @author mitsuhiroseino
 *
 */
public class WorkbookDefinitionImpl extends AbstractDefinition {

	/**
	 * シート定義
	 */
	private List<WorksheetDefinitionImpl> worksheets = new ArrayList<>();

	/**
	 * デフォルトコンストラクタ
	 */
	public WorkbookDefinitionImpl() {
		super();
	}

	/**
	 * シートを取得します
	 * @return
	 */
	public List<WorksheetDefinitionImpl> getWorksheets() {
		return worksheets;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addChild(Definition child) {
		this.worksheets.add((WorksheetDefinitionImpl) child);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getId() {
		return null;
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
	public Definition getParent() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		List<Map<String, Object>> sheetMaps = new ArrayList<>();
		for(Definition sheet: worksheets) {
			sheetMaps.add(sheet.toMap());
		}
		map.put("sheets", sheetMaps);
		return map;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<? extends Definition> getChildren() {
		return this.worksheets;
	}

	public Object aplly(Object value) {
		return value;
	}

}
