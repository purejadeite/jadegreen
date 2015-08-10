package purejadeite.jadegreen.definition.cell;

import java.util.List;
import java.util.Map;

import purejadeite.jadegreen.definition.AbstractDefinition;
import purejadeite.jadegreen.definition.BookDefinitionImpl;
import purejadeite.jadegreen.definition.Definition;

/**
 * 単一セルのリンク定義です
 * @author mitsuhiroseino
 */
public abstract class AbstractLinkCellDefinition extends AbstractDefinition implements LinkCellDefinition {

	private static final long serialVersionUID = -7883930267830991162L;

	protected String mySheetKeyId;

	/**
	 * リンク先のキーになる定義ID
	 */
	protected String sheetKeyId;

	/**
	 * リンク先取得する値の定義ID
	 */
	protected String valueId;

	/**
	 * Book読み込み定義
	 */
	protected BookDefinitionImpl book;

	/**
	 * コンストラクタ
	 * @param book ブック読み込み定義
	 * @param parent シート読み込み定義
	 * @param id 定義ID
	 * @param converters コンバーター
	 * @param config リンク設定
	 */
	protected AbstractLinkCellDefinition(BookDefinitionImpl book, Definition parent, String id, boolean stuff, Map<String, String> config) {
		super(parent, id, stuff);
		this.book = book;
		this.mySheetKeyId = config.get("mySheetKeyId");
		this.sheetKeyId = config.get("sheetKeyId");
		this.valueId = config.get("valueId");
	}

	public String getSheetKeyId() {
		return sheetKeyId;
	}

	/**
	 * {@inheritDoc}
	 */
	public Definition getMySheetKeyDefinition() {
		return book.get(mySheetKeyId);
	}

	/**
	 * {@inheritDoc}
	 */
	public Definition getSheetKeyDefinition() {
		return book.get(sheetKeyId);
	}

	/**
	 * {@inheritDoc}
	 */
	public Definition getValueDefinition() {
		return book.get(valueId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJson() {
		return super.toJson() + "," + getJson("mySheetKeyId", mySheetKeyId) + ","
				+ getJson("sheetKeyId", sheetKeyId) + "," + getJson("valueId", valueId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return super.toString() + "mySheetKeyId=" + mySheetKeyId + ", sheetKeyId=" + sheetKeyId + ", valueId=" + valueId;
	}

	/**
	 * サポートされていないオペレーションです
	 */
	@Override
	public List<Definition> getChildren() {
		throw new UnsupportedOperationException();
	}

	/**
	 * サポートされていないオペレーションです
	 */
	@Override
	public void addChild(Definition child) {
		throw new UnsupportedOperationException();
	}

}
