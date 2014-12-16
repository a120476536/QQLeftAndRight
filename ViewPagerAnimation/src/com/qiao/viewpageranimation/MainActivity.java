package com.qiao.viewpageranimation;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.qiao.Fragment.FragmentFour;
import com.qiao.Fragment.FragmentOne;
import com.qiao.Fragment.FragmentThr;
import com.qiao.Fragment.FragmentTwo;
import com.qiao.Fragment.MenuFragment;
import com.qiao.Fragment.MenuFragment2;
import com.qiao.callback.CallBack;
import com.qiao.callback.CallBackBoolean;
/**
 * viewpager+fragment侧滑+导航条动画
 * @author 有点凉了
 * QQ群：123869487
 * 求基友共同进步，求大神入群指点
 * -----------------------------
 * 被注释掉的方法是页面滑动后的移动效果
 * 
 * 新增：定义flag属性当：原因是标题四个模块在某些情况下不需要使用为了方便操作直接定义两套布局。if true 四个模块  if false 三个模块。。。
 */
public class MainActivity extends SlidingFragmentActivity implements CallBack,CallBackBoolean,OnClickListener{
	private static final String TAG="MainActivity";
	private ImageView cusor;
	private int bmpW;// 动画图片宽度
	private int offset = 0;// 动画图片偏移量
	private List<Fragment> list = null;//
	private List<Fragment> listTwo = null;//
	
	private TextView textView_one,textView_two,textView_thr,textView_four;
	private Button button_main_left,button_main_right;
	
	private ViewPager mPager;
	private int currIndex = 0;// 当前页卡编号
	
    /**
     * ViewPager的当前选中页
     */
    private int currentIndex;
    /**
     * 屏幕的宽度
     */
    private int screenWidth;
    
    boolean flag = true;
    
    
    private CanvasTransformer mTransformer;  
    private CanvasTransformer mTransformer2;  
    private SlidingMenu sm = null;
    private LinearLayout layout_main_title;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getIntent().getExtras();
		if (bundle==null) {
			flag = true;
		}else {
			flag = bundle.getBoolean("flag");
		}
		if (flag) {
			setContentView(R.layout.activity_main);
		}else if (!flag) {
			setContentView(R.layout.activity_main2);
		}
		
		LeftAndRight();
		if (flag) {
			findById();
		}else if (!flag) {
			findById2();
		}
//		flag = false;
		if (flag) {
			init();
			if (listTwo!=null) {
				listTwo.clear();
			}
		}else if (!flag) {
			init2();
			if (list!=null) {
				list.clear();
			}
		}
		initTabLineWidth();
		
//		普通的动画移动
//		InitImageView();
//		InitTextView();
//		InitViewPager();
	}

	
	
	private void LeftAndRight() {
		setBehindContentView(R.layout.menu_frame);//加载左侧界面
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new MenuFragment()).commit();
		 //主界面动画
        mTransformer = new CanvasTransformer() {
			
			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
//				float scale = (float) (percentOpen*0.25 + 0.75); 
				float scale = (float) (1 - percentOpen * 0.25);
                canvas.scale(scale, scale, canvas.getWidth()/2, canvas.getHeight()/2);              
			}
		};
		//背景层动画
		mTransformer2 = new CanvasTransformer() {
			
			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				float scale = (float) (percentOpen * 0.25 + 0.75);
                canvas.scale(scale, scale, 0,
                        canvas.getHeight() / 2);           
			}
		};
		sm = getSlidingMenu();
        sm.setSecondaryMenu(R.layout.menu_frame2);//加载右侧界面
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame2, new MenuFragment2()).commit();
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        sm.setFadeEnabled(false);
        sm.setBehindScrollScale(0.25f);
        sm.setFadeDegree(0.75f);
        sm.setMode(SlidingMenu.LEFT_RIGHT);
//        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        sm.setBackgroundImage(R.drawable.img_frame_background);
        sm.setBehindWidth((int)(getWindowManager().getDefaultDisplay().getWidth() / 1.35));
        sm.setBehindCanvasTransformer(mTransformer2);
        sm.setAboveCanvasTransformer(mTransformer);
	}

	   /**
     * 重置颜色
     */
    private void resetTextView() {
    	textView_one.setTextColor(Color.BLACK);
    	textView_two.setTextColor(Color.BLACK);
    	textView_thr.setTextColor(Color.BLACK);
    	textView_four.setTextColor(Color.BLACK);
    }
    /**
     * 重置颜色
     */
    private void resetTextView2() {
    	textView_one.setTextColor(Color.BLACK);
    	textView_two.setTextColor(Color.BLACK);
    	textView_thr.setTextColor(Color.BLACK);
//    	textView_four.setTextColor(Color.BLACK);
    }
	private void initTabLineWidth() {
		   DisplayMetrics dpMetrics = new DisplayMetrics();
	        getWindow().getWindowManager().getDefaultDisplay()
	                .getMetrics(dpMetrics);
	        screenWidth = dpMetrics.widthPixels;
	        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) cusor
	                .getLayoutParams();
	        if (flag) {
	        	lp.width = screenWidth / 4;
			}
	        if (!flag) {
	        	lp.width = screenWidth / 3;
//	        	textView_thr.setVisibility(View.GONE);
			}
