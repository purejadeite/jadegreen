package com.purejadeite.jadegreen.content;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.BookDefinition;

/**
 * bookのコンテンツ
 * @author mitsuhiroseino
 */
public class BookContent extends AbstractContent<NoContent, BookDefinition> {

	private static final long serialVersionUID = -1677962020788016225L;

	/**
	 * sheetのコンテンツリスト
	 */
	private List<SheetContent> sheets = new ArrayList<>();

	/**
	 * コンストラクタ
	 * @param definition 定義
	 * @param name book名
	 */
	public BookContent(BookDefinition definition) {
		super(null, null, definition);
		ContentManager.getInstance().register(this);
	}

	/**
	 * sheetを追加します
	 * @param sheet
	 */
	public void addSheet(SheetContent sheet) {
		if (!sheets.contains(sheet)) {
			sheets.add(sheet);
		}
	}

	/**
	 * sheetを削除します
	 * @param sheet
	 */
//	public void removeSheet(SheetContent sheet) {
//		sheets.remove(sheet);
//	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Status addValue(int row, int col, Object value) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getRawValuesImpl() {
		List<Object> values = new ArrayList<>();
		for (SheetContent sheet : sheets) {
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
	public boolean isClosed() {
		if (closed) {
			return true;
		}
		for (SheetContent sheet : sheets) {
			if (!sheet.isClosed()) {
				return false;
			}
		}
		close();
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValuesImpl() {
		List<Object> values = new ArrayList<>();
		for (SheetContent sheet : sheets) {
			Object vals = sheet.getValues();
			if (vals != SpecificValue.NO_OUTPUT && vals != SpecificValue.UNDEFINED) {
				values.add(vals);
			}
		}
		return definition.applyOptions(values, this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		List<Map<String, Object>> sheetMaps = new ArrayList<>();
		for(SheetContent sheet: sheets) {
			sheetMaps.add(sheet.toMap());
		}
		map.put("sheets", sheetMaps);
		return map;
	}

}