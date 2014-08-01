/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.trusted;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.TruCMD;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.databaseUtils.SimpleMessageUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.objects.SimpleMessage;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jztech101
 */
public class CustomMSG extends GenericCommand {

    @CMD
    @TruCMD
    public CustomMSG() {
        super(GeneralUtils.toArray("custommessage cmsg custommsg customsg"), 5, "custommessage (+/-)[Command] [permlevel] [Response]","Responses may contain $1, $2, etc which indicate the argument separated by a space. $* indicates all remaining arguments.");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {

        if (args[0].toLowerCase().equalsIgnoreCase("list")) {
                List<String> SimpleCommands = GeneralRegistry.SimpleMessages.stream().filter(g -> g.getPermLevel() <= UserPermLevel).map(GenericCommand::getCommand).collect(Collectors.toList());
                IRCUtils.SendMessage(user, channel, StringUtils.join(SimpleCommands, ", "), isPrivate);
        }else{
                if (args[1].startsWith("-")) {
                    removeCommand(user, channel, isPrivate, UserPermLevel, args[1].substring(1));
                } else if (args[1].startsWith("+")) {
                    modifyCommand(user, channel, Integer.parseInt(args[2]), isPrivate, args[1].substring(1), UserPermLevel, GeneralUtils.buildMessage(3, args.length, args).replace("\n", " "));
                } else {
                    addCommand(user, channel, Integer.parseInt(args[2]), isPrivate, args[1], GeneralUtils.buildMessage(3, args.length, args).replace("\n", " "));
                }
        }
    }


    private void addCommand(User user, Channel channel, int accessLevel, boolean isPrivate, String cmd, String msg) {
        if (GetUtils.getCommand(cmd) != null) {
            user.send().notice("Custom Message already exists");
            return;
        }else
            GeneralRegistry.SimpleMessages.add(new SimpleMessage(cmd, accessLevel, msg, false));
        SimpleMessageUtils.saveSimpleMessages();
        IRCUtils.SendMessage(user, channel, "Custom Message added", isPrivate);
    }


    @SuppressWarnings("SuspiciousMethodCalls")
    private void modifyCommand(User user, Channel channel, int accessLevel, boolean isPrivate, String command, int UserPermLevel, String msg) {
        SimpleMessage cmd = SimpleMessageUtils.getSimpleMessage(command);
        if (cmd.getPermLevel() <= UserPermLevel) {
            if (cmd != null && !cmd.getLockedStatus()) {
                GeneralRegistry.SimpleMessages.remove(cmd);
                GeneralRegistry.SimpleMessages.add(new SimpleMessage(command, accessLevel, msg, false));
                SimpleMessageUtils.saveSimpleMessages();
            } else if (cmd.getLockedStatus()) {
                user.send().notice("Custom Message Locked");
            } else {
                user.send().notice("Custom Message Does Not Exist");
            }
            IRCUtils.SendMessage(user, channel, "Custom Message modified", isPrivate);
        } else {
            user.send().notice("Permission Denied");
        }
    }
    @SuppressWarnings({"SuspiciousMethodCalls", "ConstantConditions"})
    private void removeCommand(User user, Channel channel, boolean isPrivate, int UserPermLevel, String command) {
        SimpleMessage cmd = SimpleMessageUtils.getSimpleMessage(command);
        if (cmd.getPermLevel() <= UserPermLevel) {
            if (cmd == null) {
                user.send().notice("Custom Message does not exist");
            } else if (cmd.getLockedStatus()) {
                user.send().notice("Custom Message is locked");
            } else {
                GeneralRegistry.SimpleMessages.remove(cmd);
                SimpleMessageUtils.saveSimpleMessages();
                IRCUtils.SendMessage(user, channel, "Custom Message removed", isPrivate);
            }
        } else {
            user.send().notice("Permission Denied");
        }
    }
}
