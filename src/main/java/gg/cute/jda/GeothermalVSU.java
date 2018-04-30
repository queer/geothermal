package gg.cute.jda;

import net.dv8tion.jda.Core;
import net.dv8tion.jda.audio.AudioConnection;
import net.dv8tion.jda.audio.AudioWebSocket;
import net.dv8tion.jda.manager.AudioManager;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Voice state/server update handler, since the default one goes :fire:
 *
 * @author amy
 * @since 1/19/18.
 */
public final class GeothermalVSU {
    private static final Logger logger = LoggerFactory.getLogger(GeothermalVSU.class);
    
    private GeothermalVSU() {
    }
    
    public static void acceptVSU(final Core core, final String sessionId, final JSONObject content) {
        logger.info("    VSU: {}", content);
        logger.info("Session: {}", sessionId);
        final String guildId = content.getString("guild_id");
        String endpoint = content.getString("endpoint");
        logger.info("Geothermal got endpoint: " + endpoint + " for guild: " + guildId);
        endpoint = endpoint.replace(":80", "");
        final String token = content.getString("token");
        final AudioManager audioManager = core.getAudioManager(guildId);
        // TODO: Ensure not connected
        synchronized(audioManager.CONNECTION_LOCK) {
            try {
                final AudioWebSocket socket = new AudioWebSocket(audioManager.getListenerProxy(), endpoint, core, guildId,
                        sessionId, token, audioManager.isAutoReconnect());
                logger.info("Connected to socket");
                final AudioConnection connection = new AudioConnection(socket, audioManager.getQueuedAudioConnectionId(),
                        core.getSendFactory());
                logger.info("Created audio connection");
                audioManager.setAudioConnection(connection);
                logger.info("Set audio connection");
                socket.startConnection();
                logger.info("Starting connection!");
            } catch(final Throwable t) {
                t.printStackTrace();
            }
        }
    }
}
