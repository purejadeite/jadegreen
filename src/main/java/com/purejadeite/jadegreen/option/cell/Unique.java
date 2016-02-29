package com.purejadeite.jadegreen.option.cell;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.content.ContentInterface;
import com.purejadeite.jadegreen.definition.DefinitionInterface;

/**
 * 重複削除
 * @author mitsuhiroseino
 *
 */
public class Unique extends AbstractListCellOption {

	/**
	 * コンストラクタ
	 * @param config コンバーターのコンフィグ
	 */
	public Unique(DefinitionInterface<?> definition, Map<String, Object> config) {
		super(definition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object applyImpl(List<Object> values, ContentInterface<?, ?> content) {
		return new ArrayList<>(new LinkedHashSet<>(values));
	}

}