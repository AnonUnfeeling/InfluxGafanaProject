package com.jdroidcoder.configs;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.jdroidcoder.components.GraphiteComponent;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.TimeUnit;

/**
 * Created by jdroidcoder on 21.01.17.
 */
@Configuration
@EnableMetrics
public class MetricsConfig extends MetricsConfigurerAdapter  {
    @Autowired
    private GraphiteComponent graphiteComponent;
    private static final Logger log = LoggerFactory.getLogger(MetricsConfig.class);

    @Override
    public void configureReporters(MetricRegistry metricRegistry) {
        log.debug("Initializing Metrics Log reporting");
        registerReporter(GraphiteReporter.forRegistry(metricRegistry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .prefixedWith("eyJrIjoiNjQyMWFabHNnWlFHQklwTzVMZHh3N1VRMmM4U1J0UnciLCJuIjoiYWRtaW4iLCJpZCI6MX0=")
                .build(graphiteComponent.getGraphite())).start(1, TimeUnit.MINUTES);
    }
}
