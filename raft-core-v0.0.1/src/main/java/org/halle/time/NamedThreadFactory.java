package org.halle.time;

import org.halle.util.Requires;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {
    private static final Logger LOG  = LoggerFactory.getLogger(NamedThreadFactory.class);
    private final AtomicInteger id             = new AtomicInteger(0);
    private final boolean                            daemon;
    private final String        name;
    private final int           priority;
    private final ThreadGroup   group;
    @Override
    public Thread newThread(Runnable r) {
        Requires.requireNonNull(r, "runnable");
        final String name2 = this.name + this.id.getAndIncrement();
        final Thread t = wrapThread(this.group, r, name2);

        try {
            if (t.isDaemon() != this.daemon) {
                t.setDaemon(this.daemon);
            }

            if (t.getPriority() != this.priority) {
                t.setPriority(this.priority);
            }
        } catch (final Exception ignored) {
            // Doesn't matter even if failed to set.
        }

        LOG.info("Creates new {}.", t);
        return t;
    }
    public NamedThreadFactory(String name) {
        this(name, false, Thread.NORM_PRIORITY);
    }


    public NamedThreadFactory(String name, int priority) {
        this(name, false, priority);
    }

    public NamedThreadFactory(String name, boolean daemon, int priority) {
        this.name = name + " #";
        this.daemon = daemon;
        this.priority = priority;
        final SecurityManager s = System.getSecurityManager();
        this.group = (s == null) ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
    }


    private static final class LogUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            LOG.error("Uncaught exception in thread {}", t, e);
        }
    }
    protected Thread wrapThread(final ThreadGroup group, final Runnable r, final String name) {
        return new Thread(group, r, name);
    }

}
