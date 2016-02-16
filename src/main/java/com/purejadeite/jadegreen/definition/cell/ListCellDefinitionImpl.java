package com.purejadeite.jadegreen.definition.cell;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.purejadeite.jadegreen.definition.SheetDefinition;
import com.purejadeite.jadegreen.definition.table.TableDefinition;

/**
 * 値がListの単一セルの読み込み定義です
 * @author mitsuhiroseino
 */
public class ListCellDefinitionImpl extends CellDefinitionImpl {

	protected static final String CFG_SPLITTER = "splitter";

	/**
	 * 分割文字列
	 */
	protected String splitter;

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親定義
	 * @param config
	 *            コンフィグ
	 */
	public ListCellDefinitionImpl(SheetDefinition parent, Map<String, Object> config) {
		super(parent, config);
		this.splitter = getString(config, CFG_SPLITTER, "\n");
	}

	public String getSplitter() {
		return splitter;
	}

	public static boolean assess(TableDefinition<?> table, Map<String, Object> config) {
		return CellDefinitionImpl.assess(config, table) && config.containsKey(CFG_SPLITTER);
	}

	@SuppressWarnings("unchecked")
	public Object applyValue(Object value, Object appliedValues) {
		// 行列番号が取得範囲内の場合
		List<Object> vals = null;
		if (appliedValues == null) {
			vals = new ArrayList<Object>();
		} else {
			vals = (List<Object>) appliedValues;
		}
		if (value instanceof String) {
			String strValue = (String) value;
			String[] strArray = StringUtils.split(strValue, getSplitter());
			for (String str : strArray) {
				vals.add(str);
			}
		} else {
			vals.add(value);
		}
		return vals;
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
