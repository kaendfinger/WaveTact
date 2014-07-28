package com.techcavern.wavetact.commands.utils;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.text.WordUtils;
import org.pircbotz.Channel;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;


public class MCStatus extends GenericCommand {
    @CMD
    @GenCMD

    public MCStatus() {
        super(GeneralUtils.toArray("mcstatus mc"), 0, "mcstatus - takes zero arguments, checks status of MC servers");
    }

    @Override
    public void onCommand(User user, PircBotZ Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        String Result;
        String Res;
        URL url;
        url = new URL("https://status.mojang.com/check");
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        Result = reader.readLine().replace("\"},{\"", " | ").replace("\":\"", ": ").replace("green", "Online").replace("red", "Offline").replace("[{\"", "").replace("\"}]", "").replace(".minecraft.net", "").replace(".mojang.com", "").replace("server", " Server").replace(".net", "");
        Result = WordUtils.capitalizeFully(Result);
        if (Result != null) {
            IRCUtils.SendMessage(user, channel, Result, isPrivate);
        } else {
            user.send().notice("MC Status Currently Unavailable");
        }
    }

}
