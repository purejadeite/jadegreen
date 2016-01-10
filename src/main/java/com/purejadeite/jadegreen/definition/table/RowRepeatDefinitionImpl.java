package com.purejadeite.jadegreen.definition.table;

import static com.purejadeite.jadegreen.definition.DefinitionKeys.*;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.WorksheetDefinition;
import com.purejadeite.jadegreen.definition.cell.TableCellDefinition;
import com.purejadeite.util.RoughlyMapUtils;

/**
 * 行方向の繰り返し範囲の情報を保持するクラスの抽象クラスです
 * @author mitsuhiroseino
 */
public class RowRepeatDefinitionImpl extends AbstractTableDefinition<TableCellDefinition<?>> {

	private static final long serialVersionUID = -8127378359336780394L;

	/**
	 * コンストラクタ
	 * @param parent 親の読み込み情報
	 * @param id 定義ID
	 * @param beginRow 開始行
	 * @param endRow 終了行
	 * @param endKey 開始キー項目
	 * @param endValue 終了キー値
	 * @param options オプション
	 */
	public RowRepeatDefinitionImpl(WorksheetDefinition parent, String id, int beginRow, int endRow, String endKey,
			String endValue, List<Map<String, Object>> options) {
		super(parent, id, beginRow, endRow, endKey, endValue, options);
	}

	public static TableDefinition<?> newInstance(WorksheetDefinition parent, Map<String, Object> config) {
		String id = RoughlyMapUtils.getString(config, ID);
		int beginRow = RoughlyMapUtils.getIntValue(config, ROW);
		int endRow = RoughlyMapUtils.getIntValue(config, END_ROW);
		String endKey = RoughlyMapUtils.getString(config, BREAK_ID);
		String endValue = RoughlyMapUtils.getString(config, BREAK_VALUE);
		List<Map<String, Object>> options = RoughlyMapUtils.getList(config, OPTIONS);
		return new RowRepeatDefinitionImpl(parent, id, beginRow, endRow, endKey, endValue,
				options);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isIncluded(int row, int col) {
		return (begin <= row && row <= end);
	}
}
