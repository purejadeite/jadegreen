package purejadeite.jadegreen.content;

import purejadeite.jadegreen.content.cell.CellContentImpl;
import purejadeite.jadegreen.content.cell.LinkCellContentImpl;
import purejadeite.jadegreen.content.range.RangeContentImpl;
import purejadeite.jadegreen.definition.Definition;
import purejadeite.jadegreen.definition.SheetDefinitionImpl;
import purejadeite.jadegreen.definition.cell.CellDefinition;
import purejadeite.jadegreen.definition.cell.CellDefinitionImpl;
import purejadeite.jadegreen.definition.cell.LinkCellDefinitionImpl;
import purejadeite.jadegreen.definition.range.RangeDefinition;

import static purejadeite.jadegreen.content.Status.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Sheet読み込み定義
 * 
 * @author mitsuhiroseino
 *
 */
public class SheetContentImpl extends AbstractContent<SheetDefinitionImpl> {

	private String name;

	private List<Content> contents = new ArrayList<>();

	private int prevRow = -1;

	private int prevCol = -1;

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
	}

	/**
	 * {@inheritDoc}
	 */
	public Status addValue(int row, int col, Object value) {
		if (isClosed()) {
			return END;
		}
		Status status = addValueImpl(row, col, value);
		if (status == END) {
			close();
		}
		prevRow = row;
		prevCol = col;
		return status;
	}

	private Status addValueImpl(int row, int col, Object value) {
		Status status = addDummyValues(row, col);
		if (status == END) {
			return status;
		}
		for (Content content : contents) {
			Status addStatus = content.addValue(row, col, value);
			if (0 < addStatus.compareTo(status)) {
				status = addStatus;
			}
		}
		return status;
	}

	private Status addDummyValues(int row, int col) {
		Status status = END;
		int assumedRow = prevRow + 1;
		int assumedCol = prevCol + 1;
		if (row != 1 && (row != assumedRow)) {
			// 行が抜けている場合
			// ダミー行の追加処理
			for (int r = assumedRow; r < row; r++) {
				Status addStatus = addDummyRow(r);
				if (0 < addStatus.compareTo(status)) {
					status = addStatus;
				}
			}
		}
		if (col != 1 && (col != assumedCol)) {
			// 列が抜けている場合
			// ダミー列の追加処理
			for (int c = assumedCol; c < col; c++) {
				Status addStatus = addDummyCol(row, c);
				if (0 < addStatus.compareTo(status)) {
					status = addStatus;
				}
			}
		}
		return status;
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
