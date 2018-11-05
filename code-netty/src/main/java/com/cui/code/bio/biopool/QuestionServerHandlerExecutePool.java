package com.cui.code.bio.biopool;

import lombok.extern.slf4j.Slf4j;

import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 问题处理器线程池，加上了队列
 *
 * @author cuishixiang
 * @date 2018-11-05
 */
@Slf4j
public class QuestionServerHandlerExecutePool {

    private Socket socket;
    private ExecutorService executorService;

    public QuestionServerHandlerExecutePool(Socket socket) {
        this.socket = socket;
    }

    public QuestionServerHandlerExecutePool(int maxPoolSize, int queueSize) {
        executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), maxPoolSize,
                2, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(queueSize));
    }

    public void execute(Runnable task) {
        executorService.execute(task);
    }
}
