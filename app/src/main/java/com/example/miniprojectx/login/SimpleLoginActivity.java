package com.example.miniprojectx.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.miniprojectx.MainActivity;
import com.example.miniprojectx.R;
import com.example.miniprojectx.data.FacebookUser;

public class SimpleLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_login);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new LoginFragment())
                    .commit();
        }
    }

    public void changeSingup() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new SignUpFragment())
                .addToBackStack(null)
                .commit();
    }

    public void changeFacebookSignup(FacebookUser user) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, FacebookSignupFragment.newInstance(user))
                .addToBackStack(null)
                .commit();
    }


    public void moveMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
