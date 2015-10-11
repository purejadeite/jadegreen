package com.purejadeite.jadegreen.content;

import java.util.List;

import com.purejadeite.jadegreen.ContentException;
import com.purejadeite.jadegreen.DefinitionException;
import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.cell.LinkCellDefinition;

public class LinkContentUtils {

	/**
	 * <pre>
	 * ブック配下の全シートから相手シートのキーになる値を取得
	 * </pre>
	 *
	 * @param book
	 *            対象ブック
	 * @param definition
	 *            取得する値の定義
	 * @return 対象の値リスト
	 */
	public static List<Content<?>> getSheetKeyContents(WorkbookContent book, LinkCellDefinition<?> definition) {
		Definition<?> sheetKeyDefinition = definition.getSheetKeyDefinition();
		List<Content<?>> sheetKeyContents = book.searchContents(sheetKeyDefinition);
//		if (sheetKeyContents.isEmpty()) {
//			// 相手のキーが無いならば定義が不正なので例外
//			throw new DefinitionException(sheetKeyDefinition, "リンク先シートのキーが定義されていません");
//		}
		return sheetKeyContents;
	}

	/**
	 * <pre>
	 * シート配下からキーになる値を取得
	 * </pre>
	 *
	 * @param sheet
	 *            対象シート
	 * @param definition
	 *            取得する値の定義
	 * @return 対象の値
	 */
	public static Content<?> getMySheetKeyContent(WorksheetContent sheet, LinkCellDefinition<?> definition) {
		Definition<?> sheetKeyDefinition = definition.getMySheetKeyDefinition();
		List<Content<?>> sheetKeyContents = sheet.searchContents(sheetKeyDefinition);
		if (sheetKeyContents.isEmpty()) {
			// キーが無いならば定義が不正なので例外
			throw new DefinitionException("シートのキーが定義されていません", sheetKeyDefinition);
		} else if (sheetKeyContents.size() != 1) {
			// キーが一意でないならば例外
			throw new ContentException("シートのキーが複数存在します", sheetKeyContents);
		}
		return sheetKeyContents.get(0);
	}

	/**
	 * 指定のキーの値と一致するキーを持つシートを取得する
	 *
	 * @param mySheetKeyContent
	 *            キーとなる値
	 * @param sheetKeyContents
	 *            相手先候補シートのキーの値
	 * @return シート
	 */
	public static WorksheetContent getTargetSheet(Content<?> mySheetKeyContent, List<Content<?>> sheetKeyContents) {
		WorksheetContent sheetContent = null;
		Object mySheetKeyValues = mySheetKeyContent.getValues();
		for (Content<?> sheetKeyContent : sheetKeyContents) {
			if (mySheetKeyValues.equals(sheetKeyContent.getValues())) {
				if (sheetContent != null) {
					// キーが一意でないならば例外
					throw new ContentException("リンク対象のシートが複数存在します", sheetKeyContent);
				}
				sheetContent = sheetKeyContent.getUpperContent(WorksheetContent.class);
			}
		}
		return sheetContent;
	}

	/**
	 * 値のリストの中から対象シートの値を取得する
	 * @param targetSheet 対象シート
	 * @param valueContents 値のリスト
	 * @return 対象シートの値
	 */
	public static Content<?> getValueContent(WorksheetContent targetSheet, List<Content<?>> valueContents) {
		for (Content<?> valueContent : valueContents) {
			// そのキーと同じシートインスタンスにある値を探す
			if (targetSheet == valueContent.getUpperContent(WorksheetContent.class)) {
				return valueContent;
			}
		}
		return null;
	}

}
