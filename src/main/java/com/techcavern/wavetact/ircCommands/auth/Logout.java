package com.techcavern.wavetact.ircCommands.auth;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.AuthedUser;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.*;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
public class Logout extends IRCCommand {

    public Logout() {
        super(GeneralUtils.toArray("logout"), 0, "logout", "Logs you out", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        AuthedUser authedUser = PermUtils.getAuthedUser(network, user.getNick());
        if (authedUser == null) {
            ErrorUtils.sendError(user, "Error, you are not logged in");
        } else {
            Registry.AuthedUsers.remove(authedUser);
            if (IRCUtils.getCachedWhoisEvent(network, user.getNick()) != null)
                Registry.WhoisEventCache.remove(IRCUtils.getCachedWhoisEvent(network, user.getNick()));
            IRCUtils.sendMessage(user, network, channel, "You are now logged out", prefix);
        }
    }
}