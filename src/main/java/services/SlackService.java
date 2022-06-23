package services;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.methods.request.reactions.ReactionsListRequest;
import com.slack.api.methods.response.reactions.ReactionsListResponse;
import exceptions.SlackException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SlackService {
    private static final Logger logger = LoggerFactory.getLogger(SlackService.class);

    public static ReactionsListResponse getReactionPageResponse(MethodsClient slackClient, String userId, int limit) throws IOException, SlackException, SlackApiException {
        ReactionsListRequest.ReactionsListRequestBuilder requestBuilder = ReactionsListRequest.builder();
        requestBuilder.user(userId);
        requestBuilder.limit(limit);

        ReactionsListRequest request = requestBuilder.build();

        ReactionsListResponse reactionsListResponse = slackClient.reactionsList(request);

        validateResponse(reactionsListResponse);
        return reactionsListResponse;
    }

    private static void validateResponse(SlackApiTextResponse response) throws SlackException {
        if (response.isOk()) {
            return;
        }
        logger.error("Slack response not valid!");
        throw new SlackException(response);
    }
}
