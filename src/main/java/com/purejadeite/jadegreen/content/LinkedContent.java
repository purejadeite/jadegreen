package com.purejadeite.jadegreen.content;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.jadegreen.ContentException;
import com.purejadeite.jadegreen.definition.DefinitionInterface;
import com.purejadeite.jadegreen.definition.LinkedDefinition;
import com.purejadeite.util.collection.Table;

/**
 * 他のコンテンツを参照するクラス
 *
 * @author mitsuhiroseino
 */
public class LinkedContent extends AbstractContent<ParentContentInterface<?, ?, ?>, LinkedDefinition> {

	private static final Logger LOGGER = LoggerFactory.getLogger(LinkedContent.class);

	public LinkedContent(ParentContentInterface<?, ?, ?> parent, LinkedDefinition definition) {
		super(parent, definition);
	}

	/**
	 * {@inheritDoc} リンクは取得した値がないため無視をする対象とします。
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
		DefinitionInterface<?> myKeyDefinition = definition.getMyKeyDefinition();
		DefinitionInterface<?> keyDefinition = definition.getKeyDefinition();
		DefinitionInterface<?> valueDefinition = definition.getValueDefinition();

		// 相手シートを取得
		List<SheetContent> sheetContents = this.getSheets(myKeyDefinition, keyDefinition);
		if (sheetContents.isEmpty()) {
			LOGGER.warn("取得元シートが存在しないためリンクしません：" + keyDefinition.getFullId());
			return null;
		}
		if (sheetContents.size() != 1) {
			throw new ContentException("取得元シートを特定できません：" + keyDefinition.getFullId());
		}
		SheetContent sheetContent = sheetContents.get(0);

		// 相手から取得する値を取得
		ContentInterface<?, ?> valueContent = sheetContent.getCell(valueDefinition);
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
