package com.purejadeite.jadegreen.definition.table;

import java.util.Map;

import com.purejadeite.jadegreen.definition.WorksheetDefinition;
import com.purejadeite.jadegreen.definition.cell.TableCellDefinition;

/**
 * 列方向の繰り返し範囲の情報を保持するクラスの抽象クラスです
 * @author mitsuhiroseino
 */
public class ColumnRepeatDefinitionImpl extends AbstractTableDefinition<TableCellDefinition<?>> {

	private static final long serialVersionUID = -1028109893285661849L;

	/**
	 * 列番号
	 */
	public static final String CFG_COLUMN = "column";

	/**
	 * 終了列番号
	 */
	public static final String CFG_END_COLUMN = "endColumn";

	/**
	 * 列方向の繰り返しの子要素
	 */
	public static final String CFG_ROWS = "rows";

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
	public ColumnRepeatDefinitionImpl(WorksheetDefinition parent, Map<String, Object> config) {
		super(parent, config);
	}

	public static boolean assess(TableDefinition<?> table, Map<String, Object> config) {
		return table == null && config.containsKey(CFG_COLUMN) && config.containsKey(CFG_ROWS);
	}

	/**
	 * {@inheritDoc}
	 */
	protected String getBeginDefinitionKey() {
		return CFG_COLUMN;
	}

	/**
	 * {@inheritDoc}
	 */
	protected String getEndConfigDefinitionKey() {
		return CFG_END_COLUMN;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isIncluded(int row, int col) {
		return (begin <= col && col <= end);
	}

}
