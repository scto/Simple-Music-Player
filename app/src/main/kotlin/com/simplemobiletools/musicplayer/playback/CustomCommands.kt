package com.simplemobiletools.musicplayer.playback

import android.content.Context
import android.os.Bundle
import androidx.media3.session.CommandButton
import androidx.media3.session.SessionCommand
import com.simplemobiletools.musicplayer.R
import com.simplemobiletools.musicplayer.helpers.PATH

/**
 * Enum class representing custom commands that are used within the app and by media controller clients (e.g. system media controls).
 */
enum class CustomCommands(val customAction: String) {
    CLOSE_PLAYER(customAction = PATH + "CLOSE_PLAYER"),
    RELOAD_CONTENT(customAction = PATH + "RELOAD_CONTENT"),
    SAVE_QUEUE(customAction = PATH + "SAVE_QUEUE"),
    TOGGLE_SLEEP_TIMER(customAction = PATH + "TOGGLE_SLEEP_TIMER"),
    TOGGLE_SKIP_SILENCE(customAction = PATH + "TOGGLE_SKIP_SILENCE");

    val sessionCommand = SessionCommand(customAction, Bundle.EMPTY)

    companion object {
        fun fromSessionCommand(sessionCommand: SessionCommand): CustomCommands? {
            return values().find { it.customAction == sessionCommand.customAction }
        }
    }
}

internal val customCommands = CustomCommands.values().map { it.sessionCommand }

internal fun Context.getCustomLayout(): List<CommandButton> {
    return listOf(
        CommandButton.Builder()
            .setDisplayName(getString(R.string.close))
            .setSessionCommand(CustomCommands.CLOSE_PLAYER.sessionCommand)
            .setIconResId(R.drawable.ic_cross_vector)
            .build()
    )
}
