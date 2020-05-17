package com.itfitness.fmtshopdetails.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.itfitness.fmtshopdetails.R;
import com.itfitness.fmtshopdetails.widget.scrollablelayout.ScrollableHelper;

import java.util.ArrayList;

/**
 * @ProjectName: FMTShopDetails
 * @Package: com.itfitness.fmtshopdetails.fragment
 * @ClassName: ShopGoodsFragment
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/5/17 10:13
 * @UpdateUser: 更新者：itfitness
 * @UpdateDate: 2020/5/17 10:13
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class ShopGoodsFragment extends Fragment implements ScrollableHelper.ScrollableContainer{
    private RecyclerView recyclerView;
    private BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_shopgoods, null, false);
        recyclerView = inflate.findViewById(R.id.rv_shopgoods);
        initDatas();
        return inflate;
    }

    private void initDatas() {
        ArrayList<String> testDatas = new ArrayList<>();
        for(int i = 0 ; i < 20 ; i++){
            testDatas.add("");
        }
        if(baseQuickAdapter == null){
            baseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_shopgoods,testDatas) {
                @Override
                protected void convert(BaseViewHolder helper, String item) {

                }
            };
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(baseQuickAdapter);
        }else {
            baseQuickAdapter.setNewData(testDatas);
            baseQuickAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public View getScrollableView() {
        return recyclerView;
    }
}
