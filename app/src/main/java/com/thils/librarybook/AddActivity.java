package com.thils.librarybook;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

public class AddActivity extends AppCompatActivity {


    private EditText etName, etAuthor, etSupplier, etYear, etPath;
    private Button btnAdd;
    private Spinner etCat, etPub;

    private ProgressDialog dialog;

    private String category = null;
    private String rare = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait");
        etName = (EditText) findViewById(R.id.etName);
        etAuthor = (EditText) findViewById(R.id.etAuthor);
        etSupplier = (EditText) findViewById(R.id.etSupplier);
        etPath = (EditText) findViewById(R.id.etPath);
        etYear = (EditText) findViewById(R.id.etYear);
        etCat = (Spinner) findViewById(R.id.etCat);
        etPub = (Spinner) findViewById(R.id.etPub);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        setSpinner1();
        setSpinner2();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAdd();
            }
        });
    }

    public void onAdd() {
        String name = etName.getText().toString();
        String ay = etAuthor.getText().toString();
        String sup = etSupplier.getText().toString();
        String yr = etYear.getText().toString();
        String path = etPath.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), "Enter valid name", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(ay)) {
            Toast.makeText(getApplicationContext(), "Enter valid author name", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(sup)) {
            Toast.makeText(getApplicationContext(), "Enter valid supplier name", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(path)) {
            Toast.makeText(getApplicationContext(), "Enter valid path name", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(yr)) {
            Toast.makeText(getApplicationContext(), "Enter valid year", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(category)) {
            Toast.makeText(getApplicationContext(), "Enter valid category", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(rare)) {
            Toast.makeText(getApplicationContext(), "Enter valid public", Toast.LENGTH_LONG).show();
            return;
        }
        int pubval = rare.equals("rare") ? 0 : 1;

        dialog.show();
        AndroidNetworking.post("http://192.168.42.32/Library/Server/book.php")
                .addBodyParameter("action", "addbook")
                .addBodyParameter("name", name)
                .addBodyParameter("author", ay)
                .addBodyParameter("supplier", sup)
                .addBodyParameter("year", yr)
                .addBodyParameter("bookfile", path)
                .addBodyParameter("category", category)
                .addBodyParameter("public", String.valueOf(pubval))
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

    public void setSpinner1() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.cat, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etCat.setAdapter(adapter);
        etCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                category = null;
            }
        });
    }

    public void setSpinner2() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.rare, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etPub.setAdapter(adapter);
        etPub.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rare = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                rare = null;
            }
        });
    }
}
