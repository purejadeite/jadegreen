package com.purejadeite.jadegreen.content;

import static com.purejadeite.jadegreen.content.Status.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.jadegreen.definition.cell.TableCellDefinition;

/**
 * Table配下のCellのコンテンツ
 * @author mitsuhiroseino
 */
public class AbstractTableCellContent<D extends TableCellDefinition<?>> extends AbstractContent<TableContent, D> implements TableCellContent<D> {

	private static final long serialVersionUID = 1420210546938530625L;

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractTableCellContent.class);

	/**
	 * 値
	 */
	protected List<Object> values = new ArrayList<>();

	/**
	 * コンストラクタ
	 */
	public AbstractTableCellContent(String uuid, TableContent parent, D definition) {
		super(uuid, parent, definition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getRawValuesImpl() {
		return values;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValuesImpl() {
		return definition.applyOptions(values);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Status addValue(int row, int col, Object value) {
		if (closed) {
			// 既にクローズしてる
			return END;
		}
		if (definition.isIncluded(row, col)) {
			if (definition.isEndValue(value)) {
				// 今回の値でクローズし
				close();
				LOGGER.debug("break:id=" + this.getId() + ",cell(" + row + "," + col +").value=" + value);
				return END;
			} else {
				// 値を取得
				this.values.add(value);
				LOGGER.debug("success:id=" + this.getId() + ",cell(" + row + "," + col +").value=" + value);
				return SUCCESS;
			}
		}
		// 値を取得しない
		return NO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		return values.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close(int size) {
		close();
		while(values.size() < size) {
			values.add(null);
		}
		while(size < values.size()){
			values.remove(values.size() - 1);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("values", values);
		return map;
	}
}
