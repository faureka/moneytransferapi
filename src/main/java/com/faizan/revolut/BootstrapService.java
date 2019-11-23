package com.faizan.revolut;

import com.faizan.revolut.server.logic.AccountsLogicHelper;
import io.dropwizard.lifecycle.Managed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BootstrapService implements Managed {
    private static final Logger logger = LoggerFactory.getLogger(BootstrapService.class);

    @Override
    public void start() throws Exception {
        AccountsLogicHelper.bootstrapAccounts();
        logger.info("Server started");
    }

    @Override
    public void stop() throws Exception {
        logger.info("Server stopped");
    }
}
