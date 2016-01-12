package com.purejadeite.jadegreen.definition.cell;

import static com.purejadeite.jadegreen.definition.DefinitionKeys.*;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.purejadeite.jadegreen.definition.WorksheetDefinition;
import com.purejadeite.util.collection.RoughlyMapUtils;

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
	 *            シート読み込み定義
	 * @param id
	 *            定義ID
	 * @param row
	 *            取得対象行
	 * @param col
	 *            取得対象列
	 * @param options
	 *            コンバーター
	 */
	private ListCellDefinitionImpl(WorksheetDefinition parent, String id, int row, int col, String splitter,
			List<Map<String, Object>> options) {
		super(parent, id, row, col, options);
		this.splitter = splitter;
	}

	public static CellDefinition<WorksheetDefinition> newInstance(WorksheetDefinition parent, Map<String, Object> config) {
		String id = RoughlyMapUtils.getString(config, ID);
		int row = RoughlyMapUtils.getIntValue(config, ROW);
		int col = RoughlyMapUtils.getIntValue(config, COLUMN);
		String splitter = RoughlyMapUtils.getString(config, SPLITTER, "\n");
		List<Map<String, Object>> options = RoughlyMapUtils.getList(config, OPTIONS);
		return new ListCellDefinitionImpl(parent, id, row, col, splitter, options);
	}

	@Override
	public Object apply(Object value) {
		Object val = super.apply(value);
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
