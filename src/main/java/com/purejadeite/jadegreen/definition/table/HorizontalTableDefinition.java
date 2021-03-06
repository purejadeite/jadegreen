package com.purejadeite.jadegreen.definition.table;

import java.util.Map;

import com.purejadeite.jadegreen.definition.ParentDefinitionInterface;
import com.purejadeite.jadegreen.definition.SheetDefinition;
import com.purejadeite.jadegreen.definition.table.cell.TableCellDefinitionInterface;

/**
 * 列方向の繰り返し範囲の情報を保持するクラスの抽象クラスです
 * @author mitsuhiroseino
 */
public class HorizontalTableDefinition extends AbstractTableDefinition<TableCellDefinitionInterface<?, ?>> {

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
	public HorizontalTableDefinition(SheetDefinition parent, Map<String, Object> config) {
		super(parent, config);
	}

	public static boolean assess(Map<String, Object> config, ParentDefinitionInterface<?, ?> table) {
		return config.containsKey(CFG_COLUMN) && config.containsKey(CFG_ROWS);
	}

	/**
	 * {@inheritDoc}
	 */
	protected String getBeginId() {
		return CFG_COLUMN;
	}

	/**
	 * {@inheritDoc}
	 */
	protected String getEndId() {
		return CFG_END_COLUMN;
	}

}
