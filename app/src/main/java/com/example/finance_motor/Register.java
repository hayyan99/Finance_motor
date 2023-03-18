package com.example.finance_motor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.android.volley.VolleyError;


public class Register extends AppCompatActivity {
    // UI components
    private EditText editTextName, editTextEmail, editTextPassword, editTextPhone;
    private Button buttonUploadImage, buttonSubmit;
    private ImageView imageViewSelectedImage;

    // Selected image
    private Bitmap selectedImageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize UI components
        editTextName = findViewById(R.id.edit_text_name);
        editTextEmail = findViewById(R.id.edit_text_email);
        editTextPassword = findViewById(R.id.edit_text_password);
        editTextPhone = findViewById(R.id.edit_text_phone);
        buttonUploadImage = findViewById(R.id.button_upload_image);
        buttonSubmit = findViewById(R.id.button_submit);
        imageViewSelectedImage = findViewById(R.id.image_view_selected_image);

        // Handle button click to upload image
        buttonUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show image picker
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }

        });

        // Handle button click to submit data
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get form data
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                String phone = editTextPhone.getText().toString();

                // Validate form data
                if (name.isEmpty()) {
                    Toast.makeText(Register.this, "Name is required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (email.isEmpty()) {
                    Toast.makeText(Register.this, "Email is required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.isEmpty()) {
                    Toast.makeText(Register.this, "Password is required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.isEmpty()) {
                    Toast.makeText(Register.this, "Phone is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Convert image to base64 string
                String imageString = null;
                if (selectedImageBitmap != null) {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    selectedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    byte[] imageBytes = outputStream.toByteArray();
                    imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                }

                // Send data to API
                new SendDataToApiTask().execute(name, email, password, phone, imageString);
            }
        });
    }

    // Handle image picker result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get selected image
            Uri selectedImageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                selectedImageBitmap = BitmapFactory
                        .decodeStream(inputStream);
                imageViewSelectedImage.setImageBitmap(selectedImageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // AsyncTask to send data to API
    private class SendDataToApiTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            // API endpoint URL
            String url = "http://192.168.149.111/MVFP/user_reg.php";

            // Build POST data
            String name = params[0];
            String email = params[1];
            String password = params[2];
            String phone = params[3];
            String imageString = params[4];

            String postData = "name=" + name + "&email=" + email + "&password=" + password + "&phone=" + phone;
            if (imageString != null) {
                postData += "&profile_img=" + imageString;
            }

            // Send POST request
            try {
                URL apiUrl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) apiUrl.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.getOutputStream().write(postData.getBytes());

                // Read response
                InputStream inputStream = conn.getInputStream();
                byte[] responseBytes = new byte[1024];
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                int bytesRead;
                while ((bytesRead = inputStream.read(responseBytes)) > 0) {
                    outputStream.write(responseBytes, 0, bytesRead);
                }
                inputStream.close();
                String response = outputStream.toString("UTF-8");
                outputStream.close();
                return response;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String response) {
            if (response != null && response.equals("success")) {
                // Data was submitted successfully
                Toast.makeText(Register.this, "Data submitted successfully", Toast.LENGTH_SHORT).show();
                // Move to login activity
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            } else {
                // Data submission failed
                Toast.makeText(Register.this, "Data submission failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
