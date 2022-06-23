package builders.blockbuilders;

import com.slack.api.model.block.LayoutBlock;

import java.util.List;

import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;

public interface BlockBuilder {

    static LayoutBlock getMrkdwnSection(String mrkdwn) {
        return section(section -> section.text(markdownText(mt -> mt.text(mrkdwn))));
    }

    List<LayoutBlock> getBlocks();
}
