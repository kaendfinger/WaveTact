/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.controller;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.databaseUtils.SimpleActionUtils;
import com.techcavern.wavetact.utils.databaseUtils.SimpleMessageUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * @author jztech101
 */
public class LockMSG extends GenericCommand {
    @CMD
    @ConCMD

    public LockMSG() {
        super(GeneralUtils.toArray("lockmessage lomsg lockm lom"), 9001, "lockmessage [command]", "locks a custom message");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {

            if (args[1].startsWith("-")) {
                SimpleMessageUtils.getSimpleMessage(args[1].replaceFirst("-", "")).unlock();
                SimpleMessageUtils.saveSimpleMessages();
                user.send().notice("Simple Message Unlocked");

            } else {
                SimpleMessageUtils.getSimpleMessage(args[1].replaceFirst("-", "")).lock();
                SimpleMessageUtils.saveSimpleMessages();
                user.send().notice("Simple Message locked");

            }
        }
    }