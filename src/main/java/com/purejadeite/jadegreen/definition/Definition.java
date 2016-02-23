package com.purejadeite.jadegreen.definition;

import java.util.Map;

import com.purejadeite.jadegreen.content.Content;
import com.purejadeite.jadegreen.definition.table.CategoryDefinition;
import com.purejadeite.jadegreen.definition.table.TableDefinition;
import com.purejadeite.jadegreen.option.Options;

/**
 *
 * Excelファイル読み込みの定義情報インターフェイス
 *
 * @author mitsuhiroseino
 *
 */
public interface Definition<P extends ParentDefinition<?, ?>> {

	/**
	 * 定義ID
	 */
	public static final String CFG_ID = "id";

	/**
	 * オプション
	 */
	public static final String[] CFG_OPTIONS = {"options", "option"};

	/**
	 * 定義IDを取得します
	 *
	 * @return
	 */
	public String getId();

	/**
	 * ルート辿った定義IDを取得します
	 *
	 * @return
	 */
	public String getFullId();

	/**
	 * 親定義を取得します
	 *
	 * @return
	 */
	public P getParent();

	/**
	 * コンバーターなどを取得します
	 * @return コンバーターなどのオプション
	 */
	public Options getOptions();

	/**
	 * 値にオプションを適用します
	 * @return
	 */
	public Object applyOptions(Object values, Content<?, ?> content);

	/**
	 * 定義をMap形式で取得します
	 * @return 定義
	 */
	public Map<String,Object> toMap();

	/**
	 * 自身の属するブック定義を取得します。
	 * @return ブック定義
	 */
	public BookDefinition getBook();

	/**
	 * 自身の属するシート定義を取得します。
	 * @return シート定義
	 */
	public SheetDefinition getSheet();

	/**
	 * 指定のIDのシート定義を出力します
	 * @param sheetId シートのID
	 * @return シート定義
	 */
	public SheetDefinition getSheet(String sheetId);

	/**
	 * 出力対象のシート定義を取得します。
	 * @return 出力対象のシート定義
	 */
	public SheetDefinition getOutputSheet();

	/**
	 * 自身の属するテーブル定義を取得します。
	 * @return テーブル定義
	 */
	public TableDefinition<?> getTable();

	/**
	 * 自身の属するカテゴリ定義を取得します。
	 * @return カテゴリ定義
	 */
	public CategoryDefinition<?> getCategory();

	/**
	 * 同じシート内のセル定義を取得します。
	 * @param cellId セルのID
	 * @return 同一シート配下のセル定義
	 */
	public Definition<?> getCell(String cellId);

	/**
	 * 指定のシート内のセル定義を取得します。
	 * @param sheetId シートのID
	 * @param cellId セルのID
	 * @return 指定シート配下のセル定義
	 */
	public Definition<?> getCell(String sheetId, String cellId);
}
