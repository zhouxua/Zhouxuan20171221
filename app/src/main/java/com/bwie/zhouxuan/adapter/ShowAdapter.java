package com.bwie.zhouxuan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.zhouxuan.R;
import com.bwie.zhouxuan.XinagqingActivity;
import com.bwie.zhouxuan.api.MessageEvent;
import com.bwie.zhouxuan.api.MyOnItemClickListener;
import com.bwie.zhouxuan.bean.ShowBean;
import com.bwie.zhouxuan.bean.XiangqingBean;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by dream on 2017/12/21.
 */

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.MyViewHolder> {
         Context context;
         ShowBean.TuijianBean list;
    private MyOnItemClickListener itemClickListener;

    public void setOnItemClickListener(MyOnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    public ShowAdapter(Context context, ShowBean.TuijianBean list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        String[] split = list.getList().get(position).getImages().split("\\|");
        holder.sdv.setImageURI(split[0]);
       holder.price.setText("￥"+list.getList().get(position).getPrice());
       holder.title.setText(list.getList().get(position).getTitle());
        ShowAdapter.this.setOnItemClickListener(new MyOnItemClickListener() {
            @Override
            public void OnItemClickListener(View view, int position) {
//                Toast.makeText(context, "position" + position + "被点击", Toast.LENGTH_SHORT).show();
                int pid = list.getList().get(position).getPid();
                EventBus.getDefault().postSticky(new MessageEvent(""+pid));
                Intent intent = new Intent(context, XinagqingActivity.class);
                context.startActivity(intent);
            }
        });
        if (itemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.OnItemClickListener(holder.itemView, holder.getLayoutPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.getList().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView sdv;
        TextView price,title;
        public MyViewHolder(View itemView) {
            super(itemView);
            sdv = itemView.findViewById(R.id.sdv);
            price = itemView.findViewById(R.id.price);
            title = itemView.findViewById(R.id.title);
        }
    }
}
