package org.danielli.xultimate.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.danielli.xultimate.util.io.IOUtils;
import org.danielli.xultimate.util.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/analysis")
public class AnalysisController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AnalysisController.class);
	
	@RequestMapping(method = { RequestMethod.GET }, value = { "/tprofiler/topmethod" })
	public Object toAnalysisTProfilerTopMethod() throws IOException {
		return "analysis/tprofiler_topmethod";
	}
	
	private static final Pattern TPROFILE_TOPMETHOD_PATTERN = Pattern.compile("");
	
	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST }, value = { "/tprofiler/topmethod" })
	public Object doAnalysisTProfilerTopMethod(MultipartFile multipartFile) throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("title", "TProfiler topmethod统计图表");
		result.put("subtitle", "数据通过ProfilerLogAnalysis分析生成");
		List<String> categories = new ArrayList<>();
		result.put("categories", categories);
		result.put("yAxis.title", "时间或次数");
		result.put("tooltip.valueSuffix", "");
		List<Map<String, Object>> series = new ArrayList<>();
		Map<String, Object> singleSerie = null;
		
		singleSerie = new HashMap<>(); 
		List<Double> countData = new ArrayList<>();
		singleSerie.put("name", "Count");
		singleSerie.put("data", countData);
		series.add(singleSerie);
		
		singleSerie = new HashMap<>(); 
		List<Double> avgTimeData = new ArrayList<>();
		singleSerie.put("name", "avgTime");
		singleSerie.put("data", avgTimeData);
		series.add(singleSerie);
		
		singleSerie = new HashMap<>(); 
		List<Double> totalTimeData = new ArrayList<>();
		singleSerie.put("name", "totalTime");
		singleSerie.put("data", totalTimeData);
		series.add(singleSerie);
		
		result.put("series", series);
		
		Scanner scanner = null;
		try {
			scanner = new Scanner(IOUtils.toBufferedInputStream(multipartFile.getInputStream()));
			while (scanner.hasNextLine()) {
				Matcher matcher = TPROFILE_TOPMETHOD_PATTERN.matcher(scanner.nextLine());
				while (matcher.find()) {
					String methodName = matcher.group(1);
					categories.add(methodName);
					Double count = NumberUtils.createDouble(matcher.group(2));
					countData.add(count);
					Double avgTime = NumberUtils.createDouble(matcher.group(3));
					avgTimeData.add(avgTime);
					Double totalTime = NumberUtils.createDouble(matcher.group(4));
					totalTimeData.add(totalTime);
				}
			}
			return result;
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			throw e;
		} finally {
			IOUtils.closeQuietly(scanner);
		}
	}

	@RequestMapping(method = { RequestMethod.GET }, value = { "/mysql/status" })
	public Object toAnalysisMySQLStatus() throws IOException {
		return "analysis/mysql_status";
	}
	
	private static final Pattern MYSQ_STATUS_PATTERN = Pattern.compile("");
	
	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST }, value = { "/mysql/status" })
	public Object doAnalysisMySQLStatus(MultipartFile multipartFile) throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("title", "MySQL STATUS统计图表");
		result.put("subtitle", "数据通过analyz.sh分析生成");
		List<String> categories = new ArrayList<>();
		result.put("categories", categories);
		result.put("yAxis.title", "QPS");
		result.put("tooltip.valueSuffix", "");
		List<Map<String, Object>> series = new ArrayList<>();
		Map<String, Object> singleSerie = new HashMap<>();
		List<Double> qpsData = new ArrayList<>();
		singleSerie.put("name", "QPS");
		singleSerie.put("data", qpsData);
		series.add(singleSerie);
		result.put("series", "");
		
		Scanner scanner = null;
		try {
			scanner = new Scanner(IOUtils.toBufferedInputStream(multipartFile.getInputStream()));
			while (scanner.hasNextLine()) {
				Matcher matcher = MYSQ_STATUS_PATTERN.matcher(scanner.nextLine());
				while (matcher.find()) {
					categories.add(matcher.group(2));
					qpsData.add(NumberUtils.createDouble(matcher.group(5)));
				}
			}
			return result;
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			throw e;
		} finally {
			IOUtils.closeQuietly(scanner);
		}
	}
}