//	        Log.i(TAG, "==lp.width:="+lp.width);
	        cusor.setLayoutParams(lp);
	        
	}

	private void init() {
		list = new ArrayList<Fragment>();
		FragmentOne fragmentOne = new FragmentOne();
		list.add(fragmentOne);
		FragmentTwo fragmentTwo = new FragmentTwo();
		list.add(fragmentTwo);
		FragmentThr fragmentThr = new FragmentThr();
		list.add(fragmentThr);
		FragmentFour fragmentFour = new FragmentFour();
		list.add(fragmentFour);
//		Log.i(TAG, "==list.size():="+list.size());
		mPager.setAdapter(new MyAdapter(getSupportFragmentManager(),list));
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				resetTextView();
				switch (position) {
				case 0:
					textView_one.setTextColor(Color.BLUE);
					break;
				case 1:
					textView_two.setTextColor(Color.BLUE);
					break;
				case 2:
					textView_thr.setTextColor(Color.BLUE);
					break;
				case 3:
					textView_four.setTextColor(Color.BLUE);
					break;

				default:
					break;
				}
				currentIndex = position;
			}
			
			 /**
             * position :当前页面，及你点击滑动的页面 offset:当前页面偏移的百分比
             * offsetPixels:当前页面偏移的像素位置
             */
			@Override
			public void onPageScrolled(int position, float offset, int offsetPixels) {
				// TODO Auto-generated method stub
				 LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) cusor
	                        .getLayoutParams();
	 
//				 Log.i(TAG, "==offset:="+offset);
	                /**
	                 * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来
	                 * 设置mTabLineIv的左边距 滑动场景：
	                 * 记3个页面,
	                 * 从左到右分别为0,1,2 
	                 * 0->1; 1->2; 2->1; 1->0
	                 */
	 
				 
				 Log.i(TAG, "==-->currentIndex:="+currentIndex+"  position:="+position );
					 Log.i(TAG, "==走的flag");
					 if (currentIndex == 0 && position == 0)// 0->1
					 {
						 lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 4) + currentIndex
								 * (screenWidth / 4));
						 Log.i(TAG, "==走了0——>1：="+lp.leftMargin);
						 
					 } 
					 
					 if (currentIndex == 1 && position == 0) // 1->0
					 {
						 lp.leftMargin = (int) (-(1 - offset) * (screenWidth * 1.0 / 4) + currentIndex * (screenWidth / 4));
						 
						 Log.i(TAG, "==走了 1->0：="+lp.leftMargin);
						 
					 } 
					 
					 if (currentIndex == 1 && position == 1) // 1->2
					 {
						 lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 4) + currentIndex * (screenWidth / 4));
						 Log.i(TAG, "==走了1->2");
						 // Log.i(TAG, "==lp.leftMargin:=  1->2:="+lp.leftMargin);
					 } 
					 
					 if (currentIndex == 2 && position == 1) // 2->1
					 {
						 lp.leftMargin = (int) (-(1 - offset) * (screenWidth * 1.0 / 4) + currentIndex * (screenWidth / 4));
						 Log.i(TAG, "==走了2->1");
					 }
					 
					 if (currentIndex == 2 && position == 2) //2->3
					 {
						 lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 4) + currentIndex * (screenWidth / 4));
					 }
					 
					 if (currentIndex == 3 && position == 2) //3->2
					 {
						 lp.leftMargin = (int) (-(1 - offset) * (screenWidth * 1.0 / 4) + currentIndex * (screenWidth / 4));
					 }
	                cusor.setLayoutParams(lp);
			}
			 /**
             * state滑动中的状态 有三种状态（0，1，2） 1：正在滑动 2：滑动完毕 0：什么都没做。
             */
			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	private void init2() {
		listTwo = new ArrayList<Fragment>();
		FragmentOne fragmentOne = new FragmentOne();
		listTwo.add(fragmentOne);
		FragmentTwo fragmentTwo = new FragmentTwo();
		listTwo.add(fragmentTwo);
		FragmentThr fragmentThr = new FragmentThr();
		listTwo.add(fragmentThr);
//		Log.i(TAG, "==list.size():="+list.size());
		mPager.setAdapter(new MyAdapter(getSupportFragmentManager(),listTwo));
		mPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				resetTextView2();
				switch (position) {
				case 0:
					textView_one.setTextColor(Color.BLUE);
					break;
				case 1:
					textView_two.setTextColor(Color.BLUE);
					break;
				case 2:
					textView_thr.setTextColor(Color.BLUE);
					break;
				/*case 3:
					textView_four.setTextColor(Color.BLUE);
					break;*/
					
				default:
					break;
				}
				currentIndex = position;
			}
			
			/**
			 * position :当前页面，及你点击滑动的页面 offset:当前页面偏移的百分比
			 * offsetPixels:当前页面偏移的像素位置
			 */
			@Override
			public void onPageScrolled(int position, float offset, int offsetPixels) {
				// TODO Auto-generated method stub
				LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) cusor
						.getLayoutParams();
				
