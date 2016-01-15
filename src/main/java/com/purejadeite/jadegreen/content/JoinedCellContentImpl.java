package com.purejadeite.jadegreen.content;

import static com.purejadeite.jadegreen.content.Status.*;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.cell.JoinedCellDefinitionImpl;

/**
 * 他のセルに結合したCellクラス
 *
 * @author mitsuhiroseino
 */
public class JoinedCellContentImpl extends AbstractContent<JoinedCellDefinitionImpl>
		implements JoinedCellContent<JoinedCellDefinitionImpl> {

	private static final long serialVersionUID = 3474501722301631948L;

	private static final Logger LOGGER = LoggerFactory.getLogger(JoinedCellContentImpl.class);

	public JoinedCellContentImpl(Content<?> parentContent, JoinedCellDefinitionImpl definition) {
		super(parentContent, definition);
	}

	/**
	 * {@inheritDoc} 結合している単一セルは値の追加が完了しているとして扱います。
	 */
	@Override
	public Status addValue(int row, int col, Object value) {
		// 値を取得しない
		return END;
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
		// Contentのルートを取得
		WorkbookContent book = this.getUpperContent(WorkbookContent.class);
		// 自分のsheetを取得
		WorksheetContent sheet = this.getUpperContent(WorksheetContent.class);

		// 全Contentから欲しい値のContentを取得
		List<Content<?>> valueContents = book.searchContents(definition.getValueDefinition());
		if (valueContents.isEmpty()) {
			// 欲しい値が無いのはシート自体が無い可能性があるので警告だけ
			LOGGER.warn("結合元のキー値に一致する結合先のキー値が存在しませんでした。:" + definition.getValueDefinition().getFullId());
			return null;
		}

		// 全Contentから相手のシートのキーになるContentを取得
		List<Content<?>> keyContents = JoinedContentUtils.getKeyContents(book, definition);
		if (keyContents == null) {
			throw new IllegalStateException("結合先シートのキーが見つかりません：" + definition.getKeyDefinition().getFullId());
		}

		// 自分の属するシートのキーを取得
		Content<?> myKeyContent = JoinedContentUtils.getMyKeyContent(sheet, definition);
		if (myKeyContent == null) {
			throw new IllegalStateException("結合元シートのキーが見つかりません：" + definition.getMyKeyDefinition().getFullId());
		}

		// 値の取得元シートを取得
		WorksheetContent targetSheet = JoinedContentUtils.getTargetSheet(myKeyContent, keyContents);
		if (targetSheet == null) {
			// 紐付くシートなし
			return null;
		}

		// 所得元の値のセルを取得
		Content<?> valueContent = JoinedContentUtils.getValueContent(targetSheet, valueContents);
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
	public boolean isClosed() {
		return true;
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

}
