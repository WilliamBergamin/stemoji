package listeners.shortcuts.callbacks;

import builders.viewbuilders.ViewBuilder;
import com.slack.api.bolt.context.builtin.GlobalShortcutContext;
import com.slack.api.bolt.handler.builtin.GlobalShortcutHandler;
import com.slack.api.bolt.request.builtin.GlobalShortcutRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.views.ViewsOpenResponse;
import listeners.Callback;

import java.io.IOException;

public class StemojiGlobalShortcutCallback extends Callback implements GlobalShortcutHandler {

    public StemojiGlobalShortcutCallback(MethodsClient client) {
        super(client);
    }

    @Override
    public Response apply(GlobalShortcutRequest req, GlobalShortcutContext ctx) throws IOException, SlackApiException {

        ViewsOpenResponse viewsOpenRes = ctx.client().viewsOpen(viewOpenReqBuilder -> viewOpenReqBuilder
                .triggerId(ctx.getTriggerId())
                .view(ViewBuilder.getStemojiView()));

        if (!viewsOpenRes.isOk()) {
            logger.error(viewsOpenRes.toString());
        }

        return ctx.ack();
    }
}
