package com.faizan.revolut;

import com.faizan.revolut.server.logic.config.RevolutConfig;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class MoneyTransfer extends Application<RevolutConfig> {
    private static final String REST_RESOURCE_PKG = "com.faizan.revolut.webservices.rest";

    public static void main(String[] args) throws Exception {
        new MoneyTransfer().run(args);
    }

    @Override
    public void initialize(Bootstrap<RevolutConfig> configuration) {

        //add capability to replace from env for a particular config variable
        configuration.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(configuration.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)));
    }

    @Override
    public void run(RevolutConfig configuration, Environment environment) throws Exception {
        environment.getObjectMapper().setSerializationInclusion(Include.ALWAYS);
        environment.jersey().register(REST_RESOURCE_PKG);
        environment.lifecycle().manage(new BootstrapService());
    }

    @Override
    public String getName() {
        return "REVOLUT";
    }
}
