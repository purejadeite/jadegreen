package com.purejadeite.jadegreen.content;

import java.util.ArrayList;
import java.util.List;

import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.LinkDefinition;

/**
 * 値のリンクを行う抽象クラス
 * @author mitsuhiroseino
 */
abstract public class AbstractLinkContent<D extends LinkDefinition> extends AbstractContent<D> implements LinkContent {

	public AbstractLinkContent(Content parent, D definition) {
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
