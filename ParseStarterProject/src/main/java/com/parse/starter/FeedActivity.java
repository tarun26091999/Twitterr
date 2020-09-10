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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        setTitle("Your Feed");

        final ListView feedList = (ListView) findViewById(R.id.feedList);



        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tweet");

        query.whereContainedIn("username" , ParseUser.getCurrentUser().getList("isFollowing"));
        query.orderByDescending("createdAt");
        query.setLimit(50);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(e == null) {

                    if(objects.size() > 0) {

                        List< Map <String , String> > tweetdata = new ArrayList<Map <String , String> >();

                        for(ParseObject tweet : objects) {

                                Map<String , String> tweetinfo = new HashMap<String , String>();

                                tweetinfo.put("username" , tweet.getString("username"));

                                tweetinfo.put("content" , tweet.getString("tweet") );

                                tweetdata.add(tweetinfo);

                        }

                        SimpleAdapter simpleAdapter = new SimpleAdapter(FeedActivity.this, tweetdata, android.R.layout.simple_list_item_2, new String[] {"username" , "content" } , new int[] {android.R.id.text1  , android.R.id.text2 });

                        feedList.setAdapter(simpleAdapter);

                    }

                }

            }
        });


    }
}