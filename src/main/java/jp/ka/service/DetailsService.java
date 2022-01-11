package jp.ka.service;

import jp.ka.bean.LogItem;
import jp.ka.utils.Tools;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Service
public class DetailsService {

	@Value("${log_dir}")
	private String dir;

	@Value("${timezone}")
	private String timezone;

	public Map<String, Object> load(String filename, Long date, String proxy, String real) {
		if (!dir.substring(dir.length()-1).equals("/")) dir = dir+"/";
		List<LogItem> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		long count = 0;

		try {
			String line;
			BufferedReader br = new BufferedReader(new FileReader(dir + filename.replaceAll("_", "/")));
			while ((line = br.readLine()) != null) {
				String[] s = line.split(" #");
				if (s.length != 14) continue;
				String[] req = s[11].split(" ");
				if (req.length != 3) req = new String[]{"", "", ""};
				Date t = Tools.formatDate(s[2]);

				// 指定日期查询
				if (date != null && (t.getTime() < date || t.getTime() >= date+24*60*60*1000)) {
					continue;
				}
				// 指定 ip 查询（代理）
				if (proxy != null && !proxy.equals(s[0])) {
					continue;
				}
				// 指定 ip 查询（真实）
				if (real != null && !real.equals(s[1])) {
					continue;
				}

				list.add(new LogItem(s[0], s[1], Tools.formatDateToString(timezone, t), s[3], s[4], s[5], s[6], s[7], s[8], s[9], s[10], req[0], req[1], req[2], s[12], s[13]));
				count++;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		Collections.reverse(list);
		map.put("list", list);
		map.put("count", count);
		return map;
	}

}
