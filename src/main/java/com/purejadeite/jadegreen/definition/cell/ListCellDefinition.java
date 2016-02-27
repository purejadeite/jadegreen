package com.purejadeite.jadegreen.definition.cell;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.purejadeite.jadegreen.definition.ParentDefinitionInterface;
import com.purejadeite.jadegreen.definition.SheetDefinition;
import com.purejadeite.util.collection.Table;

/**
 * 値がListの単一セルの読み込み定義です
 * @author mitsuhiroseino
 */
public class ListCellDefinition extends CellDefinition {

	protected static final String CFG_SPLITTER = "splitter";

	protected static final String CFG_ALWAYS = "always";

	/**
	 * 分割文字列
	 */
	protected String splitter;

	protected boolean always;

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
		this.splitter = getString(config, CFG_SPLITTER, "\n");
		this.always = getBooleanValue(config, CFG_ALWAYS, true);
	}

	public static boolean assess(Map<String, Object> config, ParentDefinitionInterface<?, ?> table) {
		return config.containsKey(CFG_COLUMN) && config.containsKey(CFG_ROW) && config.containsKey(CFG_SPLITTER);
	}

	@Override
	public Object capture(Table<String> table) {
		String value = table.get(row - 1, col - 1);
		if (value == null) {
			return null;
		}
		String[] values = StringUtils.split(value, splitter);
		if (values.length == 1 && !always) {
			return value;
		} else {
			return Arrays.asList(values);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("splitter", splitter);
		map.put("always", always);
		return map;
	}

}
