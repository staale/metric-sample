package org.staale.metrics;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Timer;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

/**
* Created by staaleu on 1/8/14.
*/
class CalulateSize extends RecursiveTask<Long> {

    private static final Counter size = Reg.counter(CalulateSize.class, "size");
    private static final Counter files = Reg.counter(CalulateSize.class, "files");
    private static final Counter directories = Reg.counter(CalulateSize.class, "directories");
    private static final Histogram dirCount = Reg.histogram(CalulateSize.class, "dirCount");
    private static final Timer jobTimer = Reg.timer(CalulateSize.class, "jobTimer");

    private final File root;

    public CalulateSize(final File root) {

        this.root = root;
    }

    @Override
    protected Long compute() {
        try (final Timer.Context ignored = jobTimer.time()) {
            if (root.isFile()) {
                files.inc(1);
                size.inc(root.length());
                return root.length();
            } else if (root.listFiles() == null) {
                directories.inc(1);
                dirCount.update(0);
                return 0L;
            } else {
                directories.inc(1);
                List<CalulateSize> children = Arrays.stream(root.listFiles()).map(CalulateSize::new).collect(Collectors.toList());
                dirCount.update(children.size());
                children.forEach(CalulateSize::fork);
                return children.stream().mapToLong(CalulateSize::join).sum();
            }
        }
    }
}
