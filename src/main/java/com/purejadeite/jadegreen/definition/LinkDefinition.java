package com.purejadeite.jadegreen.definition;

/**
 * リンク定義です
 * @author mitsuhiroseino
 */
public interface LinkDefinition<P extends Definition<?>> extends Definition<P> {

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
