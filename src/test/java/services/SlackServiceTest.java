package services;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.reactions.ReactionsListRequest;
import com.slack.api.methods.response.reactions.ReactionsListResponse;
import exceptions.SlackException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SlackServiceTest {

  private static final Logger logger = LoggerFactory.getLogger(SlackServiceTest.class);

  @Test
  public void TestGetReactionPageResponse() throws SlackApiException, IOException, SlackException {
    // SETUP
    ReactionsListResponse response = new ReactionsListResponse();
    response.setOk(true);
    response.setItems(
        new ArrayList<>() {
          {
            add(new ReactionsListResponse.Item());
          }
        });

    MethodsClient client = mock(MethodsClient.class);
    when(client.reactionsList(any(ReactionsListRequest.class))).thenReturn(response);

    // EXECUTE
    ReactionsListResponse res = SlackService.getReactionPageResponse(client, "BillID", 100);

    // ASSERT
    assertEquals(res.getItems().size(), 1);
  }
}
