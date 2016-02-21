package com.purejadeite.jadegreen.content;

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
	public StaticContentImpl(SheetContent parent, CellDefinition<?> definition) {
		super(parent, definition);
	}

}
