package com.project.end;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private String[] mItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        recyclerView.setAdapter(new RecyclerView.Adapter<PlanetViewHolder>() {
            @NonNull
            @Override
            public PlanetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(
                        android.R.layout.simple_list_item_1,
                        parent,
                        false);
                PlanetViewHolder vh = new PlanetViewHolder(v);
                return vh;
            }


            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onBindViewHolder(PlanetViewHolder vh, int position) {
                TextView tv = (TextView) vh.itemView;
                tv.setText(mItem[position]);
                tv.setTextColor(getResources().getColor(android.R.color.white));
                tv.setGravity(Gravity.CENTER);
                tv.setBackground(getResources().getDrawable(R.drawable.rounded_corner_view));
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

    void init() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        mItem = new String[]{
                "أهم الأحداث",
                "الأبواب",
                "الإحصائيات والتواريخ",
                "المسجد الأقصى",
                "مدينة القدس",

        };


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
           /* Toast.makeText(getApplicationContext(),
                    "You have clicked " + ((TextView) v).getText(),
                    Toast.LENGTH_LONG).show();
           */
            String itemClicked = ((TextView) v).getText().toString();
            if (itemClicked == "الإحصائيات والتواريخ") {
                Intent intent = new Intent(MainActivity.this, MainSql.class);
                intent.putExtra("item", itemClicked);
                startActivity(intent);
            } else {

                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("item", itemClicked);
                startActivity(intent);
            }
        }
    }
}