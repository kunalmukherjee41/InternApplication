package com.kunal.internapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kunal.internapplication.adapter.TitleDialogAdapter;
import com.kunal.internapplication.model.Item;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TitleDialog.TitleDialogListener, TitleDialogAdapter.OnButtonClickedListener {

    private static final int PICK_IMAGE = 1;
    private RecyclerView recyclerView;
    private TitleDialogAdapter adapter;
    private List<Item> items;
    private ProgressBar progressBar;
    private int pos;
    private List<Bitmap> bitmaps;
    private TextView comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        comment = findViewById(R.id.comment);
        comment.setVisibility(View.VISIBLE);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> openDialog());

        items = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerview);
//        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemViewCacheSize(items.size());
        adapter = new TitleDialogAdapter(items, this, this);
        adapter.setHasStableIds(true);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }

    private void openDialog() {
        TitleDialog titleDialog = new TitleDialog();
        titleDialog.show(getSupportFragmentManager(), "Title Dialog");
    }

    @Override
    public void applyTexts(String title) {
        comment.setVisibility(View.GONE);
        Item item1 = new Item();
        item1.setTitle(title);
        items.add(item1);
        adapter.addItems(items);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bitmaps = new ArrayList<>();
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            ClipData clipData = data.getClipData();

            if (items.get(pos).getBitmaps() != null) {
                bitmaps.addAll(items.get(pos).getBitmaps());
            }

            if (clipData != null) {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    Uri imageUri = clipData.getItemAt(i).getUri();
                    try {
                        InputStream is = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        bitmaps.add(Bitmap.createScaledBitmap(bitmap, 200, 200, true));
                        items.get(pos).setBitmaps(bitmaps);
                        adapter.notifyDataSetChanged();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            } else {
                Uri imageUri = data.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    bitmaps.add(Bitmap.createScaledBitmap(bitmap, 200, 200, true));
                    items.get(pos).setBitmaps(bitmaps);
                    adapter.notifyDataSetChanged();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onButtonClick(int position, List<Item> items) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        pos = position;
        this.items.clear();
        this.items.addAll(items);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Images"), PICK_IMAGE);
    }
}