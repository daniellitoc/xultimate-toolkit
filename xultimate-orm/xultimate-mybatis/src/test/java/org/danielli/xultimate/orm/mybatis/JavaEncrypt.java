package org.danielli.xultimate.orm.mybatis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.util.crypto.CipherUtils;
import org.danielli.xultimate.util.crypto.SymmetricAlgorithms;
import org.danielli.xultimate.util.io.FileUtils;
import org.danielli.xultimate.util.io.IOUtils;

public class JavaEncrypt {

	public static void main(String[] args) throws IOException {
		File file = new File("/home/toc/Documents/git/xultimate-toolkit/xultimate-orm/xultimate-mybatis/src/test/resources/划分");
		// 加密
//		FileInputStream tmpInput = FileUtils.openInputStream(file);
//		FileOutputStream tmpOutput = FileUtils.openOutputStream(new File(file.getParentFile(), "Encrypt" + file.getName()));
//		CipherUtils.encrypt(SymmetricAlgorithms.AES.getCipher(), SymmetricAlgorithms.AES.getKey(""), tmpInput, tmpOutput);
//		IOUtils.closeQuietly(tmpInput);
//		IOUtils.closeQuietly(tmpOutput);

		// 解密
		FileInputStream tmpInput = FileUtils.openInputStream(file);
		FileOutputStream tmpOutput = FileUtils.openOutputStream(new File(file.getParentFile(), StringUtils.replace(file.getName(), "Encrypt", "")));
		CipherUtils.decrypt(SymmetricAlgorithms.AES.getCipher(), SymmetricAlgorithms.AES.getKey(""), tmpInput, tmpOutput);
		IOUtils.closeQuietly(tmpInput);
		IOUtils.closeQuietly(tmpOutput);
	}

}
