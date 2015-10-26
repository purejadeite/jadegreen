package com.purejadeite.jadegreen.definition.cell;

import com.purejadeite.jadegreen.definition.MappingDefinition;
import com.purejadeite.jadegreen.definition.ParentMappingDefinition;

/**
 * 単一セルのリンク定義です
 * @author mitsuhiroseino
 */
public interface LinkCellDefinition<P extends ParentMappingDefinition<?, ?>> extends CellDefinition<P> {

	public String getSheetKeyId();

	/**
	 * {@inheritDoc}
	 */
	public MappingDefinition<?> getMySheetKeyDefinition();

	/**
	 * {@inheritDoc}
	 */
	public MappingDefinition<?> getSheetKeyDefinition();

	/**
	 * {@inheritDoc}
	 */
	public MappingDefinition<?> getValueDefinition();

}
