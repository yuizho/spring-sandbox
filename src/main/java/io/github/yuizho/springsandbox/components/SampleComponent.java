package io.github.yuizho.springsandbox.components;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class SampleComponent {
    private final Logger logger;

    public SampleComponent(Logger logger) {
        this.logger = logger;
    }

    public boolean isWorking() {
        logger.debug("I'm fine!!!");
        return true;
    }
}
