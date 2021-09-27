package com.example.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.profile.model.ResponseData;
import com.example.profile.service.ApiClient;
import com.example.profile.service.ApiProfile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private EditText editNama, editNoHp, editEmail;
    private Button btnSave;
    private boolean updateOperation = false;
    private int id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        progressDialog = new ProgressDialog(InputActivity.this);
        editNama = findViewById(R.id.edit_nama);
        editEmail = findViewById(R.id.edit_email);
        editNoHp = findViewById(R.id.edit_noHP);
        btnSave = findViewById(R.id.btn_simpan);

        Intent receivedData = getIntent();
        Bundle data = receivedData.getExtras();
        if(data.getString("OPERATION").equals("insert")) {
            updateOperation = false;
        } else{
            updateOperation = true;
            id = data.getInt("ID");
            editNama.setText(data.getString("NAMA"));
            editEmail.setText(data.getString("EMAIL"));
            editNoHp.setText(data.getString("NO TELEPON"));
        } btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_input, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.item_menu_delete) {
            deleteData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item = menu.findItem(R.id.item_menu_delete);

        if(updateOperation==true) {
            item.setEnabled(true);
            item.getIcon().setAlpha(255);
        } else{
            item.setEnabled(false);
            item.getIcon().setAlpha(130);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    private void deleteData() {
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("Hapus Data");
        builder.setMessage("Apakah anda yakin ingin menghapus data?")
                .setCancelable(false)
                .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        progressDialog.setMessage("Deleting...");
                        progressDialog.show();
                        ApiProfile api = ApiClient.getRetrofitInstance().create(ApiProfile.class);
                        Call<ResponseData> call = api.deleteData(String.valueOf(id));
                        call.enqueue(new Callback<ResponseData>() {
                            @Override
                            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                                String value = response.body().getValue();
                                String message = response.body().getMessage();
                                progressDialog.dismiss();

                                if(value.equals("1")) {
                                    Toast.makeText(InputActivity.this, "SUKSES: " + message, Toast.LENGTH_LONG).show();
                                } else{
                                    Toast.makeText(InputActivity.this, "GAGAL: " + message, Toast.LENGTH_LONG).show();
                                }

                                finish();
                            }

                            @Override
                            public void onFailure(Call<ResponseData> call, Throwable t) {
                                progressDialog.dismiss();
                                Toast.makeText(InputActivity.this, "Gagal menghubungi server...", Toast.LENGTH_SHORT).show();
                                t.printStackTrace();
                                Log.d("Detele Data Error", t.toString());
                            }
                        });
                    }
                }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void saveData() {
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        String nama = editNama.getText().toString();
        String noHp = editNoHp.getText().toString();
        String email = editEmail.getText().toString();
        if(!(nama.equals("") && noHp.equals("") && email.equals(""))) {
            ApiProfile api = ApiClient.getRetrofitInstance().create(ApiProfile.class);
            Call<ResponseData> call;
            if(updateOperation == false){
                call = api.addData(nama,noHp,email);
            } else{
                call = api.editData(String.valueOf(id), nama, noHp, email);
                updateOperation = false;
            }
            call.enqueue(new Callback<ResponseData>() {
                @Override
                public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                    String value = response.body().getValue();
                    String message = response.body().getMessage();
                    progressDialog.dismiss();
                    if(value.equals("1")) {
                        Toast.makeText(InputActivity.this,"SUKSES: " + message, Toast.LENGTH_LONG).show();
                        finish();
                    }  else{
                        Toast.makeText(InputActivity.this, "GAGAL: " + message, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseData> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(InputActivity.this, "Gagal menghubungi server...", Toast.LENGTH_LONG).show();
                    t.printStackTrace();
                    Log.d("Input Data Error", t.toString());
                }
            });
        } else {
            Toast.makeText(this, "Data nama dan nomor telepon harus diberikan", Toast.LENGTH_LONG).show();
        }
    }
}