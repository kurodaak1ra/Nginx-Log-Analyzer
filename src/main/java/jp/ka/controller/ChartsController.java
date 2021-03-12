package jp.ka.controller;

import jp.ka.service.ChartsService;
import jp.ka.utils.Tools;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
public class ChartsController {

	@Resource
	private ChartsService chartsService;

	@RequestMapping("/charts/{filename}")
	public String charts(@PathVariable String filename, Model model) {
		Tools.chartModel(model, chartsService.load(filename, null, null, null));

		return "charts";
	}

}
