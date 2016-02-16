package com.purejadeite.jadegreen.definition.table;

import java.util.Map;

import com.purejadeite.jadegreen.definition.ParentDefinition;
import com.purejadeite.jadegreen.definition.SheetDefinition;
import com.purejadeite.jadegreen.definition.table.cell.TableCellDefinition;

/**
 * 行方向の繰り返し範囲の情報を保持するクラスの抽象クラスです
 * @author mitsuhiroseino
 */
public class RowRepeatDefinitionImpl extends AbstractTableDefinition<TableCellDefinition<?>> {

	private static final long serialVersionUID = -8127378359336780394L;

	/**
	 * 行番号
	 */
	public static final String CFG_ROW = "row";

	/**
	 * 終了行番号
	 */
	public static final String CFG_END_ROW = "endRow";

	/**
	 * 行方向の繰り返しの子要素
	 */
	public static final String CFG_COLUMNS = "columns";

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
	public RowRepeatDefinitionImpl(SheetDefinition parent, Map<String, Object> config) {
		super(parent, config);
	}

	public static boolean assess(Map<String, Object> config, ParentDefinition<?, ?> table) {
		return config.containsKey(CFG_ROW) && config.containsKey(CFG_COLUMNS);
	}

	/**
	 * {@inheritDoc}
	 */
	protected String getBeginDefinitionKey() {
		return CFG_ROW;
	}

	/**
	 * {@inheritDoc}
	 */
	protected String getEndConfigDefinitionKey() {
		return CFG_END_ROW;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isIncluded(int row, int col) {
		return (begin <= row && row <= end);
	}
}
