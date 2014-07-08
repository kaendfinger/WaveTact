package com.techcavern.wavetact.commands.chanop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.objects.PermChannel;
import com.techcavern.wavetact.utils.databaseUtils.PermChannelUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by jztech101 on 7/5/14.
 */
public class AutoOp extends GenericCommand {
    @CMD
    public AutoOp() {
        super(GeneralUtils.toArray("autoop autop ap"), 10, "autoop (-)[user] - Define whether to autoop the user or not");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, String... args) throws Exception {
        if (args[0].startsWith("-")) {
            PermChannel PLChannel = PermChannelUtils.getPermLevelChannel(Bot.getServerInfo().getNetwork(), PermUtils.getAccount(Bot, GetUtils.getUserByNick(channel, args[0])), channel.getName());
            if (PLChannel != null) {
                PLChannel.setAuto(false);
                PermChannelUtils.savePermChannels();
                channel.send().message("User will no longer be auto-opped");
            } else {
                channel.send().message("User is not found on channel access lists");
            }
        } else {
            PermChannel PLChannel = PermChannelUtils.getPermLevelChannel(Bot.getServerInfo().getNetwork(), PermUtils.getAccount(Bot, GetUtils.getUserByNick(channel, args[0])), channel.getName());
            if (PLChannel != null) {
                PLChannel.setAuto(true);
                PermChannelUtils.savePermChannels();
                channel.send().message("User will henceforth be auto-opped");

            } else {
                channel.send().message("User is not found on channel access lists");
            }
        }
    }
}
