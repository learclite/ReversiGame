package com.example.florian.reversi.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.florian.reversi.R;

/**
 * Created by Florian on 05-May-17.
 */

public class RulesFragment extends DialogFragment {
    WebView wv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("About Reversi");

        View view = inflater.inflate(R.layout.rules_fragment,null);

        wv = (WebView)view.findViewById(R.id.rules_text);
        wv.setVerticalScrollBarEnabled(true);

        String htmlAsString = getString(R.string.html);
        wv.loadDataWithBaseURL(null, htmlAsString, "text/html", "utf-8", null);

        setCancelable(true);
        return view;
    }
}
