package com.purejadeite.jadegreen.content;

import java.util.List;

import com.purejadeite.jadegreen.definition.BookDefinition;
import com.purejadeite.jadegreen.definition.DefinitionInterface;
import com.purejadeite.jadegreen.definition.LinkedDefinition;
import com.purejadeite.jadegreen.definition.SheetDefinition;
import com.purejadeite.jadegreen.definition.UnionSheetDefinition;
import com.purejadeite.jadegreen.definition.cell.CellDefinition;
import com.purejadeite.jadegreen.definition.cell.CellDefinitionInterface;
import com.purejadeite.jadegreen.definition.cell.JoinedCellDefinition;
import com.purejadeite.jadegreen.definition.cell.ListCellDefinition;
import com.purejadeite.jadegreen.definition.cell.ValueDefinition;
import com.purejadeite.jadegreen.definition.table.CategoryDefinitionInterface;
import com.purejadeite.jadegreen.definition.table.TableDefinitionInterface;
import com.purejadeite.jadegreen.definition.table.cell.AnchorTableCellDefinition;
import com.purejadeite.jadegreen.definition.table.cell.JoinedTableCellDefinition;
import com.purejadeite.jadegreen.definition.table.cell.TableCellDefinitionInterface;
import com.purejadeite.jadegreen.definition.table.cell.TableValueDefinition;

public class ContentBuilder {

	public ContentBuilder() {
	}

	public static BookContent build(BookDefinition bookDefinition) {
		return new BookContent(bookDefinition);
	}

	public static SheetContent build(BookContent bookContent, SheetDefinition sheetDefinition, String sheetName) {
		SheetContent sheetContent = null;
		if (sheetDefinition instanceof UnionSheetDefinition) {
			// 複数のシートの内容を1シートに集約する場合は既存のシートコンテンツを取得
			List<SheetContent> lastSheets = bookContent.getSheets(sheetDefinition);
			if (lastSheets != null && !lastSheets.isEmpty()) {
				// 既にあるならばそれを使う
				sheetContent = lastSheets.get(0);
				return sheetContent;
			}
		}
		// 新規作成
		sheetContent = new SheetContent(bookContent, sheetDefinition, sheetName);
		// 子要素も作る
		build(sheetContent);
		return sheetContent;
	}

	public static void build(ParentContentInterface<?, ContentInterface<?, ?>, ?> parentContent) {
		for (DefinitionInterface<?> definition : parentContent.getDefinition().getChildren()) {
			parentContent.addChild(build(definition, parentContent));
		}
	}

	// シートまたはカテゴリ配下のコンテンツをビルド
	public static ContentInterface<?, ?> build(DefinitionInterface<?> definition, ParentContentInterface<?, ContentInterface<?, ?>, ?> parentContent) {
		if (definition instanceof JoinedCellDefinition) {
			// 単独セルの結合の場合
			return  new JoinedCellContent(parentContent, (JoinedCellDefinition) definition);
		} else if (definition instanceof LinkedDefinition) {
			// リンクの場合
			return new LinkedContent(parentContent, (LinkedDefinition) definition);
		} else if (definition instanceof ListCellDefinition) {
			// セルの値を分割する場合
			return new ListCellContent(parentContent, (ListCellDefinition) definition);
		} else if (definition instanceof ValueDefinition) {
			// 単独固定値の場合
			return new CellContent(parentContent, (CellDefinitionInterface<?, ?>) definition);
		} else if (definition instanceof CellDefinition) {
			// 単独セルの場合
			if (parentContent.getDefinition().getSheet() instanceof UnionSheetDefinition) {
				// 集約する場合
				return new UnionCellContent(parentContent, (CellDefinitionInterface<?, ?>) definition);
			} else {
				// 集約しない場合
				return new CellContent(parentContent, (CellDefinitionInterface<?, ?>) definition);
			}
		} else if (definition instanceof CategoryDefinitionInterface) {
			// 範囲の場合
			CategoryContentInterface categoryContent = new CategoryContent(parentContent, (CategoryDefinitionInterface<?>) definition);
			for (DefinitionInterface<?> cellDefinition : categoryContent.getDefinition().getChildren()) {
				categoryContent.addChild(build(cellDefinition, categoryContent));
			}
			return categoryContent;
		} else if (definition instanceof TableDefinitionInterface) {
			// テーブルの場合
			TableContentInterface tableContent = new TableContent(parentContent, (TableDefinitionInterface<?>) definition);
			for (DefinitionInterface<?> cellDefinition : tableContent.getDefinition().getChildren()) {
				tableContent.addChild(build(cellDefinition, tableContent));
			}
			return tableContent;
		}
		return null;
	}

	// テーブル配下のコンテンツをビルド
	public static TableCellContentInterface<?> build(DefinitionInterface<?> definition, TableContentInterface parentContent) {
		if (definition instanceof JoinedTableCellDefinition) {
			// 結合の場合
			return new JoinedTableCellContent(parentContent, (JoinedTableCellDefinition) definition);
		} else if (definition instanceof TableValueDefinition) {
			// 固定値の場合
			return new TableCellContent(parentContent, (TableValueDefinition<?>) definition);
		} else if (definition instanceof AnchorTableCellDefinition) {
			// ターゲットの場合
			return new StaticTableCellContent(parentContent, (AnchorTableCellDefinition<?>) definition);
		} else if (definition instanceof TableCellDefinitionInterface) {
			// セルの場合
			return new TableCellContent(parentContent, (TableCellDefinitionInterface<?, ?>) definition);
		}
		return null;
	}

}
