package com.purejadeite.jadegreen.definition.table;

import static com.purejadeite.jadegreen.definition.DefinitionKeys.*;

import java.util.Map;

import com.purejadeite.jadegreen.definition.WorksheetDefinition;
import com.purejadeite.jadegreen.definition.cell.TableCellDefinition;

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
	public RowRepeatDefinitionImpl(WorksheetDefinition parent, Map<String, Object> config) {
		super(parent, config);
	}

	/**
	 * {@inheritDoc}
	 */
	protected String getBeginDefinitionKey() {
		return ROW;
	}

	/**
	 * {@inheritDoc}
	 */
	protected String getEndConfigDefinitionKey() {
		return END_ROW;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isIncluded(int row, int col) {
		return (begin <= row && row <= end);
	}
}
