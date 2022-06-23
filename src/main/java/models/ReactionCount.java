package models;

import org.jetbrains.annotations.NotNull;

public class ReactionCount implements Comparable<ReactionCount> {
    private final String reactionName;
    private final Integer count;

    public ReactionCount(String reactionName, int count) {
        this.reactionName = reactionName;
        this.count = count;
    }

    public String getReactionName() {
        return reactionName;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "ReactionCount{" + "reactionName='" + reactionName + '\'' + ", count=" + count + '}';
    }

    @Override
    public int compareTo(@NotNull ReactionCount o) {
        return this.count.compareTo(o.getCount());
    }
}
