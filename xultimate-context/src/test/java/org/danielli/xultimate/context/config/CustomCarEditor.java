package org.danielli.xultimate.context.config;

import java.beans.PropertyEditorSupport;

import org.springframework.stereotype.Service;

@Service("customCarEditor")
public class CustomCarEditor extends PropertyEditorSupport {
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		String[] infos = text.split(",");
		Car car = new Car();
		car.setBrand(infos[0]);
		car.setMaxSpeed(Integer.parseInt(infos[1]));
		car.setPrice(Double.parseDouble(infos[2]));
		setValue(car);
	}
}
