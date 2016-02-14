package com.purejadeite.jadegreen.option;

import com.purejadeite.jadegreen.option.book.BookOption;
import com.purejadeite.jadegreen.option.book.BookOptionManager;
import com.purejadeite.jadegreen.option.cell.CellOption;
import com.purejadeite.jadegreen.option.cell.CellOptionManager;
import com.purejadeite.jadegreen.option.sheet.SheetOption;
import com.purejadeite.jadegreen.option.sheet.SheetOptionManager;
import com.purejadeite.jadegreen.option.table.TableOption;
import com.purejadeite.jadegreen.option.table.TableOptionManager;

public class OptionRegistry {

	public static void registerCellOption(Class<? extends CellOption> clazz) {
		CellOptionManager.register(clazz);
	}

	public static void registerTableOption(Class<? extends TableOption> clazz) {
		TableOptionManager.register(clazz);
	}

	public static void registerSheetOption(Class<? extends SheetOption> clazz) {
		SheetOptionManager.register(clazz);
	}

	public static void registerBookOption(Class<? extends BookOption> clazz) {
		BookOptionManager.register(clazz);
	}

}
