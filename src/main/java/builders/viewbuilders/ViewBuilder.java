package builders.viewbuilders;

import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.view.View;

import java.util.List;

import static com.slack.api.model.block.Blocks.asBlocks;
import static com.slack.api.model.block.Blocks.input;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.usersSelect;
import static com.slack.api.model.view.Views.*;

public class ViewBuilder {

    public static View getStemojiView() {
        return view(view -> view
                .callbackId("stemoji-shortcut-callback-id")
                .type("modal")
                .notifyOnClose(true)
                .title(viewTitle(title -> title
                        .type("plain_text")
                        .text("Stemoji submission")
                        .emoji(true)))
                .submit(viewSubmit(submit -> submit
                        .type("plain_text")
                        .text("Submit")
                        .emoji(true)))
                .close(viewClose(close -> close
                        .type("plain_text")
                        .text("Cancel")
                        .emoji(true)))
                .blocks(asBlocks(input(section -> section
                                .label(plainText("Select a user"))
                                .blockId("user-select-block")
                                .element(usersSelect(userSelect -> userSelect.actionId("user")))
                        ))
                )
        );
    }
}
