package com.purejadeite.jadegreen.content;

import static com.purejadeite.jadegreen.content.Status.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.jadegreen.definition.cell.CellDefinition;

/**
 * <pre>
 * 値の取得元セルの情報を保持する抽象クラス
 * </pre>
 * @author mitsuhiroseino
 */
public class CellContentImpl extends AbstractCellContent<CellDefinition> {

	private static final Logger LOGGER = LoggerFactory.getLogger(CellContentImpl.class);

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
}
