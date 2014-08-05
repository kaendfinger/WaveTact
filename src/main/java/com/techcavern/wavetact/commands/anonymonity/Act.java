/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.anonymonity;

import com.techcavern.wavetact.annot.AnonCMD;
import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * @author jztech101
 */
public class Act extends GenericCommand {

    @CMD
    @AnonCMD
    public Act() {
        super(GeneralUtils.toArray("act do"), 5, "act [something]","make the bot do something");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        if (isPrivate && channel != null) {
            isPrivate = false;
        }
        IRCUtils.SendAction(user, channel, StringUtils.join(args, " ").replace("\n", " "), isPrivate);
    }
}