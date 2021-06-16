package com.project.end;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;


public class MainSql extends AppCompatActivity {
    String videoURL;
    PlayerView playerView;
    SimpleExoPlayer player;
    Switch aSwitch;
    TextView increase;
    TextView decrease;
    TextView changeColor;
    float font = 14f;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playBackPosition = 0;
    TextView data;
    Button bb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.ThemeDark);
        } else {
            setTheme(R.style.Theme_EndProject);
        }
        getSupportActionBar().setTitle("الإحصائيات والتواريخ");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sql);
        aSwitch = findViewById(R.id.mode);
        increase = findViewById(R.id.increasetv);
        decrease = findViewById(R.id.decreasetv);
        changeColor = findViewById(R.id.changetv);

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                font += 4f;
                data.setTextSize(TypedValue.COMPLEX_UNIT_SP, font);
            }
        });
        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                font -= 4f;
                data.setTextSize(TypedValue.COMPLEX_UNIT_SP, font);
            }
        });
        changeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.setTextColor(Color.RED);
            }
        });

        playerView = findViewById(R.id.videoView);
        videoURL = "https://firebasestorage.googleapis.com/v0/b/testt-7a84b.appspot.com/o/%D8%A3%D9%88%D9%84%D9%89%20%D8%A7%D9%84%D9%82%D8%A8%D9%84%D8%AA%D9%8A%D9%86%20%D9%88%D8%AB%D8%A7%D9%84%D8%AB%20%D8%A7%D9%84%D8%AD%D8%B1%D9%85%D9%8A%D9%86..%20%D8%AA%D8%B9%D8%B1%D9%81%20%D8%B9%D9%84%D9%89%20%D9%85%D8%B9%D8%A7%D9%84%D9%85%20%D8%A7%D9%84%D9%85%D8%B3%D8%AC%D8%AF%20%D8%A7%D9%84%D8%A3%D9%82%D8%B5%D9%89%20%D8%A7%D9%84%D9%85%D8%A8%D8%A7%D8%B1%D9%83.mp4?alt=media&token=21cc4677-853e-454c-99bf-bbe98f65ecc2";

        bb = findViewById(R.id.but);
        data = findViewById(R.id.textView);
        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(MainSql.this);
                myDB.addDate(data.getText().toString().trim());
            }
        });
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            aSwitch.setChecked(true);
        }
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    reset();
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    reset();
                }
            }
        });
    }

    public void initVideo() {
        //player
        player = ExoPlayerFactory.newSimpleInstance(this);
        //connect player with player view
        playerView.setPlayer(player);
        //media source
        Uri uri = Uri.parse(videoURL);
        DataSource.Factory dataSource = new DefaultDataSourceFactory(this, "exoplayer-codelab");
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSource).createMediaSource(uri);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playBackPosition);
        player.prepare(mediaSource, false, false);
    }

    public void releasesVideo() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playBackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
            player = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        initVideo();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (player != null) {
            initVideo();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releasesVideo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasesVideo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymanu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_all) {
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    //
    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all Data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(MainSql.this);
                myDB.deleteAllData();
                //Refresh Activity
                Intent intent = new Intent(MainSql.this, MainSql.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();

    }

    private void reset() {
        Intent intent = new Intent(this, MainSql.class);
        startActivity(intent);
        finish();
    }

}