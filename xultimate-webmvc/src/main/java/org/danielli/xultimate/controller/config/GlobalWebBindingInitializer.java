package org.danielli.xultimate.controller.config;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

public class GlobalWebBindingInitializer implements WebBindingInitializer {

	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
//		binder.registerCustomEditor(requiredType, propertyEditor)
//		binder.addValidators(validators)
	}

}
