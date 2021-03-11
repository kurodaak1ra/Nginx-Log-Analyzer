package jp.ka.service;

import jp.ka.bean.Item;
import jp.ka.utils.Tools;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DetailsService {

	@Value("${log_dir}")
	private String dir;

	public Map<String, Object> load(String filename, Long date, String proxy, String real) {
		if (!dir.substring(dir.length()-1).equals("/")) dir = dir+"/";
		List<Item> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		long count = 0;

		try {
			String line;
			BufferedReader br = new BufferedReader(new FileReader(dir + filename.replaceAll("_", "/")));
			while ((line = br.readLine()) != null) {
				String[] s = line.split(" #");
				if (s.length != 14) continue;
				String[] req = s[3].split(" ");
				if (req.length != 3) req = new String[]{"", "", ""};
				long t = Tools.formatDate(s[2]).getTime();

				// 指定日期查询
				if (date != null && (t < date || t >= date+86400000)) {
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

				list.add(new Item(s[0], s[1], t, req[0], req[1], req[2], s[4], s[5], s[6], s[7], s[8], s[9], s[10], s[11], s[12], s[13]));
				count++;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		map.put("list", list);
		map.put("count", count);
		return map;
	}

}
