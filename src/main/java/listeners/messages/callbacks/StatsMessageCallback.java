package listeners.messages.callbacks;

import com.slack.api.app_backend.events.payload.EventsApiPayload;
import com.slack.api.bolt.context.builtin.EventContext;
import com.slack.api.bolt.handler.BoltEventHandler;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.event.MessageEvent;
import controllers.ReactionStatisticController;
import listeners.Callback;

import java.io.IOException;
import java.util.List;

public class StatsMessageCallback extends Callback implements BoltEventHandler<MessageEvent> {

    public StatsMessageCallback(MethodsClient client) {
        super(client);
    }

    @Override
    public Response apply(EventsApiPayload<MessageEvent> pl, EventContext ctx) throws IOException, SlackApiException {
        logger.info("Stats message executing");

        ReactionStatisticController reactionStatisticController = new ReactionStatisticController(client);
        List<LayoutBlock> blocks = reactionStatisticController.getReactionStatisticBlocks(pl.getEvent().getUser());

        ChatPostMessageResponse msgRes = ctx.say(blocks);
        if (!msgRes.isOk()) {
            logger.error("Something went wrong with the chatPostMessage");
            logger.error(msgRes.toString());
        }

        return ctx.ack();
    }
}
