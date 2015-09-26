package com.purejadeite.jadegreen.content;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.WorkbookDefinition;

/**
 * Workbook読み込み定義
 * @author mitsuhiroseino
 *
 */
public class WorkbookContent extends AbstractContent<WorkbookDefinition> {

	private String name;

	private List<WorksheetContent> sheets = new ArrayList<>();

	public WorkbookContent(WorkbookDefinition definition, String name) {
		super(null, definition);
		this.name = name;
	}

	public void addSheet(WorksheetContent sheet) {
		sheets.add(sheet);
	}

	public Status addValue(int row, int col, Object value) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	public Object getRawValuesImpl(Definition<?>... ignore) {
		List<Object> values = new ArrayList<>();
		for (WorksheetContent sheet : sheets) {
			Object vals = sheet.getRawValues(ignore);
			if (vals != SpecificValue.INVALID) {
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
	public Object getValuesImpl(Definition<?>... ignore) {
		List<Object> values = new ArrayList<>();
		for (WorksheetContent sheet : sheets) {
			Object vals = sheet.getValues(ignore);
			if (vals != SpecificValue.NO_OUTPUT && vals != SpecificValue.INVALID) {
				values.add(vals);
			}
		}
		return values;
	}

	@Override
	public List<Content<?>> searchContents(Definition<?> key) {
		List<Content<?>> contents = new ArrayList<>();
		if (definition == key) {
			contents.add(this);
		}
		for (WorksheetContent sheet : sheets) {
			contents.addAll(sheet.searchContents(key));
		}
		return contents;
	}

	public <C extends Content<?>> C getUpperContent(Class<C> contentClazz) {
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

	public String getName() {
		return name;
	}

}