package com.itfitness.fmtshopdetails.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itfitness.fmtshopdetails.R;
import com.itfitness.fmtshopdetails.widget.scrollablelayout.ScrollableHelper;

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
public class EvaluationFragment extends Fragment implements ScrollableHelper.ScrollableContainer {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_shop, null, false);
        TextView tvName = inflate.findViewById(R.id.tv_name);
        tvName.setText("评价");
        return inflate;
    }

    @Override
    public View getScrollableView() {
        return null;
    }
}
