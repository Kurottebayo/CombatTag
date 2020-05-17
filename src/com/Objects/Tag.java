package com.Objects;

import java.io.Serializable;

public class Tag implements Serializable {
    private String player;
    private int loggedOutTimes;
    private boolean LoggedOut;

    public Tag(String player, int loggedOutTimes, boolean LoggedOut) {
        this.player = player;
        this.loggedOutTimes = loggedOutTimes;
        this.LoggedOut = LoggedOut;
    }

    public boolean isLoggedOut() {
        return LoggedOut;
    }

    public void setLoggedOut(boolean hasLoggedOut) {
        this.LoggedOut = hasLoggedOut;
    }

    public int getLoggedOutTimes() {
        return loggedOutTimes;
    }

    public void setLoggedOutTimes(int loggedOutTimes) {
        this.loggedOutTimes = loggedOutTimes;
    }

    public String getPlayer() {
        return player;
    }

    public void setName(String player) {
        this.player = player;
    }
}
