package de.sloth.spotiregx.lib.util;

import android.text.Editable;
import android.text.TextWatcher;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;


public class TextChangedListener implements TextWatcher {

    private final long delayInMillis;
    private final Consumer<Editable> onTextChanged;

    private Timer timer;


    public TextChangedListener(long delayInMillis, Consumer<Editable> onTextChanged){
        this.delayInMillis = delayInMillis;
        this.onTextChanged = onTextChanged;
        this.timer = new Timer();
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
            }, delayInMillis);
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
