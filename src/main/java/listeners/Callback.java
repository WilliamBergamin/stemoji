package listeners;

import com.slack.api.methods.MethodsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Callback {

    protected final Logger logger;
    protected MethodsClient client;

    protected Callback(MethodsClient client) {
        logger = LoggerFactory.getLogger(this.getClass());
        this.client = client;
        logger.info(this.getClass().getSimpleName() + " REGISTERED");
    }
}
