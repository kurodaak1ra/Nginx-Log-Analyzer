package jp.ka.controller;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

	@Value("${log_dir}")
	private String dir;

	// 文件过滤器
	private FileFilter fileFilter = new FileFilter() {
		@Override
		public boolean accept(File pathname) {
			String extension = FilenameUtils.getExtension(pathname.getName());
			if (extension.matches("log.*")) {
				return true;
			}
			if (pathname.isDirectory()) {
				return true;
			}
			return false;
		}
	};

	@RequestMapping("/")
	public String index(Model model) {
		if (!dir.substring(dir.length()-1).equals("/")) dir = dir+"/";

		// 递归遍历文件夹内部类
		class Recursion {
			private List<File> items = new ArrayList<>();

			private List<File> init(File[] files) {
				for (File file : files) {
					if (file.isDirectory()) {
						init(file.listFiles(fileFilter));
					} else {
						items.add(file);
					}
				}
				return items;
			}
		}

		List<String> filepaths = new ArrayList<>();
		File[] files = new File(dir).listFiles(fileFilter);
		if (files != null) {
			List<File> items = new Recursion().init(files);
			for (File item : items) {
				String tmp = item.toString().replaceAll(dir, "").replaceAll("/", "_");
				filepaths.add(tmp);
			}
		}
		model.addAttribute("filepaths", filepaths);

		return "index";
	}

}
