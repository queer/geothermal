package com.mewna.event;

import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import com.mewna.api.ApiContext;
import lombok.Value;

/**
 * @author amy
 * @since 1/19/18.
 */
@Value
@SuppressWarnings({"unused", "WeakerAccess"})
public class TrackEvent {
    private final Type t;
    private final TrackData d;
    private final long ts = System.currentTimeMillis();
    
    public TrackEvent(final Type type, final ApiContext ctx, final AudioTrackInfo info) {
        t = type;
        d = new TrackData(ctx, info);
    }
    
    @Value
    public static final class TrackData {
        private final ApiContext ctx;
        private final AudioTrackInfo info;
    }
    
    public enum Type {
        /**
         * Track started playing
         */
        AUDIO_TRACK_START,
        /**
         * Track stopped playing
         */
        AUDIO_TRACK_STOP,
        /**
         * Track was paused
         */
        AUDIO_TRACK_PAUSE,
        /**
         * Track was queued
         */
        AUDIO_TRACK_QUEUE,
        /**
         * Track was invalid
         */
        AUDIO_TRACK_INVALID,
        /**
         * Request to fetch current track
         */
        AUDIO_TRACK_NOW_PLAYING,
        /**
         * Queue ended
         */
        AUDIO_QUEUE_END,
    }
}
