package com.itfitness.fmtshopdetails.widget.roundedimage;
import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * @ProjectName: FMTShopDetails
 * @Package: com.itfitness.fmtshopdetails
 * @ClassName: Corner
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/5/17 10:03
 * @UpdateUser: 更新者：itfitness
 * @UpdateDate: 2020/5/17 10:03
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({
        Corner.TOP_LEFT, Corner.TOP_RIGHT,
        Corner.BOTTOM_LEFT, Corner.BOTTOM_RIGHT
})
public @interface Corner {
    int TOP_LEFT = 0;
    int TOP_RIGHT = 1;
    int BOTTOM_RIGHT = 2;
    int BOTTOM_LEFT = 3;
}
