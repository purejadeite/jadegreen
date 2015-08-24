package com.purejadeite.jadegreen.content.range;

import static com.purejadeite.jadegreen.content.Status.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.jadegreen.content.AbstractContent;
import com.purejadeite.jadegreen.content.Content;
import com.purejadeite.jadegreen.content.SpecificValue;
import com.purejadeite.jadegreen.content.Status;
import com.purejadeite.jadegreen.content.cell.LinkRangeCellContentImpl;
import com.purejadeite.jadegreen.content.cell.RangeCellContent;
import com.purejadeite.jadegreen.content.cell.RangeCellContentImpl;
import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.cell.LinkRangeCellDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.RangeCellDefinition;
import com.purejadeite.jadegreen.definition.range.RangeDefinition;

/**
 * テーブル形式の範囲の情報を保持するクラスの抽象クラスです
 * @author mitsuhiroseino
 */
public class RangeContentImpl extends AbstractContent<RangeDefinition> implements RangeContent {

	private static final long serialVersionUID = 8859089448405461049L;

	private static final Logger LOGGER = LoggerFactory.getLogger(RangeContentImpl.class);

	protected List<RangeCellContent> cells = new ArrayList<>();

	/**
	 * レコード数
	 */
	protected int size = 0;

	private boolean begin = false;

	/**
	 * コンストラクタ
	 */
	public RangeContentImpl(Content parent, RangeDefinition definition) {
		super(parent, definition);
		for (Definition childDefinition : definition.getChildren()) {
			if (childDefinition instanceof LinkRangeCellDefinitionImpl) {
				// リンクの場合
				cells.add(new LinkRangeCellContentImpl(this, (LinkRangeCellDefinitionImpl) childDefinition));
			} else if (childDefinition instanceof RangeCellDefinition) {
				// セルの場合
				cells.add(new RangeCellContentImpl(this, (RangeCellDefinition) childDefinition));
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
  		for (RangeCellContent cell : cells) {
			Status cellStatus = cell.addValue(row, col, value);
			// このrangeにおける値の取得状況
			// 何れかのCellが終わりに達したら、このRangeは処理を終わるので下記の順に優先される
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
				LOGGER.debug("success:id=" + this.getId() + ",row=" + row + ",col=" + col +",value=" + value);
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
		for (RangeCellContent cell : cells) {
			int cellSize = cell.size();
			if (-1 < cellSize && cellSize < minSize) {
				minSize = cellSize;
			}
		}
		size = minSize;
		// 一番取得数が少ないところに切りそろえる
		for (RangeCellContent cell : cells) {
			cell.close(size);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getRawValuesImpl(final Definition... ignore) {
		return getCellValues(new CellValueGetter() {
			@Override
			public Object get(RangeCellContent cell) {
				return cell.getRawValues(ignore);
			}
		}, ignore);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValuesImpl(final Definition... ignore) {
		Object values = getCellValues(new CellValueGetter() {
			@Override
			public Object get(RangeCellContent cell) {
				return cell.getValues(ignore);
			}
		}, ignore);
		return definition.convert(values);
	}

	/**
	 * 値取得を行う共通処理
	 * @param getter Getterの実装
	 * @param ignore 取得しない定義
	 * @return 値
	 */
	private Object getCellValues(CellValueGetter getter, Definition... ignore) {
		List<Map<String, Object>> values = new ArrayList<>(size);

		for (RangeCellContent cell : cells) {
			if (!ArrayUtils.contains(ignore, cell.getDefinition())) {
				@SuppressWarnings("unchecked")
				List<Object> vals = (List<Object>) getter.get(cell);
				if (SpecificValue.STUFF.equals(vals)) {
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
					line.put(cell.getId(), val);
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
		public Object get(RangeCellContent cell);
	}

	@Override
	public List<Content> searchContents(Definition key) {
		List<Content> contents = new ArrayList<>();
		if (definition == key) {
			contents.add(this);
		}
		for (RangeCellContent cell : cells) {
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
		map.put("size", size);
		List<Map<String, Object>> cellMaps = new ArrayList<>();
		for(Content cell: cells) {
			cellMaps.add(cell.toMap());
		}
		map.put("cells", cellMaps);
		return map;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + super.toString() + ", size=" + size
				+ ", cells=" + cells + "]";
	}
}
