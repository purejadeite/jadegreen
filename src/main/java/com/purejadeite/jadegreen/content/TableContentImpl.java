package com.purejadeite.jadegreen.content;

import static com.purejadeite.jadegreen.content.Status.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.cell.JoinedTableCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.TableCellDefinition;
import com.purejadeite.jadegreen.definition.cell.TableValueDefinitionImpl;
import com.purejadeite.jadegreen.definition.table.TableDefinition;

/**
 * テーブル形式の範囲の情報を保持するクラスの抽象クラスです
 *
 * @author mitsuhiroseino
 */
public class TableContentImpl extends AbstractContent<WorksheetContent, TableDefinition<?>>implements TableContent {

	private static final long serialVersionUID = 2393648151533807595L;

	protected List<TableCellContent<?>> cells = new ArrayList<>();

	/**
	 * レコード数
	 */
	protected int size = 0;

	private boolean begin = false;

	/**
	 * コンストラクタ
	 */
	public TableContentImpl(String uuid, WorksheetContent parent, TableDefinition<?> definition) {
		super(uuid, parent, definition);
		for (Definition<?> childDefinition : definition.getChildren()) {
			if (childDefinition instanceof JoinedTableCellDefinitionImpl) {
				// 結合の場合
				cells.add(new JoinedTableCellContentImpl(uuid, this, (JoinedTableCellDefinitionImpl) childDefinition));
			} else if (childDefinition instanceof TableValueDefinitionImpl) {
				// 固定値の場合
				cells.add(new StaticTableCellContentImpl(uuid, this, (TableCellDefinition<?>) childDefinition));
			} else if (childDefinition instanceof TableCellDefinition) {
				// セルの場合
				cells.add(new TableCellContentImpl(uuid, this, (TableCellDefinition<?>) childDefinition));
			}
		}
	}

	public List<TableCellContent<?>> getCells() {
		return cells;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Status addValue(int row, int col, Object value) {
		if (closed) {
			return END;
		}
		if (!definition.isIncluded(row, col)) {
			// 対象範囲ではない場合
			// TODO 値の取得が左上から右下に向かうので、列方向の繰り返しの場合の終わりはどう判断するか
			if (begin) {
				// 取得が始まっているのに対象範囲外ということは
				// 取得範囲を超えたと判断し終わり
				close();
				return END;
			} else {
				// まだ始まってない
				return NO;
			}
		}
		// 取得対象範囲
		begin = true;
		Status status = NO;
		for (TableCellContent<?> cell : cells) {
			Status cellStatus = cell.addValue(row, col, value);
			// このtableにおける値の取得状況
			// 何れかのCellが終わりに達したら、このTableは処理を終わるので下記の順に優先される
			// NO < SUCCESS < END
			if (cellStatus == END) {
				status = END;
			} else if (0 < cellStatus.compareTo(status)) {
				status = cellStatus;
			}
			if (cellStatus == END) {
				// トリガーとなる項目に終わりを示す値があった
				break;
			} else if (cellStatus == SUCCESS) {
				// 値を取得した
				size = cell.size();
			}
		}
		if (status == END) {
			close();
		}
		return status;
	}

	@Override
	public void close() {
		super.close();
		closeChildren();
	}

	private void closeChildren() {
		// 最少のサイズを取得
		int minSize = Integer.MAX_VALUE;
		for (TableCellContent<?> cell : cells) {
			int cellSize = cell.size();
			if (-1 < cellSize && cellSize < minSize) {
				minSize = cellSize;
			}
		}
		size = minSize;
		// 一番取得数が少ないところに切りそろえる
		for (TableCellContent<?> cell : cells) {
			cell.close(size);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getRawValuesImpl() {
		return getCellValues(new CellValueGetter() {
			@Override
			public Object get(TableCellContent<?> cell) {
				return cell.getRawValues();
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValuesImpl() {
		Object values = getCellValues(new CellValueGetter() {
			@Override
			public Object get(TableCellContent<?> cell) {
				return cell.getValues();
			}
		});
		return definition.applyOptions(values);
	}

	/**
	 * 値取得を行う共通処理
	 *
	 * @param getter
	 *            Getterの実装
	 * @param ignore
	 *            取得しない定義
	 * @return 値
	 */
	private Object getCellValues(CellValueGetter getter) {
		List<Map<String, Object>> values = new ArrayList<>(size);

		for (TableCellContent<?> cell : cells) {
			@SuppressWarnings("unchecked")
			List<Object> vals = (List<Object>) getter.get(cell);
			if (vals == null || SpecificValue.NO_OUTPUT.equals(vals)) {
				continue;
			}
			for (int i = 0; i < vals.size(); i++) {
				Object val = vals.get(i);
				Map<String, Object> record = null;
				if (values.size() < i + 1) {
					record = new HashMap<>();
					values.add(record);
				} else {
					record = values.get(i);
				}
				if (!SpecificValue.UNDEFINED.equals(val)) {
					record.put(cell.getId(), val);
				}
			}
		}
		return values;
	}

	/**
	 * 値取得処理の共有用インターフェイス
	 *
	 * @author mitsuhiroseino
	 */
	private static interface CellValueGetter {
		/**
		 * cellから値を取得します
		 *
		 * @param cell
		 *            Cell読み込み情報
		 * @return 値
		 */
		public Object get(TableCellContent<?> cell);
	}

	@Override
	public List<Content<?, ?>> searchContents(Definition<?> key) {
		List<Content<?, ?>> contents = new ArrayList<>();
		if (definition == key) {
			contents.add(this);
		}
		for (TableCellContent<?> cell : cells) {
			contents.addAll(cell.searchContents(key));
		}
		return contents;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		List<Map<String, Object>> cellMaps = new ArrayList<>();
		for (Content<?, ?> cell : cells) {
			cellMaps.add(cell.toMap());
		}
		map.put("cells", cellMaps);
		map.put("begin", begin);
		return map;
	}

	@Override
	public int size() {
		return size;
	}
}
