package com.purejadeite.jadegreen.content;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.BookDefinition;
import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.util.collection.Table;

/**
 * bookのコンテンツ
 *
 * @author mitsuhiroseino
 */
public class BookContent extends AbstractParentContent<NoContent<?>, SheetContent, BookDefinition> {

	private static final long serialVersionUID = -1677962020788016225L;

	/**
	 * コンストラクタ
	 *
	 * @param definition
	 *            定義
	 * @param name
	 *            book名
	 */
	public BookContent(BookDefinition definition) {
		super(null, definition);
	}

	/**
	 * sheetを追加します
	 *
	 * @param sheet
	 */
	public void addSheet(SheetContent sheet) {
		if (!children.contains(sheet)) {
			children.add(sheet);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getRawValuesImpl() {
		List<Object> values = new ArrayList<>();
		for (SheetContent sheet : children) {
			Object vals = sheet.getRawValues();
			if (vals != SpecificValue.UNDEFINED) {
				values.add(vals);
			}
		}
		return values;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValuesImpl() {
		List<Object> values = new ArrayList<>();
		for (SheetContent sheet : children) {
			Object vals = sheet.getValues();
			if (vals != SpecificValue.NO_OUTPUT && vals != SpecificValue.UNDEFINED) {
				values.add(vals);
			}
		}
		return definition.applyOptions(values, this);
	}

	/**
	 * シートコンテンツを取得します
	 * @param sheetDefinition シート定義
	 * @return シートコンテンツリスト
	 */
	public List<SheetContent> getSheets(Definition<?> sheetDefinition) {
		List<SheetContent> sheets = new ArrayList<>();
		for (SheetContent sheet : children) {
			if (sheet.getDefinition() == sheetDefinition) {
				sheets.add(sheet);
			}
		}
		return sheets;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Content<?, ?>> getContents(Definition<?> definition) {
		List<Content<?, ?>> contents = new ArrayList<>();
		if (this.getDefinition() == definition) {
			// 対象が自分だった場合
			contents.add(this);
			return contents;
		}
		for (SheetContent sheet : children) {
			if (sheet.getDefinition() == definition) {
				// 対象がsheetだった場合
				contents.add(sheet);
			} else {
				// 対象がsheet配下のcellだった場合
				Content<?, ?> content = sheet.getCell(definition);
				if (content != null) {
					contents.add(content);
				}
			}
		}
		return contents;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addChild(SheetContent child) {
		children.add(child);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		List<Map<String, Object>> sheetMaps = new ArrayList<>();
		for (SheetContent sheet : children) {
			sheetMaps.add(sheet.toMap());
		}
		map.put("sheets", sheetMaps);
		return map;
	}

	@Override
	public int capture(Table<String> table) {
		return 0;
	}

}