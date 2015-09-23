package com.purejadeite.jadegreen.definition.cell;

import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.ParentDefinition;

/**
 * 単一セルのリンク定義です
 * @author mitsuhiroseino
 */
public interface LinkCellDefinition<P extends ParentDefinition<?, ?>> extends CellDefinition<P> {

	public String getSheetKeyId();

	/**
	 * {@inheritDoc}
	 */
	public Definition<?> getMySheetKeyDefinition();

	/**
	 * {@inheritDoc}
	 */
	public Definition<?> getSheetKeyDefinition();

	/**
	 * {@inheritDoc}
	 */
	public Definition<?> getValueDefinition();

}
