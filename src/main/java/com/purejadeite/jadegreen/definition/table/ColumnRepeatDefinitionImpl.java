package com.purejadeite.jadegreen.definition.table;

import static com.purejadeite.jadegreen.definition.DefinitionKeys.*;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.WorksheetDefinition;
import com.purejadeite.jadegreen.definition.cell.TableCellDefinition;
import com.purejadeite.util.collection.RoughlyMapUtils;

/**
 * 列方向の繰り返し範囲の情報を保持するクラスの抽象クラスです
 * @author mitsuhiroseino
 */
public class ColumnRepeatDefinitionImpl extends AbstractTableDefinition<TableCellDefinition<?>> {

	private static final long serialVersionUID = -1028109893285661849L;

	/**
	 * コンストラクタ
	 * @param parent 親の読み込み情報
	 * @param id 定義ID
	 * @param beginCol 開始列
	 * @param endCol 終了列
	 * @param endKey 開始キー項目
	 * @param endValue 終了キー値
	 * @param options オプション
	 */
	public ColumnRepeatDefinitionImpl(WorksheetDefinition parent, String id, int beginCol, int endCol, String endKey,
			String endValue,
			List<Map<String, Object>> options) {
		super(parent, id, beginCol, endCol, endKey, endValue, options);
	}

	public static TableDefinition<?> newInstance(WorksheetDefinition parent, Map<String, Object> config) {
		String id = RoughlyMapUtils.getString(config, ID);
		int beginCol = RoughlyMapUtils.getIntValue(config, COLUMN);
		int endCol = RoughlyMapUtils.getIntValue(config, END_COLUMN);
		String breakId = RoughlyMapUtils.getString(config, BREAK_ID);
		String breakValue = RoughlyMapUtils.getString(config, BREAK_VALUE);
		List<Map<String, Object>> options = RoughlyMapUtils.getList(config, OPTIONS);
		return new ColumnRepeatDefinitionImpl(parent, id, beginCol, endCol, breakId, breakValue,
				options);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isIncluded(int row, int col) {
		return (begin <= col && col <= end);
	}

}
