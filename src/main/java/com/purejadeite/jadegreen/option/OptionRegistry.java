package com.purejadeite.jadegreen.option;

import com.purejadeite.jadegreen.option.book.BookOptionInterface;
import com.purejadeite.jadegreen.option.book.BookOptionManager;
import com.purejadeite.jadegreen.option.cell.CellOptionInterface;
import com.purejadeite.jadegreen.option.cell.CellOptionManager;
import com.purejadeite.jadegreen.option.sheet.SheetOptionInterface;
import com.purejadeite.jadegreen.option.sheet.SheetOptionManager;
import com.purejadeite.jadegreen.option.table.TableOptionInterface;
import com.purejadeite.jadegreen.option.table.TableOptionManager;

public class OptionRegistry {

	public static void registerCellOption(Class<? extends CellOptionInterface> clazz) {
		CellOptionManager.register(clazz);
	}

	public static void registerTableOption(Class<? extends TableOptionInterface> clazz) {
		TableOptionManager.register(clazz);
	}

	public static void registerSheetOption(Class<? extends SheetOptionInterface> clazz) {
		SheetOptionManager.register(clazz);
	}

	public static void registerBookOption(Class<? extends BookOptionInterface> clazz) {
		BookOptionManager.register(clazz);
	}

}
