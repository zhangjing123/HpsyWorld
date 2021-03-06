package com.kuwai.ysy.module.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kuwai.ysy.R;
import com.kuwai.ysy.module.home.bean.main.PersonPicBean;
import com.kuwai.ysy.module.mine.bean.PersolHomePageBean;
import com.kuwai.ysy.utils.Utils;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class OtherPicAdapter extends RecyclerView.Adapter<OtherPicAdapter.ViewHolder> {

    private static final int TYPE_ADD = 1;
    private static final int TYPE_PIC = 2;
    private List<PersonPicBean.DataBean> mList = new ArrayList<>();
    private Context context;
    private static final int MAX_SIZE = 18;

    public OtherPicAdapter(OnAddItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        final ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_other_pic, parent, false));

        viewHolder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemAddClick();
                }
            }
        });

        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_ADD;
        } else {
            return TYPE_PIC;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (getItemViewType(position) == TYPE_ADD) {
            holder.ivPhoto.setVisibility(View.GONE);
            holder.ivAdd.setVisibility(View.VISIBLE);
            holder.ivVideo.setVisibility(View.GONE);
        } else {
            holder.ivAdd.setVisibility(View.GONE);
            holder.ivPhoto.setVisibility(View.VISIBLE);
            if (!"0".equals(mList.get(position).getVideo_id())) {
                holder.ivVideo.setVisibility(View.VISIBLE);
            } else {
                holder.ivVideo.setVisibility(View.GONE);
            }
            GlideUtil.loadRetangle(context, mList.get(position).getImg(), holder.ivPhoto);
            //holder.ivPhoto.setImageBitmap(BitmapFactory.decodeFile(mList.get(position)));
        }
        if (mList.size() >= MAX_SIZE) {
            //最多8张
            holder.ivAdd.setVisibility(View.GONE);
        } else {
            holder.ivPhoto.setVisibility(View.VISIBLE);
        }

        holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.photoClick(position, v);
                }
            }
        });
    }

    public void setData(List<PersonPicBean.DataBean> data) {
        this.mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public List<PersonPicBean.DataBean> getData() {
       return  mList;
    }

    @Override
    public int getItemCount() {
        if (mList.size() > MAX_SIZE) {
            return MAX_SIZE;
        }
        return mList.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivPhoto;
        private ImageView ivAdd;
        private ImageView ivVideo;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.iv_photo);
            ivAdd = itemView.findViewById(R.id.iv_add);
            ivVideo = itemView.findViewById(R.id.iv_video);
        }
    }

    private OnAddItemClickListener itemClickListener;

    public interface OnAddItemClickListener {
        /**
         * 继续添加图片接口
         */
        void onItemAddClick();

        void photoClick(int position, View v);
    }
}
