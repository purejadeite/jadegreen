package com.purejadeite.jadegreen;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;class TestHelper {

	private static ObjectMapper MAPPER = new ObjectMapper();

	public static String toJsonString(String jsonFilePath) throws IOException {
		return toJsonString(new File(jsonFilePath));
	}

	public static String toJsonString(File definitionsDir, String jsonFileName) throws IOException {
		return toJsonString(new File(definitionsDir, jsonFileName));
	}

	public static String toJsonString(String definitionsDirPath, String jsonFileName) throws IOException {
		return toJsonString(new File(definitionsDirPath, jsonFileName));
	}

	public static String toJsonString(File jsonFile) throws IOException {
		return FileUtils.readFileToString(jsonFile);
	}

	public static Object toJsonObject(String jsonFilePath) throws IOException {
		return toJsonObject(new File(jsonFilePath));
	}

	public static Object toJsonObject(File definitionsDir, String jsonFileName) throws IOException {
		return toJsonObject(new File(definitionsDir, jsonFileName));
	}

	public static Object toJsonObject(String definitionsDirPath, String jsonFileName) throws IOException {
		return toJsonObject(new File(definitionsDirPath, jsonFileName));
	}

	public static Object toJsonObject(File jsonFile) throws IOException {
		String json = toJsonString(jsonFile);
		Object jsonObject = MAPPER.readValue(json, Object.class);
		return jsonObject;
	}

	public static List<Map<String, Object>> toJsonList(String jsonFilePath) throws IOException {
		return toJsonList(new File(jsonFilePath));
	}

	public static List<Map<String, Object>> toJsonList(File definitionsDir, String jsonFileName) throws IOException {
		return toJsonList(new File(definitionsDir, jsonFileName));
	}

	public static List<Map<String, Object>> toJsonList(String definitionsDirPath, String jsonFileName) throws IOException {
		return toJsonList(new File(definitionsDirPath, jsonFileName));
	}

	public static List<Map<String, Object>> toJsonList(File jsonFile) throws IOException {
		String json = toJsonString(jsonFile);
		List<Map<String, Object>> list = null;
		String trimedJson = StringUtils.trim(json);
		if (!StringUtils.startsWith(trimedJson, "[") && !StringUtils.endsWith(trimedJson, "]")) {
			json = "[" + json + "]";
		}
		list = MAPPER.readValue(json, new TypeReference<List<Map<String, Object>>>() {
		});
		return list;
	}

	public static Map<String, Object> toJsonMap(String jsonFilePath) throws IOException {
		return toJsonMap(new File(jsonFilePath));
	}

	public static Map<String, Object> toJsonMap(File definitionsDir, String jsonFileName) throws IOException {
		return toJsonMap(new File(definitionsDir, jsonFileName));
	}

	public static Map<String, Object> toJsonMap(String definitionsDirPath, String jsonFileName) throws IOException {
		return toJsonMap(new File(definitionsDirPath, jsonFileName));
	}

	public static Map<String, Object> toJsonMap(File jsonFile) throws IOException {
		String json = toJsonString(jsonFile);
		Map<String, Object> map = null;
		String trimedJson = StringUtils.trim(json);
		if (!StringUtils.startsWith(trimedJson, "{") && !StringUtils.endsWith(trimedJson, "}")) {
			json = "{\"json\":" + json + "}";
		}
		map = MAPPER.readValue(json, new TypeReference<Map<String, Object>>() {
		});
		return map;
	}

}
