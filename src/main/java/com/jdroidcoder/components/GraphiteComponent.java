package com.jdroidcoder.components;

import com.codahale.metrics.graphite.Graphite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * Created by jdroidcoder on 21.01.17.
 */
@Component
public class GraphiteComponent {
    private Graphite graphite = null;


    @Autowired
    public GraphiteComponent(@Value("${graphite.host}") String host,
                             @Value("${graphite.port}") Integer port) {
        if (graphite == null) {
            graphite = new Graphite(new InetSocketAddress(host, port));
        }
    }

    public Graphite getGraphite() {
        return graphite;
    }
}
