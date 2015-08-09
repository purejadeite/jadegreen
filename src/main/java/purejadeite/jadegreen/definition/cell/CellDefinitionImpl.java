package purejadeite.jadegreen.definition.cell;

import java.util.List;
import java.util.Map;

import purejadeite.jadegreen.definition.Definition;

/**
 * 単一セルの読み込み定義です
 * @author mitsuhiroseino
 */
public class CellDefinitionImpl extends AbstractCellDefinition {

	/**
	 * 取得対象列
	 */
	protected int row = 0;

	/**
	 * 取得対象行
	 */
	protected int col = 0;

	/**
	 * コンストラクタ
	 * @param parent シート読み込み定義
	 * @param id 定義ID
	 * @param row 取得対象行
	 * @param col 取得対象列
	 * @param converters コンバーター
	 */
	private CellDefinitionImpl(Definition parent, String id, boolean stuff, int row, int col, List<Map<String, String>> converters) {
		super(parent, id, stuff, converters);
		this.row = row;
		this.col = col;
	}

	/**
	 * インスタンスを取得します
	 * @param parent シート読み込み定義
	 * @param id 定義ID
	 * @param row 取得対象行
	 * @param col 取得対象列
	 * @param converters コンバーター
	 * @return ラップされたCell読み込み定義
	 */
	public static CellDefinition getInstance(Definition parent, String id, boolean stuff, int row, int col,
			List<Map<String, String>> converters) {
		CellDefinition cell = new CellDefinitionImpl(parent, id, stuff, row, col, converters);
		return cell;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMinRow() {
		return row;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMaxRow() {
		return row;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMinCol() {
		return col;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMaxCol() {
		return col;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isIncluded(int row, int col) {
		if (this.row == row && this.col == col) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJson() {
		return "{" + super.toJson() + "," + getJson("row", row) + "," + getJson("col", col) + "}";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + super.toString() + ", row=" + row + ", col=" + col + "]";
	}

}
