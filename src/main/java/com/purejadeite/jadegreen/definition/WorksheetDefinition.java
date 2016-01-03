package com.purejadeite.jadegreen.definition;

import static com.purejadeite.jadegreen.definition.DefinitionKeys.*;

import java.util.HashMap;
import java.util.Map;

import com.purejadeite.jadegreen.definition.cell.CellDefinition;
import com.purejadeite.util.RoughlyMapUtils;

/**
 * Sheet読み込み定義
 *
 * @author mitsuhiroseino
 *
 */
public class WorksheetDefinition extends AbstractParentMappingDefinition<WorkbookDefinition, MappingDefinition<?>> {

	private static final long serialVersionUID = -1303967958765873003L;

	/**
	 * コンフィグ：シート名
	 */
	private static final String CFG_NAME = "name";

	/**
	 * コンフィグ：join・相手先シートID
	 */
	private static final String CFG_JOIN_SHEET_ID = "join.sheetId";

	/**
	 * コンフィグ：join・相手先キーID
	 */
	private static final String CFG_JOIN_KEY_ID = "join.keyId";

	/**
	 * コンフィグ：join・自身のキーID
	 */
	private static final String CFG_JOIN_MY_KEY_ID = "join.myKeyId";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_NAME };

	/**
	 * 対象シート条件・シート名
	 */
	private String name;

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
	 * 結合先のシートのID
	 */
	protected String joinSheetId;

	/**
	 * 結合先のキーになる項目のID
	 */
	protected String joinKeyId;

	/**
	 * 自身のシートのキーになる項目のID
	 */
	protected String joinMyKeyId;

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            Book読み込み定義
	 * @param config
	 *            コンフィグ
	 */
	public WorksheetDefinition(WorkbookDefinition parent, Map<String, Object> config) {
		super(parent, config);
		this.validateConfig(config, CONFIG);
		this.name = RoughlyMapUtils.getString(config, CFG_NAME);
		this.joinSheetId = RoughlyMapUtils.getString(config, CFG_JOIN_SHEET_ID, null);
		this.joinKeyId = RoughlyMapUtils.getString(config, CFG_JOIN_KEY_ID, null);
		this.joinMyKeyId = RoughlyMapUtils.getString(config, CFG_JOIN_MY_KEY_ID, null);
		this.definitions = new HashMap<>();
	}

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            Book読み込み定義
	 * @param id
	 *
	 *            定義ID
	 * @param name
	 *            シート名
	 * @param noOutput
	 *            出力要否
	 */
	public WorksheetDefinition(WorkbookDefinition parent, String id, String name, boolean noOutput, Map<String, Object> config) {
		super(parent, id, noOutput);
		this.name = name;
		this.joinSheetId = RoughlyMapUtils.getString(config, CFG_JOIN_SHEET_ID, null);
		this.joinKeyId = RoughlyMapUtils.getString(config, CFG_JOIN_KEY_ID, null);
		this.joinMyKeyId = RoughlyMapUtils.getString(config, CFG_JOIN_MY_KEY_ID, null);
		this.definitions = new HashMap<>();
	}

	/**
	 * インスタンスを取得します
	 * @param parent 親定義
	 * @param config コンフィグ
	 * @return インスタンス
	 */
	public static WorksheetDefinition newInstance(WorkbookDefinition parent, Map<String, Object> config) {
		String id = RoughlyMapUtils.getString(config, ID);
		String name = RoughlyMapUtils.getString(config, NAME);
		boolean noOutput = RoughlyMapUtils.getBooleanValue(config, NO_OUTPUT);
		return new WorksheetDefinition(parent, id, name, noOutput, config);
	}

	public String getJoinSheetId() {
		return joinSheetId;
	}

	public String getJoinKeyId() {
		return joinKeyId;
	}

	public String getJoinMyKeyId() {
		return joinMyKeyId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addChild(MappingDefinition<?> child) {
		super.addChild(child);
		this.addCell(child);
	}

	/**
	 * Cell読み込み定義を追加します
	 *
	 * @param child
	 *            Cell読み込み定義
	 */
	private void addCell(MappingDefinition<?> child) {
		if (child != null) {
			definitions.put(child.getId(), child);
			child.setDefinitions(definitions);
			if (child instanceof CellDefinition) {
				// 単独Cellの場合
				CellDefinition<?> cell = (CellDefinition<?>) child;
				cell.setSheet(this);
				int i;
				// 最少行番号
				i = cell.getMinRow();
				if (i != CellDefinition.NO_ADDRESS) {
					minRow = Math.min(i, minRow);
					maxRow = Math.max(i, maxRow);
				}
				// 最大行番号
				i = cell.getMaxRow();
				if (i != CellDefinition.NO_ADDRESS) {
					minRow = Math.min(i, minRow);
					maxRow = Math.max(i, maxRow);
				}
				// 最少列番号
				i = cell.getMinCol();
				if (i != CellDefinition.NO_ADDRESS) {
					minCol = Math.min(i, minCol);
					maxCol = Math.max(i, maxCol);
				}
				// 最大列番号
				i = cell.getMaxCol();
				if (i != CellDefinition.NO_ADDRESS) {
					minCol = Math.min(i, minCol);
					maxCol = Math.max(i, maxCol);
				}
			} else if (child instanceof ParentMappingDefinition) {
				// Tableの場合は子要素のCellをばらして追加
				ParentMappingDefinition<?, ?> pmd = (ParentMappingDefinition<?, ?>) child;
				for (MappingDefinition<?> c : pmd.getChildren()) {
					addCell(c);
				}
			}
		}
	}

	/**
	 * 定義上の最少行番号を取得します
	 *
	 * @return 定義上の最少行番号
	 */
	public int getMinRow() {
		return minRow;
	}

	/**
	 * 定義上の最大行番号を取得します
	 *
	 * @return 定義上の最大行番号
	 */
	public int getMaxRow() {
		return maxRow;
	}

	/**
	 * 定義上の最少列番号を取得します
	 *
	 * @return 定義上の最少列番号
	 */
	public int getMinCol() {
		return minCol;
	}

	/**
	 * 定義上の最大列番号を取得します
	 *
	 * @return 定義上の最大列番号
	 */
	public int getMaxCol() {
		return maxCol;
	}

	/**
	 * シート名を取得します
	 *
	 * @return シート名
	 */
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("name", name);
		map.put("minRow", minRow);
		map.put("maxRow", maxRow);
		map.put("minCol", minCol);
		map.put("maxCol", maxCol);
		return map;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object apply(Object value) {
		return value;
	}

	public MappingDefinition<?> getDefinition(String id) {
		return definitions.get(id);
	}
}
