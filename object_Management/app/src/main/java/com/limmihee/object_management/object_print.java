package com.limmihee.object_management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class object_print extends AppCompatActivity {
    private TextView name;
    private TextView info;
    private TextView link;
    private RatingBar ratingBar;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_print);

        name = (TextView)findViewById(R.id.textView2) ;
        info = (TextView)findViewById(R.id.textView4) ;
        link= (TextView)findViewById(R.id.textView10) ;
        ratingBar= (RatingBar)findViewById(R.id.ratingBar);

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
            title =  jArr.getJSONObject(0).getString("name");
            //ratingBar.setRating(Integer.parseInt(jArr.getJSONObject(0).getString("ratingBar")));
            info.setText(jArr.getJSONObject(0).getString("text"));
            link.setText(jArr.getJSONObject(0).getString("link"));

        }
        catch(JSONException je){
            Toast.makeText(object_print.this, " " + je, Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(object_print.this, " " + e, Toast.LENGTH_SHORT).show();
        }
        name.setText(title);
//        ratingBar.setRating(0.5f);
        ratingBar.setRating(1);
    }

}
