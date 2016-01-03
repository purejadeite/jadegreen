package com.purejadeite.jadegreen.definition.cell;

import static com.purejadeite.jadegreen.definition.DefinitionKeys.*;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.table.TableDefinition;
import com.purejadeite.util.RoughlyMapUtils;

/**
 * 列方向の繰り返しを持つテーブル配下のCell読み込み定義
 *
 * @author mitsuhiroseino
 */
public class ColumnCellDefinitionImpl extends AbstractTableCellDefinition<TableDefinition<?>> {

	private static final long serialVersionUID = -1504987549447864687L;

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            テーブル読み込み定義
	 * @param id
	 *            定義ID
	 * @param row
	 *            取得対象行
	 * @param beginCol
	 *            取得開始列
	 * @param endCol
	 *            取得終了列
	 * @param endKey
	 *            終了キー項目フラグ
	 * @param endValue
	 *            終了キー値
	 * @param options
	 *            オプション
	 */
	private ColumnCellDefinitionImpl(TableDefinition<?> parent, String id, boolean noOutput, int row, int beginCol, int endCol,
			boolean endKey, String endValue, List<Map<String, Object>> options) {
		super(parent, id, noOutput, row, row, beginCol, endCol, endKey, endValue, options);
	}

	public static CellDefinition<TableDefinition<?>> newInstance(TableDefinition<?> table, Map<String, Object> config) {
		String id = RoughlyMapUtils.getString(config, ID);
		boolean noOutput = RoughlyMapUtils.getBooleanValue(config, NO_OUTPUT);
		int row = RoughlyMapUtils.getIntValue(config, ROW);
		List<Map<String, Object>> options = RoughlyMapUtils.getList(config, OPTIONS);
		boolean endKey = false;
		String endValue = null;
		if (id.equals(table.getEndKeyId())) {
			// 終了条件
			endKey = true;
			endValue = table.getEndValue();
		}
		return new ColumnCellDefinitionImpl(table, id, noOutput, row, table.getBegin(), table.getEnd(), endKey,
				endValue, options);
	}

}
