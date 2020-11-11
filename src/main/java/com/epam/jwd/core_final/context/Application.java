package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.context.impl.menu.MainInterface;
import com.epam.jwd.core_final.exception.InvalidStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Application {
    Logger LOGGER = LoggerFactory.getLogger(Application.class);

    static void start() throws InvalidStateException {
        //todo
        NassaContext.NASSA_CONTEXT.init();
        LOGGER.info("Init successful");
        MainInterface.MAIN_INTERFACE.show();
    }
}