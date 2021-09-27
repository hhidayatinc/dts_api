package com.example.profile.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.profile.InputActivity;
import com.example.profile.R;
import com.example.profile.TampilActivity;
import com.example.profile.model.Profile;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHandler> {
    private List<Profile> dataProfile;
    private Context context;

    public ProfileAdapter(List<Profile> dataProfile, Context context) {
        this.dataProfile = dataProfile;
        this.context = context;
    }

    @NonNull
    @Override
    public ProfileViewHandler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_contact, parent, false);
        return new ProfileViewHandler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.ProfileViewHandler holder, int position) {
        Profile tempProfile = dataProfile.get(position);
        holder.id = tempProfile.getId();
        holder.nama = tempProfile.getNama();
        holder.email = tempProfile.getEmail();
        holder.noHp = tempProfile.getNoHp();

    }


    @Override
    public int getItemCount() {
        return dataProfile.size();
    }

    public class ProfileViewHandler extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private int id;
        private String nama;
        private String email;
        private String noHp;
        private TextView tvNama, tvEmail;

        public ProfileViewHandler(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvEmail = itemView.findViewById(R.id.tv_email);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent openDisplay = new Intent(context, TampilActivity.class);
            openDisplay.putExtra("NAMA", nama);
            openDisplay.putExtra("EMAIL", tvEmail.getText());
            openDisplay.putExtra("NO HP", noHp);
            itemView.getContext().startActivity(openDisplay);
        }

        @Override
        public boolean onLongClick(View view) {
            Intent openInput = new Intent(context, InputActivity.class);
            openInput.putExtra("OPERATION", "update");
            openInput.putExtra("ID", id);
            openInput.putExtra("NAMA", nama);
            openInput.putExtra("EMAIL", tvEmail.getText());
            openInput.putExtra("NO HP", noHp);
            itemView.getContext().startActivity(openInput);
            return true;
        }
    }
}
