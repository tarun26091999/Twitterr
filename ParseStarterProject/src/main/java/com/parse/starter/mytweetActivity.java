package com.parse.starter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class mytweetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytweet);

        setTitle("My Tweets");

        final ListView mytweetList = (ListView) findViewById(R.id.mytweetList);


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tweet");

        query.whereEqualTo("username" , ParseUser.getCurrentUser().getUsername());

        query.orderByDescending("createdAt");
        query.setLimit(50);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(e == null) {

                    if(objects.size() > 0) {

                        List<Map<String , String>> tweetdata = new ArrayList<Map <String , String> >();

                        for(ParseObject tweet : objects) {

                            Map<String , String> tweetinfo = new HashMap<String , String>();

                            tweetinfo.put("username" , tweet.getString("username"));

                            tweetinfo.put("content" , tweet.getString("tweet") );

                            tweetdata.add(tweetinfo);

                        }

                        SimpleAdapter simpleAdapter = new SimpleAdapter(mytweetActivity.this, tweetdata, android.R.layout.simple_list_item_2, new String[] {"username" , "content" } , new int[] {android.R.id.text1  , android.R.id.text2 });

                        mytweetList.setAdapter(simpleAdapter);

                    }

                }

            }
        });


    }
}