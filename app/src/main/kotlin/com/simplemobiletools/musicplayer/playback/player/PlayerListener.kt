package com.simplemobiletools.musicplayer.playback.player

import android.widget.Toast
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import com.simplemobiletools.commons.extensions.toast
import com.simplemobiletools.musicplayer.R
import com.simplemobiletools.musicplayer.extensions.broadcastUpdateWidgetState
import com.simplemobiletools.musicplayer.playback.PlaybackService
import com.simplemobiletools.musicplayer.playback.PlaybackService.Companion.updatePlaybackInfo

@UnstableApi
internal fun PlaybackService.getPlayerListener() = object : Player.Listener {

    override fun onPlayerError(error: PlaybackException) = toast(R.string.unknown_error_occurred, Toast.LENGTH_LONG)

    override fun onEvents(player: Player, events: Player.Events) {
        if (
            events.containsAny(
                Player.EVENT_POSITION_DISCONTINUITY,
                Player.EVENT_MEDIA_ITEM_TRANSITION,
                Player.EVENT_TRACKS_CHANGED,
                Player.EVENT_TIMELINE_CHANGED,
                Player.EVENT_PLAYBACK_STATE_CHANGED,
                Player.EVENT_IS_PLAYING_CHANGED,
                Player.EVENT_PLAYLIST_METADATA_CHANGED
            )
        ) {
            updatePlaybackState()
        }
    }
}
