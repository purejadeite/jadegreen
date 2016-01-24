package com.purejadeite.jadegreen.content;

import static com.purejadeite.jadegreen.content.Status.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.WorksheetDefinition;
import com.purejadeite.jadegreen.definition.cell.CellDefinition;
import com.purejadeite.jadegreen.definition.cell.CellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.JoinedCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.ValueDefinitionImpl;
import com.purejadeite.jadegreen.definition.table.TableDefinition;

/**
 * Worksheetのコンテンツ
 *
 * @author mitsuhiroseino
 */
public class WorksheetContent extends AbstractContent<WorkbookContent, WorksheetDefinition> {

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
	private List<Content<?, ?>> contents = new ArrayList<>();

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親コンテンツ
	 * @param definition
	 *            定義
	 * @param sheetName
	 *            worksheet名
	 */
	public WorksheetContent(WorkbookContent parent, WorksheetDefinition definition, String sheetName) {
		super(UUID.randomUUID().toString(), parent, definition);
		this.sheetName = sheetName;
		Content<?, ?> content = null;
		for (Definition<?> childDefinition : definition.getChildren()) {
			if (childDefinition instanceof JoinedCellDefinitionImpl) {
				// 単独セルの結合の場合
				content = new JoinedCellContentImpl(uuid, this, (JoinedCellDefinitionImpl) childDefinition);
			} else if (childDefinition instanceof CellDefinitionImpl) {
				// 単独セルの場合
				content = new CellContentImpl(uuid, this, (CellDefinition<?>) childDefinition);
			} else if (childDefinition instanceof ValueDefinitionImpl) {
				// 単独固定値の場合
				content = new CellContentImpl(uuid, this, (CellDefinition<?>) childDefinition);
			} else if (childDefinition instanceof TableDefinition) {
				// テーブルの場合
				content = new TableContentImpl(uuid, this, (TableDefinition<?>) childDefinition);
			} else {
				continue;
			}
			contents.add(content);
			ContentManager.getInstance().register(this, content);
		}
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
		// セルの値を取得
		Status status = addValueImpl(row, col, value);
		if (status == END) {
			close();
		}
		return status;
	}

	/**
	 * 対象の範囲に含まれるセルが存在する場合、値を取得します
	 *
	 * @param row
	 *            行
	 * @param col
	 *            列
	 * @param value
	 *            値
	 * @return 取得状況
	 */
	private Status addValueImpl(int row, int col, Object value) {
		Status status = END;
		LOGGER.trace("cell(" + row + "," + col + ").value=" + value);
		for (Content<?, ?> content : contents) {
			Status addStatus = content.addValue(row, col, value);
			if (0 < addStatus.compareTo(status)) {
				status = addStatus;
			}
		}
		return status;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isClosed() {
		if (closed) {
			return true;
		}
		for (Content<?, ?> content : contents) {
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
		super.close();
		LOGGER.debug("close: " + sheetName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getRawValuesImpl() {
		Map<String, Object> values = new HashMap<>();
		for (Content<?, ?> content : contents) {
			values.put(content.getId(), content.getRawValues());
		}
		return values;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValuesImpl() {
		if (!this.getDefinition().isOutput()) {
			return SpecificValue.NO_OUTPUT;
		}
		Map<String, Object> values = new HashMap<>();
		values.put("sheetName", sheetName);
		for (Content<?, ?> content : contents) {
			Object vals = content.getValues();
			if (vals != SpecificValue.NO_OUTPUT) {
				values.put(content.getId(), vals);
			}
		}
		return values;
	}

	public String getSheetName() {
		return sheetName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("sheetName", sheetName);
		List<Map<String, Object>> contentMaps = new ArrayList<>();
		for (Content<?, ?> content : contents) {
			contentMaps.add(content.toMap());
		}
		map.put("contents", contentMaps);
		return map;
	}

}
