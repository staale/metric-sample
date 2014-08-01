package org.staale.metrics;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

/**
 * Created by staaleu on 31/7/14.
 */
public interface Reg {

    MetricRegistry REGISTRY = new MetricRegistry();

    public static Counter counter(String name) {
        return REGISTRY.counter(name);
    }

    public static Histogram histogram(String name) {
        return REGISTRY.histogram(name);
    }

    public static Meter meter(String name) {
        return REGISTRY.meter(name);
    }

    public static Timer timer(String name) {
        return REGISTRY.timer(name);
    }

    public static Counter counter(Class<?> clazz, String ... names) {
        return counter(MetricRegistry.name(clazz, names));
    }

    public static Histogram histogram(Class<?> clazz, String ... names) {
        return histogram(MetricRegistry.name(clazz, names));
    }

    public static Meter meter(Class<?> clazz, String ... names) {
        return meter(MetricRegistry.name(clazz, names));
    }

    public static Timer timer(Class<?> clazz, String ... names) {
        return timer(MetricRegistry.name(clazz, names));
    }

}
