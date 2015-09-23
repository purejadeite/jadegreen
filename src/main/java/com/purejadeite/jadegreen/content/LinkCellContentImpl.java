package com.purejadeite.jadegreen.content;

import static com.purejadeite.jadegreen.content.Status.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.cell.LinkCellDefinitionImpl;

/**
 * <pre>
 * 値の取得元セルの情報を保持する抽象クラス
 * </pre>
 * @author mitsuhiroseino
 */
public class LinkCellContentImpl extends AbstractContent<LinkCellDefinitionImpl> implements LinkCellContent<LinkCellDefinitionImpl>{

	private static final Logger LOGGER = LoggerFactory.getLogger(LinkCellContentImpl.class);

	public LinkCellContentImpl(Content<?> parentContent, LinkCellDefinitionImpl definition) {
		super(parentContent, definition);
	}

	/**
	 * {@inheritDoc}
	 * リンクしている単一セルは値の追加が完了しているとして扱います。
	 */
	@Override
	public Status addValue(int row, int col, Object value) {
		// 値を取得しない
		return END;
	}

	/**
	 * {@inheritDoc}
	 * リンクしている単一セルは取得した値がないため無視をする対象とします。
	 */
	@Override
	public Object getRawValuesImpl(Definition<?>... ignore) {
		// 値は無視してもらう
		return SpecificValue.INVALID;
	}

	/**
	 * {@inheritDoc}
	 * シートのキー情報でシート同士をひも付け、相手シートの値を取得します。
	 */
	@Override
	public Object getValuesImpl(Definition<?>... ignore) {
		// Contentのルートを取得
		Content<?> book = this.getUpperContent(WorkbookContentImpl.class);

		// 全Contentから欲しい値のContentを取得
		List<Content<?>> valueContents = book.searchContents(definition.getValueDefinition());
		if (valueContents.isEmpty()) {
			// 欲しい値が無いのはシート自体が無い可能性があるので警告だけ
			LOGGER.warn("リンク先の値が存在しませんでした。:" + definition.getValueDefinition().getFullId());
			return null;
		} else if (valueContents.size() == 1 && StringUtils.isEmpty(definition.getSheetKeyId())) {
			// 欲しい値が1つでキーが指定されていない場合はそれの値を返す
			// 1ファイルに各シートが1つずつしかない場合を想定
			LOGGER.debug("リンク先が1つだけ:" + definition.getValueDefinition().getFullId());
			return valueContents.get(0).getValues(ignore);
		}

		// 全Contentから相手のシートのキーになるContentを取得
		List<Content<?>> sheetKeyContents = getSheetKeyContents(book);

		// 自分の属するシートのキーを取得
		Content<?> mySheetKeyContent = getMySheetKeyContent(book);

		// 値の取得元シートを取得
		Content<?> targetSheet = getTargetSheet(mySheetKeyContent, sheetKeyContents);

		// 所得元の値のセルを取得
		Content<?> valueContent = getValueContent(targetSheet, valueContents);
		if (valueContent != null) {
			return valueContent.getValues(ignore);
		} else {
			// 値が見つからないならばシートの状態がおかしい
			throw new IllegalStateException("リンク先の値を取得できませんでした。");
		}
	}

	@Override
	public boolean isClosed() {
		return true;
	}

	public List<Content<?>> getSheetKeyContents(Content<?> book) {
		return LinkContentUtils.getSheetKeyContents(book, definition);
	}

	public Content<?> getMySheetKeyContent(Content<?> book) {
		Content<?> sheet = this.getUpperContent(WorksheetContentImpl.class);
		return LinkContentUtils.getMySheetKeyContent(book, sheet, definition);
	}

	public Content<?> getTargetSheet(Content<?> mySheetKeyContent, List<Content<?>> sheetKeyContents) {
		return LinkContentUtils.getTargetSheet(mySheetKeyContent, sheetKeyContents);
	}

	public Content<?> getValueContent(Content<?> targetSheet, List<Content<?>> valueContents) {
		return LinkContentUtils.getValueContent(targetSheet, valueContents);
	}

	@Override
	public List<Content<?>> searchContents(Definition<?> key) {
		List<Content<?>> contents = new ArrayList<>();
		if (definition == key) {
			contents.add(this);
		}
		return contents;
	}

}
