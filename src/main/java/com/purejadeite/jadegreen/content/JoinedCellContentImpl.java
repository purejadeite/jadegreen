package com.purejadeite.jadegreen.content;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.jadegreen.ContentException;
import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.cell.JoinedCellDefinitionImpl;
import com.purejadeite.util.collection.Table;

/**
 * 他のセルに結合したCellクラス
 *
 * @author mitsuhiroseino
 */
public class JoinedCellContentImpl extends AbstractContent<ParentContent<?, ?, ?>, JoinedCellDefinitionImpl>
		implements JoinedCellContent<ParentContent<?, ?, ?>, JoinedCellDefinitionImpl> {

	private static final long serialVersionUID = 3474501722301631948L;

	private static final Logger LOGGER = LoggerFactory.getLogger(JoinedCellContentImpl.class);

	public JoinedCellContentImpl(ParentContent<?, ?, ?> parent, JoinedCellDefinitionImpl definition) {
		super(parent, definition);
	}

	/**
	 * {@inheritDoc} 結合している単一セルは取得した値がないため無視をする対象とします。
	 */
	@Override
	public Object getRawValuesImpl() {
		// 値は無視してもらう
		return SpecificValue.UNDEFINED;
	}

	/**
	 * {@inheritDoc} シートのキー情報でシート同士をひも付け、相手シートの値を取得します。
	 */
	@Override
	public Object getValuesImpl() {
		Definition<?> myKeyDefinition = definition.getMyKeyDefinition();
		Definition<?> keyDefinition = definition.getKeyDefinition();
		Definition<?> valueDefinition = definition.getValueDefinition();

		// 相手シートを取得
		List<SheetContent> sheetContents = this.getSheets(myKeyDefinition, keyDefinition);
		if (sheetContents.isEmpty()) {
			LOGGER.warn("結合先シートが存在しないため結合しません：" + keyDefinition.getFullId());
			return null;
		}
		if (sheetContents.size() != 1) {
			throw new ContentException("結合先シートを特定できません：" + keyDefinition.getFullId());
		}
		SheetContent sheetContent = sheetContents.get(0);

		// 相手から取得する値を取得
		Content<?, ?> valueContent = sheetContent.getCell(valueDefinition);
		if (valueContent != null) {
			return valueContent.getValues();
		} else {
			// ない場合もある
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int capture(Table<String> table) {
		return 0;
	}

}
