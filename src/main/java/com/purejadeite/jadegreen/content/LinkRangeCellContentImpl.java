package com.purejadeite.jadegreen.content;

import static com.purejadeite.jadegreen.content.Status.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.cell.LinkRangeCellDefinitionImpl;

/**
 * <pre>
 * 値の取得元セルの情報を保持する抽象クラス
 * </pre>
 * @author mitsuhiroseino
 */
public class LinkRangeCellContentImpl extends AbstractLinkCellContent<LinkRangeCellDefinitionImpl> implements RangeCellContent {

	private static final Logger LOGGER = LoggerFactory.getLogger(LinkRangeCellContentImpl.class);

	public LinkRangeCellContentImpl(Content parent, LinkRangeCellDefinitionImpl definition) {
		super(parent, definition);
	}

	/**
	 * {@inheritDoc}
	 * リンクしているテーブルのセルは、同テーブルの他のセルや上限数によって完了状態になるので、
	 * 値の追加が完了していないとして扱います。
	 */
	@Override
	public Status addValue(int row, int col, Object value) {
		// 値を取得しない
		return NO;
	}

	/**
	 * {@inheritDoc}
	 * リンクしているテーブルのセルは取得した値がないため無視をする対象とします。
	 */
	@Override
	public Object getRawValuesImpl(Definition... ignore) {
		// 値は無視してもらう
		return SpecificValue.INVALID;
	}

	@Override
	public Object getValuesImpl(Definition... ignore) {
		// Contentのルートを取得
		Content book = this.getUpperContent(WorkbookContentImpl.class);

		// 全Contentから相手のシートのキーになるContentを取得
		List<Content> sheetKeyContents = getSheetKeyContents(book);

		// 自分の属するシートのキーを取得
		Content mySheetKeyContent = getMySheetKeyContent(book);
		LOGGER.debug("自分のシート:" + mySheetKeyContent.getFullId());

		// 値の取得元シートを取得
		Content sheetContent = getTargetSheet(mySheetKeyContent, sheetKeyContents);

		// 取得元のキーとなるレコードを取得
		Content keyContent = sheetContent.searchContents(definition.getKeyDefinition()).get(0);

		// 取得元のテーブル丸ごと取得
		Content rangeContent = keyContent.getUpperContent(RangeContentImpl.class);

		// 取得元のキーとなるレコードを取得
//		Content valueContent = sheetContent.searchContents(cell.getValueDefinition()).get(0);

		// 自分の属するsheetを取得
		Content mySheetContent = this.getUpperContent(WorksheetContentImpl.class);

		// 自分のキーとなるレコードを取得
		Content myKeyContent = mySheetContent.searchContents(definition.getMyKeyDefinition()).get(0);

		// valueのID
		String[] ids = StringUtils.split(definition.getValueId(), ".");
		String valueId = ids[ids.length - 1];

		// 相手のキーと自分のキーが一致したら相手の値を取得
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rangeValues = (List<Map<String, Object>>) rangeContent.getValues();
		@SuppressWarnings("unchecked")
		List<Object> myKeys = (List<Object>) myKeyContent.getValues();
		List<Object> myValues = new ArrayList<>();
		for (Object myKey : myKeys) {
			myValues.add(null);
			for (Map<String, Object> rangeValue : rangeValues) {
				if (rangeValue.get(keyContent.getId()).equals(myKey)) {
					myValues.set(myValues.size() - 1, rangeValue.get(valueId));
					continue;
				}
			}
		}
		return myValues;
	}

	@Override
	public List<Content> searchContents(Definition key) {
		List<Content> contents = new ArrayList<>();
		if (definition == key) {
			contents.add(this);
		}
		return contents;
	}

	@Override
	public boolean isClosed() {
		return true;
	}

	@Override
	public void close(int size) {
	}

	@Override
	public int size() {
		return -1;
	}
}