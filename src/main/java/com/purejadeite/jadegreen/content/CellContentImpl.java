package com.purejadeite.jadegreen.content;

import static com.purejadeite.jadegreen.content.Status.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.jadegreen.definition.cell.CellDefinition;

/**
 * セルの値を保持するクラス
 * @author mitsuhiroseino
 */
public class CellContentImpl extends AbstractCellContent<CellDefinition<?>> {

	private static final long serialVersionUID = -1289731718432529281L;

	/**
	 * ロガー
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CellContentImpl.class);

	/**
	 * コンストラクタ
	 * @param parent 親コンテンツ
	 * @param definition 定義
	 */
	public CellContentImpl(String uuid, WorksheetContent parent, CellDefinition<?> definition) {
		super(uuid, parent, definition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Status addValue(int row, int col, Object value) {
		if (closed) {
			// 値の取得が終了している場合
			return END;
		}
		if (definition.isIncluded(row, col)) {
			// 行列番号が取得範囲内の場合
			this.values = value;
			close();
			LOGGER.debug("success:id=" + this.getId() + ",row=" + row + ",col=" + col +",value=" + value);
			// 取得
			return SUCCESS;
		}
		// 未取得
		return NO;
	}

}
