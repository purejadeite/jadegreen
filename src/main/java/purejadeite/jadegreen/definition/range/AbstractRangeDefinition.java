package purejadeite.jadegreen.definition.range;

import purejadeite.jadegreen.definition.AbstractDefinition;
import purejadeite.jadegreen.definition.Definition;
import purejadeite.jadegreen.definition.cell.CellDefinition;
import purejadeite.jadegreen.definition.converter.range.RangeConverter;
import purejadeite.jadegreen.definition.converter.range.RangeConverterFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * テーブル形式の範囲の情報を保持するクラスの抽象クラスです
 * @author mitsuhiroseino
 */
public abstract class AbstractRangeDefinition extends AbstractDefinition implements RangeDefinition {

	private static final long serialVersionUID = -3017051211378561813L;

	/**
	 * 開始位置
	 */
	protected int begin = 0;

	/**
	 * 終了位置
	 */
	protected int end = 0;

	/**
	 * 配下のセル読み込み情報
	 */
	protected List<CellDefinition> cells = new ArrayList<>();

	/**
	 * コンバーター
	 */
	protected RangeConverter converter = null;

	/**
	 * 開始キー項目
	 * ※現在未使用
	 */
	protected String beginKeyId = null;

	/**
	 * 開始キー値
	 * ※現在未使用
	 */
	protected String beginValue = null;

	/**
	 * 終了キー項目
	 */
	protected String endKeyId = null;

	/**
	 * 終了キー値
	 */
	protected String endValue = null;

	/**
	 * レコード数
	 */
	protected int size = 0;

	/**
	 * コンストラクタ
	 * @param parent 親の読み込み情報
	 * @param id 定義ID
	 * @param stuff データの読み込みのみ行うか
	 * @param begin 開始位置
	 * @param end 終了位置
	 * @param endKey 開始キー項目
	 * @param endValue 終了キー値
	 * @param converters コンバーター
	 */
	protected AbstractRangeDefinition(Definition parent, String id, boolean stuff, int begin,
			int end, String endKeyId, String endValue, List<Map<String, String>> converters) {
		super(parent, id, stuff);
		this.begin = begin;
		this.end = end <= 0 ? Integer.MAX_VALUE : end;
		this.endKeyId = endKeyId;
		this.endValue = endValue;
		this.converter = RangeConverterFactory.build(converters);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RangeConverter getConverter() {
		return converter;
	}

	@Override
	public Object convert(Object value) {
		if (converter == null) {
			return value;
		} else {
			return converter.convert(value);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(CellDefinition cell) {
		cells.add(cell);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getEndKeyId() {
		return endKeyId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setEndKeyId(String endKeyId) {
		this.endKeyId = endKeyId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getEndValue() {
		return endValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setEndValue(String endValue) {
		this.endValue = endValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getBegin() {
		return begin;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getEnd() {
		return end;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<? extends Definition> getChildren() {
		return this.cells;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addChild(Definition child) {
		cells.add((CellDefinition) child);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addChildren(List<Definition> children) {
		for (Definition child : children) {
			addChild(child);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJson() {
		return "{" + getJson("id", id) + "," + getJson("stuff", stuff) + "," + getJson("begin", begin) + ","
				+ getJson("end", end) + "," + getJson("beginKey", beginKeyId) + "," + getJson("beginValue", beginValue)
				+ "," + getJson("endKeyId", endKeyId) + "," + getJson("endValue", endValue) + ","
				+ getJson("size", size) + "," + getJson("parent", parent.getFullId()) + "," + getJson("cells", cells)
				+ "}";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "id=" + id + ", stuff=" + stuff + ", begin=" + begin + ", end=" + end + ", beginKey=" + beginKeyId
				+ ", beginValue=" + beginValue + ", endKeyId=" + endKeyId + ", endValue=" + endValue + ", size=" + size
				+ ", parent=" + parent.getFullId() + ", cells=" + cells;
	}
}
