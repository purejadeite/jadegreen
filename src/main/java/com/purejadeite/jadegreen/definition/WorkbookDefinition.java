package com.purejadeite.jadegreen.definition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Bookの読み込み定義です
 * @author mitsuhiroseino
 *
 */
public class WorkbookDefinition extends AbstractParentMappingDefinition<NoDefinition, WorksheetDefinition> {

	private static final long serialVersionUID = 1118031049839086924L;

	/**
	 * コンストラクタ
	 * @param config コンフィグ
	 */
	public WorkbookDefinition(Map<String, Object> config) {
		super(null, config);
	}

	/**
	 * インスタンスを取得します
	 * @param config コンフィグ
	 * @return インスタンス
	 */
	public static WorkbookDefinition newInstance(Map<String, Object> config) {
		return new WorkbookDefinition(config);
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
		for(WorksheetDefinition sheet: getChildren()) {
			sheetMaps.add(sheet.toMap());
		}
		map.put("sheets", sheetMaps);
		return map;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object apply(Object value) {
		return value;
	}

}
