package com.itfitness.fmtshopdetails.widget;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.itfitness.fmtshopdetails.R;

/**
 * @ProjectName: FMTShopDetails
 * @Package: com.itfitness.fmtshopdetails.widget
 * @ClassName: ShadowLayout
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/5/17 9:43
 * @UpdateUser: 更新者：itfitness
 * @UpdateDate: 2020/5/17 9:43
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class ShadowLayout extends RelativeLayout {
    private Paint mPaint;
    private float mShadowRadius;//控件圆角大小
    private int mShadowColor;//阴影颜色
    private float mShadowSize;//阴影大小
    private float mXOffect;//X轴偏移量
    private float mYOffect;//Y轴偏移量
    private int mBackGround;//控件背景颜色
    public ShadowLayout(Context context) {
        this(context,null);
    }

    public ShadowLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShadowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if(attrs!=null){
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ShadowLayout);
            if (typedArray != null) {
                mShadowRadius = typedArray.getDimension(R.styleable.ShadowLayout_radius,10);
                mShadowColor = typedArray.getColor(R.styleable.ShadowLayout_shadowColor, Color.BLACK);
                mShadowSize = typedArray.getDimension(R.styleable.ShadowLayout_shadowSize,10);
                mBackGround = typedArray.getColor(R.styleable.ShadowLayout_layoutBackground,Color.WHITE);
                mXOffect = typedArray.getDimension(R.styleable.ShadowLayout_xOffect,0F);
                mYOffect = typedArray.getDimension(R.styleable.ShadowLayout_yOffect,0F);
                typedArray.recycle();
            }
        }
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);//关闭硬件加速
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setShadowLayer(mShadowSize,mXOffect,mYOffect,mShadowColor);//设置画笔的阴影
        mPaint.setColor(mBackGround);//设置画笔的颜色
        mPaint.setStyle(Paint.Style.FILL);//设置画笔样式为填充
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.drawRoundRect(new RectF(mShadowSize-mXOffect,mShadowSize-mYOffect, getWidth()-mShadowSize-mXOffect,getHeight()-mShadowSize-mYOffect),mShadowRadius,mShadowRadius,mPaint);//在绘制子控件之前绘制阴影，否则会遮住子控件
        super.dispatchDraw(canvas);
    }
}

