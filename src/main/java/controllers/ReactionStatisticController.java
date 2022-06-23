package controllers;

import builders.blockbuilders.BlockBuilder;
import builders.blockbuilders.ExceptionBlockBuilder;
import builders.blockbuilders.ReactionStatBlockBuilder;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.reactions.ReactionsListResponse;
import com.slack.api.model.Reaction;
import com.slack.api.model.block.LayoutBlock;
import exceptions.SlackException;
import models.ReactionCount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.SlackService;

import java.io.IOException;
import java.util.*;

public class ReactionStatisticController {

    private static final Logger logger = LoggerFactory.getLogger(ReactionStatisticController.class);
    private final MethodsClient client;
    private final int REACTED_MESSAGE_LIMIT = 100;
    private final int MAX_NUMBER_OF_REACTIONS = 8;

    public ReactionStatisticController(MethodsClient client) {
        this.client = client;
    }

    public List<LayoutBlock> getReactionStatisticBlocks(String userId)
            throws SlackApiException, IOException {
        try {
            ArrayList<Reaction> reactions = getReactions(userId);
            Map<String, Integer> reactionCountMap = getReactionCountMap(reactions);
            ArrayList<ReactionCount> sortedReactionsCounts = getSortedKeyValue(reactionCountMap);

            BlockBuilder blockBuilder = new ReactionStatBlockBuilder(
                    sortedReactionsCounts.subList(0, getCutOffLength(sortedReactionsCounts.size())));
            return blockBuilder.getBlocks();
        } catch (SlackException e) {
            logger.error(e.getErrorMessage(), e);
            BlockBuilder blockBuilder = new ExceptionBlockBuilder(e);
            return blockBuilder.getBlocks();
        }
    }

    int getCutOffLength(int size) {
        if (size == 0) {
            return 0;
        }
        return Math.min(size, MAX_NUMBER_OF_REACTIONS);
    }

    ArrayList<ReactionCount> getSortedKeyValue(Map<String, Integer> reactionCountMap) {
        ArrayList<ReactionCount> sortedReactionCounts = new ArrayList<ReactionCount>() {{
            reactionCountMap.forEach((k, v) -> add(new ReactionCount(k, v)));
        }};
        sortedReactionCounts.sort(Collections.reverseOrder());
        return sortedReactionCounts;
    }

    Map<String, Integer> getReactionCountMap(ArrayList<Reaction> reactions) {
        Map<String, Integer> reactionCountMap = new HashMap<>();
        int value;
        for (Reaction reaction : reactions) {
            value = reactionCountMap.getOrDefault(reaction.getName(), 0);
            reactionCountMap.put(reaction.getName(), value + reaction.getCount());
        }
        return reactionCountMap;
    }

    ArrayList<Reaction> getReactions(String userId) throws IOException, SlackException, SlackApiException {
        ReactionsListResponse reactionsListResponse = SlackService.getReactionPageResponse(client, userId, REACTED_MESSAGE_LIMIT);
        HashSet<ReactionsListResponse.Item> reactionItemSet = new HashSet<>(reactionsListResponse.getItems());
        return new ArrayList<Reaction>() {{
            reactionItemSet.forEach((item) -> addAll(item.getMessage().getReactions()));
        }};
    }
}
