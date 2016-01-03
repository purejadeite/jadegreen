package com.purejadeite.jadegreen.content;

import static com.purejadeite.jadegreen.content.Status.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import com.purejadeite.jadegreen.definition.MappingDefinition;
import com.purejadeite.jadegreen.definition.cell.JoinedTableCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.TableCellDefinition;
import com.purejadeite.jadegreen.definition.table.TableDefinition;

/**
 * テーブル形式の範囲の情報を保持するクラスの抽象クラスです
 * @author mitsuhiroseino
 */
public class TableContentImpl extends AbstractContent<TableDefinition<?>> implements TableContent {

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
	public TableContentImpl(Content<?> parent, TableDefinition<?> definition) {
		super(parent, definition);
		for (MappingDefinition<?> childDefinition : definition.getChildren()) {
			if (childDefinition instanceof JoinedTableCellDefinitionImpl) {
				// 結合の場合
				cells.add(new JoinedTableCellContentImpl(this, (JoinedTableCellDefinitionImpl) childDefinition));
			} else if (childDefinition instanceof TableCellDefinition) {
				// セルの場合
				cells.add(new TableCellContentImpl(this, (TableCellDefinition<?>) childDefinition));
			}
		}
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
	public Object getRawValuesImpl(final MappingDefinition<?>... ignore) {
		return getCellValues(new CellValueGetter() {
			@Override
			public Object get(TableCellContent<?> cell) {
				return cell.getRawValues(ignore);
			}
		}, ignore);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValuesImpl(final MappingDefinition<?>... ignore) {
		Object values = getCellValues(new CellValueGetter() {
			@Override
			public Object get(TableCellContent<?> cell) {
				return cell.getValues(ignore);
			}
		}, ignore);
		return definition.apply(values);
	}

	/**
	 * 値取得を行う共通処理
	 * @param getter Getterの実装
	 * @param ignore 取得しない定義
	 * @return 値
	 */
	private Object getCellValues(CellValueGetter getter, MappingDefinition<?>... ignore) {
		List<Map<String, Object>> values = new ArrayList<>(size);

		for (TableCellContent<?> cell : cells) {
			if (!ArrayUtils.contains(ignore, cell.getDefinition())) {
				@SuppressWarnings("unchecked")
				List<Object> vals = (List<Object>) getter.get(cell);
				if (vals == null || SpecificValue.NO_OUTPUT.equals(vals)) {
					continue;
				}
				for (int i = 0; i < vals.size(); i++) {
					Object val = vals.get(i);
					Map<String, Object> line = null;
					if (values.size() < i + 1) {
						line = new HashMap<>();
						values.add(line);
					} else {
						line = values.get(i);
					}
					if (!SpecificValue.UNDEFINED.equals(val)) {
						line.put(cell.getId(), val);
					}
				}
			}
		}
		return values;
	}

	/**
	 * 値取得処理の共有用インターフェイス
	 * @author mitsuhiroseino
	 */
	private static interface CellValueGetter {
		/**
		 * cellから値を取得します
		 * @param cell Cell読み込み情報
		 * @return 値
		 */
		public Object get(TableCellContent<?> cell);
	}

	@Override
	public List<Content<?>> searchContents(MappingDefinition<?> key) {
		List<Content<?>> contents = new ArrayList<>();
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
		for(Content<?> cell: cells) {
			cellMaps.add(cell.toMap());
		}
		map.put("cells", cellMaps);
		map.put("begin", begin);
		return map;
	}
}
