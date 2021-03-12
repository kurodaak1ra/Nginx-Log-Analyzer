package jp.ka.controller;

import jp.ka.service.DetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Map;

@Controller
public class DetailsController {

	@Value("${ip_query_api}")
	private String api;

	@Resource
	private DetailsService detailsService;

	@RequestMapping("/details/{filename}")
	public String details(@PathVariable String filename, Model model) {
		Map<String, Object> map = detailsService.load(filename, null, null, null);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("count", map.get("count"));
		model.addAttribute("api", api);

		return "details";
	}

}
