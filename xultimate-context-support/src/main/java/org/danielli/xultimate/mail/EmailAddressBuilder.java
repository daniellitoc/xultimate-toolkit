package org.danielli.xultimate.mail;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang3.builder.Builder;
import org.danielli.xultimate.util.CharsetUtils;
import org.springframework.mail.MailParseException;

public class EmailAddressBuilder implements Builder<String> {

    protected String address;

    protected String personal;
    
    protected String charset = CharsetUtils.CharEncoding.UTF_8;
    
    public EmailAddressBuilder() {
		
	}
    
    public EmailAddressBuilder(String address, String personal, String charset) {
		setAddress(address);
		setPersonal(personal);
		setCharset(charset);
	}
    
	public void setAddress(String address) {
		this.address = address;
	}

	public void setPersonal(String personal) {
		this.personal = personal;
	}

	public void setCharset(String charset) {
		if (CharsetUtils.isSupported(charset)) {
			this.charset = charset;
		}
	}
	
	@Override
	public String build() {
		InternetAddress internetAddress = new InternetAddress();
		internetAddress.setAddress(address);
		try {
			internetAddress.setPersonal(personal, charset);
		} catch (Exception e) {
			throw new MailParseException(e.getMessage(), e);
		}
		return internetAddress.toString();
	}
}
