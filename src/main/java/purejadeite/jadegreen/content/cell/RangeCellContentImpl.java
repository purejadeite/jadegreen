package purejadeite.jadegreen.content.cell;

import purejadeite.jadegreen.content.AbstractContent;
import purejadeite.jadegreen.content.Content;
import purejadeite.jadegreen.definition.Definition;
import purejadeite.jadegreen.definition.cell.RangeCellDefinition;
import purejadeite.jadegreen.reader.Status;

import static purejadeite.jadegreen.reader.Status.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Rangeの構成要素となるCell読み込み定義
 * @author mitsuhiroseino
 */
public class RangeCellContentImpl extends AbstractContent<RangeCellDefinition> implements RangeCellContent {

	/**
	 * 値
	 */
	protected List<Object> values = new ArrayList<>();

	/**
	 * コンストラクタ
	 */
	public RangeCellContentImpl(Content parent, RangeCellDefinition definition) {
		super(parent, definition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getRawValuesImpl(Definition... ignore) {
		return values;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValuesImpl(Definition... ignore) {
		return definition.convert(values);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Status addValue(int row, int col, Object value) {
		if (closed) {
			// 既にクローズしてる
			return END;
		}
		if (definition.isIncluded(row, col)) {
			if (definition.isEndValue(value)) {
				// 今回の値でクローズし
				close();
				return END;
			} else {
				// 値を取得
				this.values.add(value);
				return SUCCESS;
			}
		}
		// 値を取得しない
		return NO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		return values.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close(int size) {
		close();
		while(size < values.size()){
			values.remove(values.size() - 1);
		}
	}

	@Override
	public String getId() {
		return definition.getId();
	}

	@Override
	public Definition getDefinition() {
		return definition;
	}

	@Override
	public List<Content> searchContents(Definition key) {
		List<Content> contents = new ArrayList<>();
		if (definition == key) {
			contents.add(this);
		}
		return contents;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJson() {
		return "{" + super.toJson() + "," + getJson("closed", closed) + "," + getJson("values", values) + "}";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + super.toString() + ", closed=" + closed + ", values=" + values
				+ "]";
	}
}
