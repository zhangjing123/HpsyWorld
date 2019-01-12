package com.kuwai.ysy.module.find.business.FoundLocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.AddressChooseActivity;
import com.kuwai.ysy.module.circle.business.publishdy.PublishDyActivity;
import com.kuwai.ysy.module.find.adapter.LocalCityAdapter;
import com.kuwai.ysy.module.find.adapter.ProvinceAdapter;
import com.kuwai.ysy.module.find.api.FoundApiFactory;
import com.kuwai.ysy.module.find.bean.FoundHome.LocalNextBean;
import com.kuwai.ysy.module.find.bean.ProvincesAndCityBean;
import com.kuwai.ysy.utils.ListDataSave;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyEditText;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;

import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.Province;
import io.reactivex.functions.Consumer;

/**
 * Created by sunnysa on 2018/11/23.
 */

public class FoundLocationFragment extends BaseActivity<FoundLocationPresenter> implements FoundLocationContract.IHomeView {

    private ImageView mImg;
    private MyEditText mEtSearch;
    private RecyclerView mRvProvince;
    private RecyclerView mRvCity;
    private RecyclerView mRvArea;
    private FrameLayout mFrameCity;
    private ProvinceAdapter provinceAdapter;
    private LocalCityAdapter cityAdapter;
    private LocalCityAdapter areaAdapter;
    private NavigationLayout navigationLayout;

    private ProvincesAndCityBean mProvincesAndCityBean;
    private LocalNextBean mLocalNextBean, mAeraBean;
    final ArrayList<ProvincesAndCityBean> data = new ArrayList<>();
    private String cityId, cityName;

    @Override
    protected FoundLocationPresenter getPresenter() {
        return new FoundLocationPresenter(this);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_found_local_city;
    }

    @Override
    public void showViewLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showViewError(Throwable t) {

    }

    @Override
    public void setHomeData(ProvincesAndCityBean provincesAndCityBean) {
        data.clear();
        data.add(provincesAndCityBean);
        dataSave.setDataList("cityList", data);
        mProvincesAndCityBean = provincesAndCityBean;
        provinceAdapter.replaceData(provincesAndCityBean.getData());
    }

    @Override
    public void setCityData(LocalNextBean localNextBean) {
        mLocalNextBean = localNextBean;
        cityAdapter.replaceData(localNextBean.getData());
    }

    @Override
    public void setAreaData(LocalNextBean localNextBean) {
        mAeraBean = localNextBean;
        areaAdapter.replaceData(mAeraBean.getData());
    }

    @Override
    public void showError(int errorCode, String msg) {

    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        mImg = findViewById(R.id.img);
        mEtSearch = findViewById(R.id.et_search);
        //Utils.showOrHide(this,mEtSearch);
        mRvProvince = findViewById(R.id.rv_province);
        navigationLayout = findViewById(R.id.navigation);
        navigationLayout.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRvCity = findViewById(R.id.rv_city);
        mRvArea = findViewById(R.id.rv_area);
        mFrameCity = findViewById(R.id.frame_city);

        mRvProvince.setLayoutManager(new LinearLayoutManager(this));
        mRvCity.setLayoutManager(new LinearLayoutManager(this));
        mRvArea.setLayoutManager(new LinearLayoutManager(this));

        provinceAdapter = new ProvinceAdapter(R.layout.item_city_text);
        cityAdapter = new LocalCityAdapter(R.layout.item_city_text);
        areaAdapter = new LocalCityAdapter(R.layout.item_city_text);

        mRvProvince.setAdapter(provinceAdapter);
        mRvCity.setAdapter(cityAdapter);
        mRvArea.setAdapter(areaAdapter);
        areaAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SPManager.get().putString("cityName", cityName);
                SPManager.get().putString("cityId", cityId);
                Intent aintent = new Intent(FoundLocationFragment.this, PublishDyActivity.class);
                aintent.putExtra("city", cityName);
                setResult(RESULT_OK, aintent);
                finish();
            }
        });

        provinceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                for (ProvincesAndCityBean.DataBean dataBean : mProvincesAndCityBean.getData()) {
                    dataBean.isChecked = false;
                }
                mProvincesAndCityBean.getData().get(position).isChecked = true;

                mPresenter.requestNextData(mProvincesAndCityBean.getData().get(position).getRegion_id());

                if (mAeraBean != null) {
                    areaAdapter.getData().clear();
                    areaAdapter.notifyDataSetChanged();
                }

                provinceAdapter.notifyDataSetChanged();
            }
        });

        cityAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                for (LocalNextBean.DataBean dataBean : mLocalNextBean.getData()) {
                    dataBean.ischecked = false;
                }
                mLocalNextBean.getData().get(position).ischecked = true;
                cityId = String.valueOf(mLocalNextBean.getData().get(position).getRegion_id());
                cityName = mLocalNextBean.getData().get(position).getRegion_name();
                mPresenter.requestAreaData(mLocalNextBean.getData().get(position).getRegion_id());
                cityAdapter.notifyDataSetChanged();
            }
        });
        getData();
    }

    ListDataSave dataSave;

    private void getData() {
        dataSave = new ListDataSave(this, "cityList");
        if (dataSave.getDataList("cityList", ProvincesAndCityBean.class).size() > 0) {
            mProvincesAndCityBean = dataSave.getDataList("cityList", ProvincesAndCityBean.class).get(0);
            provinceAdapter.replaceData(mProvincesAndCityBean.getData());
        } else {
            mPresenter.requestHomeData();
        }
    }
}
