package com.galaxy.authority.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyThreadPool {
	private static MyThreadPool threadPool = null;
    private static ExecutorService pool = null;


    public static synchronized MyThreadPool get(){
        if(threadPool==null){
            threadPool = new MyThreadPool();
            if(pool==null){
                pool = Executors.newFixedThreadPool(StaticConst.THREAD_POOL_SIZE);
            }
        }
        return threadPool;
    }

    public void execute(Runnable runnable){
        pool.execute(runnable);
    }

}
