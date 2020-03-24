package cc.zoyn.uvisos.keyword;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import cc.zoyn.uvisos.UvisOS;

/**
 * 关键词管理器(自定义回复)
 * 
 * @author Zoyn
 */
public class KeywordManager {

	private static Map<String, String> words = new HashMap<>();
	private static final Gson gson = new GsonBuilder()
			.enableComplexMapKeySerialization()
			.setPrettyPrinting()
			.create();
	private static File file = new File(UvisOS.getInstance().getDataFolder(), "\\keyword.json");

	public static Map<String, String> getWords() {
		return words;
	}

	public static void setKeyword(String keyWord, String data) {
		words.put(keyWord, data);
	}

	public static String getKeyword(String keyWord) {
		return words.get(keyWord);
	}

	public static void removeKeyword(String keyWord) {
		if (words.containsKey(keyWord)) {
			words.remove(keyWord);
		}
	}

	public static void save() {
		String data = gson.toJson(words);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fileWritter = new FileWriter(file);
			fileWritter.write(data);
			fileWritter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		UvisOS.logInfo("关键词数据已保存");
	}

	public static void load() {
		// 读取
		if (!file.exists()) {
			return;
		}
		StringBuffer content = new StringBuffer();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null) {
				content.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String data = content.toString();
		// 解析
		Type type = new TypeToken<Map<String, String>>() {
		}.getType();
		Map<String, String> cache = gson.fromJson(data, type);

		words.clear();
		words.putAll(cache);
		UvisOS.logInfo("关键词数据已加载");
	}
}
