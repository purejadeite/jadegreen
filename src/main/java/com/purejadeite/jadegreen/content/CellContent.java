package com.purejadeite.jadegreen.content;

import com.purejadeite.jadegreen.definition.cell.CellDefinitionInterface;

/**
 * セルの値を保持するクラス
 * @author mitsuhiroseino
 */
public class CellContent extends AbstractSingleValueContent<CellDefinitionInterface<?, ?>> {

	private static final long serialVersionUID = -1289731718432529281L;

	/**
	 * コンストラクタ
	 * @param parent 親コンテンツ
	 * @param definition 定義
	 */
	public CellContent(ParentContentInterface<?, ?, ?> parent, CellDefinitionInterface<?, ?> definition) {
		super(parent, definition);
	}

}
