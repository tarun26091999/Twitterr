/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;



public class MainActivity extends AppCompatActivity implements View.OnClickListener , View.OnKeyListener {



  Boolean signUpActive = true;
  TextView change;
  EditText passwordEdit;


  public void showUserList() {

    Intent intent = new Intent(getApplicationContext() , UserListActivity.class);
    startActivity(intent);

    Toast.makeText(this, "Logged In!", Toast.LENGTH_SHORT).show();

  }

  @Override
  public boolean onKey(View view, int i, KeyEvent keyEvent) {

    if( i== KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {

      SignUp(view);

    }

    return false;
  }

  @Override
  public void onClick(View view) {

    if(view.getId() == R.id.change ) {

      Button SignUpButton = (Button) findViewById(R.id.SignUpButton);

      if( signUpActive ) {

        signUpActive = false;
        SignUpButton.setText("Login");
        change.setText("New User? Sign Up");

      } else {

        signUpActive = true;
        SignUpButton.setText("Sign Up");
        change.setText("Already an user? Login");

      }

    } else if(view.getId() == R.id.Layout || view.getId() == R.id.Logo) {

      InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
      inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken() , 0);

    }

  }

  public void SignUp (View view) {

    EditText usernameEdit = (EditText) findViewById(R.id.usernameEdit);

    if( usernameEdit.getText().toString().matches("") || passwordEdit.getText().toString().matches("") ) {

      Toast.makeText(this, "Username and password required", Toast.LENGTH_SHORT).show();

    } else {

      if(signUpActive) {

        ParseUser user = new ParseUser();

        user.setUsername(usernameEdit.getText().toString());
        user.setPassword(passwordEdit.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
          @Override
          public void done(ParseException e) {

            if (e == null) {

              showUserList();

            } else {
              //Log.e("error", e.toString());
              Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }

          }
        });
      } else {

        ParseUser.logInInBackground(usernameEdit.getText().toString(), passwordEdit.getText().toString(), new LogInCallback() {
          @Override
          public void done(ParseUser user, ParseException e) {

            if( user != null) {

              //Log.i("Login" , "successful");

              showUserList();

            } else {

              Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }

          }
        });

      }
    }



  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    setTitle("Login / SignUp");

    change = (TextView) findViewById(R.id.change);

    change.setOnClickListener(this);

    RelativeLayout Layout = (RelativeLayout) findViewById(R.id.Layout);

    ImageView Logo = (ImageView) findViewById(R.id.Logo);

    Layout.setOnClickListener(this);

    Logo.setOnClickListener(this);

    passwordEdit = (EditText) findViewById(R.id.passwordEdit);

    passwordEdit.setOnKeyListener(this);

    if(ParseUser.getCurrentUser() != null) {

      showUserList();

    }

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}