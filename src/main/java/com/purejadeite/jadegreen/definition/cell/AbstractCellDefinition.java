package com.purejadeite.jadegreen.definition.cell;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.AbstractDefinition;
import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.ParentDefinition;
import com.purejadeite.jadegreen.definition.option.cell.CellOptionManager;

/**
 * <pre>
 * 値の取得元セルの情報を保持する抽象クラス
 * </pre>
 *
 * @author mitsuhiroseino
 */
abstract public class AbstractCellDefinition<P extends ParentDefinition<?, ?>> extends AbstractDefinition<P>
		implements CellDefinition<P> {

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
	protected void buildOptions(Definition<?> definition, List<Map<String, Object>> options) {
		this.options = CellOptionManager.build(definition, options);
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
