package purejadeite.jadegreen.definition.cell;

import purejadeite.jadegreen.definition.Definition;

/**
 * 単一セルのリンク定義です
 * @author mitsuhiroseino
 */
public interface LinkCellDefinition extends Definition {

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
