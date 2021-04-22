package com.thils.librarybook;

import android.app.ProgressDialog;
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
import com.androidnetworking.interfaces.StringRequestListener;

public class DeleteActivity extends AppCompatActivity {

    private EditText etName;
    private Button btnDelete;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait");
        etName = (EditText) findViewById(R.id.etName);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDelete();
            }
        });
    }

    private void onDelete() {
        String name = etName.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), "Enter valid name", Toast.LENGTH_LONG).show();
            return;
        }
        dialog.show();
        AndroidNetworking.delete("http://192.168.42.32/Library/Server/book.php")
                .addBodyParameter("action", "deletebook")
                .addBodyParameter("bid", name)
                .setTag("test1")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        dialog.hide();
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(ANError anError) {
                        dialog.hide();
                        Toast.makeText(getApplicationContext(), anError.toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
