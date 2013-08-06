package org.danielli.xultimate.context.scheduling.quartz;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.JobMethodInvocationFailedException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.MethodInvoker;
import org.springframework.util.ReflectionUtils;

/**
 * 支持数据库集群方案，用于创建JobDetail的FactoryBean。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class MethodInvokingJobDetailFactoryBean implements FactoryBean<JobDetail>, BeanNameAware, InitializingBean, ApplicationContextAware {
	
	private String beanName;
	
	private String group = Scheduler.DEFAULT_GROUP;

	private String methodInvokingBeanName;

	private JobDetail jobDetail;
	
	private static ApplicationContext applicationContext;
	
	private static Method setResultMethod;

	static {
		try {
			Class<?> jobExecutionContextClass =
					QuartzJobBean.class.getClassLoader().loadClass("org.quartz.JobExecutionContext");
			setResultMethod = jobExecutionContextClass.getMethod("setResult", Object.class);
		}
		catch (Exception ex) {
			throw new IllegalStateException("Incompatible Quartz API: " + ex);
		}
	}
	

	/**
	 * Set the group of the job.
	 * <p>Default is the default group of the Scheduler.
	 * @see org.quartz.JobDetail#setGroup
	 * @see org.quartz.Scheduler#DEFAULT_GROUP
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public void afterPropertiesSet() throws ClassNotFoundException, NoSuchMethodException {

		this.jobDetail = JobBuilder.newJob(MethodInvokingJob.class).withIdentity(beanName, group).storeDurably(true).build();
		jobDetail.getJobDataMap().put("methodInvokingBeanName", methodInvokingBeanName);
		
		postProcessJobDetail(this.jobDetail);
	}

	/**
	 * Callback for post-processing the JobDetail to be exposed by this FactoryBean.
	 * <p>The default implementation is empty. Can be overridden in subclasses.
	 * @param jobDetail the JobDetail prepared by this FactoryBean
	 */
	protected void postProcessJobDetail(JobDetail jobDetail) {
	}

	public JobDetail getObject() {
		return this.jobDetail;
	}

	public Class<? extends JobDetail> getObjectType() {
		return (this.jobDetail != null ? this.jobDetail.getClass() : JobDetail.class);
	}

	public boolean isSingleton() {
		return true;
	}

	public void setMethodInvokingBeanName(String methodInvokingBeanName) {
		this.methodInvokingBeanName = methodInvokingBeanName;
	}

	@SuppressWarnings("static-access")
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	/**
	 * Quartz Job implementation that invokes a specified method.
	 * Automatically applied by MethodInvokingJobDetailFactoryBean.
	 */
	public static class MethodInvokingJob extends QuartzJobBean {

		protected final Log logger = LogFactory.getLog(MethodInvokingJob.class);
		
		private String methodInvokingBeanName;

		public void setMethodInvokingBeanName(String methodInvokingBeanName) {
			this.methodInvokingBeanName = methodInvokingBeanName;
		}
		
		/**
		 * Invoke the method via the MethodInvoker.
		 */
		@Override
		protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
			try {
				ReflectionUtils.invokeMethod(setResultMethod, context, applicationContext.getBean(methodInvokingBeanName));
			}
			catch (Exception ex) {
				// -> "unhandled exception", to be logged at error level by Quartz
				throw new JobMethodInvocationFailedException((MethodInvoker) applicationContext.getBean("#" + methodInvokingBeanName), ex);
			}
		}
	}
}
