package com.trncic.dottedbarsample;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trncic.library.DottedProgressBar;

public class MainActivity extends AppCompatActivity {

  DottedProgressBar bar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    bar = (DottedProgressBar) findViewById(R.id.progress);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  public void stopProgress(View view) {
    bar.stopProgress();
  }

  public void startProgress(View view) {
    bar.startProgress();
    bar.setSpace(60);
  }

  public void openDialog(View v) {
    View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog, null);
    final DottedProgressBar progressBar = (DottedProgressBar) dialogView.findViewById(R.id.progress);
    final TextView textView = (TextView) dialogView.findViewById(R.id.text);
    progressBar.startProgress();
    AlertDialog dialog = new AlertDialog.Builder(this).setView(dialogView).create();
    dialog.getWindow().setLayout(400, ViewGroup.LayoutParams.WRAP_CONTENT);
    dialog.show();
    progressBar.postDelayed(new Runnable() {
      @Override public void run() {
        progressBar.stopProgress();
        progressBar.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.VISIBLE);
      }
    }, 1000);
  }
}
