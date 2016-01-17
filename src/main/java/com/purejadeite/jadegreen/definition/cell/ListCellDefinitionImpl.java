package com.purejadeite.jadegreen.definition.cell;

import static com.purejadeite.jadegreen.definition.DefinitionKeys.*;
import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.purejadeite.jadegreen.definition.WorksheetDefinition;

/**
 * 単一セル内で一覧になっているセルの読み込み定義です
 *
 * @author mitsuhiroseino
 */
public class ListCellDefinitionImpl extends CellDefinitionImpl {

	private static final long serialVersionUID = 8480774727785544322L;

	private String splitter;

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親定義
	 * @param config
	 *            コンフィグ
	 */
	public ListCellDefinitionImpl(WorksheetDefinition parent, Map<String, Object> config) {
		super(parent, config);
		splitter = getString(config, SPLITTER, "\n");
	}

	@Override
	public Object applyOptions(Object value) {
		Object val = super.applyOptions(value);
		if (val == null) {
			return val;
		} else {
			String strVal = val.toString();
			return StringUtils.split(strVal, splitter);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("splitter", splitter);
		return map;
	}

}
