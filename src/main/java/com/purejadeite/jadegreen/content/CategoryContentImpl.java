package com.purejadeite.jadegreen.content;

import static com.purejadeite.jadegreen.content.Status.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.table.CategoryDefinition;

/**
 * 任意の集まりの情報を保持するクラスの抽象クラスです
 *
 * @author mitsuhiroseino
 */
public class CategoryContentImpl extends AbstractParentContent<ParentContent<?, ?, ?>, Content<?, ?>, CategoryDefinition<?>>implements CategoryContent {

	/**
	 * コンストラクタ
	 */
	public CategoryContentImpl(ParentContent<?, ?, ?> parent, CategoryDefinition<?> definition) {
		super(parent, definition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Status addValue(int row, int col, Object value) {
		if (closed) {
			return END;
		}
		// 取得対象範囲
		boolean closedAll = true;
		Status status = NO;
		for (Content<?, ?> child : children) {
			Status cellStatus = child.addValue(row, col, value);
			if (cellStatus == SUCCESS) {
				status = SUCCESS;
				if (child instanceof TableContent) {
					// TableはENDが来たら終わり
					closedAll = false;
				}
			} else if (cellStatus == NO) {
				// SUCCESS,END以外のものがあるということは、配下のセルでまだ値を取得していないものがあるということ
				closedAll = false;
			}
		}
		if (closedAll) {
			close();
		}
		return closedAll ? END : status;
	}

	@Override
	public void open() {
		super.open();
		for (Content<?, ?> cell : children) {
			cell.open();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getRawValuesImpl() {
		Map<String, Object> values = new LinkedHashMap<>();
		for (Content<?, ?> cell : children) {
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
		for (Content<?, ?> cell : children) {
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
		for (Content<?, ?> cell : children) {
			cellMaps.add(cell.toMap());
		}
		map.put("cells", cellMaps);
		return map;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CategoryContent getCategory() {
		return this;
	}

}
