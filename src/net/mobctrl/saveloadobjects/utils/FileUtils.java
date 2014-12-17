package net.mobctrl.saveloadobjects.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @date 2014年11月8日 上午11:30:13
 * @author Zheng Haibo
 * @web http://www.mobctrl.net
 * @Description: 文件保存
 */
public class FileUtils {

	private FileUtils() {

	}

	public static FileUtils fileUtils = null;
	public static EncryptionDecryption encryptionDecryption = null;
	public static ObjectMapper objectMapper = null;

	/**
	 * 单例
	 * 
	 * @return
	 */
	public synchronized static FileUtils getInstance() {
		return getInstance("www.mobctrl.net");
	}

	public synchronized static FileUtils getInstance(String encodeKey) {
		if (fileUtils == null) {
			fileUtils = new FileUtils();
		}
		if (encryptionDecryption == null) {
			try {
				encryptionDecryption = new EncryptionDecryption(encodeKey);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		return fileUtils;
	}

	/**
	 * 读文件
	 * 
	 * @param filePath
	 * @return
	 */
	private String readTextFile(String filePath) {
		File file = new File(filePath);
		if (file.isFile()) {
			InputStream inputStream = null;
			BufferedReader bufferedReader = null;
			try {
				String content = "";
				inputStream = new FileInputStream(file);
				if (inputStream != null) {
					bufferedReader = new BufferedReader(new InputStreamReader(
							inputStream));
					String line = "";
					while ((line = bufferedReader.readLine()) != null) {
						content += line;
					}
				}
				return content;
			} catch (Exception e) {
				System.out.println(e.toString());
			} finally {
				try {
					if (bufferedReader != null) {
						bufferedReader.close();
						bufferedReader = null;
					}
					if (inputStream != null) {
						inputStream.close();
						inputStream = null;
					}
				} catch (Exception e) {
					System.out.println(e.toString());
				}
			}
		}
		return null;
	}

	/**
	 * 写文件
	 * 
	 * @param filePath
	 * @param sets
	 * @throws IOException
	 */
	private synchronized void writeFile(String filePath, String content)
			throws IOException {
		FileWriter fw = new FileWriter(filePath);
		PrintWriter out = new PrintWriter(fw);
		out.write(content);
		out.println();
		fw.close();
		out.close();
	}

	/**
	 * 
	 * @param object
	 *            需要保存的对象
	 * @param filePath
	 *            文件名
	 * @param isEncode
	 *            是否需要加密
	 * @return
	 */
	public boolean saveObject(Object object, String filePath, boolean isEncode) {
		String jsonString = null;
		// 将对象转化为Json字符串
		try {
			String str = objectMapper.writeValueAsString(object);
			if (isEncode && encryptionDecryption != null) {
				jsonString = encryptionDecryption.encrypt(str);
			} else {
				jsonString = str;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("debug:writeObjects json error...");
			return false;
		}
		if (jsonString == null) {
			return false;
		}
		try {
			File file = new File(filePath);
			if (file.exists() && file.isFile()) {
				file.delete();
			}
			writeFile(filePath, jsonString);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("debug:writeObjects write file error...");
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param filePath
	 * @param clazz
	 * @param isDecode
	 *            如果原数据加密，isDecode必须为true，如果源数据未加密，必须设置为false
	 * @return
	 */
	public Object loadObject(String filePath, Class<?> clazz, boolean isDecode) {
		try {
			String content = loadStringFromFile(filePath, isDecode);
			return objectMapper.readValue(content, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public <T> T loadObject(String filePath, JavaType type, boolean isDecode) {
		try {
			String content = loadStringFromFile(filePath, isDecode);
			return objectMapper.readValue(content, type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public <T> T loadObject(String filePath, TypeReference<?> typeRefer,
			boolean isDecode) {
		try {
			String content = loadStringFromFile(filePath, isDecode);
			return objectMapper.readValue(content, typeRefer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String loadStringFromFile(String filePath, boolean isDecode) {
		String content = readTextFile(filePath);
		if (isDecode && null != encryptionDecryption) {
			try {
				content = encryptionDecryption.decrypt(content);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("debug:content = " + content);
		return content;
	}

}
