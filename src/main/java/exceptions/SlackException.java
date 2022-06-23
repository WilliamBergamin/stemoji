package exceptions;

import com.slack.api.methods.SlackApiTextResponse;

import java.util.List;
import java.util.Map;

public class SlackException extends Exception {

    private final String errorMessage;
    private final String warningMessage;
    private final String neededMessage;
    private final Map<String, List<String>> httpResponseHeaders;

    public SlackException(SlackApiTextResponse badResponse) {
        this.errorMessage = badResponse.getError();
        this.warningMessage = badResponse.getWarning();
        this.neededMessage = badResponse.getNeeded();
        this.httpResponseHeaders = badResponse.getHttpResponseHeaders();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public Map<String, List<String>> getHttpResponseHeaders() {
        return httpResponseHeaders;
    }

    public String getNeededMessage() {
        return neededMessage;
    }

    @Override
    public String toString() {
        return "SlackException{"
                + "errorMessage='"
                + errorMessage
                + '\''
                + ", warningMessage='"
                + warningMessage
                + '\''
                + ", neededMessage='"
                + neededMessage
                + '\''
                + ", httpResponseHeaders="
                + httpResponseHeaders
                + '}';
    }
}
