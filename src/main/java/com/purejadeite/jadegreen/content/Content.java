package com.purejadeite.jadegreen.content;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.Definition;

/**
 * Excelファイルから取得した値
 * @author mitsuhiroseino
 */
public interface Content<P extends Content<?, ?>, D extends Definition<?>> {

	public List<String> getKey();

	public String getUuid();

	/**
	 * 定義IDを取得します
	 * @return 定義ID
	 */
	public String getId();

	/**
	 * 完全な定義IDを取得します
	 * @return 完全な定義ID
	 */
	public String getFullId();

	/**
	 * 定義を取得します
	 * @return 定義
	 */
	public D getDefinition();

	/**
	 * 取得可能な状態か判定します
	 * @return true:取得可能, false:取得不可
	 */
	public boolean isClosed();

	/**
	 * 取得が完了しているか判定します
	 * @return true:取得完了, false:未取得
	 */
	public boolean isAquired();

	/**
	 * 値の取得を終了します
	 */
	public void close();

	/**
	 * 値の取得を開始します
	 */
	public void open();

	/**
	 * 値を追加します
	 * @param row 行番号
	 * @param col 列番号
	 * @param value 値
	 * @return 取得状況
	 */
	public Status addValue(int row, int col, Object value);

	/**
	 * 編集していない値を取得します
	 * @return 値
	 */
	public Object getRawValues();

	/**
	 * 編集した値を取得します
	 * @return 値
	 */
	public Object getValues();

	/**
	 * コンテンツをMap形式で取得します
	 * @return コンテンツ
	 */
	public Map<String,Object> toMap();

	/**
	 * ブックコンテンツを取得します
	 * @return ブックコンテンツ
	 */
	public BookContent getBook();

	/**
	 * シートコンテンツを取得します
	 * @return シートコンテンツ
	 */
	public SheetContent getSheet();

	/**
	 * keyDefinitionのシートを取得します
	 * @param sheetDefinition シートの定義
	 * @return シートのリスト
	 */
	public List<SheetContent> getSheets(Definition<?> sheetDefinition);

	/**
	 * myKeyDefinitionの示すコンテンツと等しい値を持つ、keyDefinitionの示すコンテンツのシートを取得します
	 * @param myKeyContent キーとなるコンテンツの定義
	 * @param keyDefinition 比較対象のコンテンツの定義
	 * @return キーの一致するシートのリスト
	 */
	public List<SheetContent> getSheets(Definition<?> myKeyDefinition, Definition<?> keyDefinition);

	/**
	 * myKeyContentと等しい値を持つ、keyDefinitionの示すコンテンツのシートを取得します
	 * @param myKeyContent キーとなる値を持ったコンテンツ
	 * @param keyDefinition 比較対象のコンテンツの定義
	 * @return キーの一致するシートのリスト
	 */
	public List<SheetContent> getSheets(Content<?, ?> myKeyContent, Definition<?> keyDefinition);

	/**
	 * セルコンテンツを取得します
	 * @param cellDefinition セル定義
	 * @return セルコンテンツ
	 */
	public Content<?, ?> getCell(Definition<?> cellDefinition);

	/**
	 * セルコンテンツを取得します
	 * @param id 定義ID
	 * @return セルコンテンツ
	 */
	public Content<?, ?> getCell(String id);

	/**
	 * 任意のコンテンツを取得します
	 * @param definition 定義
	 * @return 指定の定義のコンテンツリスト
	 */
	public List<Content<?, ?>> getContents(Definition<?> definition);

	/**
	 * 親を取得します
	 * @return 親コンテンツ
	 */
	public P getParent();

}
