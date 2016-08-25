/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
 * v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License v2.0
 * w/Classpath exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause OR GPL-2.0 WITH
 * Classpath-exception-2.0
 */
package com.sun.corba.ee.impl.threadpool;

import com.sun.corba.ee.spi.threadpool.ThreadPool;
import com.sun.corba.ee.spi.threadpool.Work;
import com.sun.corba.ee.spi.threadpool.WorkQueue;
import org.glassfish.gmbal.NameValue;

/**
 * Works with thread pool to implement producer/consumer queue
 * Complete re-write of the old WorkQueue / ThreadPool implementations
 * in terms of java.util.concurrent
 *
 * @author lprimak
 */
public class WorkQueueImpl implements WorkQueue {
    public WorkQueueImpl() {
        this(new ThreadPoolImpl(WORKQUEUE_DEFAULT_NAME), WORKQUEUE_DEFAULT_NAME);
    }

    public WorkQueueImpl(ThreadPool workerThreadPool) {
        this(workerThreadPool, WORKQUEUE_DEFAULT_NAME);
    }

    public WorkQueueImpl(ThreadPool workerThreadPool, String name) {
        this.threadPool = (AbstractThreadPool)workerThreadPool;
        this.name = name;
    }

    @Override
    public void addWork(Work aWorkItem) {
        ++workItemsAdded;
        aWorkItem.setEnqueueTime(System.currentTimeMillis());
        threadPool.submit(aWorkItem);
    }

    @NameValue
    @Override
    public String getName() {
        return name;
    }

    @Override
    public long totalWorkItemsAdded() {
        return workItemsAdded;
    }

    @Override
    public int workItemsInQueue() {
        return threadPool.getQueue().size();
    }

    @Override
    public long averageTimeInQueue() {
        if (workItemsDequeued == 0) {
            return 0;
        } else { 
            return (totalTimeInQueue / workItemsDequeued);
        }
    }
    
    void incrDequeue(Work work) {
        ++workItemsDequeued;
        totalTimeInQueue += System.currentTimeMillis() - work.getEnqueueTime() ;
    }

    @Override
    public void setThreadPool(ThreadPool aThreadPool) {
        this.threadPool = (AbstractThreadPool)aThreadPool;
    }

    @Override
    public ThreadPool getThreadPool() {
        return threadPool;
    }
    
    
    private final String name;
    private AbstractThreadPool threadPool;
    
    private long workItemsAdded = 0;
    private long workItemsDequeued = 0;
    private long totalTimeInQueue = 0;

    public static final String WORKQUEUE_DEFAULT_NAME = "default-workqueue";
    private static final long serialVersionUID = 1L;
}
