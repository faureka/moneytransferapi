package com.faizan.revolut;

import com.faizan.revolut.server.logic.config.RevolutConfig;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class MoneyTransfer extends Application<RevolutConfig> {
    private static final String REST_RESOURCE_PKG = "com.faizan.revolut.webservices.rest";

    public static void main(String[] args) throws Exception {
        new MoneyTransfer().run(args);
    }

    @Override
    public void initialize(Bootstrap<RevolutConfig> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<RevolutConfig>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(RevolutConfig configuration) {
                return configuration.getSwaggerBundleConfiguration();
            }
        });
    }

    @Override
    public void run(RevolutConfig configuration, Environment environment) throws Exception {
        environment.getObjectMapper().setSerializationInclusion(Include.ALWAYS);
        environment.jersey().packages(REST_RESOURCE_PKG);
        environment.lifecycle().manage(new BootstrapService());
    }

    @Override
    public String getName() {
        return "REVOLUT";
    }
}
