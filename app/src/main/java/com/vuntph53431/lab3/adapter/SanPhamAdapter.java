package com.vuntph53431.lab3.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import com.vuntph53431.lab3.R;
import com.vuntph53431.lab3.dao.SanPhamDAO;
import com.vuntph53431.lab3.model.SanPham;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.SanPhamViewHolder> {
    private ArrayList<SanPham> lst;
    private Context context;
    private SanPhamDAO dao;
    public SanPhamAdapter(ArrayList<SanPham> lst, Context context) {
        this.lst = lst;
        this.context = context;
        dao = new SanPhamDAO(context);
    }


    @NonNull
    @Override
    public SanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);

        return new SanPhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamViewHolder holder, int position) {

        SanPham pos = lst.get(position);
        holder.tvTitle.setText(pos.getTitle());
        holder.tvContent.setText(pos.getContent());
        holder.tvDate.setText(pos.getDate());
        holder.cbCheck.setChecked(pos.getStatus() == 1);
        holder.btnDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Warning");
            builder.setMessage("Bạn có muốn xóa sản phẩm này không?");
            builder.setPositiveButton("Có", (dialog, which) ->{
                int id = pos.getId();
                Boolean check = dao.deleteSP(id);
                if (check != null && check){
                    Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                    lst.remove(position);
                    notifyDataSetChanged();
                }else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }

            });
            builder.setNegativeButton("Không", (dialog, which) ->{
                dialog.dismiss();
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
        holder.cbCheck.setOnCheckedChangeListener((buttonView, isChecked) -> {

                if (pos.getStatus() == 0) {
                    pos.setStatus(1);
                    Toast.makeText(context, "Update Status thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Update status thất bại", Toast.LENGTH_SHORT).show();
                }

                Boolean check = dao.updateStatus(pos);
                if (check != null && check) {
                    Toast.makeText(context, "Update thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Update thất bại", Toast.LENGTH_SHORT).show();
                }

        });
        holder.btnEdit.setOnClickListener(v -> {
            edit(pos,position);
        });
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    public static class SanPhamViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle,tvContent,tvDate;
        private ImageButton btnEdit,btnDelete;
        private CheckBox cbCheck;
        public SanPhamViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvDate = itemView.findViewById(R.id.tvDate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            cbCheck = itemView.findViewById(R.id.cbCheck);

        }
    }
    public void edit(SanPham sp, int pos) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        TextInputEditText edtTitle = view.findViewById(R.id.edtTitle);
        TextInputEditText edtContent = view.findViewById(R.id.edtContent);
        TextInputEditText edtDate = view.findViewById(R.id.edtDate);
        TextInputEditText edtStyle = view.findViewById(R.id.edtStyle);
        AppCompatButton btnUpdate = view.findViewById(R.id.btnUpdate);
        AppCompatButton btnCancel = view.findViewById(R.id.btnCancel);
        edtTitle.setText(sp.getTitle());
        edtContent.setText(sp.getContent());
        edtDate.setText(sp.getDate());
        edtStyle.setText(sp.getStyle());
        edtStyle.setOnClickListener(v -> {
            String[] str = {"Khó", "Bình Thường", "Dễ"};
            AlertDialog.Builder dialog1 = new AlertDialog.Builder(context);
            dialog1.setTitle("Chọn độ khó");
            dialog1.setItems(str, (dialogInterface, i) -> {
                edtStyle.setText(str[i]);
            });
            AlertDialog dialog2 = dialog1.create();
            dialog2.show();


        });
        btnUpdate.setOnClickListener(v -> {
            String title = edtTitle.getText().toString();
            String content = edtContent.getText().toString();
            String date = edtDate.getText().toString();
            String style = edtStyle.getText().toString();
            sp.setTitle(title);
            sp.setContent(content);
            sp.setDate(date);
            sp.setStyle(style);


            boolean check = dao.updateSP(sp);
            if (check) {
                lst.set(pos, sp);
                notifyItemChanged(pos);
            } else {
                Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();


        });
        btnCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }
}
