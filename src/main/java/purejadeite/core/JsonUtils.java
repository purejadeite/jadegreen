package purejadeite.core;

import java.util.List;

public class JsonUtils {

	/**
	 * JSON形式のnameとvalueの表現を取得します
	 * 
	 * @param name
	 *            プロパティ名
	 * @param value
	 *            値
	 * @return "name": "value"形式の文字列
	 */
	public static String getJsonStyle(String name, Object value) {
		String json = null;
		if (name != null) {
			json = "\"" + name + "\":";
		} else {
			json = "";
		}

		if (value == null) {
			json += "null";
		} else if (value instanceof ToJson) {
			json += ((ToJson) value).toJson();
		} else if (value instanceof List) {
			json += "[";
			@SuppressWarnings("unchecked")
			List<Object> vals = (List<Object>) value;
			for (int i = 0; i < vals.size(); i++) {
				if (i != 0) {
					json += ",";
				}
				Object v = vals.get(i);
				json += getJsonStyle(null, v);
			}
			json += "]";
		} else if (value instanceof Boolean) {
			json += value;
		} else if (value instanceof Number) {
			json += value;
		} else if (value instanceof String) {
			String v = (String) value;
			json += "\"" + v.replace("\r", "\\r").replace("\n", "\\n")
					.replace("\t", "\\t").replace("\"", "\\\"") + "\"";
		} else {
			json += "\"" + value + "\"";
		}
		return json;
	}
}
