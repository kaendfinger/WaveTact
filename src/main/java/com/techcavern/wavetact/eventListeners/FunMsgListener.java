/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.eventListeners;

import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.Registry;
import com.techcavern.wavetactdb.tables.Channelproperty;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.pircbotx.Colors;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import static com.techcavern.wavetactdb.Tables.CHANNELPROPERTY;
import static com.techcavern.wavetactdb.Tables.NETWORKPROPERTY;

/**
 * @author jztech101
 */
public class FunMsgListener extends ListenerAdapter {
    @Override
    public void onMessage(MessageEvent event) throws Exception {
        boolean funmsg = false;
        Record rec = DatabaseUtils.getChannelProperty(IRCUtils.getNetworkNameByNetwork(event.getBot()), event.getChannel().getName(), "funmsg");
        if (rec != null && rec.getValue(Channelproperty.CHANNELPROPERTY.VALUE).equalsIgnoreCase("true"))
            funmsg = true;
        final boolean funmsg2 = funmsg;
            class process implements Runnable {
                public void run() {
                    String commandchar = IRCUtils.getCommandChar(event.getBot(), event.getChannel());
                    if(commandchar == null){
                        return;
                    }
                    if (PermUtils.getPermLevel(event.getBot(), event.getUser().getNick(), event.getChannel()) > -2 && !event.getMessage().startsWith(commandchar)) {
                        String[] message = StringUtils.split(event.getMessage(), " ");
                        for (String arg : message) {
                            try {
                                arg = Colors.removeFormattingAndColors(arg);
                                if (arg.toLowerCase().replaceAll("o+", "o").replaceAll("0+", "o").contains("yolo") && funmsg2) {
                                    if (IRCUtils.checkIfCanKick(event.getChannel(), event.getBot(), event.getUser())) {
                                        IRCUtils.sendKick(event.getBot().getUserBot(), event.getUser(), event.getBot(), event.getChannel(), "YOLO");
                                    } else {
                                        IRCUtils.sendAction(event.getUser(), event.getBot(), event.getChannel(), "kicks " + IRCUtils.noPing(event.getUser().getNick()) + " (YOLO)", "");
                                    }
                                    return;
                                }
                                Record autourlRecord = DatabaseUtils.getChannelProperty(IRCUtils.getNetworkNameByNetwork(event.getBot()), event.getChannel().getName(), "autourl");
                                boolean autourl = autourlRecord != null && autourlRecord.getValue(CHANNELPROPERTY.VALUE).equalsIgnoreCase("true");
                                Record ignorehttpRecord = DatabaseUtils.getChannelProperty(IRCUtils.getNetworkNameByNetwork(event.getBot()), event.getChannel().getName(), "ignorehttp");
                                boolean ignorehttp = ignorehttpRecord != null && ignorehttpRecord.getValue(CHANNELPROPERTY.VALUE).equalsIgnoreCase("true");
                                if (ignorehttp && !arg.startsWith("https://") && !arg.startsWith("http://")) {
                                    arg = "http://" + arg;
                                }
                                if ((funmsg2 || autourl) && Registry.urlValidator.isValid(arg)) {
                                    try {
                                        Document doc = Jsoup.connect(arg).userAgent(Registry.USER_AGENT).get();
                                        if (doc.location().contains("stop-irc-bullying.eu") && funmsg2) {
                                            if (IRCUtils.checkIfCanKick(event.getChannel(), event.getBot(), event.getUser())) {
                                                IRCUtils.sendKick(event.getBot().getUserBot(), event.getUser(), event.getBot(), event.getChannel(), "┻━┻ ︵ ¯\\ (ツ)/¯ ︵ ┻━┻ [https://goo.gl/Tkb9dh]");
                                            } else {
                                                IRCUtils.sendAction(event.getUser(), event.getBot(), event.getChannel(), "kicks " + IRCUtils.noPing(event.getUser().getNick()) + " (┻━┻ ︵ ¯\\ (ツ)/¯ ︵ ┻━┻) [https://goo.gl/Tkb9dh]", "");
                                            }
                                            /**
                                             * My apologies to those using this site responsibly. But in my experience, this site has been linked numerous times for entertainment purposes
                                             * In fact, I have yet to notice a time when it is linked for its intended purpose. And if you are using this site for its intended purpose, please think of
                                             * better of way of expressing how you feel. Linking a generic site rarely solves any problems. Instead explain to the person how and why they offended you. If
                                             * they ignore you, then you ignore them.
                                             */
                                        } else if (autourl) {
                                            String title = doc.title();
                                            if (!title.isEmpty()) {
                                                IRCUtils.sendMessage(event.getBot(), event.getChannel(), "[" + IRCUtils.noPing(event.getUser().getNick()) + "] " + title, "");
                                            }
                                        }
                                    }catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            Registry.threadPool.execute(new process());
        }
    }







