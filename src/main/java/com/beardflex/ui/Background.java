package com.beardflex.ui;

import javafx.concurrent.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by David on 14/03/2017.
 */
public class Background implements ThreadFactory {

    private static Background instance;
    private Background(){}
    public static Background get() {
        if(instance == null) {
            instance = new Background();
        }
        return instance;
    }

    private final Logger log = LogManager.getLogger();
    private final AtomicInteger counter = new AtomicInteger();
    private final int Max_Background_Threads = 10;
    private final String Name = "/dev/tools Background Worker";
    private final String Name_Pattern = "%s-%d";
    private final ExecutorService backgroundService = Executors.newFixedThreadPool(
            Max_Background_Threads,
            this);

    public void fireAndForget(Task<?> task) {
        backgroundService.submit(task);
    }

    public <T> Future<T> monitor(Callable<T> task) {
        return backgroundService.submit(task);
    }

    @Override
    public Thread newThread(Runnable r) {
        final String threadName = String.format(Name_Pattern, Name, counter.getAndIncrement());
        return new Thread(r, threadName);
    }
}
