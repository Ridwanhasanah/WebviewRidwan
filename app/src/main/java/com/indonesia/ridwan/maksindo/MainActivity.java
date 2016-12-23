package com.indonesia.ridwan.maksindo;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private WebView webv;
    private ProgressBar progressBar;
    private String url = "https://www.maksindo.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        webv = (WebView) findViewById(R.id.webview);

        webv.setWebViewClient(new WebViewClient());
        getWindow().requestFeature(Window.FEATURE_PROGRESS);

        webv.getSettings().setJavaScriptEnabled(true);
        webv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webv.getSettings().setDomStorageEnabled(true);
        webv.getSettings().setSupportMultipleWindows(true);


        WebSettings webset = webv.getSettings();
        webset.setJavaScriptEnabled(true);
        webv.loadUrl(url);

        webv.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            //If you will not use this method url links are opeen in new brower not in webview
            /*public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }*/
            //If url has "tel:245678" , on clicking the number it will directly call to inbuilt calling feature of phone
            public boolean shouldOverrideUrlLoading(WebView view ,String url){

                if(url.startsWith("tel:")){
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    startActivity(intent);
                } else {

                    view.loadUrl(url);


                }
                return true;
            }

            //Show loader on url load
            public void onLoadResource (WebView view, String url) {
                if (progressDialog == null) {
                    // in standard case YourActivity.this
                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }
            }
            public void onPageFinished(WebView view, String url) {
                try{
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }

        });



        webv.getSettings().setBuiltInZoomControls(true);
        webv.getSettings().setSupportZoom(true);


    }

    @Override
    public void onBackPressed() {
        if (webv.isFocused() && webv.canGoBack()) {
            webv.goBack();
        } else {
            super.onBackPressed();
            finish();
        }
    }
}
