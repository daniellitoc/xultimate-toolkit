package org.danielli.xultimate.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.vo.User;
import org.danielli.xultimate.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/users")
public class UserController {
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
//		binder.registerCustomEditor(requiredType, propertyEditor)
//		binder.addValidators(validators)
	}
	
	@RequestMapping(method = { RequestMethod.GET }, value = { "/forward" })
	public String forward(ModelMap modelMap) {
		return "forward:/users/redirect";
	}
	
	@RequestMapping(method = { RequestMethod.GET }, value = { "/redirect" })
	public String redirect(User user, ModelMap modelMap) {
		return "redirect:http://www.google.com";
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
	public String param11(@RequestParam(required = false) String message, ModelMap modelMap) {
		if (StringUtils.isEmpty(message)) {
			message = "Daniel Li";
		}
		modelMap.put("message", message);
		return "param1";
	}
	
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
	
	@RequestMapping(method = { RequestMethod.GET }, value = { "/param1_4/{message:[a-zA-z]+}" })
	public String param14(@PathVariable("message") String message, ModelMap modelMap) {
		return "param1";
	}
	
	@RequestMapping(method = { RequestMethod.GET }, value = { "/param1_5" })
	public String param15(@RequestParam String message, HttpServletRequest request, ModelMap modelMap) {
		@SuppressWarnings("unused")
		Cookie cookie = WebUtils.getCookie(request, "value");
		return "param1";
	}
	
	@RequestMapping(method = { RequestMethod.GET }, value = { "/param1_6" })
	public String param16(@RequestParam String message, HttpServletRequest request, ModelMap modelMap) {
		@SuppressWarnings("unused")
		String header = WebUtils.getHeader(request, WebUtils.CONTENT_ENCODING);
		return "param1";
	}
	
	@RequestMapping(method = { RequestMethod.GET }, value = { "/param1_7" })
	public String param16(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		return "param1";
	}
	
	@RequestMapping(method = { RequestMethod.GET }, value = { "/param2_1_1" })
	public String param211(@RequestParam("userString") User user, ModelMap modelMap) {
		modelMap.put("user", user);
		return "param2";
	}
	
	@RequestMapping(method = { RequestMethod.GET }, value = { "/param2_1_2" })
	@ResponseBody
	public User param212(User user) {
		user.setId(10000L);
		return user;
	}
	
	// 验证不通过进入param2_error
	@RequestMapping(method = { RequestMethod.GET }, value = { "/param2_2_2" })
	public String param221(@Valid User user, BindingResult bindingResult, ModelMap modelMap) {
		if (bindingResult.hasErrors()) {
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			for (FieldError fieldError : fieldErrors) {
				System.out.println(fieldError);
			}
			System.out.println(bindingResult.getFieldError("name"));
			System.out.println(bindingResult.getFieldErrorCount());
			System.out.println(bindingResult.getFieldValue("name"));
			System.out.println(bindingResult.getFieldError("name").getCode());
			System.out.println(Arrays.toString(bindingResult.getFieldError("name").getCodes()));
			System.out.println(Arrays.toString(bindingResult.getFieldError("name").getArguments()));
			return "param2_error";
		}
		return "param2";
	}
	
	// 验证不通过通过抛出BindException
	@RequestMapping(method = { RequestMethod.GET }, value = { "/param2_2_1" })
	public String param222(@Valid User user, ModelMap modelMap) {
		return "param2";
	}
	
	// 手动验证
	@RequestMapping(method = { RequestMethod.GET }, value = { "/param2_3_1" })
	public String param231(User user, BindingResult bindingResult, ModelMap modelMap) {
		ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "name", "Required");
		if (!StringUtils.equals(user.getName(), "Daniel Li")) {
			bindingResult.rejectValue("name", "Exists");
		}
		if (bindingResult.hasErrors()) {
			return "param2_error";
		}
		return "param2";
	}
	
	// 没有执行验证
	@RequestMapping(method = { RequestMethod.GET }, value = { "/param2_3_2" })
	public String param232(User user, BindingResult bindingResult, ModelMap modelMap) {
		if (bindingResult.hasErrors()) {
			return "param2_error";
		}
		return "param2";
	}
}
