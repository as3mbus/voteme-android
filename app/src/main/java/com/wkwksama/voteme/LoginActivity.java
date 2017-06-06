package com.wkwksama.voteme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    protected class BtnLoginListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent iLogin = new Intent(v.getContext(),MainActivity.class);
            startActivity(iLogin);
        }
    }
    protected class BtnRegisterListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent iAPI = new Intent(v.getContext(),TestActivity.class);
            startActivity(iAPI);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin = (Button)this.findViewById(R.id.btn_signin);
        btnLogin.setOnClickListener(new BtnLoginListener());
        Button btnRegis = (Button)this.findViewById(R.id.btn_signup);
        btnRegis.setOnClickListener(new BtnRegisterListener());

    }
}
