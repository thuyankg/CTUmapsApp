package com.anle.myapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Display3dmodelMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display3dmodel_main);
        //set Toolbar
        Intent HomePage = new Intent(this, HomeMainActivity.class);
        Intent linkBroswer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://skfb.ly/6CUOM"));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(HomePage);
                    finish();
                }
            });
        }
        final Intent intent = new Intent(this,Display3dmodelActivity.class);
        List<ListName> image_details = getListData();
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new CustomListAdapter(this, image_details));

        // Khi người dùng click vào các ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                ListName listName = (ListName) o;
                String str = listName.toString();
                Toast.makeText(Display3dmodelMainActivity.this, "Selected :" + " " + listName, Toast.LENGTH_LONG).show();
                switch (str){
                    case "Nhà học A3":
                        intent.putExtra("ASSET_ID","1hUY2kfXrIn");
                        intent.putExtra("link","https://skfb.ly/6CFxw");
                        startActivity(intent);
                        break;
                    case "Nhà học B1":
                        intent.putExtra("ASSET_ID","7fGNNsO00ti");
                        intent.putExtra("link","https://skfb.ly/6CFxr");
                        startActivity(intent);
                        break;
                    case "Nhà học C1":
                        intent.putExtra("ASSET_ID","aMXhJMJ2Dd7");
                        intent.putExtra("link","https://skfb.ly/6CFx7");
                        startActivity(intent);
                        break;
                    case "Khoa Công nghệ thông tin và Truyền thông":
                        intent.putExtra("ASSET_ID","aOEAJbfxCVN");
                        intent.putExtra("link","https://skfb.ly/6CFx8");
                        startActivity(intent);
                        break;
                    case "Khoa Công nghệ":
                        intent.putExtra("ASSET_ID","0p1E3pbHCOf");
                        intent.putExtra("link","https://skfb.ly/6CFxH");
                        startActivity(intent);
                        break;
                    case "Hội trường Rùa":
                        intent.putExtra("ASSET_ID","5EIXCkDDjRR");
                        intent.putExtra("link","https://skfb.ly/6CFxD");
                        startActivity(intent);
                        break;
                    case "Khoa Khoa học Chính trị":
                        intent.putExtra("ASSET_ID","e6BHhkJ0XOg");
                        intent.putExtra("link","https://skfb.ly/6CFxF");
                        startActivity(intent);
                        break;
                    case "Khoa Khoa học Tự nhiên":
                        intent.putExtra("ASSET_ID","3x9ceDypUSh");
                        intent.putExtra("link","https://skfb.ly/6CFxI");
                        startActivity(intent);
                        break;
                    case "Khoa Khoa học xã hội nhân văn":
                        intent.putExtra("ASSET_ID","38-NGZ8KFN2");
                        intent.putExtra("link","https://skfb.ly/6CEpv");
                        startActivity(intent);
                        break;
                    case "Khoa Kinh Tế":
                        intent.putExtra("ASSET_ID","0YZl2wODba8");
                        intent.putExtra("link","https://skfb.ly/6CFx9");
                        startActivity(intent);
                        break;
                    case "Ký túc xá A":
                        intent.putExtra("ASSET_ID","eHeOBYecT1v");
                        intent.putExtra("link","https://skfb.ly/6CFxx");
                        startActivity(intent);
                        break;
                    case "Ký túc xá B":
                        intent.putExtra("ASSET_ID","56_FNGM3RkD");
                        intent.putExtra("link","https://skfb.ly/6CFxB");
                        startActivity(intent);
                        break;
                    case "Khoa Môi trường và Tài nguyên thiên nhiên":
                        intent.putExtra("ASSET_ID","aEhpx6n2rRt");
                        intent.putExtra("link","https://skfb.ly/6CFxK");
                        startActivity(intent);
                        break;
                    case "Nhà Điều Hành":
                        intent.putExtra("ASSET_ID","11QyJyUpq40");
                        intent.putExtra("link","https://skfb.ly/6CFxC");
                        startActivity(intent);
                        break;
                    case "Nhà thi đấu":
                        intent.putExtra("ASSET_ID","bAwIY9Ku_X-");
                        intent.putExtra("link","https://skfb.ly/6CFxy");
                        startActivity(intent);
                        break;
                    case "Khoa nông nghiệp":
                        intent.putExtra("ASSET_ID","8Vt2WXWPyZU");
                        intent.putExtra("link","https://skfb.ly/6CFxu");
                        startActivity(intent);
                        break;
                    case "Khoa Sư phạm":
                        intent.putExtra("ASSET_ID","aehc8OYJyV5");
                        intent.putExtra("link","https://skfb.ly/6CFxM");
                        startActivity(intent);
                        break;
                    case "Khoa Thủy sản":
                        intent.putExtra("ASSET_ID","fceEG5Qp4ak");
                        intent.putExtra("link","https://skfb.ly/6CFxv");
                        startActivity(intent);
                        break;
                    case "Trung tâm học liệu":
                        intent.putExtra("ASSET_ID","1_VR1BhEKtl");
                        intent.putExtra("link","https://skfb.ly/6CFxA");
                        startActivity(intent);
                        break;
                    case "Văn phòng Đoàn":
                        intent.putExtra("ASSET_ID","1u4fxP9-P0R");
                        intent.putExtra("link","https://skfb.ly/68wB6");
                        startActivity(intent);
                        break;
                    case "Viện nghiên cứu và Phát triển công nghệ sinh học":
                        intent.putExtra("ASSET_ID","1iTg44vELth");
                        intent.putExtra("link","https://skfb.ly/6CEpz");
                        startActivity(intent);
                        break;
                    case "Đại học Cần Thơ":

                        startActivity(linkBroswer);
                        break;
                }
            }
        });
    }

    private  List<ListName> getListData() {
        List<ListName> list = new ArrayList<ListName>();
        ListName a3 = new ListName("Nhà học A3", "a3");
        ListName b1 = new ListName("Nhà học B1", "b1");
        ListName c1 = new ListName("Nhà học C1", "c1");
        ListName cntt = new ListName("Khoa Công nghệ thông tin và Truyền thông", "cntt");
        ListName congnghe = new ListName("Khoa Công nghệ", "congnghe");
        ListName htr = new ListName("Hội trường Rùa", "htr");
        ListName khoahocchinhtri = new ListName("Khoa Khoa học Chính trị", "khoahocchinhtri");
        ListName khoahoctunhien = new ListName("Khoa Khoa học Tự nhiên", "khoahoctunhien");
        ListName khoahocxahoinhanvan = new ListName("Khoa Khoa học xã hội nhân văn", "khoahocxahoinhanvan");
        ListName kinhte = new ListName("Khoa Kinh Tế", "kinhte");
        ListName ktxa = new ListName("Ký túc xá A", "ktxa");
        ListName ktxb = new ListName("Ký túc xá B", "ktxb");
        ListName mttntn = new ListName("Khoa Môi trường và Tài nguyên thiên nhiên", "mttntn");
        ListName ndh = new ListName("Nhà Điều Hành", "ndh");
        ListName nhathidau = new ListName("Nhà thi đấu", "nhathidau");
        ListName nongnghiep = new ListName("Khoa nông nghiệp", "nongnghiep");
        ListName supham = new ListName("Khoa Sư phạm", "supham");
        ListName thuysan = new ListName("Khoa Thủy sản", "thuysan");
        ListName tthl = new ListName("Trung tâm học liệu", "tthl");
        ListName vanphongdoan = new ListName("Văn phòng Đoàn", "vanphongdoan");
        ListName viennccnsh = new ListName("Viện nghiên cứu và Phát triển công nghệ sinh học", "viennccnsh");
        ListName dhct = new ListName("Đại học Cần Thơ", "dhct");
        list.add(a3);
        list.add(b1);
        list.add(c1);
        list.add(cntt);
        list.add(congnghe);
        list.add(htr);
        list.add(khoahocchinhtri);
        list.add(khoahoctunhien);
        list.add(khoahocxahoinhanvan);
        list.add(kinhte);
        list.add(ktxa);
        list.add(ktxb);
        list.add(mttntn);
        list.add(ndh);
        list.add(nhathidau);
        list.add(nongnghiep);
        list.add(supham);
        list.add(thuysan);
        list.add(tthl);
        list.add(vanphongdoan);
        list.add(viennccnsh);
        list.add(dhct);



        return list;
    }
}