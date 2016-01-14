package com.purejadeite.jadegreen.content;

import static com.purejadeite.jadegreen.content.Status.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.cell.JoinedTableCellDefinitionImpl;

/**
 * 他のセルに結合したtable配下のCellクラス
 * @author mitsuhiroseino
 */
public class JoinedTableCellContentImpl extends AbstractTableCellContent<JoinedTableCellDefinitionImpl> implements TableCellContent<JoinedTableCellDefinitionImpl>, JoinedCellContent<JoinedTableCellDefinitionImpl> {

	private static final long serialVersionUID = 7690624851647074594L;

	/**
	 * ロガー
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(JoinedTableCellContentImpl.class);

	/**
	 * コンストラクタ
	 * @param parent 親コンテンツ
	 * @param definition 定義
	 */
	public JoinedTableCellContentImpl(Content<?> parent, JoinedTableCellDefinitionImpl definition) {
		super(parent, definition);
	}

	/**
	 * {@inheritDoc}
	 * 結合しているテーブルのセルは、同テーブルの他のセルや上限数によって完了状態になるので、
	 * 値の追加が完了していないとして扱います。
	 */
	@Override
	public Status addValue(int row, int col, Object value) {
		// 値を取得しない
		return NO;
	}

	/**
	 * {@inheritDoc}
	 * 結合しているテーブルのセルは取得した値がないため無視をする対象とします。
	 */
	@Override
	public Object getRawValuesImpl(Definition<?>... ignore) {
		// 値は無視してもらう
		return SpecificValue.UNDEFINED;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValuesImpl(Definition<?>... ignore) {
		// Contentのルートを取得
		WorkbookContent book = this.getUpperContent(WorkbookContent.class);
		// 自分のsheetを取得
		WorksheetContent sheet = this.getUpperContent(WorksheetContent.class);

		// 全Contentから相手のシートのキーになるContentを取得
		List<Content<?>> sheetKeyContents = JoinedContentUtils.getKeyContents(book, definition);
		if (sheetKeyContents == null) {
			throw new IllegalStateException("結合先シートのキーが見つかりません：" + definition.getKeyDefinition().getFullId());
		}

		// 自分のシートのキーを取得
		Content<?> mySheetKeyContent = JoinedContentUtils.getMyKeyContent(sheet, definition);
		if (mySheetKeyContent == null) {
			throw new IllegalStateException("結合元シートのキーが見つかりません：" + definition.getMyKeyDefinition().getFullId());
		}
		LOGGER.debug("自分のシート:" + mySheetKeyContent.getFullId());

		// 値の取得元シートを取得
		WorksheetContent sheetContent = JoinedContentUtils.getTargetSheet(mySheetKeyContent, sheetKeyContents);
		if (sheetContent == null) {
			return null;
		}

		// 結合先のキーとなるレコードを取得
		List<Content<?>> keyContents = sheetContent.searchContents(definition.getTableKeyDefinition());
		if (keyContents.isEmpty()) {
			return null;
		}
		Content<?> keyContent = keyContents.get(0);

		// 結合先のテーブル丸ごと取得
		Content<?> tableContent = keyContent.getUpperContent(TableContentImpl.class);

		// 結合元の属するsheetを取得
		Content<?> mySheetContent = this.getUpperContent(WorksheetContent.class);

		// 結合元のキーとなるレコードを取得
		List<Content<?>> myKeyContents = mySheetContent.searchContents(definition.getMyTableKeyDefinition());
		if (myKeyContents.isEmpty()) {
			return null;
		}
		Content<?> myKeyContent = myKeyContents.get(0);

		// valueのID
		String[] ids = StringUtils.split(definition.getValueId(), ".");
		String valueId = ids[ids.length - 1];

		// 相手のキーと自分のキーが一致したら相手の値を取得
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> tableValues = (List<Map<String, Object>>) tableContent.getValues();
		@SuppressWarnings("unchecked")
		List<Object> myKeys = (List<Object>) myKeyContent.getValues();
		List<Object> myValues = new ArrayList<>();
		for (Object myKey : myKeys) {
			myValues.add(null);
			for (Map<String, Object> tableValue : tableValues) {
				if (tableValue.get(keyContent.getId()).equals(myKey)) {
					myValues.set(myValues.size() - 1, tableValue.get(valueId));
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
	public List<Content<?>> searchContents(Definition<?> key) {
		List<Content<?>> contents = new ArrayList<>();
		if (definition == key) {
			contents.add(this);
		}
		return contents;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isClosed() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close(int size) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		return -1;
	}

}