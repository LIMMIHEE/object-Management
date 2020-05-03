package com.limmihee.object_management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {
    private LinearLayout container;
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



        container = (LinearLayout)findViewById(R.id.MainView) ;
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
        AssetManager assetManager = getResources().getAssets();
        try {
            AssetManager.AssetInputStream ais = (AssetManager.AssetInputStream) assetManager.open("OBJ.json");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(ais));
            StringBuilder stringBuilder = new StringBuilder();

            // 용량 넘침 대비.
            int bufferSize = 1024*1024;
            char readBuf[] = new char[bufferSize];
            int resultsize=0;

            //파일 내용 읽어오기.
            while((resultsize = bufferedReader.read(readBuf)) != -1){
                if(resultsize==bufferSize){
                    stringBuilder.append(readBuf);
                }else{
                    for(int i=0; i<resultsize; i++){
                        stringBuilder.append(readBuf[i]);
                    }
                }
            }
            String jString = stringBuilder.toString();
            JSONObject jsonObject =  new JSONObject(jString);
            JSONArray jArr = new JSONArray(jsonObject.getString("OBJ"));
            String btnTitle [] = new String[jArr.length()];


            for(int i=0; i<jArr.length(); i++){
                String title =  jArr.getJSONObject(i).getString("name").toString();
                String info =  jArr.getJSONObject(i).getString("text").toString();
                Card_View(title,info);
            }



        }
        catch(JSONException je){
            Toast.makeText(MainActivity.this, " " + je, Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(MainActivity.this, " " + e, Toast.LENGTH_SHORT).show();
        }


    }

    public void Card_View(String title, String info){
        String text = title +"\n" +info;
        CardView cardView = new CardView(this);
        //cardView.setId();
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //어떤 id로 몇 번째 값인지 찾기?
                Intent intent = new Intent(getApplicationContext(), object_print.class);
                startActivity(intent);
            }
        });
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.setMargins(10,10,10,10);
        lp.setMarginEnd(30);
        lp.width=900;
        lp.height=200;

        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams linearL = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearL.setMargins(10,10,10,10);
        linearL.setMarginEnd(30);
        linearLayout.setLayoutParams(lp);

        TextView textView = new TextView(this);
        textView.setText(text);
        //textView.setTextColor();
        textView.setTextSize(19);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setPadding(7,7,7,7);
        //linearLayout.addView(textView);

        textView.setLayoutParams(lp);
       cardView.addView(textView,0);



//        TextView textView_info = new TextView(this);
//        textView_info.setText(info);
//        //textView.setTextColor();
//        textView_info.setTextSize(10);
//        textView_info.setPadding(7,7,7,7);
//        cardView.addView(textView_info);
//        cardView.setPadding(20,20,20,20);
//        cardView.setRadius(10);
//
//        cardView.addView(textView_info,1);

        linearLayout.addView(cardView);
        linearLayout.setLayoutParams(lp);

        container.addView(linearLayout);

    }

}

