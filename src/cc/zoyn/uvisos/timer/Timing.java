package cc.zoyn.uvisos.timer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Timing {

    private Thread thread;
    private boolean isRun = true;
    private static Map<String, Task> taskMap = new HashMap<>();
    private static SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

    static {
        taskMap.put("6:00:00", new SixClockTask());
        taskMap.put("18:06:00", new SixClockTask());
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
