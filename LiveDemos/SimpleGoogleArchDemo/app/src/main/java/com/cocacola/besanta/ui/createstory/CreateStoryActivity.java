package com.cocacola.besanta.ui.createstory;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.cocacola.besanta.R;
import com.cocacola.besanta.databinding.ActivityCreateStoryBinding;
import com.cocacola.besanta.di.Injectable;
import com.cocacola.besanta.ui.MainActivity;

import javax.inject.Inject;

public class CreateStoryActivity extends AppCompatActivity implements Injectable {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    CreateStoryViewModel viewModel;
    ActivityCreateStoryBinding binding;
    private static int CAPTURE_IMAGE_REQUEST = 2;
    private String photoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_story);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CreateStoryViewModel.class);
        startActivityForResult(new Intent(this, ImageCaptureActivity.class), CAPTURE_IMAGE_REQUEST);
        initListeners();
        initSubscribers();
    }

    private void initSubscribers() {
        viewModel.shouldRedirectToMain().observe(this, shouldRedirect -> {
            if (shouldRedirect != null && shouldRedirect) {
                Intent intent = new Intent(CreateStoryActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        viewModel.getProgressBarVisibility().observe(this, visibility -> {
            if (visibility != null) {
                binding.progressBar.setVisibility(visibility ? View.VISIBLE : View.GONE);
                binding.btnTellYourStory.setEnabled(!visibility);
                binding.editStoryInfo.setEnabled(!visibility);
            }
        });
    }

    private void initListeners() {
        binding.btnTellYourStory.setOnClickListener(view -> {
            viewModel.onBtnTellYourStoryClicked(binding.editStoryInfo.getText().toString(), photoPath);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_REQUEST && data != null) {
            photoPath = data.getStringExtra(ImageCaptureActivity.EXTRA_IMAGE_PATH);
            if (resultCode == RESULT_OK && photoPath != null) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);
                ((ImageView) findViewById(R.id.img_story)).setImageBitmap(bitmap);
            } else {
                finish();
            }
        } else {
            finish();
        }
    }
}
