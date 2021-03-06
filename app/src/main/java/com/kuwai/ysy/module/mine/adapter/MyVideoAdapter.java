package com.kuwai.ysy.module.mine.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.circle.VideoPlayActivity;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.PersolHomePageBean;
import com.kuwai.ysy.module.mine.bean.WallBean;
import com.kuwai.ysy.utils.EventBusUtil;
import com.rayhahah.dialoglib.DialogInterface;
import com.rayhahah.dialoglib.MDAlertDialog;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;
import java.util.List;

import cc.shinichi.library.ImagePreview;
import io.reactivex.functions.Consumer;

public class MyVideoAdapter extends RecyclerView.Adapter<MyVideoAdapter.ViewHolder> {

    private static final int TYPE_ADD = 1;
    private static final int TYPE_PIC = 2;
    private List<WallBean.DataBean.VideoBean> mList = new ArrayList<>();
    private Context context;
    private static final int MAX_SIZE = 198;

    public MyVideoAdapter(OnAddItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        final ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_my_video, parent, false));

        viewHolder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemVideoAddClick();
                }
            }
        });

        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_ADD;
        } else {
            return TYPE_PIC;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (getItemViewType(position) == TYPE_ADD) {
            holder.ivPhoto.setVisibility(View.GONE);
            holder.ivAdd.setVisibility(View.VISIBLE);
        } else {
            holder.ivAdd.setVisibility(View.GONE);
            holder.ivPhoto.setVisibility(View.VISIBLE);
            GlideUtil.loadRetangle(context, mList.get(position - 1).getAttach(), holder.ivPhoto);
            //holder.ivPhoto.setImageBitmap(BitmapFactory.decodeFile(mList.get(position)));
        }
        holder.ivPhoto.setTag(R.id.image_key, position);
        /*if (mList.size() >= MAX_SIZE) {
            //最多8张
            holder.ivAdd.setVisibility(View.GONE);
        } else {
            holder.ivPhoto.setVisibility(View.VISIBLE);
        }*/
    }

    public void setData(List<WallBean.DataBean.VideoBean> data) {
        this.mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(@NonNull WallBean.DataBean.VideoBean data) {
        mList.add(data);
        notifyItemInserted(mList.size());
        compatibilityDataSizeChanged(1);
    }

    private void compatibilityDataSizeChanged(int size) {
        final int dataSize = mList == null ? 0 : mList.size();
        if (dataSize == size) {
            notifyDataSetChanged();
        }
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

        public ViewHolder(View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.iv_photo);
            ivAdd = itemView.findViewById(R.id.iv_add);

            ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag(R.id.image_key);
                    Intent intent = new Intent(context, VideoPlayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("vid", mList.get(pos - 1).getVideo_id());
                    bundle.putString("imgurl", mList.get(pos - 1).getAttach());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

            ivPhoto.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final int pos = (int) v.getTag(R.id.image_key);

                    new MDAlertDialog.Builder(context)
                            .setTitleVisible(false)
                            .setContentText("删除该视频？")
                            .setHeight(0.16f)
                            .setOnclickListener(new DialogInterface.OnLeftAndRightClickListener<MDAlertDialog>() {
                                @Override
                                public void clickLeftButton(MDAlertDialog dialog, View view) {
                                    dialog.dismiss();
                                }

                                @Override
                                public void clickRightButton(MDAlertDialog dialog, View view) {
                                    dialog.dismiss();
                                    delete(mList.get(pos - 1).getV_id());
                                }
                            })
                            .setCanceledOnTouchOutside(true)
                            .build().show();
                    return false;
                }
            });
        }
    }

    private OnAddItemClickListener itemClickListener;

    public interface OnAddItemClickListener {
        /**
         * 继续添加图片接口
         */
        void onItemVideoAddClick();

    }

    public void delete(int picid) {
        MineApiFactory.deleteVideo(SPManager.get().getStringValue("uid"), picid, 2).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse giftBean) throws Exception {
                if (giftBean.code == 200) {
                    EventBusUtil.sendEvent(new MessageEvent(C.MSG_DELETE_VIDEO));
                }
                ToastUtils.showShort(giftBean.msg);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                // Log.i(TAG, "accept: " + throwable);
            }
        });
    }
}
