package com.thils.librarybook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.thils.librarybook.model.User;
import com.thils.librarybook.util.Settings;

import org.json.JSONArray;

public class LoginActivity extends AppCompatActivity {

    private EditText etUName;
    private EditText etPass;
    private Button btnLogin;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait");
        etUName = (EditText) findViewById(R.id.etUName);
        etPass = (EditText) findViewById(R.id.etPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        String uName = etUName.getText().toString();
        String uPass = etUName.getText().toString();
        if (!TextUtils.isEmpty(uName) && !TextUtils.isEmpty(uPass)) {
            dialog.show();
            AndroidNetworking.post("http://192.168.42.32/Library/Server/User.php")
                    .addBodyParameter("action", "login")
                    .addBodyParameter("uname", uName)
                    .addBodyParameter("pass", uPass)
                    .setTag("test")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray response) {
                            dialog.hide();
                            if (response.length() > 0) {
                                try {
                                    User user = User.getUser(response.getJSONObject(0));
                                    if (user.username == null || user.username.length() < 1) {
                                        Toast.makeText(getApplicationContext(), "Unable to get user", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "user found " + response.toString(), Toast.LENGTH_LONG).show();
                                        Toast.makeText(getApplicationContext(), user.username, Toast.LENGTH_LONG).show();
                                        Settings s = new Settings(LoginActivity.this);
                                        s.setUser(user.username);
                                        startActivity(new Intent(LoginActivity.this, SelectionActivity.class));
                                        finish();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), "Something wrong", Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            dialog.hide();
                            Toast.makeText(getApplicationContext(), anError.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "Enter valid data", Toast.LENGTH_LONG).show();
        }
    }
}
