package com.purejadeite.jadegreen.definition.cell;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.ParentDefinitionInterface;
import com.purejadeite.util.collection.Table;

/**
 * 単一セルの繰り返し読み込み定義です
 *
 * @author mitsuhiroseino
 */
abstract public class AbstractRangeDefinition
		extends AbstractCellDefinition<ParentDefinitionInterface<?, ?>, List<Object>> {

	/**
	 * 列数/行数の上限なし
	 */
	public static final int UNLIMITED = Integer.MAX_VALUE;

	/**
	 * 繰り返しの取得終了判定値
	 */
	public static final String CFG_BREAK_VALUE = "breakValue";

	/**
	 * 取得対象列
	 */
	protected int row = 0;

	/**
	 * 取得対象行
	 */
	protected int col = 0;

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
	public AbstractRangeDefinition(ParentDefinitionInterface<?, ?> parent, Map<String, Object> config) {
		super(parent, config);
		breakValues = getAsList(config, CFG_BREAK_VALUE);
		end = getIntValue(config, getEndId(), -1);
	}

	abstract protected String getEndId();

	abstract protected int getEndRow(Table<String> table);

	abstract protected int getEndCol(Table<String> table);

	@Override
	public List<Object> capture(Table<String> table) {
		List<Object> values = new ArrayList<>();
		int endRow = getEndRow(table);
		int endCol = getEndCol(table);

		for (int r = row; r < endRow; r++) {
			for (int c = col; c < endCol; c++) {
				String value = table.get(r - 1, c - 1);
				if (end == -1 && breakValues != null && breakValues.contains(value)) {
					// 終了条件がセルの値の場合
					return values;
				}
				values.add(value);
			}
		}
		return values;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		return map;
	}

}
