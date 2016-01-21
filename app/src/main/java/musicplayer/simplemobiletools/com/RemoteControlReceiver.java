package musicplayer.simplemobiletools.com;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.KeyEvent;

import com.squareup.otto.Bus;

public class RemoteControlReceiver extends BroadcastReceiver {
    private static final int MAX_CLICK_DURATION = 1000;
    private static int clicksCnt;
    private static Handler handler = new Handler();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction())) {
            final KeyEvent event = intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
            if (event.getAction() == KeyEvent.ACTION_UP && KeyEvent.KEYCODE_HEADSETHOOK == event.getKeyCode()) {
                clicksCnt++;

                handler.removeCallbacks(runnable);
                if (clicksCnt >= 3) {
                    handler.post(runnable);
                } else {
                    handler.postDelayed(runnable, MAX_CLICK_DURATION);
                }
            }
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (clicksCnt == 0)
                return;

            final Bus bus = BusProvider.getInstance();
            if (clicksCnt == 1) {
                bus.post(new Events.PlayPauseSong());
            } else if (clicksCnt == 2) {
                bus.post(new Events.NextSong());
            } else {
                bus.post(new Events.PreviousSong());
            }
            clicksCnt = 0;
        }
    };
}