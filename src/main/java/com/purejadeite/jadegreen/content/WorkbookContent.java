package com.purejadeite.jadegreen.content;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.WorkbookDefinition;

/**
 * Workbookのコンテンツ
 * @author mitsuhiroseino
 */
public class WorkbookContent extends AbstractContent<NoContent, WorkbookDefinition> {

	private static final long serialVersionUID = -1677962020788016225L;

	/**
	 * worksheetのコンテンツリスト
	 */
	private List<WorksheetContent> sheets = new ArrayList<>();

	/**
	 * コンストラクタ
	 * @param definition 定義
	 * @param name workbook名
	 */
	public WorkbookContent(WorkbookDefinition definition) {
		super(null, null, definition);
	}

	/**
	 * worksheetを追加します
	 * @param sheet
	 */
	public void addSheet(WorksheetContent sheet) {
		sheets.add(sheet);
	}

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
		for (WorksheetContent sheet : sheets) {
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
		for (WorksheetContent sheet : sheets) {
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
		for (WorksheetContent sheet : sheets) {
			Object vals = sheet.getValues();
			if (vals != SpecificValue.NO_OUTPUT && vals != SpecificValue.UNDEFINED) {
				values.add(vals);
			}
		}
		return values;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Content<?, ?>> searchContents(Definition<?> key) {
		List<Content<?, ?>> contents = new ArrayList<>();
		if (definition == key) {
			contents.add(this);
		}
		for (WorksheetContent sheet : sheets) {
			contents.addAll(sheet.searchContents(key));
		}
		return contents;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <C extends Content<?, ?>> C getUpperContent(Class<C> contentClazz) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		List<Map<String, Object>> sheetMaps = new ArrayList<>();
		for(WorksheetContent sheet: sheets) {
			sheetMaps.add(sheet.toMap());
		}
		map.put("sheets", sheetMaps);
		return map;
	}

}