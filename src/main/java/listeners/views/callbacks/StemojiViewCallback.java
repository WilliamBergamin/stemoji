package listeners.views.callbacks;

import builders.viewbuilders.ViewBuilder;
import com.slack.api.bolt.context.builtin.ViewSubmissionContext;
import com.slack.api.bolt.handler.builtin.ViewSubmissionHandler;
import com.slack.api.bolt.request.builtin.ViewSubmissionRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.conversations.ConversationsOpenRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.conversations.ConversationsOpenResponse;
import com.slack.api.methods.response.views.ViewsOpenResponse;
import com.slack.api.methods.response.views.ViewsUpdateResponse;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.view.View;
import com.slack.api.model.view.ViewState;
import controllers.ReactionStatisticController;
import listeners.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class StemojiViewCallback extends Callback implements ViewSubmissionHandler {

    public StemojiViewCallback(MethodsClient client) {
        super(client);
    }

    @Override
    public Response apply(ViewSubmissionRequest req, ViewSubmissionContext ctx) throws SlackApiException, IOException {
        View view = req.getPayload().getView();
        Map<String, Map<String, ViewState.Value>> stateValues = view.getState().getValues();

        String selectedUserId = stateValues.get("user-select-block").get("user").getSelectedUser();

        ReactionStatisticController reactionStatisticController = new ReactionStatisticController(client);
        List<LayoutBlock> blocks = reactionStatisticController.getReactionStatisticBlocks(selectedUserId);

        ConversationsOpenResponse convoOpenRes = client.conversationsOpen(reqBuilder -> reqBuilder
                .users(Arrays.asList(req.getPayload().getUser().getId()))
        );
        String userDmChannelId = convoOpenRes.getChannel().getId();

        ChatPostMessageResponse chatMsgRes = client.chatPostMessage(chatMsgBuilder -> chatMsgBuilder
                .channel(userDmChannelId)
                .blocks(blocks)
        );

        if (!chatMsgRes.isOk()){
            logger.error(chatMsgRes.toString());
        }

        return ctx.ack();
    }
}
