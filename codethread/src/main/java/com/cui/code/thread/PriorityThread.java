package com.cui.code.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 线程优先级测试
 * <p>
 * 输出；
 * job priority :1,  Count:1267826
 * job priority :1,  Count:1259360
 * job priority :1,  Count:1274027
 * job priority :1,  Count:1281035
 * job priority :1,  Count:1266048
 * job priority :10,  Count:1268981
 * job priority :10,  Count:1255335
 * job priority :10,  Count:1278892
 * job priority :10,  Count:1283777
 * job priority :10,  Count:1275140
 * 可见在Mac10.12上设置优先级没有效果
 * <p>
 * Created by cuishixiang on 2017-10-10.
 */
public class PriorityThread {
    private static volatile boolean notStart = true;
    private static volatile boolean notEnd = true;

    public static void main(String[] args) throws InterruptedException {
        List<Job> jobs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int priority = i < 5 ? Thread.MIN_PRIORITY : Thread.MAX_PRIORITY;
            Job job = new Job(priority);
            jobs.add(job);

            Thread thread = new Thread(job, "Thread" + i);
            thread.setPriority(priority);
            thread.start();
        }

        notStart = false;
        TimeUnit.SECONDS.sleep(10);

        notEnd = false;
        for (Job job : jobs) {
            System.out.println("job priority :" + job.priority + ",  Count:" + job.jobCount);
        }
    }

    static class Job implements Runnable {
        private int priority;
        private long jobCount;

        public Job(int priority) {
            this.priority = priority;
        }

        @Override
        public void run() {
            while (notStart) {
                Thread.yield();
            }
            while (notEnd) {
                Thread.yield();
                jobCount++;
            }
        }
    }
}

