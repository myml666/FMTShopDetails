package com.itfitness.fmtshopdetails.bean;

import com.itfitness.fmtshopdetails.widget.FlowLayout;

/**
 * @ProjectName: FMTShopDetails
 * @Package: com.itfitness.fmtshopdetails.bean
 * @ClassName: ShopHDBean
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/5/17 16:12
 * @UpdateUser: 更新者：itfitness
 * @UpdateDate: 2020/5/17 16:12
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class ShopHDBean implements FlowLayout.ModuleImpl {
    private String name;

    public ShopHDBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getItemText() {
        return name;
    }
}
