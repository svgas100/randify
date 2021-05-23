package de.sloth.spotiregx.lib.util;

import android.text.Editable;
import android.text.TextWatcher;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;


public class TextChangedListener implements TextWatcher {

    private final long delay; // milliseconds
    private final Consumer<Editable> onTextChanged;

    private Timer timer;


    public TextChangedListener(long delay, Consumer<Editable> onTextChanged){
        this.delay = delay;
        this.onTextChanged = onTextChanged;

        timer = new Timer();
    }

        @Override
        public void afterTextChanged(final Editable s) {
            if(s == null){
                return;
            }

            timer.cancel();

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    onTextChanged.accept(s);
                }
            }, delay);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Noop
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Noop
        }
}
