package org.danielli.xultimate.context.mail;

import httl.Engine;
import httl.Template;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.danielli.xultimate.ui.httl.HTTLEngineUtils;
import org.danielli.xultimate.util.CharsetUtils;
import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.util.reflect.BeanUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.tamper.BeanMapping;
import com.alibaba.tamper.core.builder.BeanMappingBuilder;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext-service-config.xml", "classpath:/mail/applicationContext-service-crypto.xml", "classpath:/mail/applicationContext-service-mail.xml" })
public class JavaMailSenderTest {
	
	@Resource(name = "javaMailSender")
	private JavaMailSender javaMailSender;
	
	@Resource(name = "simpleMailMessage")
	private SimpleMailMessage simpleMailMessage;
	
	@Resource(name = "greenMail")
	private GreenMail greenMail;
	
	private BeanMapping mapping = null;
	{
		BeanMappingBuilder builder = new BeanMappingBuilder() {
			
			@Override
			protected void configure() {
				mapping(SimpleMailMessage.class, MimeMessageHelper.class).batch(false).reversable(false);
				fields(srcField("from", String.class), targetField("from", String.class));
				fields(srcField("to", String[].class), targetField("to", String[].class));
				fields(srcField("replyTo", String.class), targetField("replyTo", String.class));
				fields(srcField("cc", String[].class), targetField("cc", String[].class));
				fields(srcField("bcc", String[].class), targetField("bcc", String[].class));
			}
		};
		mapping = new BeanMapping(builder);
	}
	
