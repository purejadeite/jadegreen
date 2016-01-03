package com.purejadeite.jadegreen.definition.cell;

import java.util.Map;

import com.purejadeite.jadegreen.definition.MappingDefinition;
import com.purejadeite.jadegreen.definition.JoinDefinition;
import com.purejadeite.jadegreen.definition.Options;
import com.purejadeite.jadegreen.definition.ParentMappingDefinition;
import com.purejadeite.jadegreen.definition.WorkbookDefinition;

/**
 * 単一セルの結合定義です
 * @author mitsuhiroseino
 */
abstract public class AbstractLinkCellDefinition<P extends ParentMappingDefinition<?, ?>> extends AbstractCellDefinition<P> implements JoinedCellDefinition<P>, JoinDefinition<P> {

	private static final long serialVersionUID = 8219059199809135196L;

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = {"mySheetKeyId", "sheetKeyId", "valueId"};

	/**
	 * 自身のシートのキーになる項目のID
	 */
	protected String mySheetKeyId;

	/**
	 * 結合先のキーになる項目のID
	 */
	protected String sheetKeyId;

	/**
	 * 結合先の値を取得する対象の項目のID
	 */
	protected String valueId;

	/**
	 * Book読み込み定義
	 */
	protected WorkbookDefinition book;

	/**
	 * コンストラクタ
	 * @param book ブック読み込み定義
	 * @param parent シート読み込み定義
	 * @param id 定義ID
	 * @param options オプション
	 * @param config 結合設定
	 */
	protected AbstractLinkCellDefinition(WorkbookDefinition book, P parent, String id, boolean noOutput, Map<String, String> config) {
		super(parent, id, noOutput, null);
		this.validateConfig(config, CONFIG);
		this.book = book;
		this.mySheetKeyId = config.get("mySheetKeyId");
		this.sheetKeyId = config.get("sheetKeyId");
		this.valueId = config.get("valueId");
	}

	public String getSheetId() {
		return sheetKeyId;
	}

	/**
	 * {@inheritDoc}
	 */
	public ParentMappingDefinition<?, ?> getMyKeyDefinition() {
		MappingDefinition<?> sheet = book.get(mySheetKeyId);
		if (sheet instanceof ParentMappingDefinition) {
			return (ParentMappingDefinition<?, ?>) sheet;
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public ParentMappingDefinition<?, ?> getKeyDefinition() {
		MappingDefinition<?> sheet = book.get(sheetKeyId);
		if (sheet instanceof ParentMappingDefinition) {
			return (ParentMappingDefinition<?, ?>) sheet;
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public MappingDefinition<?> getValueDefinition() {
		return book.get(valueId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("mySheetKeyId", mySheetKeyId);
		map.put("sheetKeyId", sheetKeyId);
		map.put("valueId", valueId);
		map.put("book", book.getFullId());
		return map;
	}

	public Object apply(Object value) {
		return value;
	}

	@Override
	public int getMinRow() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public int getMaxRow() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public int getMaxCol() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public int getMinCol() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public boolean isIncluded(int row, int col) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public Options getOptions() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
