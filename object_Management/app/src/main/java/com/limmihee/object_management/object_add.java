package com.limmihee.object_management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class object_add extends AppCompatActivity {
    private TextView name;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_add);
        name = (TextView)findViewById(R.id.textView2);

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
            title =  jArr.getJSONObject(0).getString("name").toString();



        }
         catch(JSONException je){
        Toast.makeText(object_add.this, " " + je, Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(object_add.this, " " + e, Toast.LENGTH_SHORT).show();
        }
        name.setText(title);
    }
}
