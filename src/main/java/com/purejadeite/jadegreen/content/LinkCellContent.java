package com.purejadeite.jadegreen.content;

import java.util.List;

import com.purejadeite.jadegreen.definition.cell.LinkCellDefinition;



/**
 * Cell読み込み定義のインターフェイス
 * @author mitsuhiroseino
 */
public interface LinkCellContent<D extends LinkCellDefinition<?>> extends CellContent<D> {

	public List<Content<?>> getSheetKeyContents(Content<?> book);

	public Content<?> getMySheetKeyContent(Content<?> book);

	public Content<?> getTargetSheet(Content<?> mySheetKeyContent, List<Content<?>> sheetKeyContents);

	public Content<?> getValueContent(Content<?> targetSheet, List<Content<?>> valueContents);
}
