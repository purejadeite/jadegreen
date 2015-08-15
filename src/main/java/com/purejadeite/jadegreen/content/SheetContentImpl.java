package com.purejadeite.jadegreen.content;

import static com.purejadeite.jadegreen.content.Status.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.jadegreen.content.cell.CellContentImpl;
import com.purejadeite.jadegreen.content.cell.LinkCellContentImpl;
import com.purejadeite.jadegreen.content.range.RangeContentImpl;
import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.SheetDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.CellDefinition;
import com.purejadeite.jadegreen.definition.cell.CellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.LinkCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.range.RangeDefinition;

/**
 * Sheet読み込み定義
 *
 * @author mitsuhiroseino
 *
 */
public class SheetContentImpl extends AbstractContent<SheetDefinitionImpl> {

	private static final long serialVersionUID = 2123782999010149210L;

	private static final Logger LOGGER = LoggerFactory.getLogger(SheetContentImpl.class);

	private String name;

	private List<Content> contents = new ArrayList<>();

	private int prevRow = 0;

	private int prevCol = 0;

	public SheetContentImpl(Content parent, SheetDefinitionImpl definition, String name) {
		super(parent, definition);
		this.name = name;
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
		prevCol = definition.getMaxCol();
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
		LOGGER.debug("row=" + row + ",col=" + col + ",value=" + value);
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
		int maxCol = this.definition.getMaxCol();

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
		// ③現在処理している行で列が連続していない場合
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
		for (Content content : contents) {
			if (!ArrayUtils.contains(ignore, content.getDefinition())) {
				Object vals = content.getValues(ignore);
				if (vals != SpecificValue.STUFF) {
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
	public String toJson() {
		return "{" + super.toJson() + "," + getJson("name", name) + "," + getJson("contents", contents) + "}";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + super.toString() + ", name=" + name + ", contents=" + contents
				+ "]";
	}

}
