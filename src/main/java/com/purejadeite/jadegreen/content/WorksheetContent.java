package com.purejadeite.jadegreen.content;

import static com.purejadeite.jadegreen.content.Status.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.jadegreen.definition.MappingDefinition;
import com.purejadeite.jadegreen.definition.WorksheetDefinition;
import com.purejadeite.jadegreen.definition.cell.CellDefinition;
import com.purejadeite.jadegreen.definition.cell.CellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.JoinedCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.table.TableDefinition;

/**
 * Worksheetのコンテンツ
 * @author mitsuhiroseino
 */
public class WorksheetContent extends AbstractContent<WorksheetDefinition> {

	private static final long serialVersionUID = -6579860061499426256L;

	/**
	 * ロガー
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(WorksheetContent.class);

	/**
	 * worksheet名
	 */
	private String sheetName;

	/**
	 * worksheet配下のコンテンツ
	 */
	private List<Content<?>> contents = new ArrayList<>();

	/**
	 * 前回処理した行
	 */
	private int prevRow = 0;

	/**
	 * 前回処理した列
	 */
	private int prevCol = 0;

	/**
	 * 定義上の最大列
	 */
	private int maxCol = 0;

	/**
	 * コンストラクタ
	 * @param parent 親コンテンツ
	 * @param definition 定義
	 * @param sheetName worksheet名
	 */
	public WorksheetContent(Content<?> parent, WorksheetDefinition definition, String sheetName) {
		super(parent, definition);
		this.sheetName = sheetName;
		for (MappingDefinition<?> childDefinition : definition.getChildren()) {
			if (childDefinition instanceof JoinedCellDefinitionImpl) {
				// 単独セルの結合の場合
				contents.add(new JoinedCellContentImpl(this, (JoinedCellDefinitionImpl) childDefinition));
			} else if (childDefinition instanceof CellDefinitionImpl) {
				// 単独セルの場合
				contents.add(new CellContentImpl(this, (CellDefinition<?>) childDefinition));
			} else if (childDefinition instanceof TableDefinition) {
				// テーブルの場合
				contents.add(new TableContentImpl(this, (TableDefinition<?>) childDefinition));
			}
		}
		maxCol = definition.getMaxCol();
		prevCol = maxCol;
		LOGGER.debug("create: " + sheetName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Status addValue(int row, int col, Object value) {
		if (isClosed()) {
			return END;
		}
		// セルが連続していない場合の処理
		Status status = addDummyValues(row, col);
		if (status == END) {
			close();
			return status;
		}
		// 今回のセルの処理
		status = addValueImpl(row, col, value);
		if (status == END) {
			close();
		}
		return status;
	}

	/**
	 * 対象の範囲に含まれるセルが存在する場合、値を取得します
	 * @param row 行
	 * @param col 列
	 * @param value 値
	 * @return 取得状況
	 */
	private Status addValueImpl(int row, int col, Object value) {
		Status status = END;
		LOGGER.trace("cell(" + row + "," + col + ").value=" + value);
		for (Content<?> content : contents) {
			Status addStatus = content.addValue(row, col, value);
			if (0 < addStatus.compareTo(status)) {
				status = addStatus;
			}
		}
		prevRow = row;
		prevCol = col;
		return status;
	}

	/**
	 * スキップした範囲にnullを追加します
	 * @param row 行
	 * @param col 列
	 * @return 取得状況
	 */
	private Status addDummyValues(int row, int col) {
		Status status = END;
		boolean added = false;

		// ①前回処理していた行が最後の列まで処理されていない場合
		if (prevRow < row && prevCol < maxCol) {
			// 前回処理していた行にダミー列の追加処理
			for (int c = prevCol + 1; c < maxCol + 1; c++) {
				Status addStatus = addDummyCol(prevRow, c);
				if (0 < addStatus.compareTo(status)) {
					status = addStatus;
				}
			}
			added = true;
		}
		// ②前回処理していた行と今回処理する行が連続していない場合
		if (prevRow + 1 < row) {
			// ダミー行の追加処理
			for (int r = prevRow + 1; r < row; r++) {
				Status addStatus = addDummyRow(r);
				if (0 < addStatus.compareTo(status)) {
					status = addStatus;
				}
			}
			added = true;
		}
		// ③現在処理している行が先頭の列から始まっていない場合
		if (prevRow + 1 == row && maxCol <= prevCol && col != 1) {
			// ダミー列の追加処理
			for (int c = 1; c < col; c++) {
				Status addStatus = addDummyCol(row, c);
				if (0 < addStatus.compareTo(status)) {
					status = addStatus;
				}
			}
			added = true;
		}
		// ④現在処理している行で列が連続していない場合
		if (prevRow == row && prevCol + 1 < col) {
			// ダミー列の追加処理
			for (int c = prevCol + 1; c < col; c++) {
				Status addStatus = addDummyCol(row, c);
				if (0 < addStatus.compareTo(status)) {
					status = addStatus;
				}
			}
			added = true;
		}
		// ダミー行の追加があった場合のみstatusを返す
		if (added) {
			return status;
		} else {
			return NO;
		}
	}

	/**
	 * スキップした行にnullを追加します
	 * @param row 行
	 * @return 取得状況
	 */
	private Status addDummyRow(int row) {
		Status status = END;
		for (int col = 1; col < definition.getMaxCol() + 1; col++) {
			Status addStatus = addDummyCol(row, col);
			if (0 < addStatus.compareTo(status)) {
				status = addStatus;
			}
		}
		return status;
	}

	/**
	 * スキップしたセルにnullを追加します
	 * @param row 行
	 * @param col 列
	 * @return 取得状況
	 */
	private Status addDummyCol(int row, int col) {
		return addValueImpl(row, col, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isClosed() {
		if (closed) {
			return true;
		}
		for (Content<?> content : contents) {
			if (!content.isClosed()) {
				return false;
			}
		}
		close();
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() {
		if (prevCol != maxCol) {
			// 最終列まで処理していない場合はダミーセルを追加
			addDummyValues(prevRow + 1, 1);
		}
		super.close();
		LOGGER.debug("close: " + sheetName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getRawValuesImpl(MappingDefinition<?>... ignore) {
		Map<String, Object> values = new HashMap<>();
		for (Content<?> content : contents) {
			if (!ArrayUtils.contains(ignore, content.getDefinition())) {
				values.put(content.getId(), content.getRawValues(ignore));
			}
		}
		return values;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValuesImpl(MappingDefinition<?>... ignore) {
		if (!this.getDefinition().isOutput()) {
			return SpecificValue.NO_OUTPUT;
		}
		Map<String, Object> values = new HashMap<>();
		values.put("sheetName", sheetName);
		for (Content<?> content : contents) {
			if (!ArrayUtils.contains(ignore, content.getDefinition())) {
				Object vals = content.getValues(ignore);
				if (vals != SpecificValue.NO_OUTPUT) {
					values.put(content.getId(), vals);
				}
			}
		}
		return values;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Content<?>> searchContents(MappingDefinition<?> key) {
		List<Content<?>> cntnts = new ArrayList<>();
		if (definition == key) {
			cntnts.add(this);
		}
		for (Content<?> content : contents) {
			cntnts.addAll(content.searchContents(key));
		}
		return cntnts;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("sheetName", sheetName);
		List<Map<String, Object>> contentMaps = new ArrayList<>();
		for(Content<?> content: contents) {
			contentMaps.add(content.toMap());
		}
		map.put("contents", contentMaps);
		return map;
	}

}
