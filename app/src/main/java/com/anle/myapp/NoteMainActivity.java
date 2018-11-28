package com.anle.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class NoteMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_main);
        //set Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }

    String content="<p>	    Đây là đề tài luận văn tốt nghiệp ngành Công nghệ thông tin năm 2018. Ứng dụng hỗ trợ định vị vị trí và tìm kiếm địa chỉ có sẵn trong khuôn viên Đại học Cần Thơ khu 2.</p>"+
        "<ul><li>   Chức năng tìm kiếm địa chỉ: chức năng hỗ trợ khi người dùng có vị trí trong khuôn viên trường</li><li>  Chức năng xem mô hình 3d: hỗ trợ người dùng xem các mô hình 3D của các nhà học, khoa viện,.. trong khuôn viên trường</li></ul>";
    String author="<p><small>Ứng dụng được tạo bởi Lê Thị Thúy An</small></p>" ;

        TextView txtcontent = findViewById(R.id.txtcontent);
        txtcontent.setText(android.text.Html.fromHtml(content));
        TextView txtauthor = findViewById(R.id.txtauthor);
        txtauthor.setText(android.text.Html.fromHtml(author));
    }
}
