package com.purejadeite.jadegreen.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.purejadeite.jadegreen.definition.SheetDefinition;

/**
 * Worksheetのコンテンツ
 *
 * @author mitsuhiroseino
 */
public class SheetContent extends AbstractParentContent<BookContent, ContentInterface<?, ?>, SheetDefinition> {

	private static final long serialVersionUID = -6579860061499426256L;

	/**
	 * sheet名
	 */
	private String sheetName;

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親コンテンツ
	 * @param definition
	 *            定義
	 * @param sheetName
	 *            sheet名
	 */
	public SheetContent(BookContent parent, SheetDefinition definition, String sheetName) {
		super(UUID.randomUUID().toString(), parent, definition);
		this.sheetName = sheetName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getRawValuesImpl() {
		Map<String, Object> values = new HashMap<>();
		for (ContentInterface<?, ?> content : children) {
			values.put(content.getId(), content.getRawValues());
		}
		return values;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValuesImpl() {
		if (!this.getDefinition().isOutput()) {
			return SpecificValue.NO_OUTPUT;
		}
		Map<String, Object> values = new HashMap<>();
		for (ContentInterface<?, ?> content : children) {
			Object vals = content.getValues();
			if (vals != SpecificValue.NO_OUTPUT) {
				values.put(content.getId(), vals);
			}
		}
		return definition.applyOptions(values, this);
	}

	public String getSheetName() {
		return sheetName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("sheetName", sheetName);
		List<Map<String, Object>> contentMaps = new ArrayList<>();
		for (ContentInterface<?, ?> content : children) {
			contentMaps.add(content.toMap());
		}
		map.put("contents", contentMaps);
		return map;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SheetContent getSheet() {
		return this;
	}

}
