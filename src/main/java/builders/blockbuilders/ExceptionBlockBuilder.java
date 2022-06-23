package builders.blockbuilders;

import com.slack.api.model.block.LayoutBlock;

import java.util.ArrayList;

public class ExceptionBlockBuilder implements BlockBuilder {

    private final Exception exception;

    public ExceptionBlockBuilder(Exception exception) {
        this.exception = exception;
    }

    @Override
    public ArrayList<LayoutBlock> getBlocks() {
        return new ArrayList<>() {
            {
                add(BlockBuilder.getMrkdwnSection(exception.toString()));
            }
        };
    }
}
