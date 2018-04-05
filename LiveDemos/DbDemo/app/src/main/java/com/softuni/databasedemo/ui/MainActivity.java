package com.softuni.databasedemo.ui;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.softuni.databasedemo.R;
import com.softuni.databasedemo.data.local.bad.DbConnectionManager;
import com.softuni.databasedemo.data.local.bad.ProductRecord;
import com.softuni.databasedemo.data.local.best.AppDatabase;
import com.softuni.databasedemo.data.local.best.User;
import com.softuni.databasedemo.data.local.better.ProductsRepository;
import com.softuni.databasedemo.data.local.better.ProductsService;
import com.softuni.databasedemo.data.local.better.daos.ProductsDao;
import com.softuni.databasedemo.presenters.MainPresenter;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity implements MainPresenter.ViewListener {

    private MainPresenter presenter;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter();
        presenter.setViewListener(this);
        presenter.onScreenShown();

        btn.setOnClickListener(view -> onBtnClicked("Tak:" + view.toString()));

//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                  Log.e("TAG", "Tak");
//            }
//        });

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
//        for (int i = 0; i < list.size(); i++) {
//            Log.e("TAG", String.valueOf(list.get(i)));
//        }

//        for (Integer num : list) {
//            Log.e("TAG", String.valueOf(num));
//        }

//        list.forEach(integer -> Log.e("TAG", String.valueOf(integer)));

//        list.forEach(integer -> System.out.println(integer));

        list.forEach(System.out::println);
    }

    private int onBtnClicked(String msg) {
        return Log.e("TAG", msg);
    }

    @Override
    public void showProductsAsList(List<ProductRecord> allProducts) {
        for (ProductRecord product : allProducts) {
            onBtnClicked(product.toString());
        }
    }
}
