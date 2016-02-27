package com.purejadeite.jadegreen.content;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.table.CategoryDefinitionInterface;

/**
 * 任意の集まりの情報を保持するクラスの抽象クラスです
 *
 * @author mitsuhiroseino
 */
public class CategoryContent extends AbstractParentContent<ParentContentInterface<?, ?, ?>, ContentInterface<?, ?>, CategoryDefinitionInterface<?>>implements CategoryContentInterface {

	/**
	 * コンストラクタ
	 */
	public CategoryContent(ParentContentInterface<?, ?, ?> parent, CategoryDefinitionInterface<?> definition) {
		super(parent, definition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getRawValuesImpl() {
		Map<String, Object> values = new LinkedHashMap<>();
		for (ContentInterface<?, ?> cell : children) {
			values.put(cell.getId(), cell.getRawValues());
		}
		return values;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValuesImpl() {
		Map<String, Object> values = new LinkedHashMap<>();
		for (ContentInterface<?, ?> cell : children) {
			values.put(cell.getId(), cell.getValues());
		}
		return definition.applyOptions(values, this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		List<Map<String, Object>> cellMaps = new ArrayList<>();
		for (ContentInterface<?, ?> cell : children) {
			cellMaps.add(cell.toMap());
		}
		map.put("cells", cellMaps);
		return map;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CategoryContentInterface getCategory() {
		return this;
	}

}
