package com.vuntph53431.lab3.screens;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import com.vuntph53431.lab3.R;
import com.vuntph53431.lab3.adapter.SanPhamAdapter;
import com.vuntph53431.lab3.dao.SanPhamDAO;
import com.vuntph53431.lab3.model.SanPham;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rcvSP;
    private SanPhamDAO dao;
    private AppCompatButton btnAdd;
    private TextInputEditText edtTitle, edtContent, edtDate, edtStyle;
    private ArrayList<SanPham> lst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        rcvSP = findViewById(R.id.rcvSP);
        edtContent = findViewById(R.id.edtContent);
        edtTitle = findViewById(R.id.edtTitle);
        edtDate = findViewById(R.id.edtDate);
        edtStyle = findViewById(R.id.edtStyle);
        btnAdd = findViewById(R.id.btnAdd);
        dao = new SanPhamDAO(MainActivity.this);
        lst = dao.getSP();
        SanPhamAdapter adapter = new SanPhamAdapter(lst,MainActivity.this);
        rcvSP.setAdapter(adapter);
        rcvSP.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));
        clickEdtStyle();
        btnAdd.setOnClickListener(v -> {
            String title = edtTitle.getText().toString();
            String content = edtContent.getText().toString();
            String date = edtDate.getText().toString();
            String style = edtStyle.getText().toString();
            SanPham sp = new SanPham(title,content,date,style,1,2);
            Boolean check = dao.insertSP(sp);
            if(check){
                lst.add(sp);
                Toast.makeText(MainActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                loadData();
            }
        });

    }
    private void loadData(){
        lst.removeAll(lst);
        for (SanPham sp : dao.getSP()){
            lst.add(sp);
        }
        SanPhamAdapter adapter = new SanPhamAdapter(lst,MainActivity.this);
    }
    public void clickEdtStyle(){
        edtStyle.setOnClickListener(v -> {
            String[] str = {"Khó","Bình Thường","Dễ"};
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle("Chọn độ khó");
            dialog.setItems(str, (dialogInterface, i) -> {
                edtStyle.setText(str[i]);
            });
            AlertDialog dialog1 = dialog.create();
            dialog1.show();


        });
    }
}