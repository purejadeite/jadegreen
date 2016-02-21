package com.purejadeite.jadegreen.content;

import java.util.List;

import com.purejadeite.jadegreen.definition.BookDefinition;
import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.SheetDefinition;
import com.purejadeite.jadegreen.definition.cell.CellDefinition;
import com.purejadeite.jadegreen.definition.cell.CellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.JoinedCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.ListCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.ValueDefinitionImpl;
import com.purejadeite.jadegreen.definition.table.CategoryDefinition;
import com.purejadeite.jadegreen.definition.table.TableDefinition;
import com.purejadeite.jadegreen.definition.table.cell.JoinedTableCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.table.cell.TableCellDefinition;
import com.purejadeite.jadegreen.definition.table.cell.TableValueDefinitionImpl;
import com.purejadeite.jadegreen.definition.table.cell.TargetTableCellDefinitionImpl;

public class ContentBuilder {

	public ContentBuilder() {
	}

	public static BookContent build(BookDefinition bookDefinition) {
		return new BookContent(bookDefinition);
	}

	public static SheetContent build(BookContent bookContent, SheetDefinition sheetDefinition, String sheetName) {
		SheetContent sheetContent = null;
		if (sheetDefinition.isUnion()) {
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

	public static void build(ParentContent<?, Content<?, ?>, ?> parentContent) {
		for (Definition<?> definition : parentContent.getDefinition().getChildren()) {
			parentContent.addChild(build(definition, parentContent));
		}
	}

	// シートまたはカテゴリ配下のコンテンツをビルド
	public static Content<?, ?> build(Definition<?> definition, ParentContent<?, Content<?, ?>, ?> parentContent) {
		if (definition instanceof JoinedCellDefinitionImpl) {
			// 単独セルの結合の場合
			return  new JoinedCellContentImpl(parentContent, (JoinedCellDefinitionImpl) definition);
		} else if (definition instanceof ListCellDefinitionImpl) {
			// リスト形式の単独セルの場合
			return new CellContentImpl(parentContent, (CellDefinition<?>) definition);
		} else if (definition instanceof CellDefinitionImpl) {
			// 単独セルの場合
			if (parentContent.getDefinition().getSheet().isUnion()) {
				// 集約する場合
				return new UnionCellContentImpl(parentContent, (CellDefinition<?>) definition);
			} else {
				// 集約しない場合
				return new CellContentImpl(parentContent, (CellDefinition<?>) definition);
			}
		} else if (definition instanceof ValueDefinitionImpl) {
			// 単独固定値の場合
			return new CellContentImpl(parentContent, (CellDefinition<?>) definition);
		} else if (definition instanceof CategoryDefinition) {
			// 範囲の場合
			CategoryContent categoryContent = new CategoryContentImpl(parentContent, (CategoryDefinition<?>) definition);
			for (Definition<?> cellDefinition : categoryContent.getDefinition().getChildren()) {
				categoryContent.addChild(build(cellDefinition, categoryContent));
			}
			return categoryContent;
		} else if (definition instanceof TableDefinition) {
			// テーブルの場合
			TableContent tableContent = new TableContentImpl(parentContent, (TableDefinition<?>) definition);
			for (Definition<?> cellDefinition : tableContent.getDefinition().getChildren()) {
				tableContent.addChild(build(cellDefinition, tableContent));
			}
			return tableContent;
		}
		return null;
	}

	// テーブル配下のコンテンツをビルド
	public static TableCellContent<?> build(Definition<?> definition, TableContent parentContent) {
		if (definition instanceof JoinedTableCellDefinitionImpl) {
			// 結合の場合
			return new JoinedTableCellContentImpl(parentContent, (JoinedTableCellDefinitionImpl) definition);
		} else if (definition instanceof TableValueDefinitionImpl) {
			// 固定値の場合
			return new TableCellContentImpl(parentContent, (TableCellDefinition<?>) definition);
		} else if (definition instanceof TargetTableCellDefinitionImpl) {
			// ターゲットの場合
			return new StaticTableCellContentImpl(parentContent, (TableCellDefinition<?>) definition);
		} else if (definition instanceof TableCellDefinition) {
			// セルの場合
			return new TableCellContentImpl(parentContent, (TableCellDefinition<?>) definition);
		}
		return null;
	}

}
