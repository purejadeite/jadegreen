package com.purejadeite.jadegreen.definition.cell;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.purejadeite.jadegreen.definition.ParentDefinitionInterface;
import com.purejadeite.jadegreen.definition.SheetDefinition;
import com.purejadeite.util.collection.Table;

/**
 * 値がListの単一セルの読み込み定義です
 * @author mitsuhiroseino
 */
public class ListCellDefinition extends AbstractListDefinition {

	protected static final String CFG_SPLITTER = "splitter";

	/**
	 * 取得対象列
	 */
	protected int row = 0;

	/**
	 * 取得対象行
	 */
	protected int col = 0;

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
	public ListCellDefinition(SheetDefinition parent, Map<String, Object> config) {
		super(parent, config);
		col = getIntValue(config, CFG_COLUMN);
		row = getIntValue(config, CFG_ROW);
		splitter = getString(config, CFG_SPLITTER, "\n");
	}

	public static boolean assess(Map<String, Object> config, ParentDefinitionInterface<?, ?> table) {
		return config.containsKey(CFG_COLUMN) && config.containsKey(CFG_ROW) && config.containsKey(CFG_SPLITTER);
	}

	@Override
	public List<Object> capture(Table<String> table) {
		String value = table.get(row - 1, col - 1);
		if (value == null) {
			return null;
		}
		Object[] values = StringUtils.split(value, splitter);
		return Arrays.asList(values);
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
