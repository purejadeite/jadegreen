package com.purejadeite.jadegreen.content;

import java.util.List;

import com.purejadeite.jadegreen.definition.cell.LinkCellDefinition;



/**
 * Cell読み込み定義のインターフェイス
 * @author mitsuhiroseino
 */
public interface LinkCellContent<D extends LinkCellDefinition<?>> extends CellContent<D> {

	public List<Content<?>> getSheetKeyContents(WorkbookContent book);

	public Content<?> getMySheetKeyContent(WorkbookContent book);

	public WorksheetContent getTargetSheet(Content<?> mySheetKeyContent, List<Content<?>> sheetKeyContents);

	public Content<?> getValueContent(WorksheetContent targetSheet, List<Content<?>> valueContents);
}
