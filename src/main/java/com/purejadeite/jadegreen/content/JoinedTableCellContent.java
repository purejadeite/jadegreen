package com.purejadeite.jadegreen.content;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.jadegreen.ContentException;
import com.purejadeite.jadegreen.definition.DefinitionInterface;
import com.purejadeite.jadegreen.definition.table.cell.JoinedTableCellDefinition;
import com.purejadeite.util.collection.Table;

/**
 * 他のセルに結合したtable配下のCellクラス
 * @author mitsuhiroseino
 */
public class JoinedTableCellContent extends AbstractTableCellContent<JoinedTableCellDefinition> implements TableCellContentInterface<JoinedTableCellDefinition>, JoinedCellContentInterface<TableContentInterface, JoinedTableCellDefinition> {

	private static final long serialVersionUID = 7690624851647074594L;

	/**
	 * ロガー
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(JoinedTableCellContent.class);

	/**
	 * コンストラクタ
	 * @param parent 親コンテンツ
	 * @param definition 定義
	 */
	public JoinedTableCellContent(TableContentInterface parent, JoinedTableCellDefinition definition) {
		super(parent, definition);
	}

	/**
	 * {@inheritDoc}
	 * 結合しているテーブルのセルは取得した値がないため無視をする対象とします。
	 */
	@Override
	public Object getRawValuesImpl() {
		// 値は無視してもらう
		return SpecificValue.UNDEFINED;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValuesImpl() {
		DefinitionInterface<?> myKeyDefinition = definition.getMyKeyDefinition();
		DefinitionInterface<?> keyDefinition = definition.getKeyDefinition();
		DefinitionInterface<?> myTableKeyDefinition = definition.getMyTableKeyDefinition();
		DefinitionInterface<?> keyTableDefinition = definition.getTableKeyDefinition();

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
		// 結合先のキーとなるレコードを取得
		ContentInterface<?, ?> tableKeyContent = sheetContent.getCell(keyTableDefinition);
		if (tableKeyContent == null) {
			return null;
		}

		// 結合先のテーブル丸ごと取得
		ContentInterface<?, ?> tableContent = tableKeyContent.getParent();

		// 結合元の属するsheetを取得
		SheetContent mySheetContent = this.getSheet();

		// 結合元のキーとなるレコードを取得
		ContentInterface<?, ?> myTableKeyContent = mySheetContent.getCell(myTableKeyDefinition);
		if (myTableKeyContent == null) {
			return null;
		}

		// valueのID
		// 相手のキーと自分のキーが一致したら相手の値を取得
		// 相手のテーブルの値全行分
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> tableValues = (List<Map<String, Object>>) tableContent.getValues();
		// 自分のキーとなる項目の値全行分
		@SuppressWarnings("unchecked")
		List<Object> myTableKeyValues = (List<Object>) myTableKeyContent.getValues();
		List<Object> myValues = new ArrayList<>();
		// 相手のレコードのキーと自分のキーを比較して一致したら取得
		for (Object myTableKeyValue : myTableKeyValues) {
			myValues.add(null);
			for (Map<String, Object> tableValue : tableValues) {
				Object tableKeyValue = tableValue.get(tableKeyContent.getId());
				if (tableKeyValue.equals(myTableKeyValue)) {
					myValues.set(myValues.size() - 1, tableValue.get(definition.getValueId()));
					continue;
				}
			}
		}
		return myValues;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		return -1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int capture(Table<String> table) {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int capture(Table<String> table, int size) {
		return 0;
	}

}