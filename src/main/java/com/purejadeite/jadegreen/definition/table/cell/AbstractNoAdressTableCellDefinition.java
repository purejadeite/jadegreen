package com.purejadeite.jadegreen.definition.table.cell;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.cell.AbstractNoAdressCellDefinition;
import com.purejadeite.jadegreen.definition.table.TableDefinitionInterface;

/**
 * Tableの構成要素となるCell読み込み定義
 * @author mitsuhiroseino
 */
abstract public class AbstractNoAdressTableCellDefinition<P extends TableDefinitionInterface<?>> extends AbstractNoAdressCellDefinition<P> implements TableCellDefinitionInterface<P> {

	private static final long serialVersionUID = -3632667088280645598L;

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親定義
	 * @param config
	 *            コンフィグ
	 */
	protected AbstractNoAdressTableCellDefinition(P parent, Map<String, Object> config) {
		super(parent, config);
	}

	@Override
	public void setBreakKey(boolean breakId) {
	}

	@Override
	public void setBreakValues(List<String> breakValues) {
	}

	@Override
	public boolean isBreakKey() {
		return false;
	}

	@Override
	public List<String> getBreakValues() {
		return null;
	}

}
