package com.purejadeite.jadegreen.definition.cell;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.purejadeite.jadegreen.definition.SheetDefinition;
import com.purejadeite.jadegreen.definition.table.TableDefinition;
import com.purejadeite.util.collection.Table;

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

	@Override
	public Object capture(Table<String> table) {
		String value = table.get(row - 1, col - 1);
		String[] values = StringUtils.split(value, splitter);
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
