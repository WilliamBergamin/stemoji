package listeners.events.callbacks;

import com.slack.api.app_backend.events.payload.EventsApiPayload;
import com.slack.api.bolt.context.builtin.EventContext;
import com.slack.api.bolt.handler.BoltEventHandler;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.event.AppHomeOpenedEvent;
import com.slack.api.model.view.View;
import controllers.ReactionStatisticController;
import listeners.Callback;

import java.io.IOException;
import java.util.List;

import static com.slack.api.model.view.Views.view;

public class AppHomeOpenedCallback extends Callback
        implements BoltEventHandler<AppHomeOpenedEvent> {

    public AppHomeOpenedCallback(MethodsClient client) {
        super(client);
    }

    @Override
    public Response apply(EventsApiPayload<AppHomeOpenedEvent> pl, EventContext ctx) throws IOException, SlackApiException {
        ReactionStatisticController reactionStatisticController = new ReactionStatisticController(client);

        String userId = pl.getEvent().getUser();
        List<LayoutBlock> blocks = reactionStatisticController.getReactionStatisticBlocks(userId);
        logger.info(blocks.toString());

        View appHomeView = view(view -> view.type("home").blocks(blocks));
        ctx.client().viewsPublish(viewsPubReqBuilder -> viewsPubReqBuilder
                        .userId(pl.getEvent().getUser())
//                        .hash(payload.getEvent().getView().getHash())
                        .view(appHomeView));
        return ctx.ack();
    }
}
