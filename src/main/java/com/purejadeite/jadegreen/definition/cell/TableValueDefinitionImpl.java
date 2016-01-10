package com.purejadeite.jadegreen.definition.cell;

import static com.purejadeite.jadegreen.definition.DefinitionKeys.*;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.WorksheetDefinition;
import com.purejadeite.jadegreen.definition.table.TableDefinition;
import com.purejadeite.util.RoughlyMapUtils;

/**
 * 固定値の定義です
 *
 * @author mitsuhiroseino
 */
public class TableValueDefinitionImpl<P extends TableDefinition<?>> extends AbstractNoAdressTableCellDefinition<P> {

	private static final long serialVersionUID = 960962162579025353L;

	private String value;

	/**
	 * コンストラクタ
	 *
	 * @param table
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
	private TableValueDefinitionImpl(WorksheetDefinition sheet, P table, String id, String value,
			List<Map<String, Object>> options) {
		super(table, id, options);
		this.sheet = sheet;
		this.value = value;
	}

	/**
	 * インスタンスを取得します
	 *
	 * @param table
	 *            シート読み込み定義
	 * @param id
	 *            定義ID
	 * @param noOutput
	 *            出力可否
	 * @param row
	 *            取得対象行
	 * @param col
	 *            取得対象列
	 * @param options
	 *            コンバーター
	 * @return ラップされたCell読み込み定義
	 */
	public static <P extends TableDefinition<?>> CellDefinition<P> newInstance(WorksheetDefinition sheet, P table, Map<String, Object> config) {
		String id = RoughlyMapUtils.getString(config, ID);
		String value = RoughlyMapUtils.getString(config, VALUE);
		List<Map<String, Object>> options = RoughlyMapUtils.getList(config, OPTIONS);
		return new TableValueDefinitionImpl<P>(sheet, table, id, value, options);
	}

	@Override
	public Object apply(Object value) {
		return super.apply(value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("value", value);
		return map;
	}

}
