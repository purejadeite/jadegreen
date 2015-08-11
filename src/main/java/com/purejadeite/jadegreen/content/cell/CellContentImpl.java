package com.purejadeite.jadegreen.content.cell;

import static com.purejadeite.jadegreen.content.Status.*;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.jadegreen.content.AbstractContent;
import com.purejadeite.jadegreen.content.Content;
import com.purejadeite.jadegreen.content.Status;
import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.cell.CellDefinition;

/**
 * <pre>
 * 値の取得元セルの情報を保持する抽象クラス
 * </pre>
 * @author mitsuhiroseino
 */
public class CellContentImpl extends AbstractContent<CellDefinition> implements CellContent {

	private static final long serialVersionUID = -2503759991149163949L;

	private static final Logger LOGGER = LoggerFactory.getLogger(CellContentImpl.class);

	private Object values;

	public CellContentImpl(Content parent, CellDefinition definition) {
		super(parent, definition);
	}

	public Status addValue(int row, int col, Object value) {
		if (closed) {
			return END;
		}
		if (definition.isIncluded(row, col)) {
			this.values = value;
			close();
			LOGGER.debug("success:id=" + this.getId() + ",row=" + row + ",col=" + col +",value=" + value);
			return SUCCESS;
		}
		return NO;
	}

	@Override
	public Object getRawValuesImpl(Definition... ignore) {
		return values;
	}

	@Override
	public Object getValuesImpl(Definition... ignore) {
		return definition.convert(values);
	}

	@Override
	public String getId() {
		return definition.getId();
	}

	@Override
	public Definition getDefinition() {
		return definition;
	}

	@Override
	public List<Content> searchContents(Definition key) {
		List<Content> contents = new ArrayList<>();
		if (definition == key) {
			contents.add(this);
		}
		return contents;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJson() {
		return "{" + super.toJson() + "," + getJson("values", values) + "}";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + super.toString() + ", values=" + values + "]";
	}
}
