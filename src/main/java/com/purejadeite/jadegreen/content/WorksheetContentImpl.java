package com.purejadeite.jadegreen.content;

import static com.purejadeite.jadegreen.content.Status.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.WorksheetDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.CellDefinition;
import com.purejadeite.jadegreen.definition.cell.CellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.LinkCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.range.RangeDefinition;

/**
 * Worksheet読み込み定義
 *
 * @author mitsuhiroseino
 *
 */
public class WorksheetContentImpl extends AbstractContent<WorksheetDefinitionImpl> {

	private static final Logger LOGGER = LoggerFactory.getLogger(WorksheetContentImpl.class);

	private String sheetName;

	private List<Content> contents = new ArrayList<>();

	private int prevRow = 0;

	private int prevCol = 0;

	private int maxCol = 0;

	public WorksheetContentImpl(Content parent, WorksheetDefinitionImpl definition, String sheetName) {
		super(parent, definition);
		this.sheetName = sheetName;
		for (Definition childDefinition : definition.getChildren()) {
			if (childDefinition instanceof LinkCellDefinitionImpl) {
				// 単独セルのリンクの場合
				contents.add(new LinkCellContentImpl(this, (LinkCellDefinitionImpl) childDefinition));
			} else if (childDefinition instanceof CellDefinitionImpl) {
				// 単独セルの場合
				contents.add(new CellContentImpl(this, (CellDefinition) childDefinition));
			} else if (childDefinition instanceof RangeDefinition) {
				// テーブルの場合
				contents.add(new RangeContentImpl(this, (RangeDefinition) childDefinition));
			}
		}
		maxCol = definition.getMaxCol();
		prevCol = maxCol;
		LOGGER.debug("create: " + sheetName);
	}

	/**
	 * {@inheritDoc}
	 */
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

	private Status addValueImpl(int row, int col, Object value) {
		Status status = END;
		LOGGER.trace("cell(" + row + "," + col + ").value=" + value);
		for (Content content : contents) {
			Status addStatus = content.addValue(row, col, value);
			if (0 < addStatus.compareTo(status)) {
				status = addStatus;
			}
		}
		prevRow = row;
		prevCol = col;
		return status;
	}

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

	private Status addDummyRow(int r) {
		Status status = END;
		for (int c = 1; c < definition.getMaxCol() + 1; c++) {
			Status addStatus = addDummyCol(r, c);
			if (0 < addStatus.compareTo(status)) {
				status = addStatus;
			}
		}
		return status;
	}

	private Status addDummyCol(int r, int c) {
		return addValueImpl(r, c, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isClosed() {
		if (closed) {
			return true;
		}
		for (Content content : contents) {
			if (!content.isClosed()) {
				return false;
			}
		}
		close();
		return true;
	}

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
	public Object getRawValuesImpl(Definition... ignore) {
		Map<String, Object> values = new HashMap<>();
		for (Content content : contents) {
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
	public Object getValuesImpl(Definition... ignore) {
		Map<String, Object> values = new HashMap<>();
		values.put("sheetName", sheetName);
		for (Content content : contents) {
			if (!ArrayUtils.contains(ignore, content.getDefinition())) {
				Object vals = content.getValues(ignore);
				if (vals != SpecificValue.NO_OUTPUT) {
					values.put(content.getId(), vals);
				}
			}
		}
		return values;
	}

	@Override
	public List<Content> searchContents(Definition key) {
		List<Content> cntnts = new ArrayList<>();
		if (definition == key) {
			cntnts.add(this);
		}
		for (Content content : contents) {
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
		for(Content content: contents) {
			contentMaps.add(content.toMap());
		}
		map.put("contents", contentMaps);
		return map;
	}

}
