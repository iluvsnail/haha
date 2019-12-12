package cn.gfire.gdp.cloud.resource.classifier;

import org.junit.Ignore;
import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HsApplicationTests {


	@Test
	@Ignore
	public void testURL(){

		System.out.println(getReturnString("http://dev.gfire.cn:8888/hs-tool/api/v1/client/test/message/consume"));
	}
	public static String getReturnString(String u) {
		StringBuffer temp = new StringBuffer();
		try {
			URL url = new URL(u);
			url.openConnection();
			InputStream is = url.openStream();
			Reader rd = new InputStreamReader(is, "utf-8");
			int c = 0;
			while ((c = rd.read()) != -1) {
				temp.append((char) c);
			}
			is.close();
			return temp.toString();
		} catch (Exception e) {
			temp.append(e.getMessage());
		}
		return temp.toString();
	}

}
