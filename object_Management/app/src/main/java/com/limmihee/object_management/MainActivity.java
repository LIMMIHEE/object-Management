package com.limmihee.object_management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {
    private SearchView searchView ;
    private Context context;
    private Button button;
    private RatingBar ratingBar;
    //private FloatingActionButton floatingActionButton;
    private Animation fab_open, fab_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        context=getApplicationContext();
        button = (Button)findViewById(R.id.add_btn);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        class Listener implements RatingBar.OnRatingBarChangeListener
        {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingBar.setRating(rating);
            }
        }
        fab_open = AnimationUtils.loadAnimation(context,R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(context,R.anim.fab_close);

//        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
//        floatingActionButton.setOnClickListener(new FloatingActionButton(
//                new Fl
//        ));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),object_add.class);
                startActivity(intent);
            }
        });

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener(){
                    @Override
                    public boolean onQueryTextSubmit(String query){
                        // 검색버튼 눌렀을 때 이벤트
                        Toast.makeText(MainActivity.this, "검색 처리됨 : " + query, Toast.LENGTH_SHORT).show();
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        // 검색어가 변경 됬을 때 이벤트 처리
                        return false;
                    }

        });


    }

}

