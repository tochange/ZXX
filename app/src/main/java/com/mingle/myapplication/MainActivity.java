package com.mingle.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.mingle.sweetpick.RecyclerViewDelegate;

public class MainActivity extends AppCompatActivity {
    private RecyclerViewDelegate mSweetSheet;
    private RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        rl = (RelativeLayout) findViewById(R.id.rl);
        mSweetSheet = new RecyclerViewDelegate(rl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_recyclerView) {
            mSweetSheet.toggle();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
