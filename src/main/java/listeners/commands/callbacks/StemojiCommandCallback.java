package listeners.commands.callbacks;

import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.handler.builtin.SlashCommandHandler;
import com.slack.api.bolt.request.builtin.SlashCommandRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.block.LayoutBlock;
import controllers.ReactionStatisticController;
import listeners.Callback;

import java.io.IOException;
import java.util.List;

public class StemojiCommandCallback extends Callback implements SlashCommandHandler {

    public StemojiCommandCallback(MethodsClient client) {
        super(client);
    }

    @Override
    public Response apply(SlashCommandRequest req, SlashCommandContext ctx) throws IOException, SlackApiException {

        ReactionStatisticController reactionStatisticController = new ReactionStatisticController(client);

        String userId = req.getPayload().getUserId();
        List<LayoutBlock> blocks = reactionStatisticController.getReactionStatisticBlocks(userId);

        return Response.ok(ctx.say(blocks));
    }
}
