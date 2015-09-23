package com.purejadeite.jadegreen.content;

import java.util.List;

import com.purejadeite.jadegreen.definition.cell.LinkCellDefinition;

public class LinkContentUtils {

	public static List<Content<?>> getSheetKeyContents(Content<?> book, LinkCellDefinition<?> definition) {
		List<Content<?>> sheetKeyContents = book.searchContents(definition.getSheetKeyDefinition());
		if (sheetKeyContents.isEmpty()) {
			// 相手のキーが無いならば定義が不正なので例外
			throw new RuntimeException();
		}
		return sheetKeyContents;
	}

	public static Content<?> getMySheetKeyContent(Content<?> book, Content<?> sheet, LinkCellDefinition<?> definition) {
		List<Content<?>> mySheetKeyContents = sheet.searchContents(definition.getMySheetKeyDefinition());
		if (mySheetKeyContents.size() != 1) {
			// キーが一意でないならば例外
			throw new RuntimeException();
		}
		return mySheetKeyContents.get(0);
	}

	public static Content<?> getTargetSheet(Content<?> mySheetKeyContent, List<Content<?>> sheetKeyContents) {
		Object mySheetKeyValues = mySheetKeyContent.getValues();
		for (Content<?> sheetKeyContent : sheetKeyContents) {
			if (mySheetKeyValues.equals(sheetKeyContent.getValues())) {
				return sheetKeyContent.getUpperContent(WorksheetContentImpl.class);
			}
		}
		return null;
	}

	public static Content<?> getValueContent(Content<?> targetSheet, List<Content<?>> valueContents) {
		for (Content<?> valueContent : valueContents) {
			// そのキーと同じシートにある値を探す
			if (targetSheet == valueContent.getUpperContent(WorksheetContentImpl.class)) {
				return valueContent;
			}
		}
		return null;
	}

}
