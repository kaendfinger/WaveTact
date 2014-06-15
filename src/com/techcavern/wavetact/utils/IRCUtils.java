package com.techcavern.wavetact.utils;

import java.nio.charset.Charset;

import org.pircbotx.Channel;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.Configuration.Builder;
import org.pircbotx.User;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.techcavern.wavetact.commands.BasicCommands;
import com.techcavern.wavetact.commands.CheckUserLevel;

public class IRCUtils{
    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create(); 
	public static PircBotX createbot(String Name, String Channel,String Nick, String Server) throws Exception{
    System.out.println("Configuring"+Name);
    Builder<PircBotX> Net = new Configuration.Builder<PircBotX>();
    Net.setName(Nick);
    Net.setLogin("WaveTact");
    Net.setEncoding(Charset.isSupported("UTF-8") ? Charset.forName("UTF-8") : Charset.defaultCharset());
    Net.setServer(Server, 6667);
    Net.addAutoJoinChannel(Channel);
    Net.getListenerManager().addListener(new BasicCommands());
    Net.getListenerManager().addListener(new CheckUserLevel());
    PircBotX Bot = new PircBotX(Net.buildConfiguration());
    return Bot;
	}
	public static void onMessage(String command, String result, Channel c, User u, PircBotX bot, String message, int level) throws Exception {
        if (message.equalsIgnoreCase((GeneralRegistry.CommandChar + command))){
        	if (level == 10 && PermUtils.isController(bot, u)){

            	c.send().message(result);

        	} else if (level == 0) {
            	c.send().message(result);
        	} else {
            	c.send().message("Permission Denied");

        	}
        }

}


	
}
