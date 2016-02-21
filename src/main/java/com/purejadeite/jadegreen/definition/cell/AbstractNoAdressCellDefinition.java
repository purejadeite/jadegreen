package com.purejadeite.jadegreen.definition.cell;

import java.util.Map;

import com.purejadeite.jadegreen.definition.ParentDefinition;

/**
 * <pre>
 * 値の取得元セルの情報を保持する抽象クラス
 * </pre>
 *
 * @author mitsuhiroseino
 */
abstract public class AbstractNoAdressCellDefinition<P extends ParentDefinition<?, ?>> extends AbstractCellDefinition<P> {

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

	@Override
	public int getMinRow() {
		return NO_ADDRESS;
	}

	@Override
	public int getMaxRow() {
		return NO_ADDRESS;
	}

	@Override
	public int getMaxCol() {
		return NO_ADDRESS;
	}

	@Override
	public int getMinCol() {
		return NO_ADDRESS;
	}

	@Override
	public boolean isIncluded(int row, int col) {
		return false;
	}

	@Override
	public int getX() {
		return NO_ADDRESS - 1;
	}

	@Override
	public int getY() {
		return NO_ADDRESS - 1;
	}

}
