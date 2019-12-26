package cn.smilegoo.hs;

import org.junit.Ignore;
import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

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

	@Test
	@Ignore
	public void testFormat(){
		SimpleDateFormat simpFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		System.out.println(simpFormat.format(new Date()));

	}

}
