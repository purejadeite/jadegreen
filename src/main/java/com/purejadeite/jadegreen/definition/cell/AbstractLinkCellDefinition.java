package com.purejadeite.jadegreen.definition.cell;

import java.util.Map;

import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.LinkDefinition;
import com.purejadeite.jadegreen.definition.Options;
import com.purejadeite.jadegreen.definition.ParentDefinition;
import com.purejadeite.jadegreen.definition.WorkbookDefinitionImpl;

/**
 * 単一セルのリンク定義です
 * @author mitsuhiroseino
 */
abstract public class AbstractLinkCellDefinition<P extends ParentDefinition<?, ?>> extends AbstractCellDefinition<P> implements LinkCellDefinition<P>, LinkDefinition<P> {

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = {"mySheetKeyId", "sheetKeyId", "valueId"};

	/**
	 * 自身のシートのキーになる項目のID
	 */
	protected String mySheetKeyId;

	/**
	 * リンク先のキーになる項目のID
	 */
	protected String sheetKeyId;

	/**
	 * リンク先の値を取得する対象の項目のID
	 */
	protected String valueId;

	/**
	 * Book読み込み定義
	 */
	protected WorkbookDefinitionImpl book;

	/**
	 * コンストラクタ
	 * @param book ブック読み込み定義
	 * @param parent シート読み込み定義
	 * @param id 定義ID
	 * @param options オプション
	 * @param config リンク設定
	 */
	protected AbstractLinkCellDefinition(WorkbookDefinitionImpl book, P parent, String id, boolean noOutput, Map<String, String> config) {
		super(parent, id, noOutput, null);
		this.validateConfig(config, CONFIG);
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
	public ParentDefinition<?, ?> getMySheetKeyDefinition() {
		Definition<?> sheet = book.get(mySheetKeyId);
		if (sheet instanceof ParentDefinition) {
			return (ParentDefinition<?, ?>) sheet;
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public ParentDefinition<?, ?> getSheetKeyDefinition() {
		Definition<?> sheet = book.get(sheetKeyId);
		if (sheet instanceof ParentDefinition) {
			return (ParentDefinition<?, ?>) sheet;
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Definition<?> getValueDefinition() {
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