//				 Log.i(TAG, "==offset:="+offset);
				/**
				 * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来
				 * 设置mTabLineIv的左边距 滑动场景：
				 * 记3个页面,
				 * 从左到右分别为0,1,2 
				 * 0->1; 1->2; 2->1; 1->0
				 */
				
				
				Log.i(TAG, "==-->currentIndex:="+currentIndex+"  position:="+position );
					Log.i(TAG, "==走的 !flag");
					if (currentIndex == 0 && position == 0)// 0->1
					{
						lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex
								* (screenWidth / 3));
						Log.i(TAG, "==走了0——>1：="+lp.leftMargin);
						
					} 
					
					if (currentIndex == 1 && position == 0) // 1->0
					{
						lp.leftMargin = (int) (-(1 - offset) * (screenWidth * 1.0 / 3) + currentIndex * (screenWidth / 3));
						
						Log.i(TAG, "==走了 1->0：="+lp.leftMargin);
						
					} 
					
					if (currentIndex == 1 && position == 1) // 1->2
					{
						lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex * (screenWidth / 3));
						Log.i(TAG, "==走了1->2");
						// Log.i(TAG, "==lp.leftMargin:=  1->2:="+lp.leftMargin);
					} 
					
					if (currentIndex == 2 && position == 1) // 2->1
					{
						lp.leftMargin = (int) (-(1 - offset) * (screenWidth * 1.0 / 3) + currentIndex * (screenWidth / 3));
						Log.i(TAG, "==走了2->1");
					}
				cusor.setLayoutParams(lp);
			}
			/**
			 * state滑动中的状态 有三种状态（0，1，2） 1：正在滑动 2：滑动完毕 0：什么都没做。
			 */
			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	private void findById() {
		// TODO Auto-generated method stub
		mPager = (ViewPager) findViewById(R.id.viewPager);
		cusor = (ImageView) findViewById(R.id.cusor);
		textView_one = (TextView) findViewById(R.id.textView_one);
		textView_two = (TextView) findViewById(R.id.textView_two);
		textView_thr = (TextView) findViewById(R.id.textView_thr);
		textView_four = (TextView) findViewById(R.id.textView_four);
		button_main_left = (Button) findViewById(R.id.button_main_left);
		button_main_right = (Button) findViewById(R.id.button_main_right);
		
		layout_main_title = (LinearLayout) findViewById(R.id.layout_main_title);
		button_main_left.setOnClickListener(this);
		button_main_right.setOnClickListener(this);

	}
	private void findById2() {
		// TODO Auto-generated method stub
		mPager = (ViewPager) findViewById(R.id.viewPager);
		cusor = (ImageView) findViewById(R.id.cusor);
		textView_one = (TextView) findViewById(R.id.textView_one);
		textView_two = (TextView) findViewById(R.id.textView_two);
		textView_thr = (TextView) findViewById(R.id.textView_thr);
		button_main_left = (Button) findViewById(R.id.button_main_left);
		button_main_right = (Button) findViewById(R.id.button_main_right);
		
		layout_main_title = (LinearLayout) findViewById(R.id.layout_main_title);
		button_main_left.setOnClickListener(this);
		button_main_right.setOnClickListener(this);

	}

	

	
	
	class MyAdapter extends FragmentPagerAdapter {
		private List<Fragment> list = null;

		public MyAdapter(FragmentManager fm, List<Fragment> list) {
			super(fm);
			this.list = list;
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return list.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			super.destroyItem(container, position, object);
		}
	}

	public void setToggle(){
		toggle();
	}



	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_main_left:
			setToggle();
			break;
		case R.id.button_main_right:
			sm.showSecondaryMenu();
			break;

		default:
			break;
		}
	}



	@Override
	public void getBooleanLast(Boolean Text) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void SuccessText(String text) {
		// TODO Auto-generated method stub
		
	}


}
