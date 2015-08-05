package com.main.weezyboard;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputConnection;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rohan Jadvani on 7/14/15.
 */
public class KeyInput extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView kv;
    private Keyboard keyboard;
    private List<String> lyrics;

    private void loadLyrics() {
        String lyric;
        String path = "lyrics.txt";
        lyrics = new ArrayList<String>();
        // read file and load lyrics
        try {
            InputStream is = getAssets().open(path);
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            while ((lyric = in.readLine()) != null) {
                lyrics.add(lyric);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateInputView() {
        loadLyrics();
        kv = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboard = new Keyboard(this, R.xml.key_layout);
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        return kv;
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        Log.e("hai", Integer.toString(lyrics.size()));
        InputConnection ic = getCurrentInputConnection();
        ic.commitText(lyrics.get(primaryCode), 1);
    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

}
