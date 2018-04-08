package edu.temple.mybrowserapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class WebFragment extends Fragment {

    private static final String URL = "web url";

    private String urlSent;

    WebView webView;
    String finalUrl;

    public WebFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static WebFragment newInstance(String msg) {
        WebFragment fragment = new WebFragment();
        Bundle args = new Bundle();
        args.putString(URL, msg);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            urlSent = getArguments().getString(URL);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_web, container, false);
        Bundle args = getArguments();

        webView = ((WebView) rootView.findViewById(R.id.webView));

        webView.getSettings().setJavaScriptEnabled(true);
        finalUrl = args.getString(URL);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){
                super.onPageFinished(view, url);

                try {

                    finalUrl = url;
                    EditText editText=((MainActivity)view.getContext()).findViewById(R.id.urlEditText);
                    editText.setText(url);
                    Bundle args = getArguments();
                    args.putString(URL, url);
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        });

        loadUrl();

        return rootView;
    }

    private void loadUrl(){
        Bundle args = getArguments();
        if (finalUrl != null) {
            if (finalUrl.startsWith("http://") || finalUrl.startsWith("https://")) {
                webView.loadUrl(args.getString(URL));
            } else {
                webView.loadUrl("http://" + finalUrl);
            }
        } else {
            System.out.println("URL was not entered. ");
        }
        args.putString(URL, finalUrl);
    }

    public void loadUrl(String url){
        Bundle args = getArguments();
        if (url != null) {
            if (url.startsWith("http://") || url.startsWith("https://")) {
                webView.loadUrl(url);
            } else {
                webView.loadUrl("http://" + url);
            }
        } else {
            System.out.println("URL was not entered. ");
        }
        args.putString(URL, url);
    }
}

