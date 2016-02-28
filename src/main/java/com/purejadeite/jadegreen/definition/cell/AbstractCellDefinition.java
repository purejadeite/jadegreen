package com.purejadeite.jadegreen.definition.cell;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.AbstractDefinition;
import com.purejadeite.jadegreen.definition.DefinitionInterface;
import com.purejadeite.jadegreen.definition.ParentDefinitionInterface;
import com.purejadeite.jadegreen.option.cell.CellOptionManager;

/**
 * <pre>
 * 値の取得元セルの情報を保持する抽象クラス
 * </pre>
 *
 * @author mitsuhiroseino
 */
abstract public class AbstractCellDefinition<P extends ParentDefinitionInterface<?, ?>, V> extends AbstractDefinition<P>
		implements CellDefinitionInterface<P, V> {

	private static final long serialVersionUID = -1364121835461648806L;

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親定義
	 * @param config
	 *            コンフィグ
	 */
	protected AbstractCellDefinition(P parent, Map<String, Object> config) {
		super(parent, config);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void buildOptions(DefinitionInterface<?> definition, List<Map<String, Object>> options) {
		this.options = CellOptionManager.build(definition, options);
	}

}
