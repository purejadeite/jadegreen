package purejadeite.jadegreen.definition;

import java.util.ArrayList;
import java.util.List;

/**
 * Bookの読み込み定義です
 * @author mitsuhiroseino
 *
 */
public class BookDefinitionImpl extends AbstractDefinition {

	/**
	 * シート定義
	 */
	private List<SheetDefinitionImpl> sheets = new ArrayList<>();

	/**
	 * デフォルトコンストラクタ
	 */
	public BookDefinitionImpl() {
		super();
	}

	/**
	 * シートを取得します
	 * @return
	 */
	public List<SheetDefinitionImpl> getSheets() {
		return sheets;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addChild(Definition child) {
		this.sheets.add((SheetDefinitionImpl) child);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getId() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getFullId() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Definition getParent() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJson() {
		return "" + getJson(null, sheets);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + super.toString() + ", sheets=" + sheets + "]";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<? extends Definition> getChildren() {
		return this.sheets;
	}

}
