package purejadeite.core;


public abstract class AbstractToJson implements ToJson {

	/**
	 * JSON形式のnameとvalueの表現を取得します
	 * @param name プロパティ名
	 * @param value 値
	 * @return "name": "value"形式の文字列
	 */
	protected String getJson(String name, Object value) {
		return JsonUtils.getJsonStyle(name, value);
	}
}
