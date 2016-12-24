package mmd.lib.util;

import mmd.lib.MMDLib;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

public class ApplicationHelper {
    public static void restartApp() {
        try {
            String java = System.getProperty("java.home") + "/bin/javaw";

            List<String> vmArguments = new ArrayList<>(ManagementFactory.getRuntimeMXBean().getInputArguments());
            Iterator<String> it = vmArguments.iterator();
            while (it.hasNext()) {
                if (it.next().contains("-agentlib")) {
                    it.remove();
                }
            }

            final List<String> cmd = new ArrayList<>();


            cmd.add('"' + java + '"');

            String[] mainCommand = System.getProperty("sun.java.command").split(" ");
            if (mainCommand[0].endsWith(".jar")) {
                cmd.add("-jar");
                cmd.add(new File(mainCommand[0]).getPath());
            } else {
                cmd.add("-cp");
                cmd.add('"' + System.getProperty("java.class.path") + '"');
                cmd.add(mainCommand[0]);
            }
            cmd.addAll(Arrays.asList(mainCommand).subList(1, mainCommand.length));
            cmd.addAll(vmArguments);

            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    try {
                        ProcessBuilder builder = new ProcessBuilder(cmd);
                        builder.inheritIO();
                        builder.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            log(Type.RESTART);
            FMLCommonHandler.instance().exitJava(0, false);
        } catch (Throwable e) {
            throw new RejectedExecutionException("Error while trying to restart the application", e);
        }
    }

    public static void shutdownApp() {
        log(Type.SHUTDOWN);
        FMLCommonHandler.instance().exitJava(0, false);
    }

    private static void log(Type type) {
        type.log();
    }

    private enum Type {
        SHUTDOWN,
        RESTART;

        public void log() {
            System.out.println();
            switch (this) {
                case SHUTDOWN:
                    MMDLib.LOG.log(Level.INFO, "---=== Shutting down Minecraft! ===---");
                    break;
                case RESTART:
                    MMDLib.LOG.log(Level.INFO, "---=== Restarting Minecraft! ===---");
                    break;

            }
        }
    }
}