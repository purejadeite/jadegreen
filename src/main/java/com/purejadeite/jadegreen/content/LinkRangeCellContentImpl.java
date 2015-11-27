package com.purejadeite.jadegreen.content;

import static com.purejadeite.jadegreen.content.Status.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.jadegreen.definition.MappingDefinition;
import com.purejadeite.jadegreen.definition.cell.LinkRangeCellDefinitionImpl;

/**
 * 他のセルにリンクしたrange配下のCellクラス
 * @author mitsuhiroseino
 */
public class LinkRangeCellContentImpl extends AbstractRangeCellContent<LinkRangeCellDefinitionImpl> implements RangeCellContent<LinkRangeCellDefinitionImpl>, LinkCellContent<LinkRangeCellDefinitionImpl> {

	private static final long serialVersionUID = 7690624851647074594L;

	/**
	 * ロガー
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LinkRangeCellContentImpl.class);

	/**
	 * コンストラクタ
	 * @param parent 親コンテンツ
	 * @param definition 定義
	 */
	public LinkRangeCellContentImpl(Content<?> parent, LinkRangeCellDefinitionImpl definition) {
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
	public Object getRawValuesImpl(MappingDefinition<?>... ignore) {
		// 値は無視してもらう
		return SpecificValue.UNDEFINED;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValuesImpl(MappingDefinition<?>... ignore) {
		// Contentのルートを取得
		WorkbookContent book = this.getUpperContent(WorkbookContent.class);
		// 自分のsheetを取得
		WorksheetContent sheet = this.getUpperContent(WorksheetContent.class);

		// 全Contentから相手のシートのキーになるContentを取得
		List<Content<?>> sheetKeyContents = LinkContentUtils.getSheetKeyContents(book, definition);
		if (sheetKeyContents == null) {
			throw new IllegalStateException("リンク先シートのキーが見つかりません：" + definition.getSheetKeyDefinition().getFullId());
		}

		// 自分のシートのキーを取得
		Content<?> mySheetKeyContent = LinkContentUtils.getMySheetKeyContent(sheet, definition);
		if (mySheetKeyContent == null) {
			throw new IllegalStateException("リンク元シートのキーが見つかりません：" + definition.getMySheetKeyDefinition().getFullId());
		}
		LOGGER.debug("自分のシート:" + mySheetKeyContent.getFullId());

		// 値の取得元シートを取得
		WorksheetContent sheetContent = LinkContentUtils.getTargetSheet(mySheetKeyContent, sheetKeyContents);
		if (sheetContent == null) {
			return null;
		}

		// リンク先のキーとなるレコードを取得
		List<Content<?>> keyContents = sheetContent.searchContents(definition.getKeyDefinition());
		if (keyContents.isEmpty()) {
			return null;
		}
		Content<?> keyContent = keyContents.get(0);

		// リンク先のテーブル丸ごと取得
		Content<?> rangeContent = keyContent.getUpperContent(RangeContentImpl.class);

		// リンク元の属するsheetを取得
		Content<?> mySheetContent = this.getUpperContent(WorksheetContent.class);

		// リンク元のキーとなるレコードを取得
		List<Content<?>> myKeyContents = mySheetContent.searchContents(definition.getMyKeyDefinition());
		if (myKeyContents.isEmpty()) {
			return null;
		}
		Content<?> myKeyContent = myKeyContents.get(0);

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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Content<?>> searchContents(MappingDefinition<?> key) {
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