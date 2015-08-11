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

	private static final String DATA_DIR_PATH = "./examples";

	private static final File DEFINITIONS_DIR = new File(DATA_DIR_PATH, "definitions");

	private static final File INPUTS_DIR = new File(DATA_DIR_PATH, "inputs");

	private static final File OUTPUTS_DIR = new File(DATA_DIR_PATH, "outputs");

	private static final String[] EXCEL_EXTENSIONS = {".xlsx", ".xlsm"};
	
	@Test
	public void single()
	{
		exec("single");
	}

	private void exec(String name) {
		try {
			// 定義の読み込み
	    	BookDefinitionImpl bookDefinition = DefinitionBuilder.build(DEFINITIONS_DIR, name + ".json");
	    	// 定義の確認
			LOGGER.debug("■ " + name);
			LOGGER.debug("☆ 定義 ☆");
			LOGGER.debug("\r\n\r\n" + bookDefinition.toJson() + "\r\n");
			// excelファイルの取得
			File excelFile = null;
			for (String extension:EXCEL_EXTENSIONS) {
				excelFile = new File(INPUTS_DIR, name + extension);
				if (excelFile.exists()) {
					break;
				}
			}
			// マッパーの実行
	    	List<Map<String, Object>> mappedData = XssfValueMapper.read(excelFile, bookDefinition);
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