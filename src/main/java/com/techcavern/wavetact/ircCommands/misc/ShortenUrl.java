package com.techcavern.wavetact.ircCommands.misc;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
public class ShortenUrl extends IRCCommand {

    public ShortenUrl() {
        super(GeneralUtils.toArray("shortenurl shorturl surl"), 1, "shortenUrl [url]", "Shortens Url", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (DatabaseUtils.getConfig("googleapikey") == null) {
            IRCUtils.sendError(user, network, channel, "Google API Key is null - Contact bot controller to fix", prefix);
            return;
        }
        String shortUrl = GeneralUtils.shortenURL(args[0]);
        IRCUtils.sendMessage(user, network, channel, shortUrl, prefix);
    }
}

