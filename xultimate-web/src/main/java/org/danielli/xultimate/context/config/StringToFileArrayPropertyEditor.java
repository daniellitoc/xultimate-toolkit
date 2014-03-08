package org.danielli.xultimate.context.config;

import java.beans.PropertyEditorSupport;
import java.io.File;

import org.apache.commons.lang3.ArrayUtils;
import org.danielli.xultimate.util.io.ResourceUtils;
import org.springframework.core.io.Resource;

public class StringToFileArrayPropertyEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		try {
			Resource[] resources = ResourceUtils.getResources(text);
			if (ArrayUtils.isNotEmpty(resources)) {
				File[] files = new File[resources.length];
				for (int i = 0; i < files.length; i++) {
					files[i] = resources[i].getFile();
				}
				setValue(files);
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
		
	}
}
