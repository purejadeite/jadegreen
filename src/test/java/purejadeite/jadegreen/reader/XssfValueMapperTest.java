package purejadeite.jadegreen.reader;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import purejadeite.jadegreen.definition.BookDefinitionImpl;
import purejadeite.jadegreen.definition.DefinitionBuilder;
import purejadeite.jadegreen.reader.XssfValueMapper;

/**
 * Unit test for simple App.
 */
public class XssfValueMapperTest
{
	private static Logger LOGGER = LoggerFactory.getLogger(XssfValueMapperTest.class);

	private static final String INPUT_FILE_PATH = "./examples/inputs/sample.xlsx";

	private static final String INPUT_FILE_PATH_DOUBLE = "./examples/inputs/double.xlsx";

	private static final String INPUT_FILE_PATH_DOUBLE2 = "./examples/inputs/doublex2.xlsx";

	private static final String SIMPLE_DEFINITION_PATH = "./examples/definitions/simple.json";

	private static final String SIMPLE_OUTPUT_FILE_PATH = "./examples/outputs/simple.json";

	private static final String GROUP_DEFINITION_PATH = "./examples/definitions/group.json";

	private static final String GROUP_OUTPUT_FILE_PATH = "./examples/outputs/group.json";

	private static final String MULTI_DEFINITION_PATH = "./examples/definitions/multi.json";

	private static final String MULTI_OUTPUT_FILE_PATH = "./examples/outputs/multi.json";

	private static final String LINK_DEFINITION_PATH = "./examples/definitions/link.json";

	private static final String LINK_OUTPUT_FILE_PATH = "./examples/outputs/link.json";

	private static final String LIMIT_DEFINITION_PATH = "./examples/definitions/limit.json";

	private static final String LIMIT_OUTPUT_FILE_PATH = "./examples/outputs/limit.json";

	private static final String VALIABLE_DEFINITION_PATH = "./examples/definitions/valiable.json";

	private static final String VALIABLE_OUTPUT_FILE_PATH = "./examples/outputs/valiable.json";

	private static final String DOUBLE_DEFINITION_PATH = "./examples/definitions/double.json";

	private static final String DOUBLE_OUTPUT_FILE_PATH = "./examples/outputs/double.json";

	private static final String DOUBLE2_DEFINITION_PATH = "./examples/definitions/doublex2.json";

	private static final String DOUBLE2_OUTPUT_FILE_PATH = "./examples/outputs/doublex2.json";

	@Test
	public void simple()
	{
		exec("simple", INPUT_FILE_PATH, SIMPLE_DEFINITION_PATH, SIMPLE_OUTPUT_FILE_PATH);
	}

	@Test
	public void multi()
	{
		exec("multi", INPUT_FILE_PATH, MULTI_DEFINITION_PATH, MULTI_OUTPUT_FILE_PATH);
	}

	@Test
	public void link()
	{
		exec("link", INPUT_FILE_PATH, LINK_DEFINITION_PATH, LINK_OUTPUT_FILE_PATH);
	}

	@Test
	public void limit()
	{
		exec("limit", INPUT_FILE_PATH, LIMIT_DEFINITION_PATH, LIMIT_OUTPUT_FILE_PATH);
	}

	@Test
	public void group()
	{
		exec("group", INPUT_FILE_PATH, GROUP_DEFINITION_PATH, GROUP_OUTPUT_FILE_PATH);
	}

	@Test
	public void valiable()
	{
		exec("valiable", INPUT_FILE_PATH, VALIABLE_DEFINITION_PATH, VALIABLE_OUTPUT_FILE_PATH);
	}

	@Test
	public void dbl()
	{
		exec("double", INPUT_FILE_PATH_DOUBLE, DOUBLE_DEFINITION_PATH, DOUBLE_OUTPUT_FILE_PATH);
	}

	@Test
	public void dblx2()
	{
		exec("doublex2", INPUT_FILE_PATH_DOUBLE2, DOUBLE2_DEFINITION_PATH, DOUBLE2_OUTPUT_FILE_PATH);
	}

	private void exec(String name, String excelFilePath, String definitionFilePath, String outputFilePath) {
		try {
			// 定義の読み込み
	    	BookDefinitionImpl book = DefinitionBuilder.build(definitionFilePath);
	    	// 定義の確認
			LOGGER.debug("■ " + name);
			LOGGER.debug("☆ 定義 ☆");
			LOGGER.debug("\r\n\r\n" + book.toJson() + "\r\n");
			// リーダーの実行
	    	List<Map<String, Object>> mappedData = XssfValueMapper.read(excelFilePath, book);
	    	// 結果の確認
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(mappedData);
			LOGGER.debug("★ 結果 ★");
			LOGGER.debug("\r\n\r\n" + json + "\r\n");
			// 結果の出力
			File output = new File(outputFilePath);
			FileUtils.writeStringToFile(output, json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
