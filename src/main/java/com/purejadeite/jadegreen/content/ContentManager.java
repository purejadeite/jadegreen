package com.purejadeite.jadegreen.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.DefinitionManager;
import com.purejadeite.jadegreen.definition.SheetDefinition;

/**
 * コンテンツを管理するクラスです
 *
 * @author mitsuhiroseino
 *
 */
public class ContentManager {

	private static ThreadLocal<ContentManager> tl = new ThreadLocal<>();

	/**
	 * BookContent
	 */
	private BookContent bookContent;

	/**
	 * ContentからWorksheetContentを取得するためのMap
	 */
	private Map<List<String>, SheetContent> keyConValSheet;

	/**
	 * DefinitionからContentのリストを取得するためのMap
	 */
	private Map<String, List<Content<?, ?>>> keyDefValConts;

	/**
	 * WorksheetContentからContentのリストを取得するためのMap
	 */
	private Map<List<String>, Map<String, Content<?, ?>>> keySheetValConts;

	/**
	 * DefinitionからSheetContentを取得するためのMap
	 */
	private Map<String, List<SheetContent>> keyDefValSheet;

	private ContentManager() {
		super();
	}

	/**
	 * Contentの初期化
	 */
	public void init() {
		keyConValSheet = new HashMap<>();
		keyDefValConts = new HashMap<>();
		keySheetValConts = new HashMap<>();
		keyDefValSheet = new HashMap<>();
	}

	public static ContentManager getInstance() {
		ContentManager cm = tl.get();
		if (cm == null) {
			cm = createInstance();
		}
		return cm;
	}

	public synchronized static ContentManager createInstance() {
		ContentManager cm = tl.get();
		if (cm == null) {
			cm = new ContentManager();
			tl.set(cm);
		}
		return cm;
	}

	public void register(BookContent bookContent) {
		this.bookContent = bookContent;
	}

	public void register(SheetContent sheetContent) {
		List<SheetContent> sheetSet = keyDefValSheet.get(sheetContent.getFullId());
		if (sheetSet == null) {
			sheetSet = new ArrayList<>();
			keyDefValSheet.put(sheetContent.getFullId(), sheetSet);
		}
		sheetSet.add(sheetContent);
	}

	/**
	 * SheetContentとContentの紐付けを登録します
	 * @param sheetContent シート
	 * @param content シート配下のコンテンツ
	 */
	public void register(SheetContent sheetContent, Content<?, ?> content) {
		String definitionKey = content.getDefinition().getKey();
		String definitionId = content.getDefinition().getId();

		// 1. Content-SheetContent
		keyConValSheet.put(content.getKey(), sheetContent);

		// 2. Definition-Contents
		List<Content<?, ?>> conList = keyDefValConts.get(definitionKey);
		if (conList == null) {
			conList = new ArrayList<>();
			keyDefValConts.put(definitionKey, conList);
		}
		conList.add(content);

		// 3. Sheet-Content
		Map<String, Content<?, ?>> conMap = keySheetValConts.get(sheetContent.getKey());
		if (conMap == null) {
			conMap = new HashMap<>();
			keySheetValConts.put(sheetContent.getKey(), conMap);
		}
		conMap.put(definitionId, content);

		if (content instanceof ParentContent) {
			// tableの場合は配下のcellを個別に登録
			for (Content<?, ?> cellContent : ((ParentContent<?, ?, ?>) content).getChildren()) {
				register(sheetContent, cellContent);
			}
		}
	}

	public BookContent getBook() {
		return bookContent;
	}

	/**
	 * Contentの属するシートを取得します
	 * @param keyContent
	 * @return
	 */
	public SheetContent getSheet(Content<?, ?> keyContent) {
		return keyConValSheet.get(keyContent.getKey());
	}

	/**
	 * contentの属するシートを対象に、definitionの示すcontentを取得します
	 * @param keyContent
	 * @param targetDefinition
	 * @return
	 */
	public Content<?, ?> getSameSheetContent(Content<?, ?> keyContent, Definition<?> targetDefinition) {
		// 指定のコンテンツの属するシート
		SheetContent sheetContent = this.keyConValSheet.get(keyContent.getKey());
		Map<String, Content<?, ?>> contents = this.keySheetValConts.get(sheetContent.getKey());
		return contents.get(targetDefinition.getId());
	}

	/**
	 * 全シートを対象に、definitionの示すcontentのリストを取得します
	 * @param targetDefinition
	 * @return
	 */
	public List<Content<?, ?>> getContents(Definition<?> targetDefinition) {
		return getContents(targetDefinition, null);
	}

