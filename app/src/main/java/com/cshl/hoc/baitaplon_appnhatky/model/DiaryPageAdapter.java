package com.cshl.hoc.baitaplon_appnhatky.model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cshl.hoc.baitaplon_appnhatky.DiaryListActivity;
import com.cshl.hoc.baitaplon_appnhatky.EditActivity;
import com.cshl.hoc.baitaplon_appnhatky.R;
import com.cshl.hoc.baitaplon_appnhatky.WriteActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DiaryPageAdapter extends FirebaseRecyclerAdapter<DiaryPage, DiaryPageAdapter.DiaryPageViewHolder> {
    private Context context;
    private int numPages = 0;
    public DiaryPageAdapter(@NonNull FirebaseRecyclerOptions<DiaryPage> options, Context context) {
        super(options);
        this.context = context;
    }


    @NonNull
    @NotNull
    @Override
    public DiaryPageViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_card, parent, false);
        return new DiaryPageViewHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull DiaryPageAdapter.DiaryPageViewHolder holder, int position, @NonNull @NotNull DiaryPage model) {
        String title = model.getTitle();
        holder.recTieuDe.setText(title);
        long createdDateTime = model.getCreatedDateTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss aa");
        String createdDate = dateFormat.format(new Date(createdDateTime));
        String createdTime = timeFormat.format(new Date(createdDateTime));
        holder.recNgayViet.setText("Ngày viết: " + createdDate);
        holder.recGioViet.setText("Giờ viết: " + createdTime);
        holder.recNoiDung.setText(model.getContent());
        holder.nutSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditActivity.class);
                intent.putExtra("diaryPage", model);
                context.startActivity(intent);
            }
        });
        holder.nutXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("diary").child(model.getTitle()).removeValue();
            }
        });
    }

    public class DiaryPageViewHolder extends RecyclerView.ViewHolder{
        private TextView recTieuDe, recNgayViet, recGioViet, recNoiDung;
        private Button nutSua, nutXoa;

        public DiaryPageViewHolder(@NonNull @NotNull View v) {
            super(v);
            initView(v);
        }

        private void initView(View v) {
            recTieuDe = v.findViewById(R.id.recTieuDe);
            recNgayViet = v.findViewById(R.id.recNgayViet);
            recGioViet = v.findViewById(R.id.recGioViet);
            recNoiDung = v.findViewById(R.id.recNoiDung);
            nutSua = v.findViewById(R.id.nutSua);
            nutXoa = v.findViewById(R.id.nutXoa);
        }
    }
}
