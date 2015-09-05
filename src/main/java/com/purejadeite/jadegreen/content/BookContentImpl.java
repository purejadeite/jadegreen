package com.purejadeite.jadegreen.content;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.BookDefinitionImpl;
import com.purejadeite.jadegreen.definition.Definition;

/**
 * Sheet読み込み定義
 * @author mitsuhiroseino
 *
 */
public class BookContentImpl extends AbstractContent<BookDefinitionImpl> {

	private String name;

	private List<Content> sheets = new ArrayList<>();

	public BookContentImpl(BookDefinitionImpl definition, String name) {
		super(null, definition);
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	public void addContent(Content sheet) {
		sheets.add(sheet);
	}

	public Status addValue(int row, int col, Object value) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	public Object getRawValuesImpl(Definition... ignore) {
		List<Object> values = new ArrayList<>();
		for (Content sheet : sheets) {
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
		for (Content sheet : sheets) {
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
	public Object getValuesImpl(Definition... ignore) {
		List<Object> values = new ArrayList<>();
		for (Content sheet : sheets) {
			Object vals = sheet.getValues(ignore);
			if (vals != SpecificValue.NO_OUTPUT && vals != SpecificValue.INVALID) {
				values.add(vals);
			}
		}
		return values;
	}

	@Override
	public List<Content> searchContents(Definition key) {
		List<Content> cntnts = new ArrayList<>();
		if (definition == key) {
			cntnts.add(this);
		}
		for (Content sheet : sheets) {
			cntnts.addAll(sheet.searchContents(key));
		}
		return cntnts;
	}

	public Content getUpperContent(Class<? extends Content> contentClazz) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		List<Map<String, Object>> sheetMaps = new ArrayList<>();
		for(Content sheet: sheets) {
			sheetMaps.add(sheet.toMap());
		}
		map.put("sheets", sheetMaps);
		return map;
	}

}
