package com.purejadeite.jadegreen.content;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.DefinitionInterface;
import com.purejadeite.util.collection.Table;

/**
 * Excelファイルから取得した値
 * @author mitsuhiroseino
 */
public interface ContentInterface<P extends ContentInterface<?, ?>, D extends DefinitionInterface<?>> {

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
	 * 項目名を取得します
	 * @return 項目名
	 */
	public String getName();

	/**
	 * 定義を取得します
	 * @return 定義
	 */
	public D getDefinition();

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
	 * 自身の属するブックコンテンツを取得します
	 * @return ブックコンテンツ
	 */
	public BookContent getBook();

	/**
	 * 自身の属するシートコンテンツを取得します
	 * @return シートコンテンツ
	 */
	public SheetContent getSheet();

	/**
	 * 指定のシート定義のシートコンテンツを全て取得します
	 * @param sheetDefinition シートの定義
	 * @return シートコンテンツのリスト
	 */
	public List<SheetContent> getSheets(DefinitionInterface<?> sheetDefinition);

	/**
	 * myKeyDefinitionの示すコンテンツと等しい値を持つ、keyDefinitionの示すコンテンツのシートを取得します
	 * @param myKeyContent キーとなるコンテンツの定義
	 * @param keyDefinition 比較対象のコンテンツの定義
	 * @return キーの一致するシートのリスト
	 */
	public List<SheetContent> getSheets(DefinitionInterface<?> myKeyDefinition, DefinitionInterface<?> keyDefinition);

	/**
	 * myKeyContentと等しい値を持つ、keyDefinitionの示すコンテンツのシートを取得します
	 * @param myKeyContent キーとなる値を持ったコンテンツ
	 * @param keyDefinition 比較対象のコンテンツの定義
	 * @return キーの一致するシートのリスト
	 */
	public List<SheetContent> getSheets(ContentInterface<?, ?> myKeyContent, DefinitionInterface<?> keyDefinition);

	/**
	 * 自身の属するテーブルコンテンツを取得します
	 * @return テーブルコンテンツ
	 */
	public TableContentInterface getTable();

	/**
	 * 自身の属するカテゴリコンテンツを取得します
	 * @return カテゴリコンテンツ
	 */
	public CategoryContentInterface getCategory();

	/**
	 * 自身の配下にある指定のセル定義のセルコンテンツを取得します
	 * @param cellDefinition セル定義
	 * @return セルコンテンツ
	 */
	public ContentInterface<?, ?> getCell(DefinitionInterface<?> cellDefinition);

	/**
	 * 自身の配下にある指定のIDのセルコンテンツを取得します
	 * @param id 定義ID
	 * @return セルコンテンツ
	 */
	public ContentInterface<?, ?> getCell(String id);

	/**
	 * 任意のコンテンツを取得します
	 * @param definition 定義
	 * @return 指定の定義のコンテンツリスト
	 */
	public List<ContentInterface<?, ?>> getContents(DefinitionInterface<?> definition);

	/**
	 * 親を取得します
	 * @return 親コンテンツ
	 */
	public P getParent();

	public int capture(Table<String> table);

}
