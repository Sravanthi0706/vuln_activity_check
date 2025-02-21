package com.sr.vulnactivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText packageNameInput, activityNameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        packageNameInput = findViewById(R.id.packageNameInput);
        activityNameInput = findViewById(R.id.activityNameInput);
        Button launchButton = findViewById(R.id.launchButton);

        // Check if ADB extras are passed
        String packageName = getIntent().getStringExtra("package");
        String activityName = getIntent().getStringExtra("activity");

        if (packageName != null && activityName != null) {
            // If ADB extras exist, launch the activity and close the app
            launchAppActivity(packageName, activityName);
            finish(); // Optional: Auto-close after execution
        }

        // Launch activity when button is clicked
        launchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userPackageName = packageNameInput.getText().toString().trim();
                String userActivityName = activityNameInput.getText().toString().trim();

                if (!userPackageName.isEmpty() && !userActivityName.isEmpty()) {
                    launchAppActivity(userPackageName, userActivityName);
                } else {
                    Toast.makeText(MainActivity.this, "Enter package and activity name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void launchAppActivity(String packageName, String activityName) {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(packageName, activityName));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Activity not found!", Toast.LENGTH_SHORT).show();
        }
    }
}
