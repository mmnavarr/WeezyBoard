package com.main.weezyboard;

import android.content.Context;
import android.graphics.Point;
import android.inputmethodservice.InputMethodService;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputConnection;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rohan Jadvani on 7/14/15.
 */
public class KeyInput extends InputMethodService implements AdapterView.OnItemClickListener {

    private List<String> lyrics;
    private ListAdapter mListAdapter;
    private ListView mListView;
    private View keyboardView;

    @Override
    public View onCreateInputView() {
        keyboardView = getLayoutInflater().inflate(R.layout.keyboard, null);
        loadLyrics();
        setupList();
        return keyboardView;
    }

    private void loadLyrics() {
        lyrics = new ArrayList<String>();
        String lyric;
        String path = "lyrics.txt";
        // read file and load lyrics
        try {
            InputStream is = getAssets().open(path);
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            while ((lyric = in.readLine()) != null) {
                if (isValidLyric(lyric)) {
                    lyrics.add(lyric);
                }
            }
            populateLyrics(lyrics);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isValidLyric(String lyric) {
        return lyric.length() > 0;
    }

    private void populateLyrics(List<String> lyrics) {
        mListView = (ListView) keyboardView.findViewById(R.id.keyboard);
        mListAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, lyrics);
        mListView.setAdapter(mListAdapter);
    }

    private void setupList() {
        mListView.setOnItemClickListener(this);
        scaleHeight();
    }

    private void scaleHeight() {
        Point size = new Point();
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams
                .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context
                .WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getSize(size);
        lp.height = 2 * size.y / 5;
        mListView.setLayoutParams(lp);
        mListView.requestLayout();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        InputConnection ic = getCurrentInputConnection();
        ic.commitText(lyrics.get(position), 1);
    }

}
