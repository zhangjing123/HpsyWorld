package com.kuwai.ysy.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kuwai.ysy.R;

/**
 * 自定义组件：购买数量，带减少增加按钮
 * Created by sunnysa on 2018/11/21.
 */
public class AmountRoundView extends LinearLayout implements View.OnClickListener, TextWatcher {

    private static final String TAG = "AmountView";
    private int amount = 0; //购买数量
    private int goods_storage = 10; //商品库存

    private OnAmountChangeListener mListener;

    private TextView etAmount;
    private ImageView btnDecrease;
    private ImageView btnIncrease;

    public AmountRoundView(Context context) {
        this(context, null);
    }

    public AmountRoundView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_amount_round, this);
        etAmount = (TextView) findViewById(R.id.etAmount);
        btnDecrease = (ImageView) findViewById(R.id.btnDecrease);
        btnIncrease = (ImageView) findViewById(R.id.btnIncrease);
        btnDecrease.setOnClickListener(this);
        btnIncrease.setOnClickListener(this);

        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.AmountView);
        int btnWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_btnWidth, LayoutParams.WRAP_CONTENT);
        int tvWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_tvWidth, 50);
        int tvTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_tvTextSize, 0);
        int btnTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_btnTextSize, 0);
        obtainStyledAttributes.recycle();

        if (tvTextSize != 0) {
            etAmount.setTextSize(tvTextSize);
        }
    }

    public void setOnAmountChangeListener(OnAmountChangeListener onAmountChangeListener) {
        this.mListener = onAmountChangeListener;
    }

    public void setGoods_storage(int goods_storage) {
        this.goods_storage = goods_storage;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnDecrease) {
            if (amount > 1) {
                amount--;
                etAmount.setText(amount + "");
            } else if (amount == 1) {
                amount--;
                btnDecrease.setVisibility(INVISIBLE);
                etAmount.setVisibility(INVISIBLE);
            }
        } else if (i == R.id.btnIncrease) {
            if (amount < goods_storage) {
                amount++;
                btnDecrease.setVisibility(VISIBLE);
                etAmount.setVisibility(VISIBLE);
                etAmount.setText(amount + "");
            }
        }


        if (mListener != null) {
            mListener.onAmountChange(this, amount);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.toString().isEmpty())
            return;
        amount = Integer.valueOf(s.toString());
        if (amount > goods_storage) {
            etAmount.setText(goods_storage + "");
            return;
        }

        if (mListener != null) {
            mListener.onAmountChange(this, amount);
        }
    }


    public interface OnAmountChangeListener {
        void onAmountChange(View view, int amount);
    }

}
