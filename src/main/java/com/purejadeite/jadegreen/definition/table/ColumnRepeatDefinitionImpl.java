package com.purejadeite.jadegreen.definition.table;

import static com.purejadeite.jadegreen.definition.DefinitionKeys.*;

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

	/**
	 * {@inheritDoc}
	 */
	protected String getBeginDefinitionKey() {
		return COLUMN;
	}

	/**
	 * {@inheritDoc}
	 */
	protected String getEndConfigDefinitionKey() {
		return END_COLUMN;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isIncluded(int row, int col) {
		return (begin <= col && col <= end);
	}

}
