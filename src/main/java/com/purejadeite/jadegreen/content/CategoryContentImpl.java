package com.purejadeite.jadegreen.content;

import static com.purejadeite.jadegreen.content.Status.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.cell.CellDefinition;
import com.purejadeite.jadegreen.definition.cell.CellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.JoinedCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.ListCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.ValueDefinitionImpl;
import com.purejadeite.jadegreen.definition.table.CategoryDefinition;
import com.purejadeite.jadegreen.definition.table.TableDefinition;

/**
 * 任意の集まりの情報を保持するクラスの抽象クラスです
 *
 * @author mitsuhiroseino
 */
public class CategoryContentImpl extends AbstractParentContent<ParentContent<?, ?, ?>, Content<?, ?>, CategoryDefinition<?>>implements CategoryContent {

	/**
	 * コンストラクタ
	 */
	public CategoryContentImpl(String uuid, ParentContent<?, ?, ?> parent, CategoryDefinition<?> definition) {
		super(uuid, parent, definition);
		Content<?, ?> content = null;
		for (Definition<?> childDefinition : definition.getChildren()) {
			if (childDefinition instanceof JoinedCellDefinitionImpl) {
				// 単独セルの結合の場合
				content = new JoinedCellContentImpl(uuid, this, (JoinedCellDefinitionImpl) childDefinition);
			} else if (childDefinition instanceof ListCellDefinitionImpl) {
				// リスト形式の単独セルの場合
				content = new CellContentImpl(uuid, this, (CellDefinition<?>) childDefinition);
			} else if (childDefinition instanceof CellDefinitionImpl) {
				// 単独セルの場合
				content = new CellContentImpl(uuid, this, (CellDefinition<?>) childDefinition);
			} else if (childDefinition instanceof ValueDefinitionImpl) {
				// 単独固定値の場合
				content = new CellContentImpl(uuid, this, (CellDefinition<?>) childDefinition);
			} else if (childDefinition instanceof CategoryDefinition) {
				// 範囲の場合
				content = new CategoryContentImpl(uuid, this, (CategoryDefinition<?>) childDefinition);
			} else if (childDefinition instanceof TableDefinition) {
				// テーブルの場合
				content = new TableContentImpl(uuid, this, (TableDefinition<?>) childDefinition);
			} else {
				continue;
			}
			addChild(content);
		}
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

}
