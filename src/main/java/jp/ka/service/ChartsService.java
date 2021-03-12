package jp.ka.service;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import jp.ka.utils.Tools;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class ChartsService {

	@Value("${log_dir}")
	private String dir;

	@Value("${timezone}")
	private String timezone;

	@Value("${country_lang}")
	private String lang;

	public Map<String, Object> load(String fileName, Long date, String proxy, String real) {
		if (!dir.substring(dir.length()-1).equals("/")) dir = dir+"/";

		try {
			long count = 0;
			Map<String, Object> res = new HashMap<>();
			Map<String, Integer> os = new HashMap<>();
			Map<String, Integer> browser = new HashMap<>();
			Map<String, Integer> httpCode = new HashMap<>();
			Map<Long, Integer> visitTime = new HashMap<>();
			Map<String, Integer> ip = new HashMap<>();
			Map<String, Integer> country = new HashMap<>();
			Map<String, Integer> realIP = new HashMap<>();
			Map<String, Integer> realCountry = new HashMap<>();
			Map<String, Integer> method = new HashMap<>();
			Map<String, Integer> fileType = new HashMap<>();

			String line;
			BufferedReader br = new BufferedReader(new FileReader(dir + fileName.replaceAll("_", "/")));
			while ((line = br.readLine()) != null) {
				String[] s = line.split(" #");
				if (s.length != 14) continue;
				String[] req = s[3].split(" ");
				Date t = Tools.formatDateSimple(timezone, s[2]);

				// 指定日期查询
				if (date != null && (t.getTime() < date || t.getTime() >= date+86400000)) {
					continue;
				}
				// 指定 ip 查询（代理）
				if (proxy != null && !proxy.equals(s[0])) {
					continue;
				}
				// 指定 ip 查询（真实）
				if (real != null && !real.equals(s[8])) {
					continue;
				}

				UserAgent userAgent = UserAgent.parseUserAgentString(s[7]);
				// 操作系统
				os = os(os, userAgent);
				// 浏览器
				browser = browser(browser, userAgent);
				// http 响应码
				httpCode = httpCode(httpCode, s[4]);
				// 访问时间
				visitTime = visitTime(visitTime, t);
				// ip 地址
				ip = ip(ip, s[0]);
				// 国家
				country = country(country, s[0]);
				// 套 cf 后真实 ip
				realIP = ip(realIP, s[8]);
				// 套 cf 后真实 ip 的国家
				realCountry = country(realCountry, s[8]);
				// 请求方式
				method = method(method, req[0]);
				// 访问的文件类型
				if (req.length == 3) fileType = fileType(fileType, req[1]);
			  // 计数
				count++;
			}

			res.put("os", os);
			res.put("browser", browser);
			res.put("httpCode", httpCode);
			res.put("visitTime", visitTime);
			res.put("ip", ip);
			res.put("country", country);
			res.put("realIP", realIP);
			res.put("realCountry", realCountry);
			res.put("method", method);
			res.put("fileType", fileType);
			res.put("count", count);

			return res;
		} catch (IOException e) {
			// e.printStackTrace();
			return null;
		}
	}

	private Map<String, Integer> os(Map<String, Integer> os, UserAgent ua) {
		OperatingSystem tmpOS = ua.getOperatingSystem();
		String osName = tmpOS.getName();
		if (osName.toLowerCase().contains("symbian os")) {
			osName = "Symbian OS";
		}
		if (osName.toLowerCase().contains("android")) {
			osName = "Android";
		}
		if (osName.toLowerCase().contains("iphone")) {
			osName = "iPhone";
		}
		if (os.containsKey(osName)) {
			os.put(osName, os.get(osName)+1);
		} else {
			os.put(osName, 1);
		}
		return os;
	}

	private Map<String, Integer> browser(Map<String, Integer> browser, UserAgent ua) {
		Browser tmpBrowser = ua.getBrowser();
		String browserName = tmpBrowser.getName();
		if (browserName.toLowerCase().contains("chrome")) {
			browserName = "Chrome";
		}
		if (browserName.toLowerCase().contains("firefox")) {
			browserName = "Firefox";
		}
		if (browserName.toLowerCase().contains("opera")) {
			browserName = "Opera";
		}
		if (browserName.toLowerCase().contains("safari")) {
			browserName = "Safari";
		}
		if (browserName.toLowerCase().contains("microsoft edge")) {
			browserName = "Microsoft Edge";
		}
		if (browserName.toLowerCase().contains("internet explorer")) {
			browserName = "Internet Explorer";
		}
		if (browser.containsKey(browserName)) {
			browser.put(browserName, browser.get(browserName)+1);
		} else {
			browser.put(browserName, 1);
		}
		return browser;
	}

	private Map<String, Integer> httpCode(Map<String, Integer> data, String code) {
		if (data.containsKey(code)) {
			data.put(code, data.get(code)+1);
		} else {
			data.put(code, 1);
		}
		return data;
	}

	private Map<String, Integer> ip(Map<String, Integer> data, String ip) {
		if (data.containsKey(ip)) {
			data.put(ip, data.get(ip)+1);
		} else {
			data.put(ip, 1);
		}
		return data;
	}

	private Map<String, Integer> country(Map<String, Integer> data, String country) {
		String tmpCountry = Tools.geoip(country, lang);
		if (data.containsKey(tmpCountry)) {
			data.put(tmpCountry, data.get(tmpCountry)+1);
		} else {
			data.put(tmpCountry, 1);
		}
		return data;
	}

	private Map<Long, Integer> visitTime(Map<Long, Integer> data, Date t) {
		Long unix = t.getTime();
		if (data.containsKey(unix)) {
			data.put(unix, data.get(unix)+1);
		} else {
			data.put(unix, 1);
		}
		return data;
	}

	private Map<String, Integer> method(Map<String, Integer> data, String method) {
		boolean matches = Pattern.matches("[A-Z]+", method);
		if (!matches) method = "Unknown";
		if (data.containsKey(method)) {
			data.put(method, data.get(method)+1);
		} else {
			data.put(method, 1);
		}
		return data;
	}

	private Map<String, Integer> fileType(Map<String, Integer> data, String uri) {
		String extension = FilenameUtils.getExtension(uri.split("\\?")[0]);
		extension = extension.toLowerCase();
		if (extension == "") extension = "others";
		if (data.containsKey(extension)) {
			data.put(extension, data.get(extension)+1);
		} else {
			data.put(extension, 1);
		}
		return data;
	}

}
