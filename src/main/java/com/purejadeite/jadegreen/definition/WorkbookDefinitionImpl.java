package com.purejadeite.jadegreen.definition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Bookの読み込み定義です
 * @author mitsuhiroseino
 *
 */
public class WorkbookDefinitionImpl extends AbstractParentDefinition<NoDefinition, WorksheetDefinitionImpl> {

	/**
	 * デフォルトコンストラクタ
	 */
	public WorkbookDefinitionImpl(Map<String, Object> config) {
		super(null, config);
	}

	public static WorkbookDefinitionImpl newInstance(Map<String, Object> config) {
		return new WorkbookDefinitionImpl(config);
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
		for(WorksheetDefinitionImpl sheet: getChildren()) {
			sheetMaps.add(sheet.toMap());
		}
		map.put("sheets", sheetMaps);
		return map;
	}

	public Object apply(Object value) {
		return value;
	}

}
