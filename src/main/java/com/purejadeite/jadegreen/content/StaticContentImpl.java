package com.purejadeite.jadegreen.content;

import static com.purejadeite.jadegreen.content.Status.*;

import com.purejadeite.jadegreen.definition.cell.CellDefinition;

/**
 * セルの値を保持するクラス
 * @author mitsuhiroseino
 */
public class StaticContentImpl extends AbstractCellContent<CellDefinition<?>> {

	/**
	 * コンストラクタ
	 * @param parent 親コンテンツ
	 * @param definition 定義
	 */
	public StaticContentImpl(String uuid, WorksheetContent parent, CellDefinition<?> definition) {
		super(uuid, parent, definition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Status addValue(int row, int col, Object value) {
		return END;
	}

}
