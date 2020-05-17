package com.itfitness.fmtshopdetails;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.itfitness.fmtshopdetails.adapter.MFragmentPagerAdapter;
import com.itfitness.fmtshopdetails.bean.ShopHDBean;
import com.itfitness.fmtshopdetails.fragment.EvaluationFragment;
import com.itfitness.fmtshopdetails.fragment.ShopGoodsFragment;
import com.itfitness.fmtshopdetails.fragment.ShopInfoFragment;
import com.itfitness.fmtshopdetails.widget.FlowLayout;
import com.itfitness.fmtshopdetails.widget.ShadowLayout;
import com.itfitness.fmtshopdetails.widget.ShopHdItem;
import com.itfitness.fmtshopdetails.widget.scrollablelayout.ScrollableHelper;
import com.itfitness.fmtshopdetails.widget.scrollablelayout.ScrollableLayout;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ScrollableLayout scrollableLayout;
    private ViewPager viewPager;
    private MagicIndicator magicIndicator;
    private ArrayList<Fragment> fragments;
    private ImageView imageViewBackWhite,imageViewBackBlack;
    private RelativeLayout relativeLayoutTitleBlack;//上滑显示的标题
    private View vTitleStatusbar;//标题状态栏占位符
    private FlowLayout flowLayoutCounpons;//优惠券未展开状态
    private LinearLayout linearLayoutCounpons,linearLayoutCounponsExt;
    private TextView textViewCounpons;//展开状态的优惠券信息
    private ShadowLayout sdlShopinfo;
    private FrameLayout flowLayoutPullBack;//点击收缩
    private View vExtmask;//展开后的背景
    private boolean isShopInfoExt = false;//是否是展开状态
    private int shopInfoRectDefaultHeight = 0;//展开收缩模块的默认高度
    private float shopInfoRectStartX,shopInfoRectStartY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BarUtils.setStatusBarAlpha(this,0);
        initView();
        initListener();
        initFragments();
        initIndefator();
        initCounpons();
    }

    /**
     * 加载优惠券数据
     */
    private void initCounpons() {
        //这里是测试数据
        ArrayList<ShopHDBean> counponsDatas = new ArrayList<>();
        counponsDatas.add(new ShopHDBean("满10减2"));
        counponsDatas.add(new ShopHDBean("满15减5"));
        counponsDatas.add(new ShopHDBean("满20减7"));
        counponsDatas.add(new ShopHDBean("满50减10"));
        textViewCounpons.setText("满10减2；满15减5；满20减7；满50减10；");
        flowLayoutCounpons.setDatas(counponsDatas, ShopHdItem.class);
        flowLayoutCounpons.setOnItemSelectListener(new FlowLayout.OnItemSelectListener() {
            @Override
            public void onItemSelect(int position, FlowLayout.ModuleImpl data) {
                //当未展开状态的优惠券点击的时候，展开面板
                extShopInfoBlock();
            }
        });
    }
    private void initListener() {
        scrollableLayout.setOnScrollListener(new ScrollableLayout.OnScrollListener() {
            @Override
            public void onScroll(int currentY, int maxY) {
//                ViewHelper.setTranslationY(imageHeader, (float) (currentY * 0.5));
                float alpha = (float) currentY / (float) maxY;
                alpha = Math.min(1,Math.max(0,alpha));
                BarUtils.setStatusBarLightMode(MainActivity.this,alpha>0.5f);//如果大于0.5，状态栏模式换为亮模式
                vTitleStatusbar.setAlpha(alpha);
                relativeLayoutTitleBlack.setAlpha(alpha);//滑动改变透明度
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                scrollableLayout.getHelper().setCurrentScrollableContainer((ScrollableHelper.ScrollableContainer) fragments.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //设置上滑可以收缩模块
        sdlShopinfo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        shopInfoRectStartX = event.getX();
                        shopInfoRectStartY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float moveX = event.getX() - shopInfoRectStartX;
                        float moveY = event.getY() - shopInfoRectStartY;
                        if(isShopInfoExt && Math.abs(moveY)>Math.abs(moveX) && moveY<-30){
                            extShopInfoBlock();
                        }
                        break;
                }
                return true;
            }
        });
        flowLayoutPullBack.setOnClickListener(this);
        imageViewBackWhite.setOnClickListener(this);
        imageViewBackBlack.setOnClickListener(this);
        linearLayoutCounpons.setOnClickListener(this);
    }
    /**
     * 展开或者收缩商家信息块
     */
    private void extShopInfoBlock() {
        if(shopInfoRectDefaultHeight==0){
            shopInfoRectDefaultHeight = sdlShopinfo.getHeight();
        }
        if(isShopInfoExt){
            startAnim(false,sdlShopinfo.getHeight(),shopInfoRectDefaultHeight);
        }else {
            int headHeight = (int) sdlShopinfo.getY();
            int screenHeight = ScreenUtils.getScreenHeight();
            int changeHeight = screenHeight - headHeight;
            scrollableLayout.setCanScroll(false);
            startAnim(true,sdlShopinfo.getHeight(),changeHeight);
        }
        isShopInfoExt = !isShopInfoExt;
    }
    /**
     * 展开收起动画
     */
    private void startAnim(final boolean isExt, final int startHeight, final int endHeight){
        ValueAnimator valueAnimator = ValueAnimator.ofInt(startHeight, endHeight);
        valueAnimator.setDuration(200);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = sdlShopinfo.getLayoutParams();
                layoutParams.height = animatedValue;
                sdlShopinfo.setLayoutParams(layoutParams);
                float currentVal = Math.abs(animatedValue-startHeight);
                float totalVal = Math.abs(endHeight - startHeight);
                float progress = currentVal/totalVal;
                if(isExt){
                    //展开
                    vExtmask.setAlpha(progress);
                    linearLayoutCounpons.setAlpha(1f - progress);
                    linearLayoutCounponsExt.setAlpha(progress);
                }else {
                    //收缩
                    vExtmask.setAlpha(1f - progress);
                    linearLayoutCounpons.setAlpha(progress);
                    linearLayoutCounponsExt.setAlpha(1f - progress);
                }
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                if(!isExt){
                    //收缩
                    flowLayoutPullBack.setVisibility(View.GONE);
                    linearLayoutCounpons.setVisibility(View.VISIBLE);
                }else {
                    //展开
                    linearLayoutCounponsExt.setVisibility(View.VISIBLE);//展开后的优惠和公告
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(isExt){
                    //展开
                    flowLayoutPullBack.setVisibility(View.VISIBLE);
                    linearLayoutCounpons.setVisibility(View.GONE);
                }else {
                    //收缩
                    linearLayoutCounponsExt.setVisibility(View.GONE);
                    scrollableLayout.setCanScroll(true);
                }
            }
        });
        valueAnimator.start();
    }
    private void initFragments() {
        fragments = new ArrayList<>();
        fragments.add(new ShopGoodsFragment());
        fragments.add(new EvaluationFragment());
        fragments.add(new ShopInfoFragment());
        viewPager.setAdapter(new MFragmentPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.setOffscreenPageLimit(fragments.size());
        scrollableLayout.getHelper().setCurrentScrollableContainer((ScrollableHelper.ScrollableContainer) fragments.get(0));
    }

    private void initView() {
        scrollableLayout = findViewById(R.id.scrollableLayout);
        viewPager = findViewById(R.id.vp);
        magicIndicator = findViewById(R.id.indicator);
        imageViewBackWhite = findViewById(R.id.title_img_back_white);
        imageViewBackBlack = findViewById(R.id.title_img_back_black);
        relativeLayoutTitleBlack = findViewById(R.id.title_rl_black);
        vTitleStatusbar = findViewById(R.id.title_statusbar);
        flowLayoutCounpons = findViewById(R.id.fl_coupons);
        linearLayoutCounpons = findViewById(R.id.ll_coupons);
        textViewCounpons = findViewById(R.id.tv_counpons_ext);
        linearLayoutCounponsExt = findViewById(R.id.ll_coupons_ext);
        sdlShopinfo = findViewById(R.id.sdl_shopinfo);
        vExtmask = findViewById(R.id.v_extmask);
        flowLayoutPullBack = findViewById(R.id.fl_pullback);
        vExtmask.setAlpha(0f);
        vTitleStatusbar.setAlpha(0f);//状态栏占位也设置为透明
        relativeLayoutTitleBlack.setAlpha(0f);//默认为透明
        linearLayoutCounponsExt.setAlpha(0f);//展开状态的优惠券初始设为透明
        linearLayoutCounponsExt.setVisibility(View.GONE);
        scrollableLayout.post(new Runnable() {

            @Override
            public void run() {
                ViewGroup.LayoutParams layoutParams = vTitleStatusbar.getLayoutParams();
                layoutParams.height = BarUtils.getStatusBarHeight();
                vTitleStatusbar.setLayoutParams(layoutParams);
                int height = relativeLayoutTitleBlack.getHeight()+BarUtils.getStatusBarHeight();
                scrollableLayout.setMaxYOffset(height);
            }
        });
    }
    /**
     * 加载指示器
     *
     */
    private void initIndefator() {
        final ArrayList<String> data = new ArrayList<>();
        data.add("点菜");
        data.add("评价");
        data.add("商家");
        magicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return data.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(Color.GRAY);
                simplePagerTitleView.setSelectedColor(Color.BLACK);
                simplePagerTitleView.setText(data.get(index));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                linePagerIndicator.setColors(getResources().getColor(R.color.ColorIndicator));
                linePagerIndicator.setLineHeight(5);
                return linePagerIndicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_img_back_white:
                onBackPressed();
                break;
            case R.id.title_img_back_black:
                onBackPressed();
                break;
            case R.id.ll_coupons:
                //未展开状态下，点击展开
                extShopInfoBlock();
                break;
            case R.id.fl_pullback:
                extShopInfoBlock();
                break;
        }
    }
}
