package com.itfitness.fmtshopdetails.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.itfitness.fmtshopdetails.R;

/**
 * @ProjectName: o2o_user_android
 * @ClassName: ShopHdItem
 * @Description: java类作用描述 商家活动Item（未展开状态）
 * @Author: 作者名 itfitness
 * @CreateDate: 2020/5/7 14:32
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/5/7 14:32
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class ShopHdItem extends FrameLayout implements FlowLayout.ItemViewImpl {
    private TextView itemShophdTvName;

    public ShopHdItem(@NonNull Context context) {
        this(context,null);
    }

    public ShopHdItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShopHdItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.item_shophd,this);
        itemShophdTvName = (TextView) findViewById(R.id.item_shophd_tv_name);
    }

    @Override
    public void setSelect(boolean isSelect) {

    }

    @Override
    public void setItemText(String text) {
        if(!TextUtils.isEmpty(text)){
            text = text.replace("满", "");//未展开状态没有满字
            itemShophdTvName.setText(text.trim());
        }
    }
}
