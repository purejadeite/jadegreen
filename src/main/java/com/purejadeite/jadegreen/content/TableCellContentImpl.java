package com.purejadeite.jadegreen.content;

import com.purejadeite.jadegreen.definition.cell.TableCellDefinition;

/**
 * Tableの構成要素となるCell読み込み定義
 * @author mitsuhiroseino
 */
public class TableCellContentImpl extends AbstractTableCellContent<TableCellDefinition<?>> {

	private static final long serialVersionUID = -6172471723011313228L;

	/**
	 * コンストラクタ
	 * @param parent 親コンテンツ
	 * @param definition 定義
	 */
	public TableCellContentImpl(String uuid, TableContent parent, TableCellDefinition<?> definition) {
		super(uuid, parent, definition);
	}

}
