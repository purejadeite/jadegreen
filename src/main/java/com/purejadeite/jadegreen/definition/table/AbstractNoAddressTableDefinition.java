package com.purejadeite.jadegreen.definition.table;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.AbstractParentDefinition;
import com.purejadeite.jadegreen.definition.DefinitionInterface;
import com.purejadeite.jadegreen.definition.ParentDefinitionInterface;
import com.purejadeite.jadegreen.definition.table.cell.TableCellDefinitionInterface;
import com.purejadeite.jadegreen.option.table.TableOptionManager;

/**
 * テーブル形式のアドレスの無いクラスの抽象クラスです
 * @author mitsuhiroseino
 */
abstract public class AbstractNoAddressTableDefinition<C extends TableCellDefinitionInterface<?, ?>> extends AbstractParentDefinition<ParentDefinitionInterface<?, ?>, C> implements TableDefinitionInterface<C> {

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親定義
	 * @param config
	 *            コンフィグ
	 */
	protected AbstractNoAddressTableDefinition(ParentDefinitionInterface<?, ?> parent, Map<String, Object> config) {
		super(parent, config);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void buildOptions(DefinitionInterface<?> definition, List<Map<String, Object>> options) {
		this.options = TableOptionManager.build(definition, options);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addChild(C child) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TableDefinitionInterface<?> getTable() {
		return this;
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
