package com.purejadeite.jadegreen.definition.cell;

import java.util.Map;

import com.purejadeite.jadegreen.definition.ParentDefinitionInterface;

/**
 * <pre>
 * 値の取得元セルの情報を保持する抽象クラス
 * </pre>
 *
 * @author mitsuhiroseino
 */
abstract public class AbstractNoAdressCellDefinition<P extends ParentDefinitionInterface<?, ?>> extends AbstractCellDefinition<P> {

	private static final long serialVersionUID = 5276446229482797364L;

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親定義
	 * @param config
	 *            コンフィグ
	 */
	protected AbstractNoAdressCellDefinition(P parent, Map<String, Object> config) {
		super(parent, config);
	}

}
