package com.purejadeite.jadegreen;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.jadegreen.definition.DefinitionBuilder;
import com.purejadeite.jadegreen.definition.WorkbookDefinitionImpl;

/**
 * Unit test for simple App.
 */
public class SxssfValueMapperTest {
	private static Logger LOGGER = LoggerFactory.getLogger(SxssfValueMapperTest.class);

	private static final String DATA_DIR_PATH = "src/test/data";

	private static final File DEFINITIONS_DIR = new File(DATA_DIR_PATH, "definitions");

	private static final File INPUTS_DIR = new File(DATA_DIR_PATH, "inputs");

	private static final File OUTPUTS_DIR = new File(DATA_DIR_PATH, "outputs");

	private static final String[] EXCEL_EXTENSIONS = { ".xlsx", ".xlsm" };

	@Test
	public void single() {
		exec("single");
	}

	@Test
	public void link() {
		exec("link");
	}

	@Test
	public void multi() {
		exec("multi");
	}

	private void exec(String name) {
		try {
			// 定義の読み込み
			List<Map<String, Object>> jsonObj = TestHelper.toJsonList(DEFINITIONS_DIR, name + ".json");
			WorkbookDefinitionImpl bookDefinition = DefinitionBuilder.build(jsonObj);
			// 定義の確認
			LOGGER.debug("■ " + name);
			LOGGER.debug("☆ 定義 ☆");
			LOGGER.debug("\r\n\r\n" + bookDefinition.toMap() + "\r\n");
			// excelファイルの取得
			File excelFile = null;
			for (String extension : EXCEL_EXTENSIONS) {
				excelFile = new File(INPUTS_DIR, name + extension);
				if (excelFile.exists()) {
					break;
				}
			}

			// マッパーの実行
			List<Map<String, Object>> mappedData = SxssfValueMapper.read(excelFile, bookDefinition);

			// 結果の確認
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(mappedData);
			LOGGER.debug("★ 結果 ★");
			LOGGER.debug("\r\n\r\n" + json + "\r\n");
			// 結果の出力
			File output = new File(OUTPUTS_DIR, name + ".json");
			FileUtils.writeStringToFile(output, json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