	/**
	 * 全シートを対象に、definitionの示すcontentのリストからignoreContentを除いて取得します
	 * @param targetDefinition
	 * @param ignoreContent
	 * @return
	 */
	public List<Content<?, ?>> getContents(Definition<?> targetDefinition, Content<?, ?> ignoreContent) {
		List<Content<?, ?>> results = new ArrayList<>();
		List<Content<?, ?>> contents = keyDefValConts.get(targetDefinition.getKey());
		if (CollectionUtils.isEmpty(contents)) {
			return results;
		}
		for (Content<?, ?> content : contents) {
			if (content != ignoreContent) {
				results.add(content);
			}
		}
		return results;
	}

	/**
	 * 全シートを対象に、keyContentのdefinitionおよび値と一致するContentを取得します
	 * @param keyContent
	 * @return
	 */
	public List<Content<?, ?>> getSameValueContent(Content<?, ?> keyContent) {
		return getSameValueContent(keyContent, keyContent.getDefinition());
	}

	/**
	 * 全シートを対象に、keyContentの値とtargetDefinitionに一致するContentを取得します
	 * @param keyContent
	 * @param targetDefinition
	 * @return
	 */
	public List<Content<?, ?>> getSameValueContent(Content<?, ?> keyContent, Definition<?> targetDefinition) {
		// keyを除いたキーになるContentを取得
		List<Content<?, ?>> contents = getContents(targetDefinition, keyContent);
		List<Content<?, ?>> sameValueContent = new ArrayList<>();
		for (Content<?, ?> content : contents) {
			Object keyValues = keyContent.getValues();
			Object targetValues = content.getValues();
			if (keyValues == targetValues || (keyValues != null && keyValues.equals(targetValues))) {
				sameValueContent.add(content);
			}
		}
		return sameValueContent;
	}

	/**
	 * 指定のDefinitionに関連するシートコンテンツを全て取得します。
	 * @param definition
	 * @return
	 */
	public List<SheetContent> getSheets(Definition<?> definition) {
		SheetDefinition sheetDef = null;
		if (definition instanceof SheetDefinition) {
			sheetDef = (SheetDefinition) definition;
		} else {
			sheetDef = DefinitionManager.getInstance().getSheet(definition);
		}
		return keyDefValSheet.get(sheetDef.getFullId());
	}

	/**
	 * contentの属するシートのkeyDefinitionで示されたcontentの値と、
	 * targetDefinitionで示されたcontentの値が等しいシートを取得します
	 * @param content
	 * @param keyDefinition
	 * @param targetDefinition
	 * @return
	 */
	public List<SheetContent> getSheets(Content<?, ?> content, Definition<?> keyDefinition, Definition<?> targetDefinition) {
		Content<?, ?> keyContent = getSameSheetContent(content, keyDefinition);
		return getSheets(keyContent, targetDefinition);
	}

	/**
	 * contentの値とdefinitionがkeyContentと同じシートを取得します
	 * @param keyContent
	 * @return
	 */
	public List<SheetContent> getSheets(Content<?, ?> keyContent) {
		return getSheets(keyContent, keyContent.getDefinition());
	}

	/**
	 * targetDefinitionで示されるcontentの値がkeyContentと同じシートを取得します
	 * @param keyContent
	 * @param targetDefinition
	 * @return
	 */
	public List<SheetContent> getSheets(Content<?, ?> keyContent, Definition<?> targetDefinition) {
		Set<SheetContent> sheetContents = new HashSet<>();
		List<Content<?, ?>> contents = getSameValueContent(keyContent, targetDefinition);
		for (Content<?, ?> content : contents) {
			sheetContents.add(getSheet(content));
		}
		return new ArrayList<>(sheetContents);
	}

	/**
	 * sheetContent配下のtargetDefinitionで示されるcontentを取得します
	 * @param sheetContent
	 * @param targetDefinition
	 * @return
	 */
	public Content<?, ?> getContent(SheetContent sheetContent, Definition<?> targetDefinition) {
		return getContent(sheetContent, targetDefinition.getId());
	}

	/**
	 * sheetContent配下のtargetDefinitionで示されるcontentを取得します
	 * @param sheetContent
	 * @param targetDefinition
	 * @return
	 */
	public Content<?, ?> getContent(SheetContent sheetContent, String id) {
		Map<String, Content<?, ?>> map = keySheetValConts.get(sheetContent.getKey());
		if (MapUtils.isEmpty(map)) {
			return null;
		}
		return map.get(id);
	}

	/**
	 * 親contentを取得します
	 * @param content
	 * @return
	 */
	public Content<?, ?> getParentContent(Content<?, ?> content) {
		return getContent(getSheet(content), content.getDefinition().getParent());
	}

}
