/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.controller;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetact.utils.databaseUtils.GlobalUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.objects.Global;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;


/**
 * @author jztech101
 */
@CMD
@ConCMD
public class Globals extends GenericCommand {

    public Globals() {
        super(GeneralUtils.toArray("globals global gl"), 9001, "global (-)[user]", "adds a network admin to the bot");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {

        String account;
        if (args[0].startsWith("-")) {
            account = args[0].replaceFirst("-", "");
        } else {
            account = args[0];
        }
        String auth = PermUtils.getAuthedAccount(Bot, account);
        if(auth != null){
            account = auth;
        }
        if (account != null) {
            if (args[0].startsWith("-")) {
                if (GetUtils.getGlobalByNick(account, Bot.getServerInfo().getNetwork()) != null) {
                    GeneralRegistry.Globals.remove(GetUtils.getGlobalByNick(account, Bot.getServerInfo().getNetwork()));
                    GlobalUtils.saveGlobals();
                    IRCUtils.SendMessage(user, channel, "Global Removed", isPrivate);
                } else {
                    IRCUtils.sendError(user, "User does not existing in Globals");
                }
            }else if(args[0].equalsIgnoreCase("list")){
                String Globals = "";
                for(Global global: GeneralRegistry.Globals){
                    if(Globals.isEmpty()){
                        Globals = global.getUser();
                    }else{
                        Globals += ", " + global.getUser();
                    }
                }
                if(!Globals.isEmpty()) {
                    IRCUtils.SendMessage(user, channel, Globals, isPrivate);
                }else{
                    IRCUtils.sendError(user, "No Globals Exist");
                }
            }else {
                if (GetUtils.getGlobalByNick(account, Bot.getServerInfo().getNetwork()) != null) {
                    IRCUtils.sendError(user, "User is already in database");
                } else {
                    GeneralRegistry.Globals.add(new Global(Bot.getServerInfo().getNetwork(), account));
                    GlobalUtils.saveGlobals();
                    IRCUtils.SendMessage(user, channel, "Global Added", isPrivate);
                }
            }
        } else {


            IRCUtils.sendError(user, "User is not registered with Nickserv or not loggedin");
        }
    }
}
