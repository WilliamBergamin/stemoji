package builders.blockbuilders;

import com.slack.api.model.block.LayoutBlock;
import models.ReactionCount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.slack.api.model.block.Blocks.divider;

public class ReactionStatBlockBuilder implements BlockBuilder {

    private static final Logger logger = LoggerFactory.getLogger(ReactionStatBlockBuilder.class);
    private final List<ReactionCount> reactionCountSortedList;
    private final int maxNumberOfCharOnLine = 38;
    private final char fillerChar = '▓';
    private int largestCountValue = 0;

    public ReactionStatBlockBuilder(List<ReactionCount> reactionData) {
        this.reactionCountSortedList = reactionData;
    }

    public List<LayoutBlock> getBlocks() {
        largestCountValue =
                reactionCountSortedList.size() == 0 ? 0 : reactionCountSortedList.get(0).getCount();
        List<LayoutBlock> blocks =
                new ArrayList<>() {
                    {
                        add(BlockBuilder.getMrkdwnSection("The reachi stats :bar_chart:"));
                        add(divider());
                        for (ReactionCount reactionCount : reactionCountSortedList) {
                            add(getSection(reactionCount));
                        }
                    }
                };
        return blocks.stream().toList();
    }

    LayoutBlock getSection(ReactionCount reactionCount) {
        StringBuilder sectionBuilder = new StringBuilder();
        sectionBuilder.append(
                String.valueOf(fillerChar).repeat(Math.max(0, getBarLength(reactionCount.getCount()))));
        sectionBuilder.append(' ');
        sectionBuilder.append(':');
        sectionBuilder.append(reactionCount.getReactionName());
        sectionBuilder.append(':');
        return BlockBuilder.getMrkdwnSection(sectionBuilder.toString());
    }

    int getBarLength(int elementCount) {
        return elementCount * maxNumberOfCharOnLine / largestCountValue;
    }
}
