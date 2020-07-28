package io.github.silencecorner;

import com.xxl.job.core.enums.ExecutorBlockStrategyEnum;
import com.xxl.job.core.glue.GlueTypeEnum;

/**
 * xxl-job info
 *
 * @author xuxueli  2016-1-12 18:25:49
 */
public class XxlJobInfo {
	
	private int id;				// 主键ID

	private int jobGroup;		// 执行器主键ID
	private String jobCron;		// 任务执行CRON表达式
	private String jobDesc;
	
	private String author;		// 负责人
	private String alarmEmail;	// 报警邮件

	private GlueTypeEnum glueType;

	private String executorRouteStrategy;	// 执行器路由策略
	private String executorHandler;		    // 执行器，任务Handler名称
	private String executorParam;		    // 执行器，任务参数
	private ExecutorBlockStrategyEnum executorBlockStrategy;	// 阻塞处理策略
	private int executorTimeout;     		// 任务执行超时时间，单位秒
	private int executorFailRetryCount;		// 失败重试次数

	private String childJobId;		// 子任务ID，多个逗号分隔

	private int triggerStatus;		// 调度状态：0-停止，1-运行
	private long triggerLastTime;	// 上次调度时间
	private long triggerNextTime;	// 下次调度时间

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(int jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getJobCron() {
		return jobCron;
	}

	public void setJobCron(String jobCron) {
		this.jobCron = jobCron;
	}

	public String getJobDesc() {
		return jobDesc;
	}

	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAlarmEmail() {
		return alarmEmail;
	}

	public void setAlarmEmail(String alarmEmail) {
		this.alarmEmail = alarmEmail;
	}

	public GlueTypeEnum getGlueType() {
		return glueType;
	}

	public void setGlueType(GlueTypeEnum glueType) {
		this.glueType = glueType;
	}

	public String getExecutorRouteStrategy() {
		return executorRouteStrategy;
	}

	public void setExecutorRouteStrategy(String executorRouteStrategy) {
		this.executorRouteStrategy = executorRouteStrategy;
	}

	public String getExecutorHandler() {
		return executorHandler;
	}

	public void setExecutorHandler(String executorHandler) {
		this.executorHandler = executorHandler;
	}

	public String getExecutorParam() {
		return executorParam;
	}

	public void setExecutorParam(String executorParam) {
		this.executorParam = executorParam;
	}

	public ExecutorBlockStrategyEnum getExecutorBlockStrategy() {
		return executorBlockStrategy;
	}

	public void setExecutorBlockStrategy(ExecutorBlockStrategyEnum executorBlockStrategy) {
		this.executorBlockStrategy = executorBlockStrategy;
	}

	public int getExecutorTimeout() {
		return executorTimeout;
	}

	public void setExecutorTimeout(int executorTimeout) {
		this.executorTimeout = executorTimeout;
	}

	public int getExecutorFailRetryCount() {
		return executorFailRetryCount;
	}

	public void setExecutorFailRetryCount(int executorFailRetryCount) {
		this.executorFailRetryCount = executorFailRetryCount;
	}

	public String getChildJobId() {
		return childJobId;
	}

	public void setChildJobId(String childJobId) {
		this.childJobId = childJobId;
	}

	public int getTriggerStatus() {
		return triggerStatus;
	}

	public void setTriggerStatus(int triggerStatus) {
		this.triggerStatus = triggerStatus;
	}

	public long getTriggerLastTime() {
		return triggerLastTime;
	}

	public void setTriggerLastTime(long triggerLastTime) {
		this.triggerLastTime = triggerLastTime;
	}

	public long getTriggerNextTime() {
		return triggerNextTime;
	}

	public void setTriggerNextTime(long triggerNextTime) {
		this.triggerNextTime = triggerNextTime;
	}
}
