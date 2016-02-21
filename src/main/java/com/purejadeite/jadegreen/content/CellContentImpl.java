package com.purejadeite.jadegreen.content;

import com.purejadeite.jadegreen.definition.cell.CellDefinition;

/**
 * セルの値を保持するクラス
 * @author mitsuhiroseino
 */
public class CellContentImpl extends AbstractCellContent<CellDefinition<?>> {

	private static final long serialVersionUID = -1289731718432529281L;

	/**
	 * コンストラクタ
	 * @param parent 親コンテンツ
	 * @param definition 定義
	 */
	public CellContentImpl(ParentContent<?, ?, ?> parent, CellDefinition<?> definition) {
		super(parent, definition);
	}

}
