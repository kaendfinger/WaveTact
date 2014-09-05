package com.techcavern.wavetact.commands.fun;

import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.FunCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.Random;

@CMD
@FunCMD
public class Xkcd extends GenericCommand {

    public Xkcd() {
        super(GeneralUtils.toArray("xkcd randomxkcd"), 0, "xkcd [comic num#]","returns random comic or specified comic #");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
       Integer comicnumber = 1;
        JsonObject latestcomic = GeneralUtils.getJsonObject("http://xkcd.com/info.0.json");
        Integer latest = latestcomic.get("num").getAsInt();
        if(args.length > 0){
            comicnumber = Integer.parseInt(args[0]);
            if(latest < comicnumber){
                IRCUtils.sendError(user, "comic does not exist");
                return;
            }
        }else{
            Random random = new Random();
            comicnumber = random.nextInt(latest-1)+1;
        }
        JsonObject comic = GeneralUtils.getJsonObject("http://xkcd.com/" + comicnumber + "/info.0.json");
        String date = "Date: " + comic.get("day") + "/" + comic.get("month") + "/" + comic.get("year");
        String num =  comic.get("num").getAsString();
        String title = comic.get("title").getAsString();
        IRCUtils.SendMessage(user, channel, num + " - " + title + " - " + "http://xkcd.com/"+ num, isPrivate);
    }
}
