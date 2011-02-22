package cc.co.evenprime.bukkit.nocheat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.util.config.Configuration;

public class NoCheatConfiguration {
	
	public static final String loggerName = "cc.co.evenprime.bukkit.nocheat";
	public static final Logger logger = Logger.getLogger(loggerName);
	
	public static boolean speedhackCheckActive = true;
	public static boolean movingCheckActive = true;
	public static boolean airbuildCheckActive = false;
	
	public static int speedhackInterval = 2000;
	public static int speedhackLow = 60;
	public static int speedhackMed = 90;
	public static int speedhackHigh = 120;
	
	public static double movingDistanceLow = 0.5D;
	public static double movingDistanceMed = 1.0D;
	public static double movingDistanceHigh = 5.0D;
	
	public static boolean movingLogOnly = false;
	
	private static ConsoleHandler ch = null;
	private static FileHandler fh = null;
	
	private NoCheatConfiguration() {}
	
	public static void config(File configurationFile) {
		
		if(!configurationFile.exists()) {
			createStandardConfigFile(configurationFile);
		}
		Configuration c = new Configuration(configurationFile);
		c.load();
		
		logger.setLevel(Level.INFO);
		logger.setUseParentHandlers(false);
				
		if(ch == null) {
			ch = new ConsoleHandler();
	
			ch.setLevel(stringToLevel(c.getString("logging.logtoconsole")));
			ch.setFormatter(Logger.getLogger("Minecraft").getHandlers()[0].getFormatter());
			logger.addHandler(ch);
		}
		
		if(fh == null) {
			try {
				fh = new FileHandler(c.getString("logging.filename"), true);
				fh.setLevel(stringToLevel(c.getString("logging.logtofile")));
				fh.setFormatter(Logger.getLogger("Minecraft").getHandlers()[0].getFormatter());
				logger.addHandler(fh);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		speedhackCheckActive = c.getBoolean("active.speedhack", true);
		movingCheckActive = c.getBoolean("active.moving", true);
		airbuildCheckActive = c.getBoolean("active.airbuild", false);
		
		speedhackInterval = c.getInt("speedhack.interval", 2000);
		speedhackLow = c.getInt("speedhack.limits.low", 60);
		speedhackMed = c.getInt("speedhack.limits.med", 90);
		speedhackHigh = c.getInt("speedhack.limits.high", 120);
		
		movingLogOnly = c.getBoolean("moving.logonly", false);
	}
	
	private static Level stringToLevel(String string) {
		
		if(string == null) {
			return Level.OFF;
		}
		
		if(string.trim().equals("info")) return Level.INFO;
		if(string.trim().equals("warn")) return Level.WARNING;
		if(string.trim().equals("severe")) return Level.SEVERE;
		return Level.OFF;
	}
	
	private static void createStandardConfigFile(File f) {
		try {
			f.getParentFile().mkdirs();
			f.createNewFile();
			BufferedWriter w = new BufferedWriter(new FileWriter(f));
			
			w.write("# Logging: potential log levels are info, warn, severe, off"); w.newLine();
			w.write("logging:"); w.newLine();
			w.write("    filename: plugins/NoCheat/nocheat.log"); w.newLine();
			w.write("    logtofile: info"); w.newLine();
			w.write("    logtoconsole: severe"); w.newLine();
			w.write("# Checks that are activated (true or false)"); w.newLine();
			w.write("active:");  w.newLine();
			w.write("    speedhack: true"); w.newLine();
			w.write("    moving: true"); w.newLine();
			w.write("    airbuild: false"); w.newLine();
			w.write("# Speedhack: interval in milliseconds, limits are events in that interval") ;w.newLine();
			w.write("speedhack:"); w.newLine();
			w.write("    interval: 2000"); w.newLine();
			w.write("    limits:"); w.newLine();
			w.write("        low: 60"); w.newLine();
			w.write("        med: 90"); w.newLine();
			w.write("        high: 120"); w.newLine();
			w.write("moving:"); w.newLine();
			w.write("    logonly: false"); w.newLine();
			w.flush(); w.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}