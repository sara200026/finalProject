package com.project.end;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class DetailsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageView imageView;
    private TextView textViewMore;
    private FirebaseFirestore db;
    private String[] mItem;
    private String[] aItem;
    String itemClicked;
    private Intent intent;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (itemClicked == "الإحصائيات والتواريخ") {
            setContentView(R.layout.activity_main_sql);

        } else {
            setContentView(R.layout.activity_datiels);

        }
        init();
        itemClicked = intent.getStringExtra("item");
        getSupportActionBar().setTitle(itemClicked);
        if (itemClicked.equals("أهم الأحداث")) {
            db.collection(itemClicked)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    mItem = new String[]{

                                            "الحدث الأول :\n" + document.getString("الحدث الأول "),
                                            "الحدث الثاني :\n" + document.getString("الحدث الثاني"),
                                            "الحدث الثالث :\n" + document.getString("الحدث الثالث"),
                                            "الحدث الرابع :\n" + document.getString("الحدث الرابع")

                                    };

                                    String urlImage = document.getString("الصورة ");
                                    Picasso.get().load(urlImage).into(imageView);
                                    executeAdapter(itemClicked);

                                }
                            } else {
                                Log.w("TAG", "Error getting documents.", task.getException());
                                Toast.makeText(DetailsActivity.this, "يوجد خطأ\n" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        } else if (itemClicked.equals("الأبواب")) {
            db.collection(itemClicked)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    mItem = new String[]{

                                            "نبذة :\n" + document.getString("نبذة"),
                                            "الأبواب المغلقة :\n" + document.getString("الأبواب المغلقة"),
                                            "الأبواب المفتوحة :\n" + document.getString("الأبواب المفتوحة")

                                    };

                                    String urlImage = document.getString("الصورة");
                                    Picasso.get().load(urlImage).into(imageView);
                                    textViewMore.setVisibility(View.VISIBLE);
                                    textViewMore.setText("المزيد :\n" + document.getString("المزيد"));
                                    textViewMore.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                                            browserIntent.setData(Uri.parse(document.getString("المزيد")));
                                            startActivity(browserIntent);
                                        }
                                    });

                                    executeAdapter(itemClicked);

                                }
                            } else {
                                Log.w("TAG", "Error getting documents.", task.getException());
                                Toast.makeText(DetailsActivity.this, "يوجد خطأ\n" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        } else if (itemClicked.equals("الإحصائيات والتواريخ")) {


        } else if (itemClicked.equals("المسجد الأقصى")) {

            db.collection(itemClicked)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    mItem = new String[]{
                                            "المعلومات :\n" + document.getString("المعلومات"),
                                    };

                                    String urlImage = document.getString("الصورة");
                                    Picasso.get().load(urlImage).into(imageView);
                                    executeAdapter(itemClicked);
                                }
                            } else {
                                Log.w("TAG", "Error getting documents.", task.getException());
                                Toast.makeText(DetailsActivity.this, "يوجد خطأ\n" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


        } else if (itemClicked.equals("مدينة القدس")) {
            db.collection(itemClicked)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    mItem = new String[]{

                                            "المعلومة الأولى :\n" + document.getString("المعلومة الأولى"),
                                            "المعلومة الثانية :\n" + document.getString("المعلومة الثانية"),
                                            "المعلومة الثالثة :\n" + document.getString("المعلومة الثالثة"),
                                            "المعلومة الرابعة :\n" + document.getString("المعلومة الرابعة")

                                    };

                                    String urlImage = document.getString("الصورة");
                                    Picasso.get().load(urlImage).into(imageView);
                                    textViewMore.setVisibility(View.VISIBLE);
                                    textViewMore.setText("المزيد :\n" + document.getString("المزيد"));
                                    textViewMore.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                                            browserIntent.setData(Uri.parse(document.getString("المزيد")));
                                            startActivity(browserIntent);
                                        }
                                    });

                                    executeAdapter(itemClicked);

                                }
                            } else {
                                Log.w("TAG", "Error getting documents.", task.getException());
                                Toast.makeText(DetailsActivity.this, "يوجد خطأ\n" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }


    }

    void init() {
        recyclerView = findViewById(R.id.recyclerView);
        imageView = findViewById(R.id.imageView);
        textViewMore = findViewById(R.id.textViewMore);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        intent = getIntent();

    }

    void executeAdapter(String itemClicked) {
        recyclerView.setAdapter(new RecyclerView.Adapter<DetailsActivity.PlanetViewHolder>() {
            @NonNull
            @Override
            public DetailsActivity.PlanetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(
                        android.R.layout.simple_list_item_1,
                        parent,
                        false);
                DetailsActivity.PlanetViewHolder vh = new DetailsActivity.PlanetViewHolder(v);
                return vh;
            }


            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onBindViewHolder(DetailsActivity.PlanetViewHolder vh, int position) {
                TextView tv = (TextView) vh.itemView;
                tv.setText(mItem[position]);
                tv.setTextColor(getResources().getColor(android.R.color.white));
                tv.setGravity(Gravity.CENTER);
                tv.setBackground(getResources().getDrawable(R.drawable.rounded_corner_view));
                if (itemClicked.equals("أهم الأحداث")) {
                    tv.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.darker_gray)));
                } else if (itemClicked.equals("الأبواب")) {
                    tv.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.holo_green_dark)));


                } else if (itemClicked.equals("المسجد الأقصى")) {
                    tv.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.holo_blue_light)));

                } else if (itemClicked.equals("مدينة القدس")) {
                    tv.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.holo_orange_light)));

                }
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT,
                        Toolbar.LayoutParams.WRAP_CONTENT);
                params.setMargins(16, 10, 16, 2);
                tv.setLayoutParams(params);
            }

            @Override
            public int getItemCount() {
                return mItem.length;
            }
        });

    }

    private class PlanetViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public PlanetViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(),
                    "You have clicked " + ((TextView) v).getText(),
                    Toast.LENGTH_LONG).show();
            String nameOfTVClicked = ((TextView) v).getText().toString();


            if (nameOfTVClicked.equals("أهم الأحداث")) {

            } else if (nameOfTVClicked.equals("الأبواب")) {

            } else if (nameOfTVClicked.equals("الإحصائيات والتواريخ")) {

            } else if (nameOfTVClicked.equals("المسجد الأقصى")) {

            } else if (nameOfTVClicked.equals("مدينة القدس")) {

            }
        }
    }

}