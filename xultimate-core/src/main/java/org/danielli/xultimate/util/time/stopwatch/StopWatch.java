package org.danielli.xultimate.util.time.stopwatch;

import java.util.LinkedList;

/**
 * 秒表。
 * 
 * @author Daniel Li
 * @since 17 Jun 2013
 */
public class StopWatch {

	private final String id;	// 唯一标识。
	
	/**
	 * 任务列表
	 */
	protected final LinkedList<TaskInfo> taskList = new LinkedList<TaskInfo>();
	
	/**
	 * 秒表开始时间。时间单位为纳秒。
	 */
	protected long startTime;
	/**
	 * 秒表结束时间。时间单位为纳秒。
	 */
	protected long stopTime;
	/**
	 * 当前任务的开始时间。时间单位为纳秒。
	 */
	protected long currentTaskStartTime;

	/**
	 * 秒表当前状态
	 */
	protected StopWatchState state = StopWatchUnStartedState.INSTANCE;

	public StopWatch(String id) {
		this.id = id;
	}

	/**
	 * 开始秒表计时。
	 */
	public void start() throws IllegalStateException {
		state.start(this);
	}

	/**
	 * 结束秒表计时。
	 */
	public void stop() throws IllegalStateException {
        state.stop(this);
	}
	
	/**
	 * 重置秒表计时。
	 */
    public void reset() {
        state.reset(this);
    }
    
	/**
	 * 暂停秒表计时。
	 */
    public void suspend() throws IllegalStateException {
        state.suspend(this);
    }
    
	/**
	 * 恢复秒表计时。
	 */
    public void resume() throws IllegalStateException {
       state.resume(this);
    }
	
	/**
	 * 标记任务。
	 */
	public void mark(String taskName) throws IllegalStateException {
		state.mark(this, taskName);
	}

	/**
	 * 获取上一个任务。
	 */
	public TaskInfo getLastTaskInfo() throws IllegalStateException {
		TaskInfo lastTask = this.taskList.getLast();
		if (lastTask == null) {
			throw new IllegalStateException("No tasks run: can't get last task info");
		}
		return lastTask;
	}

	/**
	 * 获取秒表执行时间间隔，时间单位为纳秒。
	 */
	public long getTotalTime() {
		return state.getTotalTime(this);
	}
	
	/**
	 * 获取秒表的标识符。
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * 获取秒表开始时间。时间单位为纳秒。
	 */
	public long getStartTime() {
		return state.getStartTime(this);
	}

	/**
	 * 获取秒表结束时间。时间单位为纳秒。
	 * @return
	 */
	public long getStopTime() {
		return state.getStopTime(this);
	}
	
	
	/**
	 * 获取任务个数。
	 */
	public int getTaskCount() {
		return this.taskList.size();
	}

	/**
	 * 获取任务列表。
	 */
	public TaskInfo[] getTaskInfo() {
		return this.taskList.toArray(new TaskInfo[this.taskList.size()]);
	}

	/**
	 * Inner class to hold data about one task executed within the stop watch.
	 */
	public static final class TaskInfo {

		private final String taskName;

		private final long startTime;
		
		private final long stopTime;

		TaskInfo(String taskName, long startTime, long stopTime) {
			this.taskName = taskName;
			this.startTime = startTime;
			this.stopTime = stopTime;
		}

		public String getTaskName() {
			return this.taskName;
		}

		public long getStartTime() {
			return startTime;
		}

		public long getStopTime() {
			return stopTime;
		}
		
		public long getTotalTime() {
			return this.stopTime - this.startTime;
		}
	}
}