package org.danielli.xultimate.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.danielli.xultimate.model.User;
import org.danielli.xultimate.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/users")
public class UserController {
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
//		binder.registerCustomEditor(requiredType, propertyEditor)
//		binder.addValidators(validators)
	}
	
	@RequestMapping(method = { RequestMethod.GET }, value = { "/test1_normal" })
	public String test1Normal(ModelMap modelMap) {
		modelMap.put("text", "hello world");
		return "test1";
	}
	
	@RequestMapping(method = { RequestMethod.GET }, value = { "/test1_exception1" })
	public String testExceptoin1(ModelMap modelMap) throws Exception {
		modelMap.put("text", "hello world");
		return "test1";
	}
	
	@RequestMapping(method = { RequestMethod.GET }, value = { "/test1_exception2" })
	public String testExceptoin2(ModelMap modelMap) throws Exception {
		throw new Exception();
	}
	
	@RequestMapping(method = { RequestMethod.GET }, value = { "/param1_1" })
	public String param11(@RequestParam(defaultValue = "Daniel Li") String message, ModelMap modelMap) {
		modelMap.put("message", message);
		return "param1";
	}
	
	// 缺少验证
	@RequestMapping(method = { RequestMethod.GET }, value = { "/param1_2" })
	public String param12(@RequestParam String message, ModelMap modelMap) {
		modelMap.put("message", message);
		return "param1";
	}
	
	@RequestMapping(method = { RequestMethod.GET }, value = { "/param1_3" })
	public String param13(@RequestParam(value = "msg") String message, ModelMap modelMap) {
		modelMap.put("message", message);
		return "param1";
	}
	
	@RequestMapping(method = { RequestMethod.GET }, value = { "/param1_4/{message}" })
	public String param14(@PathVariable("message") String message, ModelMap modelMap) {
		return "param1";
	}
	
	// 缺少CookieUtils
	@RequestMapping(method = { RequestMethod.GET }, value = { "/param1_5" })
	public String param15(@RequestParam String message, ModelMap modelMap) {
		return "param1";
	}
	
	// 缺少HeaderUtils
	@RequestMapping(method = { RequestMethod.GET }, value = { "/param1_6" })
	public String param16(@RequestParam String message, ModelMap modelMap) {
		return "param1";
	}
	
	@RequestMapping(method = { RequestMethod.GET }, value = { "/param1_7" })
	public String param16(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		return "param1";
	}
	
	@RequestMapping(method = { RequestMethod.GET }, value = { "/param2_1" })
	public String param21(User user, ModelMap modelMap) {
		return "param2";
	}
	
	@RequestMapping(method = { RequestMethod.GET }, value = { "/param2_2_2" })
	public String param221(@Valid User user, BindingResult bindingResult, ModelMap modelMap) {
		if (bindingResult.hasErrors()) {
			return "param2_error";
		}
		return "param2";
	}
	
	@RequestMapping(method = { RequestMethod.GET }, value = { "/param2_2_1" })
	public String param222(@Valid User user, ModelMap modelMap) {
		return "param2";
	}
	
	@RequestMapping(method = { RequestMethod.GET }, value = { "/param2_3_1" })
	public String param231(User user, BindingResult bindingResult, ModelMap modelMap) {
		ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "name", "Required");
//		ValidationUtils.invokeValidator(validator, obj, errors)
		if (!StringUtils.equals(user.getName(), "Daniel Li")) {
			bindingResult.rejectValue("name", "Exists");
		}
		if (bindingResult.hasErrors()) {
			return "param2_error";
		}
		return "param2";
	}
	
	@RequestMapping(method = { RequestMethod.GET }, value = { "/param2_4" })
	public String param24(@RequestParam("user") User user, ModelMap modelMap) {
		return "param2";
	}
	
	@RequestMapping(method = { RequestMethod.GET }, value = { "/forward" })
	public String forward(ModelMap modelMap) {
		return "forward:/users/redirect";
	}
	
	@RequestMapping(method = { RequestMethod.GET }, value = { "/redirect" })
	public String redirect(User user, ModelMap modelMap) {
		return "redirect:http://www.google.com";
	}
	
	@RequestMapping(method = { RequestMethod.GET }, value = { "/to_upload" })
	public String toUpload(MultipartFile file, ModelMap modelMap) {
		return "upload";
	}
	
	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST }, value = { "/preview" })
	public byte[] report(MultipartFile file) throws IOException {
		return file.getBytes();
	}
	
	@RequestMapping(method = { RequestMethod.POST }, value = { "/do_upload" })
	public String doUpload(MultipartFile file, ModelMap modelMap) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("fileName", file.getOriginalFilename());
		result.put("size", file.getSize());
		
		modelMap.put("result", result);
		return "upload_result";
	}
}
