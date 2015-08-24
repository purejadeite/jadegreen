package com.purejadeite.jadegreen.definition;

/**
 * リンク定義です
 * @author mitsuhiroseino
 */
public interface LinkDefinition extends Definition {

	public String getSheetKeyId();

	/**
	 * {@inheritDoc}
	 */
	public Definition getMySheetKeyDefinition();

	/**
	 * {@inheritDoc}
	 */
	public Definition getSheetKeyDefinition();

	/**
	 * {@inheritDoc}
	 */
	public Definition getValueDefinition();

}