	@Test
	public void test1() {
		SimpleMailMessage msg = new SimpleMailMessage();
		BeanUtils.copyProperties(simpleMailMessage, msg);
		String subject = "Test Simple Mail Message";
		msg.setSubject(subject);
		String text = "This is a simple mail";
		msg.setText(text);
		try {
			javaMailSender.send(msg); 
		} catch (MailParseException e) {
			e.printStackTrace();	// 当发生邮件消息解析错误时
		} catch (MailAuthenticationException e) {
			e.printStackTrace(); 	// 当认证失败时抛出
		} catch (MailSendException e) {
			e.printStackTrace(); 	// 当发送邮件消息失败时抛出
		} catch (MailException e) {
			e.printStackTrace();	// 其他异常
		}
		try {
			greenMail.waitForIncomingEmail(2000, 1);
			Message[] messages = greenMail.getReceivedMessages();
			Assert.assertEquals(1, messages.length);
			Assert.assertEquals(subject, messages[0].getSubject());
			Assert.assertEquals(text, StringUtils.trim(GreenMailUtil.getBody(messages[0])));
		} catch (InterruptedException | MessagingException e) {
			e.printStackTrace();
		}
	}
	
//	@Test
	public void test2() {
		try {
			MimeMessage msg = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg , false, CharsetUtils.UTF_8.name());
			mapping.mapping(simpleMailMessage, helper);
			String subject = "Test Mime Message";
			helper.setSubject(subject);
			String htmlText =	"<html>" +
									"<head><meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\"></head>" +
									"<body>" +
										"<font color='red' size='30'>This is a simple mail</font>" +
									"</body>" +
								"</html>";
			helper.setText(htmlText, true);
			javaMailSender.send(msg);
			
			greenMail.waitForIncomingEmail(2000, 1);
			Message[] messages = greenMail.getReceivedMessages();
			Assert.assertEquals(1, messages.length);
			Assert.assertEquals(subject, messages[0].getSubject());
			Assert.assertEquals(htmlText, StringUtils.trim(GreenMailUtil.getBody(messages[0])));
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
	}
	
//	@Test
	public void test3() {
		try {
			MimeMessage msg = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg , true, CharsetUtils.UTF_8.name());
			mapping.mapping(simpleMailMessage, helper);
			String subject = "Test Mime Message With inline element";
			helper.setSubject(subject);
			String htmlText =	"<html>" +
									"<head><meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\"></head>" +
									"<body>" +
										"<font color='red' size='30'>This is a simple mail</font><div><img src='cid:img01'></img></div>" +
									"</body>" +
								"</html>";
			helper.setText(htmlText, true);
			ClassPathResource img = new ClassPathResource("/mail/img01.png");
			helper.addInline("img01",img);
			javaMailSender.send(msg);
			
			greenMail.waitForIncomingEmail(2000, 1);
			Message[] messages = greenMail.getReceivedMessages();
			Assert.assertEquals(1, messages.length);
			Assert.assertEquals(subject, messages[0].getSubject());
			Assert.assertEquals(htmlText, StringUtils.trim(GreenMailUtil.getBody(messages[0])));
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
//	@Test
	public void test4() {
		try {
			MimeMessage msg = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg , true, CharsetUtils.UTF_8.name());
			mapping.mapping(simpleMailMessage, helper);
			String subject = "Test Mime Message With Attachment";
			helper.setSubject(subject);
			String htmlText =	"<html>" +
									"<head><meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\"></head>" +
									"<body>" +
										"<font color='red' size='30'>This is a simple mail</font>" +
									"</body>" +
								"</html>";
			helper.setText(htmlText, true);
			ClassPathResource file1 = new ClassPathResource("/mail/file1.zip");
			helper.addAttachment("file01.zip",file1.getFile());
			ClassPathResource file2 = new ClassPathResource("/mail/file2.doc");
			helper.addAttachment("file02.doc",file2.getFile());
			javaMailSender.send(msg);
			
			greenMail.waitForIncomingEmail(2000, 1);
			Message[] messages = greenMail.getReceivedMessages();
			Assert.assertEquals(1, messages.length);
			Assert.assertEquals(subject, messages[0].getSubject());
			Assert.assertEquals(htmlText, StringUtils.trim(GreenMailUtil.getBody(messages[0])));
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	@Test
	public void test5() {
		try {
			MimeMessage msg = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg , true, CharsetUtils.UTF_8.name());
			mapping.mapping(simpleMailMessage, helper);
			String subject = "Test Mime Message";
			helper.setSubject(subject);
			String htmlText =	"<html>" +
									"<head><meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\"></head>" +
									"<body>" +
										"<font color='red' size='30'>This is a simple mail</font>" +
									"</body>" +
								"</html>";
			helper.setText("This is a simple mail", htmlText);
			javaMailSender.send(msg);
			
			greenMail.waitForIncomingEmail(2000, 1);
			Message[] messages = greenMail.getReceivedMessages();
			Assert.assertEquals(1, messages.length);
			Assert.assertEquals(subject, messages[0].getSubject());
			Assert.assertEquals(htmlText, StringUtils.trim(GreenMailUtil.getBody(messages[0])));
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	// 通过模板定制邮件内容
	@Resource(name = "httlEngine")
	private Engine httlEngine;
	
//	@Test
	public void test6() {
		try {
			MimeMessage msg = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg , false, CharsetUtils.UTF_8.name());
			mapping.mapping(simpleMailMessage, helper);
			String subject = "Test Mime Message";
			helper.setSubject(subject);
			Template template = httlEngine.getTemplate("/org/danielli/xultimate/context/mail/template/hello_world.httl", CharsetUtils.CharEncoding.UTF_8);
			Map<String, String> map = new HashMap<String, String>();
			map.put("userName", "Daniel Li");
			String htmlText = HTTLEngineUtils.processTemplateIntoString(template, map);
			
			helper.setText(htmlText, true);
			javaMailSender.send(msg);
			
			greenMail.waitForIncomingEmail(2000, 1);
			Message[] messages = greenMail.getReceivedMessages();
			Assert.assertEquals(1, messages.length);
			Assert.assertEquals(subject, messages[0].getSubject());
			Assert.assertEquals(htmlText, StringUtils.trim(GreenMailUtil.getBody(messages[0])));
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 异步发送邮件
	@Resource(name = "taskExecutor")
	private TaskExecutor taskExecutor;
	
//	@Test
	public void test7() {
		try {
			final MimeMessage msg = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg , false, CharsetUtils.UTF_8.name());
			mapping.mapping(simpleMailMessage, helper);
			final String subject = "Test Mime Message";
			helper.setSubject(subject);
			
			Template template = httlEngine.getTemplate("/org/danielli/xultimate/context/mail/template/hello_world.httl", CharsetUtils.CharEncoding.UTF_8);
			Map<String, String> map = new HashMap<String, String>();
			map.put("userName", "Daniel Li");
			final String htmlText = HTTLEngineUtils.processTemplateIntoString(template, map);
			
			helper.setText(htmlText, true);
			System.out.println(htmlText);
			taskExecutor.execute(new Runnable() {
				@Override
				public void run() {
					
					try {
						javaMailSender.send(msg);
						
						greenMail.waitForIncomingEmail(2000, 1);
						Message[] messages = greenMail.getReceivedMessages();
						Assert.assertEquals(1, messages.length);
						Assert.assertEquals(subject, messages[0].getSubject());
						Assert.assertEquals(htmlText, StringUtils.trim(GreenMailUtil.getBody(messages[0])));
					} catch (MessagingException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			});
		} catch (MessagingException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
//	// 使用定时任务发送邮件
//	@Resource(name = "quartzClusteredScheduler")
//	private Scheduler scheduler;
//	class SendMailJob implements Job {
//		@Override
//		public void execute(JobExecutionContext context) throws JobExecutionException {
//			JobDataMap dataMap = context.getJobDetail().getJobDataMap();
//			String from = dataMap.getString("from");
//			String[] to = (String[]) dataMap.get("to");
//			String[] cc = (String[]) dataMap.get("cc");
//			String[] bcc = (String[]) dataMap.get("bcc");
//			try {
//				MimeMessage msg = javaMailSender.createMimeMessage();
//				MimeMessageHelper helper = new MimeMessageHelper(msg , false, CharsetUtils.UTF_8.name());
//				helper.setFrom(from);
//				helper.setTo(to);
//				helper.setReplyTo(from);
//				helper.setCc(cc);
//				helper.setBcc(bcc);
//				helper.setSubject("Test Mime Message");
//				String htmlText =	"<html>" +
//										"<head><meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\"></head>" +
//										"<body>" +
//											"<font color='red' size='30'>This is a simple mail</font>" +
//										"</body>" +
//									"</html>";
//				helper.setText(htmlText, true);
//				javaMailSender.send(msg);
//			} catch (MessagingException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	public void test8() {
//		JobDetail jobDetail = JobBuilder.newJob(SendMailJob.class).withIdentity("sendMailJob", Scheduler.DEFAULT_GROUP).build();
//		
//		jobDetail.getJobDataMap().put("from", "daniellitoc@gmail.com");
//		jobDetail.getJobDataMap().put("to", new String[] { "daniellitoc@yahoo.com" });
//		jobDetail.getJobDataMap().put("cc", new String[] { "daniellitoc@msn.com" });
//		jobDetail.getJobDataMap().put("bcc", new String[] { "wiccue@msn.com" });
//		
//		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("sendMailTrigger", Scheduler.DEFAULT_GROUP).startNow().build();
//		try {
//			scheduler.scheduleJob(jobDetail, trigger);
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//		}
//	}
}
