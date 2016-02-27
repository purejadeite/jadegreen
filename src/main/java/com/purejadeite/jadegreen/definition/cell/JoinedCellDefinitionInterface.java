package com.purejadeite.jadegreen.definition.cell;

import com.purejadeite.jadegreen.definition.DefinitionInterface;
import com.purejadeite.jadegreen.definition.ParentDefinitionInterface;

/**
 * 単一セルの結合定義です
 * @author mitsuhiroseino
 */
public interface JoinedCellDefinitionInterface<P extends ParentDefinitionInterface<?, ?>> extends CellDefinitionInterface<P> {

	/**
	 * セルの結合
	 */
	public static final String CFG_JOIN = "join";

	public static final String CFG_SHEET_ID = "sheetId";

	public static final String CFG_KEY_ID = "keyId";

	public static final String CFG_VALUE_ID = "valueId";

	public static final String CFG_MY_KEY_ID = "myKeyId";

	public String getSheetId();

	/**
	 * {@inheritDoc}
	 */
	public DefinitionInterface<?> getMyKeyDefinition();

	/**
	 * {@inheritDoc}
	 */
	public DefinitionInterface<?> getKeyDefinition();

	/**
	 * {@inheritDoc}
	 */
	public DefinitionInterface<?> getValueDefinition();

}
