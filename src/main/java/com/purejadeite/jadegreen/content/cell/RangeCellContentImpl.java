package com.purejadeite.jadegreen.content.cell;

import static com.purejadeite.jadegreen.content.Status.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.jadegreen.content.AbstractContent;
import com.purejadeite.jadegreen.content.Content;
import com.purejadeite.jadegreen.content.Status;
import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.cell.RangeCellDefinition;

/**
 * Rangeの構成要素となるCell読み込み定義
 * @author mitsuhiroseino
 */
public class RangeCellContentImpl extends AbstractContent<RangeCellDefinition> implements RangeCellContent {

	private static final Logger LOGGER = LoggerFactory.getLogger(RangeCellContentImpl.class);

	/**
	 * 値
	 */
	protected List<Object> values = new ArrayList<>();

	/**
	 * コンストラクタ
	 */
	public RangeCellContentImpl(Content parent, RangeCellDefinition definition) {
		super(parent, definition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getRawValuesImpl(Definition... ignore) {
		return values;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValuesImpl(Definition... ignore) {
		return definition.aplly(values);
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
				LOGGER.debug("break:id=" + this.getId() + ",row=" + row + ",col=" + col +",value=" + value);
				return END;
			} else {
				// 値を取得
				this.values.add(value);
				LOGGER.debug("success:id=" + this.getId() + ",row=" + row + ",col=" + col +",value=" + value);
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
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("values", values);
		return map;
	}
}
