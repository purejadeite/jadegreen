package com.purejadeite.jadegreen.definition.cell;

import static com.purejadeite.jadegreen.definition.DefinitionKeys.*;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.WorksheetDefinition;
import com.purejadeite.jadegreen.definition.table.TableDefinition;
import com.purejadeite.util.collection.RoughlyMapUtils;

/**
 * 行方向の繰り返しを持つテーブル配下のCell読み込み定義
 * @author mitsuhiroseino
 */
public class RowCellDefinitionImpl extends AbstractTableCellDefinition<TableDefinition<?>> {

	private static final long serialVersionUID = 1338170679621437792L;

	/**
	 * コンストラクタ
	 * @param table テーブル読み込み定義
	 * @param id 定義ID
	 * @param beginRow 取得開始行
	 * @param endRow 取得終了行
	 * @param col 取得対象列
	 * @param endKey 終了キー項目フラグ
	 * @param endValue 終了キー値
	 * @param options オプション
	 */
	private RowCellDefinitionImpl(WorksheetDefinition sheet, TableDefinition<?> table, String id, int beginRow, int endRow, int col,
			boolean endKey, String endValue, List<Map<String, Object>> options) {
		super(table, id, beginRow, endRow, col, col, endKey, endValue, options);
		this.sheet = sheet;
	}

	public static CellDefinition<TableDefinition<?>> newInstance(WorksheetDefinition sheet, TableDefinition<?> table, Map<String, Object> config) {
		String id = RoughlyMapUtils.getString(config, ID);
		int col = RoughlyMapUtils.getIntValue(config, COLUMN);
		List<Map<String, Object>> options = RoughlyMapUtils.getList(config, OPTIONS);

		boolean endKey = false;
		String endValue = null;
		if (id.equals(table.getBreakId())) {
			// 終了条件
			endKey = true;
			endValue = table.getBreakValue();
		}
		return new RowCellDefinitionImpl(sheet, table, id, table.getBegin(), table.getEnd(), col, endKey, endValue,
				options);
	}
}
