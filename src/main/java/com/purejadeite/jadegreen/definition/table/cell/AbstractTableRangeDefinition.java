package com.purejadeite.jadegreen.definition.table.cell;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.table.TableDefinitionInterface;
import com.purejadeite.util.collection.LazyTable;
import com.purejadeite.util.collection.Table;

/**
 * 単一セルの繰り返し読み込み定義です
 * @author mitsuhiroseino
 */
abstract public class AbstractTableRangeDefinition<P extends TableDefinitionInterface<?>> extends AbstractTableCellDefinition<P> {

	/**
	 * 列数/行数の上限なし
	 */
	public static final int UNLIMITED = Integer.MAX_VALUE;

	/**
	 * 繰り返しの取得終了判定値
	 */
	public static final String CFG_BREAK_VALUE = "breakValue";

	/**
	 * 終了行／列
	 */
	protected int end;

	/**
	 * 終了キー値
	 */
	protected List<String> breakValues = null;

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親定義
	 * @param config
	 *            コンフィグ
	 */
	public AbstractTableRangeDefinition(P parent, Map<String, Object> config) {
		super(parent, config);
		breakValues = getAsList(config, CFG_BREAK_VALUE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		return map;
	}

	@Override
	public Object capture(Table<String> table) {
		Table<Object> values = new LazyTable<>();
		int beginX = beginCol - 1;
		int endX = Math.min(endCol - 1, table.getColumnSize() - 1);
		int beginY = beginRow - 1;
		int endY = Math.min(endRow - 1, table.getRowSize() - 1);
		List<String> breakValues = getBreakValues();
		boolean breakAll = false;

		for (int y = beginY; y < endY + 1; y++) {
			for (int x = beginX; x < endX + 1; x++) {
				String value = table.get(y, x);
				if (breakValues != null && breakValues.contains(value)) {
					if (x == beginX) {
						// 一列目でブレイクした場合は処理終了
						breakAll = true;
					}
					break;
				} else if (x == beginX) {
					// 一列目の場合は行を追加
					values.addRow();
				}
				// ブレイクしていないならば値を取得
				values.addValue(value);
			}
			if (breakAll) {
				// 処理終了
				break;
			}
		}
		return values.getAdjustedTable();
	}

	@Override
	public Object capture(Table<String> table, int size) {
		@SuppressWarnings("unchecked")
		List<Object> values = (List<Object>) capture(table);

		if (getTable().getBreakId() == null) {
			// 終了位置が指定されている場合(BreakIdが指定されていない場合)
			return values.size();
		}

		// BreakIdが指定されている場合はsizeに合わせる
		if (size < values.size()) {
			// 超過
			values = values.subList(0, size);
		} else if (values.size() < size) {
			// 不足
			while (values.size() < size) {
				values.add(null);
			}
		}

		return values;
	}
}
