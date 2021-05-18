package com.intern.cndd.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.intern.cndd.R;
import com.intern.cndd.prevalent.Prevalent;

public class HomeActivity extends AppCompatActivity {

    private ShapeableImageView mAvatarImageView;
    private TextView mTitleTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAvatarImageView = findViewById(R.id.avatarImageView);
        mTitleTextView = findViewById(R.id.titleTextView);

        mTitleTextView.setText("Hi, " + Prevalent.currentOnlineUser.getName() + "!");
        Glide
                .with(this)
                .load(Prevalent.currentOnlineUser.getPicture())
                .centerCrop()
                .into(mAvatarImageView);
    }
}