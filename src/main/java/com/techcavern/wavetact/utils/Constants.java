package com.techcavern.wavetact.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.techcavern.wavetact.utils.fileUtils.Configuration;
import com.techcavern.wavetact.utils.objects.*;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.pircbotx.MultiBotManager;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

public class Constants {

    public static final Gson GSON = new GsonBuilder().create();
    public static final Gson GSON_PRETTY_PRINT = new GsonBuilder().setPrettyPrinting().create();
    public static String LastLeftChannel = "";
    public static final HashMap<String, String> QuietBans = new HashMap<>();
    public static final List<PermChannel> PermChannels = new ArrayList<>();
    public static final List<ChannelProperty> Topic = new ArrayList<>();
    public static final ForkJoinPool threadPool = new ForkJoinPool(100);
    public static final List<String> Controllers = new ArrayList<>();
    public static final List<String> IRCBLs = new ArrayList<>();
    public static final List<String> DNSBLs = new ArrayList<>();
    public static final List<NetworkAdmin> NetworkAdmins = new ArrayList<>();
    public static final List<GenericCommand> AllCommands = new ArrayList<>();
    public static final List<GenericCommand> GenericCommands = new ArrayList<>();
    public static final List<GenericCommand> TrustedCommands = new ArrayList<>();
    public static final List<GenericCommand> ControllerCommands = new ArrayList<>();
    public static final List<GenericCommand> NetAdminCommands = new ArrayList<>();
    public static final List<GenericCommand> ChanOwnOpCommands = new ArrayList<>();
    public static final List<GenericCommand> ChanHalfOpCommands = new ArrayList<>();
    public static final List<AuthedUser> AuthedUsers = new ArrayList<>();
    public static final List<Account> Accounts = new ArrayList<>();
    public static final List<FunObject> Attacks = new ArrayList<>();
    public static final List<String> Eightball = new ArrayList<>();
    public static final List<GenericCommand> ChanOpCommands = new ArrayList<>();
    public static final List<GenericCommand> ChanAdminCommands = new ArrayList<>();
    public static final List<SimpleMessage> SimpleMessages = new ArrayList<>();
    public static final List<SimpleAction> SimpleActions = new ArrayList<>();
    public static final MultiBotManager WaveTact = new MultiBotManager();
    public static final Map<String, Configuration> configs = new HashMap<>();
    public static final List<UTime> BanTimes = new ArrayList<>();
    public static final List<UTime> QuietTimes = new ArrayList<>();
    public static final List<NetProperty> CommandChars = new ArrayList<>();
    public static final List<NetProperty> AuthType = new ArrayList<>();
    public static final List<CommandLine> CommandLines = new ArrayList<>();
    public static final List<CommandLine> CommandLineArguments = new ArrayList<>();
    public static final List<String> GenericListCommands = new ArrayList<>();
    public static final List<String> TrustedListCommands = new ArrayList<>();
    public static final List<String> ControllerListCommands = new ArrayList<>();
    public static final List<String> AllListCommands = new ArrayList<>();
    public static final List<String> ChanOwnOpListCommands = new ArrayList<>();
    public static final List<String> NetAdminListCommands = new ArrayList<>();
    public static final List<String> ChanHalfOpListCommands = new ArrayList<>();
    public static final List<String> ChanOpListCommands = new ArrayList<>();
    public static final List<String> ChanAdminListCommands = new ArrayList<>();
    public static final StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
    public static final Reflections wavetactreflection = new Reflections("com.techcavern.wavetact");
    public static final List<NetProperty> NetworkName = new ArrayList<>();
    public static String wundergroundapikey = null;
    public static String wolframalphaapikey = null;
    public static String wordnikapikey = null;
    public static String googleapikey = null;
    public static final List<NetProperty> NetAdminAccess = new ArrayList<>();
}
