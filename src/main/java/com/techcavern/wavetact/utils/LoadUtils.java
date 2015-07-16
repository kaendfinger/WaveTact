package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.objects.NetMessage;
import org.flywaydb.core.Flyway;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.pircbotx.Colors;
import org.pircbotx.PircBotX;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.techcavern.wavetactdb.Tables.BANS;
import static com.techcavern.wavetactdb.Tables.CONFIG;

public class LoadUtils {

    public static void initiateDatabaseConnection() throws Exception {
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:sqlite:./db.sqlite", null, null);
        flyway.migrate();
        System.err.println("Getting connection...");
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:./db.sqlite");
        System.err.println("Creating DSLContext...");
        Registry.WaveTactDB = DSL.using(conn, SQLDialect.SQLITE);
    }

    public static void registerIRCCommands() {
        Set<Class<?>> classes = Registry.wavetactreflection.getTypesAnnotatedWith(IRCCMD.class);
        for (Class<?> clss : classes) {
            try {
                Registry.IRCCommands.add(((IRCCommand) clss.newInstance()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void checkDuplicateIRCCommands() {
        for (IRCCommand g : Registry.IRCCommands) {
            for (String commandid : g.getCommandID()) {
                for (IRCCommand subg : Registry.IRCCommands) {
                    for (String subcommandid : subg.getCommandID()) {
                        if (commandid.equalsIgnoreCase(subcommandid) && subg.getCommand() != g.getCommand()) {
                            System.out.println(g.getCommand() + " has a duplicate in " + subg.getCommand());
                        }
                    }
                }
            }
        }
        System.exit(0);
    }

    public static void removeDuplicateCustomCommands() {
        if (DatabaseUtils.getConfig("currentiteration") != null && Integer.parseInt(DatabaseUtils.getConfig("currentiteration").getValue(CONFIG.VALUE)) >= Registry.currentiteration) {
            return;
        } else {
            DatabaseUtils.removeConfig("currentiteration");
            DatabaseUtils.addConfig("currentiteration", String.valueOf(Registry.currentiteration));
            for (IRCCommand g : Registry.IRCCommands) {
                for (String commandid : g.getCommandID()) {
                    DatabaseUtils.removeCustomCommand(commandid);
                }
            }
        }
    }

    public static void registerConsoleCommands() {
        Set<Class<?>> classes = Registry.wavetactreflection.getTypesAnnotatedWith(ConCMD.class);
        for (Class<?> clss : classes) {
            try {
                Registry.ConsoleCommands.add(((ConsoleCommand) clss.newInstance()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void registerAttacks() {
        Registry.Attacks.add("sends a 53 inch monitor flying at $*");
        Registry.Attacks.add("shoots a rocket at $*");
        Registry.Attacks.add("punches $* right in the crotch");
        Registry.Attacks.add("packs $* up and ships it off to another galaxy");
        Registry.Attacks.add("eats $* up for breakfast");
        Registry.Attacks.add("sends a flying desk at $*");
        Registry.Attacks.add("swallows $* whole");
        Registry.Attacks.add("ties $* up and feeds it to a shark");
        Registry.Attacks.add("runs over $* with a car");
        Registry.Attacks.add("throws a racket at $*");
        Registry.Attacks.add("gobbles up $*");
        Registry.Attacks.add("throws a 2000 pound object at $*");
        Registry.Attacks.add("starts throwing punches at $*");
        Registry.Attacks.add("sends a flying dragon at $*");
        Registry.Attacks.add("takes over $*'s computers and blasts porn at full volume");
        Registry.Attacks.add("packs $* up and ships them off to Apple");
        Registry.Attacks.add("hands $* off to Lord Voldemort");
        Registry.Attacks.add("hands $* off to a pack of a wolves");
        Registry.Attacks.add("hands $* off to a herd of centaurs");
        Registry.Attacks.add("drops $* off to a 2000 kilometer cliff");
        Registry.Attacks.add("flies $* out into the middle of nowhere");
        Registry.Attacks.add("hunts $* down with a gun");
        Registry.Attacks.add("slaps $* around with a large trout");
        Registry.Attacks.add("throws iphones at $*");
        Registry.Attacks.add("fires missile at $*");
        Registry.Attacks.add("puts $* in a rocket and sends them off to pluto");
        Registry.Attacks.add("forcefeeds $* a plate of poisoned beef");
        Registry.Attacks.add("mind controls $* to marry Dolores Umbridge");
        Registry.Attacks.add("throws poorly written code at $*");
        Registry.Attacks.add("throws knives at $*");
        Registry.Attacks.add("throws various objects at $*");
        Registry.Attacks.add("throws rocks at $*");
        Registry.Attacks.add("throws grenades at $*");
        Registry.Attacks.add("throws IE6 at $*");
        Registry.Attacks.add("throws axes at $*");
        Registry.Attacks.add("throws evil things at $*");
        Registry.Attacks.add("throws netsplits at $*");
        Registry.Attacks.add("throws hammers at $*");
        Registry.Attacks.add("throws spears at $*");
        Registry.Attacks.add("throws spikes at $*");
        Registry.Attacks.add("throws $* into a burning building");
        Registry.Attacks.add("throws sharp things at $*");
        Registry.Attacks.add("throws moldy bread at $*");
        Registry.Attacks.add("throws mojibake at $*");
        Registry.Attacks.add("throws floppy disks at $*");
        Registry.Attacks.add("throws nails at $*");
        Registry.Attacks.add("throws burning planets at $*");
        Registry.Attacks.add("throws thorns at $*");
        Registry.Attacks.add("throws skulls at $*");
        Registry.Attacks.add("throws a fresh, unboxed copy of Windows Me at $*");
        Registry.Attacks.add("casts fire at $*");
        Registry.Attacks.add("casts ice at $*");
        Registry.Attacks.add("casts death at $*");
        Registry.Attacks.add("casts " + Colors.BOLD + "DEATH" + Colors.BOLD + " at $*");
        Registry.Attacks.add("casts poison at $*");
        Registry.Attacks.add("casts stupid at $*");
        Registry.Attacks.add("attacks $* with knives");
        Registry.Attacks.add("attacks $* with idiots from #freenode");
        Registry.Attacks.add("attacks $* with an army of trolls");
        Registry.Attacks.add("attacks $* with oper abuse");
        Registry.Attacks.add("attacks $* with confusingly bad english");
        Registry.Attacks.add("attacks $* with Windows Me");
        Registry.Attacks.add("attacks $* with Quicktime for Windows");
        Registry.Attacks.add("attacks $* with ???");
        Registry.Attacks.add("attacks $* with segmentation faults");
        Registry.Attacks.add("attacks $* with relentless spyware");
        Registry.Attacks.add("attacks $* with NSA spies");
        Registry.Attacks.add("attacks $* with tracking devices");
        Registry.Attacks.add("attacks $* with a botnet");
    }

    public static void registerEightball() {
        Registry.Eightball.add("Hmm.. not today");
        Registry.Eightball.add("YES!");
        Registry.Eightball.add("Maybe");
        Registry.Eightball.add("Nope.");
        Registry.Eightball.add("Sources say no.");
        Registry.Eightball.add("Definitely");
        Registry.Eightball.add("I have my doubts");
        Registry.Eightball.add("Signs say yes");
        Registry.Eightball.add("Cannot predict now");
        Registry.Eightball.add("It is certain");
        Registry.Eightball.add("Sure");
        Registry.Eightball.add("Outlook decent");
        Registry.Eightball.add("Very doubtful");
        Registry.Eightball.add("Perhaps now is not a good time to tell you");
        Registry.Eightball.add("Concentrate and ask again");
        Registry.Eightball.add("Forget about it");
        Registry.Eightball.add("Don't count on it");
    }

    public static void addDir(String s) throws IOException {
        try {
            Field field = ClassLoader.class.getDeclaredField("usr_paths");
            field.setAccessible(true);
            String[] paths = (String[]) field.get(null);
            for (String path : paths) {
                if (s.equals(path)) {
                    return;
                }
            }
            String[] tmp = new String[paths.length + 1];
            System.arraycopy(paths, 0, tmp, 0, paths.length);
            tmp[paths.length] = s;
            field.set(null, tmp);
            System.setProperty("java.library.path", System.getProperty("java.library.path") + File.pathSeparator + s);
        } catch (IllegalAccessException e) {
            throw new IOException("Failed to get permissions to set library path");
        } catch (NoSuchFieldException e) {
            throw new IOException("Failed to get field handle to set library path");
        }
    }

    public static void registerCharReplacements() {
        Registry.CharReplacements.put("a", "á");
        Registry.CharReplacements.put("b", "ḃ");
        Registry.CharReplacements.put("c", "ƈ");
        Registry.CharReplacements.put("d", "ḋ");
        Registry.CharReplacements.put("f", "ḟ");
        Registry.CharReplacements.put("g", "ǧ");
        Registry.CharReplacements.put("h", "ĥ");
        Registry.CharReplacements.put("j", "ĵ");
        Registry.CharReplacements.put("k", "ķ");
        Registry.CharReplacements.put("l", "ĺ");
        Registry.CharReplacements.put("m", "ṁ");
        Registry.CharReplacements.put("n", "ǹ");
        Registry.CharReplacements.put("p", "ṗ");
        Registry.CharReplacements.put("q", "ɋ");
        Registry.CharReplacements.put("r", "ȓ");
        Registry.CharReplacements.put("s", "ș");
        Registry.CharReplacements.put("t", "ț");
        Registry.CharReplacements.put("w", "ŵ");
        Registry.CharReplacements.put("z", "ƶ");
        Registry.CharReplacements.put("B", "Ɓ");
        Registry.CharReplacements.put("C", "Ƈ");
        Registry.CharReplacements.put("D", "Ḋ");
        Registry.CharReplacements.put("F", "Ḟ");
        Registry.CharReplacements.put("G", "Ǵ");
        Registry.CharReplacements.put("H", "Ĥ");
        Registry.CharReplacements.put("J", "Ĵ");
        Registry.CharReplacements.put("K", "Ƙ");
        Registry.CharReplacements.put("L", "Ŀ");
        Registry.CharReplacements.put("M", "Ṁ");
        Registry.CharReplacements.put("N", "Ǹ");
        Registry.CharReplacements.put("P", "Ṗ");
        Registry.CharReplacements.put("Q", "Ɋ");
        Registry.CharReplacements.put("R", "Ɍ");
        Registry.CharReplacements.put("S", "Ṡ");
        Registry.CharReplacements.put("T", "Ṫ");
        Registry.CharReplacements.put("W", "Ẅ");
        Registry.CharReplacements.put("Z", "Ƶ");
        Registry.CharReplacements.put("e", "é");
        Registry.CharReplacements.put("i", "í");
        Registry.CharReplacements.put("o", "ó");
        Registry.CharReplacements.put("u", "ú");
        Registry.CharReplacements.put("y", "ý");
        Registry.CharReplacements.put("A", "Á");
        Registry.CharReplacements.put("E", "É");
        Registry.CharReplacements.put("I", "Í");
        Registry.CharReplacements.put("O", "Ó");
        Registry.CharReplacements.put("U", "Ú");
        Registry.CharReplacements.put("Y", "Ý");

    }

    public static void initializeMessageQueue() {
        Iterator iterator = Registry.NetworkName.keySet().iterator();
        while (iterator.hasNext()) {
            PircBotX network = (PircBotX) iterator.next();
            class MessageQueue implements Runnable {
                @Override
                public void run() {
                    while (Registry.NetworkName.get(network) != null) {
                        try {
                            if (Registry.MessageQueue.size() > 0 && network.equals(Registry.MessageQueue.element().getNetwork())) {
                                NetMessage Message = Registry.MessageQueue.remove();
                                Message.getNetwork().sendRaw().rawLine(Message.getProperty());
                                TimeUnit.MILLISECONDS.sleep(900);
                            }
                            TimeUnit.MILLISECONDS.sleep(100);
                        } catch (Exception e) {
                        }
                    }
                }

            }
            Registry.threadPool.execute(new MessageQueue());
        }

    }

    public static void addMessageQueue(PircBotX network) {
        class MessageQueue implements Runnable {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(30);
                } catch (InterruptedException c) {
                }
                while (Registry.NetworkName.get(network) != null) {
                    try {
                        if (Registry.MessageQueue.size() > 0 && network.equals(Registry.MessageQueue.element().getNetwork())) {
                            NetMessage Message = Registry.MessageQueue.remove();
                            Message.getNetwork().sendRaw().rawLine(Message.getProperty());
                            TimeUnit.MILLISECONDS.sleep(900);
                        }
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (Exception e) {
                    }
                }
            }

        }
        Registry.threadPool.execute(new MessageQueue());
    }


    public static void initializeAutoFlushWhoisCache() {
        class MessageQueue implements Runnable {
            @Override
            public void run() {
                try {
                    TimeUnit.MINUTES.sleep(30);
                    Registry.WhoisEventCache.clear();
                } catch (Exception e) {

                }
            }

        }
        Registry.threadPool.execute(new MessageQueue());
    }

    public static void initializeBanQueue() {
        class BanQueue implements Runnable {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException c) {
                }
                while (true) {
                    try {
                        for (Record banRecord : DatabaseUtils.getBans()) {
                            try {
                                if (System.currentTimeMillis() >= banRecord.getValue(BANS.TIME) + banRecord.getValue(BANS.INIT)) {
                                    PircBotX networkObject = IRCUtils.getBotByNetworkName(banRecord.getValue(BANS.NETWORK));
                                    if (!banRecord.getValue(BANS.HOSTMASK).isEmpty())
                                    IRCUtils.setMode(IRCUtils.getChannelbyName(networkObject, banRecord.getValue(BANS.CHANNEL)), networkObject, "-" + banRecord.getValue(BANS.PROPERTY), banRecord.getValue(BANS.HOSTMASK));
                                    System.out.println("Removing Bans");
                                    DatabaseUtils.removeBan(banRecord.getValue(BANS.NETWORK), banRecord.getValue(BANS.CHANNEL), banRecord.getValue(BANS.HOSTMASK), banRecord.getValue(BANS.ISMUTE));
                                }
                            } catch (IllegalArgumentException | NullPointerException e) {
                                // ignored
                            }
                        }
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        Registry.threadPool.execute(new BanQueue());
    }
}
