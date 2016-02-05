package com.purejadeite.jadegreen.definition.range;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.AbstractParentDefinition;
import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.SheetDefinition;
import com.purejadeite.jadegreen.definition.cell.CellDefinition;
import com.purejadeite.jadegreen.definition.option.table.TableOptionManager;

/**
 * 範囲の情報を保持するクラスの抽象クラスです
 * @author mitsuhiroseino
 */
abstract public class AbstractRangeDefinition<C extends CellDefinition<?>> extends AbstractParentDefinition<SheetDefinition, C> implements RangeDefinition<C> {

	/**
	 * 配下のセル読み込み情報
	 */
	protected List<C> cells = new ArrayList<>();

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親定義
	 * @param config
	 *            コンフィグ
	 */
	protected AbstractRangeDefinition(SheetDefinition parent, Map<String, Object> config) {
		super(parent, config);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void buildOptions(Definition<?> definition, List<Map<String, Object>> options) {
		this.options = TableOptionManager.build(definition, options);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<C> getChildren() {
		return this.cells;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addChild(C child) {
		cells.add(child);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addChildren(List<C> children) {
		for (C child : children) {
			addChild(child);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		List<Map<String, Object>> cellMaps = new ArrayList<>();
		for(C cell: cells) {
			cellMaps.add(cell.toMap());
		}
		map.put("cells", cellMaps);
		return map;
	}
}
