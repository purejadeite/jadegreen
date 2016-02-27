package com.purejadeite.jadegreen.content;

import com.purejadeite.jadegreen.definition.table.cell.TableCellDefinitionInterface;

/**
 * Tableの構成要素となるCell読み込み定義
 * @author mitsuhiroseino
 */
public class TableCellContent extends AbstractTableCellContent<TableCellDefinitionInterface<?>> {

	private static final long serialVersionUID = -6172471723011313228L;

	/**
	 * コンストラクタ
	 * @param parent 親コンテンツ
	 * @param definition 定義
	 */
	public TableCellContent(TableContentInterface parent, TableCellDefinitionInterface<?> definition) {
		super(parent, definition);
	}

}
