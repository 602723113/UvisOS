package cc.zoyn.uvisos.timer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Timing {

	private Thread thread;
	private boolean isRun = true;
	private static Timing instance;
	private static Map<String, Task> taskMap = new ConcurrentHashMap<String, Task>();
	private static SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

	static {
		taskMap.put("06:00:00", new SixClockTask());
	}

	public Timing() {
		instance = this;
	}

	public static Timing getInstance() {
		return instance;
	}

	public static boolean addTask(String time, Task task) {
		if (time == null || task == null) {
			return false;
		}
		if (!time.contains(":")) {
			return false;
		}
		taskMap.put(time, task);
		return true;
	}

	public boolean isRun() {
		return isRun;
	}

	public void setRun(boolean run) {
		isRun = run;
	}

	public void startup() {
		Thread thread = new Thread(() -> {
			while (isRun) {
				try {
					Thread.sleep(1000L); // 每秒才进行一次计算
					String temp = format.format(new Date());
//                    System.out.println(temp);
					taskMap.forEach((s, task) -> {
						if (temp.equalsIgnoreCase(s)) {
							task.run();
						}
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		this.thread = thread;
		thread.setName("Timing");
		thread.start();
	}

	public Thread getTimerThread() {
		return this.thread;
	}

}
