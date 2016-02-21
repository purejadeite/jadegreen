package com.purejadeite.jadegreen.content;

import static com.purejadeite.jadegreen.content.Status.*;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.jadegreen.definition.cell.CellDefinition;
import com.purejadeite.util.collection.Table;

/**
 * セルの値をList形式で保持するクラス
 * @author mitsuhiroseino
 */
public class UnionCellContentImpl extends CellContentImpl {

	/**
	 * ロガー
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(UnionCellContentImpl.class);

	/**
	 * コンストラクタ
	 * @param parent 親コンテンツ
	 * @param definition 定義
	 */
	public UnionCellContentImpl(ParentContent<?, ?, ?> parent, CellDefinition<?> definition) {
		super(parent, definition);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Status addValue(int row, int col, Object value) {
		if (closed) {
			// 値の取得が終了している場合
			return END;
		}
		if (definition.isIncluded(row, col)) {
			// 行列番号が取得範囲内の場合
			List<Object> vals = null;
			if (this.values == null) {
				vals = new ArrayList<Object>();
				this.values = vals;
			} else {
				vals = (List<Object>) values;
			}
			vals.add(value);
			close();
			LOGGER.debug("success:id=" + this.getId() + ",row=" + row + ",col=" + col +",value=" + value);
			// 取得
			return SUCCESS;
		}
		// 未取得
		return NO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int capture(Table<String> table) {
		// 行列番号が取得範囲内の場合
		List<Object> vals = null;
		if (this.values == null) {
			vals = new ArrayList<Object>();
			this.values = vals;
		} else {
			vals = (List<Object>) values;
		}
		vals.add(getDefinition().capture(table));
		return 1;
	}

}
