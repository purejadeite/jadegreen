package com.purejadeite.jadegreen.definition.cell;

import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.ParentDefinition;

/**
 * 単一セルの結合定義です
 * @author mitsuhiroseino
 */
public interface JoinedCellDefinition<P extends ParentDefinition<?, ?>> extends CellDefinition<P> {

	public String getSheetId();

	/**
	 * {@inheritDoc}
	 */
	public Definition<?> getMyKeyDefinition();

	/**
	 * {@inheritDoc}
	 */
	public Definition<?> getKeyDefinition();

	/**
	 * {@inheritDoc}
	 */
	public Definition<?> getValueDefinition();

}
