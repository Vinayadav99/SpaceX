package com.example.assignment.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.Database.MemberDatabase;
import com.example.assignment.Model.Member;
import com.example.assignment.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private Context context;
    private MemberDatabase memberDatabase;
    List<Member> memberList;

    public MyAdapter(Context context, MemberDatabase memberDatabase){
        this.context = context;
        this.memberDatabase = memberDatabase;
        memberList = this.memberDatabase.getMemberDAO().getAllMember();
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {

        Member member = memberList.get(position);
        holder.name.setText(context.getResources().getString(R.string.card_name,member.getName()));
        holder.agency.setText(context.getResources().getString(R.string.card_agency,member.getAgency()));
        holder.link.setText(context.getResources().getString(R.string.card_link,member.getLink()));
        holder.status.setText(context.getResources().getString(R.string.card_status,member.getStatus()));

        Picasso.get().load(member.getImage()).into(holder.image);

        //Util.fetchSvg(context, member.getImage(), holder.image);




    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        TextView name,agency,link,status;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.card_image);
            name = itemView.findViewById(R.id.card_name);
            agency = itemView.findViewById(R.id.card_agency);
            link = itemView.findViewById(R.id.card_hyperlink);
            status = itemView.findViewById(R.id.card_status);

            link.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

        }
    }
}
