package org.danielli.xultimate.orm.jpa.ds.support;

import org.danielli.xultimate.orm.jpa.ds.Value;

public class StringValue implements Value<String> {
	private static final long serialVersionUID = -121791044011273864L;
	
	private String sourceValue;
	private boolean hasLeftPercent;
	private boolean hasRightPercent;
	
	@Override
	public String getValue() {
		StringBuilder builder = new StringBuilder();
		if (hasLeftPercent) {
			builder.append("%");
		}
		builder.append(sourceValue);
		if (hasRightPercent) {
			builder.append("%");
		}
		return builder.toString();
	}

	public boolean isHasLeftPercent() {
		return hasLeftPercent;
	}

	public void setHasLeftPercent(boolean hasLeftPercent) {
		this.hasLeftPercent = hasLeftPercent;
	}

	public boolean isHasRightPercent() {
		return hasRightPercent;
	}

	public void setHasRightPercent(boolean hasRightPercent) {
		this.hasRightPercent = hasRightPercent;
	}

	public String getSourceValue() {
		return sourceValue;
	}

	public void setSourceValue(String sourceValue) {
		this.sourceValue = sourceValue;
	}
}
