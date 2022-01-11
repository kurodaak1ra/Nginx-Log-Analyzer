package jp.ka.controller;

import jp.ka.service.ChartsService;
import jp.ka.service.DetailsService;
import jp.ka.utils.Tools;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TimeZone;

@Controller
public class SingleController {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Value("${ip_query_api}")
	private String api;

	@Value("${timezone}")
	private String timezone;

	@Resource
	private ChartsService chartsService;

	@Resource
	private DetailsService detailsService;

	@RequestMapping("/charts/day/{date}/{filename}")
	public String singleChartsDay(@PathVariable String date, @PathVariable String filename, Model model) {
		sdf.setTimeZone(TimeZone.getTimeZone(timezone));
		try {
			Tools.chartModel(model, chartsService.load(filename, sdf.parse(date).getTime(), null, null));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return "charts";
	}

	@RequestMapping("/charts/ip/proxy/{ip}/{filename}")
	public String singleChartsIPProxy(@PathVariable String ip, @PathVariable String filename, Model model) {
		Tools.chartModel(model, chartsService.load(filename, null, ip, null));

		return "charts";
	}

	@RequestMapping("/charts/ip/real/{ip}/{filename}")
	public String singleChartsIPReal(@PathVariable String ip, @PathVariable String filename, Model model) {
		Tools.chartModel(model, chartsService.load(filename, null, null, ip));

		return "charts";
	}

	@RequestMapping("/details/day/{date}/{filename}")
	public String singleDetails(@PathVariable String date, @PathVariable String filename, Model model) {
		sdf.setTimeZone(TimeZone.getTimeZone(timezone));
		try {
			Map<String, Object> map = detailsService.load(filename, sdf.parse(date).getTime(), null, null);
			model.addAttribute("list", map.get("list"));
			model.addAttribute("count", map.get("count"));
			model.addAttribute("api", api);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return "details";
	}

	@RequestMapping("/details/ip/proxy/{ip}/{filename}")
	public String singleDetailsIPProxy(@PathVariable String ip, @PathVariable String filename, Model model) {
		Map<String, Object> map = detailsService.load(filename, null, ip, null);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("count", map.get("count"));
		model.addAttribute("api", api);

		return "details";
	}

	@RequestMapping("/details/ip/real/{ip}/{filename}")
	public String singleDetailsIPReal(@PathVariable String ip, @PathVariable String filename, Model model) {
		Map<String, Object> map = detailsService.load(filename, null, null, ip);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("count", map.get("count"));
		model.addAttribute("api", api);

		return "details";
	}

}
