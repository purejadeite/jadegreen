package com.purejadeite.jadegreen.content;

import java.util.ArrayList;
import java.util.List;

import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.cell.LinkCellDefinition;

/**
 * <pre>
 * 値の取得元セルの情報を保持する抽象クラス
 * </pre>
 * @author mitsuhiroseino
 */
public abstract class AbstractLinkCellContent<D extends LinkCellDefinition> extends AbstractContent<D> implements LinkCellContent {

	public AbstractLinkCellContent(Content parent, D definition) {
		super(parent, definition);
	}

	public List<Content> getSheetKeyContents(Content book) {
		List<Content> sheetKeyContents = book.searchContents(definition.getSheetKeyDefinition());
		if (sheetKeyContents.isEmpty()) {
			// 相手のキーが無いならば定義が不正なので例外
			throw new RuntimeException();
		}
		return sheetKeyContents;
	}

	public Content getMySheetKeyContent(Content book) {
		Content sheet = this.getUpperContent(WorksheetContentImpl.class);
		List<Content> mySheetKeyContents = sheet.searchContents(definition.getMySheetKeyDefinition());
		if (mySheetKeyContents.size() != 1) {
			// キーが一意でないならば例外
			throw new RuntimeException();
		}
		return mySheetKeyContents.get(0);
	}

	public Content getTargetSheet(Content mySheetKeyContent, List<Content> sheetKeyContents) {
		Object mySheetKeyValues = mySheetKeyContent.getValues();
		for (Content sheetKeyContent : sheetKeyContents) {
			if (mySheetKeyValues.equals(sheetKeyContent.getValues())) {
				return sheetKeyContent.getUpperContent(WorksheetContentImpl.class);
			}
		}
		return null;
	}

	public Content getValueContent(Content targetSheet, List<Content> valueContents) {
		for (Content valueContent : valueContents) {
			// そのキーと同じシートにある値を探す
			if (targetSheet == valueContent.getUpperContent(WorksheetContentImpl.class)) {
				return valueContent;
			}
		}
		return null;
	}

	@Override
	public List<Content> searchContents(Definition key) {
		List<Content> contents = new ArrayList<>();
		if (definition == key) {
			contents.add(this);
		}
		return contents;
	}

}
