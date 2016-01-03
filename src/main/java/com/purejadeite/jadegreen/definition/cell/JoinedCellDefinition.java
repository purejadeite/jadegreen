package com.purejadeite.jadegreen.definition.cell;

import com.purejadeite.jadegreen.definition.MappingDefinition;
import com.purejadeite.jadegreen.definition.ParentMappingDefinition;

/**
 * 単一セルの結合定義です
 * @author mitsuhiroseino
 */
public interface JoinedCellDefinition<P extends ParentMappingDefinition<?, ?>> extends CellDefinition<P> {

	public String getSheetId();

	/**
	 * {@inheritDoc}
	 */
	public MappingDefinition<?> getMyKeyDefinition();

	/**
	 * {@inheritDoc}
	 */
	public MappingDefinition<?> getKeyDefinition();

	/**
	 * {@inheritDoc}
	 */
	public MappingDefinition<?> getValueDefinition();

}
