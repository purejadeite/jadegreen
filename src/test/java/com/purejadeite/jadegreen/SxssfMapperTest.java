package com.purejadeite.jadegreen;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.jadegreen.definition.DefinitionBuilder;
import com.purejadeite.jadegreen.definition.WorkbookDefinition;

/**
 * Unit test for simple App.
 */
public class SxssfMapperTest {

	private static Logger LOGGER = LoggerFactory.getLogger(SxssfMapperTest.class);

	private static final String DATA_DIR_PATH = "src/test/data";

	private static final File EXPECTED_DIR = new File(DATA_DIR_PATH, "expected");

	private static final File DEFINITIONS_DIR = new File(DATA_DIR_PATH, "definitions");

	private static final File INPUTS_DIR = new File(DATA_DIR_PATH, "inputs");

	private static final String[] EXCEL_EXTENSIONS = { ".xlsx", ".xlsm" };

	@Test
	public void group() throws Exception {
		test("group");
	}

	@Test
	public void keyvalue() throws Exception {
		test("keyvalue");
	}

	@Test
	public void single() throws Exception {
		test("single");
	}

	@Test
	public void join() throws Exception {
		test("join");
	}

	@Test
	public void multi() throws Exception {
		test("multi");
	}

	@Test
	public void multijoin() throws Exception {
		test("multijoin");
	}

	@Test(expected = ContentException.class)
	public void ununiquecelljoin() throws Exception {
		test("ununiquecelljoin");
	}

	@Test(expected = ContentException.class)
	public void ununiquerangejoin() throws Exception {
		test("ununiquerangejoin");
	}

	@Test
	public void replaceid() throws Exception {
		test("replaceid");
	}

	private void test(String name) throws Exception {
		// 定義の読み込み
		Map<String, Object> jsonObj = TestHelper.toJsonMap(DEFINITIONS_DIR, name + ".json");
		WorkbookDefinition bookDefinition = DefinitionBuilder.build(jsonObj);
		// excelファイルの取得
		File excelFile = null;
		for (String extension : EXCEL_EXTENSIONS) {
			excelFile = new File(INPUTS_DIR, name + extension);
			if (excelFile.exists()) {
				break;
			}
		}
		// マッパーの実行
		List<Map<String, Object>> actual = SxssfMapper.read(excelFile, bookDefinition);

		// 結果の確認
		List<Map<String, Object>> expected = TestHelper.toJsonList(EXPECTED_DIR, name + ".json");
		LOGGER.info("actual: " + TestHelper.toJsonString(actual));
		LOGGER.info("expected: " + TestHelper.toJsonString(expected));
		assertEquals(expected, actual);
	}
}
