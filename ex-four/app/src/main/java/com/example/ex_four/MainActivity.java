package com.example.ex_four;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取按钮控件
        Button btn_2 = findViewById(R.id.btn_2);

        // 设置按钮点击事件
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取 EditText 控件
                EditText et = findViewById(R.id.et_1);
                // 获取输入的网址
                String url = et.getText().toString();

                // 创建隐式 Intent 打开网址
                Intent loadWeb = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(loadWeb); // 启动浏览器打开网址
            }
        });
    }
}
