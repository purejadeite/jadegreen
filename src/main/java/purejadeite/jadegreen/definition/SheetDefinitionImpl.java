package purejadeite.jadegreen.definition;

import purejadeite.jadegreen.definition.cell.CellDefinition;
import purejadeite.jadegreen.definition.range.RangeDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * Sheet読み込み定義
 * @author mitsuhiroseino
 *
 */
public class SheetDefinitionImpl extends AbstractDefinition {

	/**
	 * 対象シート条件・シート名
	 */
	private String name;

	/**
	 * Cell読み込み定義
	 */
	private List<Definition> cells = new ArrayList<>();

	/**
	 * 定義上の最少行番号
	 */
	private int minRow = Integer.MAX_VALUE;

	/**
	 * 定義上の最大行番号
	 */
	private int maxRow = 0;

	/**
	 * 定義上の最少列番号
	 */
	private int minCol = Integer.MAX_VALUE;

	/**
	 * 定義上の最大列番号
	 */
	private int maxCol = 0;

	/**
	 * コンストラクタ
	 * @param parent Book読み込み定義
	 * @param id 定義ID
	 * @param name シート名
	 * @param stuff 出力要否
	 */
	public SheetDefinitionImpl(Definition parent, String id, String name, boolean stuff) {
		super(parent, id, stuff);
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addChild(Definition child) {
		this.cells.add(child);
		this.addCell(child);
	}

	/**
	 * Cell読み込み定義を追加します
	 * @param child Cell読み込み定義
	 */
	private void addCell(Definition child) {
		if (child != null) {
			if (child instanceof CellDefinition) {
				// 単独Cellの場合
				CellDefinition cell = (CellDefinition) child;
				int i;
				// 最少行番号
				i = cell.getMinRow();
				if (i < minRow && i != 0) {
					minRow = i;
				}
				if (maxRow < i && i != Integer.MAX_VALUE) {
					maxRow = i;
				}
				// 最大行番号
				i = cell.getMaxRow();
				if (i < minRow && i != 0) {
					minRow = i;
				}
				if (maxRow < i && i != Integer.MAX_VALUE) {
					maxRow = i;
				}
				// 最少列番号
				i = cell.getMinCol();
				if (i < minCol && i != 0) {
					minCol = i;
				}
				if (maxCol < i && i != Integer.MAX_VALUE) {
					maxCol = i;
				}
				// 最大列番号
				i = cell.getMaxCol();
				if (i < minCol && i != 0) {
					minCol = i;
				}
				if (maxCol < i && i != Integer.MAX_VALUE) {
					maxCol = i;
				}
			} else if (child instanceof RangeDefinition) {
				// Rangeの場合は子要素のCellをばらして追加
				for (Definition c : child.getChildren()) {
					addCell(c);
				}
			}
		}
	}

	/**
	 * 定義上の最少行番号を取得します
	 * @return 定義上の最少行番号
	 */
	public int getMinRow() {
		return minRow;
	}

	/**
	 * 定義上の最大行番号を取得します
	 * @return 定義上の最大行番号
	 */
	public int getMaxRow() {
		return maxRow;
	}

	/**
	 * 定義上の最少列番号を取得します
	 * @return 定義上の最少列番号
	 */
	public int getMinCol() {
		return minCol;
	}

	/**
	 * 定義上の最大列番号を取得します
	 * @return 定義上の最大列番号
	 */
	public int getMaxCol() {
		return maxCol;
	}

	/**
	 * シート名を取得します
	 * @return シート名
	 */
	public String getName() {
		return name;
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
	public String toJson() {
		return "{" + super.toJson() + "," + getJson("name", name) + ","
				+ getJson("minRow", minRow) + "," + getJson("maxRow", maxRow) + "," + getJson("minCol", minCol) + ","
				+ getJson("maxCol", maxCol) + "," + getJson("cells", cells) + "}";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + super.toString() + ", name=" + name + ", minRow=" + minRow
				+ ", maxRow=" + maxRow + ", minCol=" + minCol + ", maxCol=" + maxCol + ", cells=" + cells + "]";
	}

}