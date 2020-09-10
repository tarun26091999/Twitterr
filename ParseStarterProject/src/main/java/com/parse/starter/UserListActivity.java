package com.parse.starter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserListActivity extends AppCompatActivity {

    ArrayList<String> users = new ArrayList<>();

    ArrayAdapter arrayAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = new MenuInflater(this);

        menuInflater.inflate(R.menu.tweet_menu , menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.tweet) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Send Tweet");

            final EditText tweetcontent = new EditText(this);

            builder.setView(tweetcontent);

            builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    //Log.i("sent" , tweetcontent.getText().toString());

                    ParseObject tweet = new ParseObject("Tweet");

                    tweet.put("username" , ParseUser.getCurrentUser().getUsername());

                    tweet.put("tweet" , tweetcontent.getText().toString());

                    tweet.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if(e == null) {

                                Toast.makeText(UserListActivity.this, "Tweet Sent!", Toast.LENGTH_SHORT).show();

                            } else {

                                Toast.makeText(UserListActivity.this, "Tweet failed!", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    dialogInterface.cancel();

                }
            });

            builder.show();

        } else if(item.getItemId() == R.id.logout) {

            ParseUser.logOut();

            Intent intent = new Intent(getApplicationContext() , MainActivity.class);
            startActivity(intent);

            Toast.makeText(this, "Logged Out!", Toast.LENGTH_SHORT).show();

        } else if(item.getItemId() == R.id.viewFeed) {

            Intent intent = new Intent(getApplicationContext() , FeedActivity.class);
            startActivity(intent);

        } else if(item.getItemId() == R.id.mytweets) {

            Intent intent = new Intent(getApplicationContext() , mytweetActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        setTitle("All Users");

        if(ParseUser.getCurrentUser().get("isFollowing") == null) {

            List<String> emptyList = new ArrayList<>();
            ParseUser.getCurrentUser().put("isFollowing" , emptyList);

        }


        final ListView listview = (ListView) findViewById(R.id.listview);

        listview.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_checked , users);

        listview.setAdapter(arrayAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                CheckedTextView checkedTextView = (CheckedTextView) view;

                if(checkedTextView.isChecked()) {

                    //Log.i("info" , "followed");

                    Toast.makeText(UserListActivity.this, "Following !", Toast.LENGTH_SHORT).show();

                    ParseUser.getCurrentUser().getList("isFollowing").add(users.get(i));

                    ParseUser.getCurrentUser().saveInBackground();

                } else {


                    //Log.i("info" , "unfollowed");

                    Toast.makeText(UserListActivity.this, "Unfollowed !", Toast.LENGTH_SHORT).show();

                    ParseUser.getCurrentUser().getList("isFollowing").remove(users.get(i));

                    try {
                        ParseUser.getCurrentUser().save();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }
                //Log.i("array" , ParseUser.getCurrentUser().getList("isFollowing").toString());

            }
        });

        users.clear();

        ParseQuery<ParseUser> query = ParseUser.getQuery();

        query.whereNotEqualTo("username" , ParseUser.getCurrentUser().getUsername());

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {

                if(e == null) {

                    if(objects.size() > 0) {

                        for(ParseUser user : objects) {

                            users.add(user.getUsername());

                        }

                        arrayAdapter.notifyDataSetChanged();

                        for (String username : users) {

                            if( ParseUser.getCurrentUser().getList("isFollowing").contains(username)) {

                                listview.setItemChecked(users.indexOf(username),true);

                            }

                        }

                    }

                }

            }
        });

    }
}