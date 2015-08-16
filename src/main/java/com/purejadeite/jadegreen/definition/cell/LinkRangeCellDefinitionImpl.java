package com.purejadeite.jadegreen.definition.cell;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.range.RangeDefinition;

/**
 * Rangeの構成要素となるCellリンク定義
 * @author mitsuhiroseino
 */
public class LinkRangeCellDefinitionImpl extends AbstractRangeCellDefinition implements LinkCellDefinition {

	private static final long serialVersionUID = -2532293550782673419L;

	/**
	 * 取得対象のRangeの定義ID
	 */
	private String mySheetKeyId;

	/**
	 * 自分側のキーとなる項目の定義ID
	 */
	private String myKeyId;

	/**
	 * 取得対象のRangeの定義ID
	 */
	private String sheetKeyId;

	/**
	 * 取得対象のキーとなる項目の定義ID
	 */
	private String keyId;

	/**
	 * 取得対象の値の定義ID
	 */
	private String valueId;

	/**
	 * Book読み込み定義
	 */
	private Definition book;

	/**
	 *
	 * @param book
	 * @param range
	 * @param id
	 * @param converters
	 * @param config
	 */
	public LinkRangeCellDefinitionImpl(Definition book, RangeDefinition range, String id, boolean stuff,
			List<Map<String, String>> converters,
			Map<String, String> config) {
		super(range, id, stuff, 0, 0, 0, 0, false, null, converters);
		this.book = book;
		this.mySheetKeyId = config.get("mySheetKeyId");
		this.myKeyId = config.get("myKeyId");
		this.sheetKeyId = config.get("sheetKeyId");
		this.keyId = config.get("keyId");
		this.valueId = config.get("valueId");
	}

	public static CellDefinition getInstance(Definition book, RangeDefinition range, String id, boolean stuff,
			List<Map<String, String>> converters,
			Map<String, String> config) {
		CellDefinition cell = new LinkRangeCellDefinitionImpl(book, range, id, stuff, converters, config);
		return cell;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMinRow() {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMaxRow() {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMinCol() {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMaxCol() {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isIncluded(int row, int col) {
		return false;
	}

	public String getSheetKeyId() {
		return sheetKeyId;
	}

	public String getKeyId() {
		return keyId;
	}

	public String getValueId() {
		return valueId;
	}

	/**
	 * {@inheritDoc}
	 */
	public Definition getMyKeyDefinition() {
		return book.get(myKeyId);
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
	public Definition getKeyDefinition() {
		return book.get(keyId);
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
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("mySheetKeyId", mySheetKeyId);
		map.put("myKeyId", myKeyId);
		map.put("sheetKeyId", sheetKeyId);
		map.put("keyId", keyId);
		map.put("valueId", valueId);
		return map;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + super.toString() + ", mySheetKeyId=" + mySheetKeyId
				+ ", myKey=" + myKeyId + ", sheetKeyId=" + sheetKeyId + ", key=" + keyId + ", value=" + valueId + "]";
	}

}
