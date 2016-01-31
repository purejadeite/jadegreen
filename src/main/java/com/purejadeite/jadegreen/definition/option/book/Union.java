package com.purejadeite.jadegreen.definition.option.book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.Definition;

/**
 * 結果を全てひとつのシートにまとめるコンバーター
 *
 * @author mitsuhiroseino
 *
 */
public class Union extends AbstractBookOption {

	/**
	 * コンストラクタ
	 *
	 * @param config
	 *            コンバーターのコンフィグ
	 */
	public Union(Definition<?> definition, Map<String, Object> config) {
		super(definition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object applyImpl(List<Map<String, Object>> values) {
		Map<String, List<Object>> newSheet = new HashMap<>();
		for (Map<String, Object> sheet : values) {
			for (String id : sheet.keySet()) {
				List<Object> newCell = newSheet.get(id);
				if (newCell == null) {
					newCell = new ArrayList<>();
					newSheet.put(id, newCell);
				}
				Object cell = sheet.get(id);
				if (cell instanceof List) {
					@SuppressWarnings("unchecked")
					List<Object> table = (List<Object>) cell;
					newCell.addAll(table);
				} else {
					newCell.add(cell);
				}
			}
		}
		List<Map<String, List<Object>>> newBook = new ArrayList<>();
		newBook.add(newSheet);
		return newBook;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		return map;
	}
}