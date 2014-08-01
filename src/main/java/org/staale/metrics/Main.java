package org.staale.metrics;

import com.codahale.metrics.ScheduledReporter;
import com.codahale.metrics.Slf4jReporter;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * Created by staaleu on 31/7/14.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        final File root = new File(args[0]).getAbsoluteFile();
        ScheduledReporter reporter = Slf4jReporter
                .forRegistry(Reg.REGISTRY)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .convertRatesTo(TimeUnit.SECONDS)
                .build();
        reporter.start(250, TimeUnit.MILLISECONDS);
        System.out.println(root.getAbsolutePath());
        System.out.println(ForkJoinPool.commonPool().submit(new CalulateSize(root)).get());
        reporter.stop();
    }

}
