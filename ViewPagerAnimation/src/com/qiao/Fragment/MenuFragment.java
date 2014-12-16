package com.qiao.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qiao.callback.CallBack;
import com.qiao.callback.CallBackBoolean;
import com.qiao.viewpageranimation.JumpActivity;
import com.qiao.viewpageranimation.JumpActivity2;
import com.qiao.viewpageranimation.MainActivity;
import com.qiao.viewpageranimation.R;

public class MenuFragment extends Fragment{
	private static final String TAG="MenuFragment";
	private CallBack callBack;
	private CallBackBoolean callBackBoolean;
	private RelativeLayout layout_one;
	
/*	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		callBack = (CallBack) getActivity();
	}*/
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		callBack = (CallBack) getActivity();
		callBackBoolean = (CallBackBoolean) getActivity();

	}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.layout_menu, null);
    	final TextView TextOne = (TextView) view.findViewById(R.id.TextOne);
    	final TextView TextTwo = (TextView) view.findViewById(R.id.TextTwo);
    	final TextView TextThr = (TextView) view.findViewById(R.id.TextThr);
    	final TextView TextFour = (TextView) view.findViewById(R.id.TextFour);
    	final TextView TextFive = (TextView) view.findViewById(R.id.TextFive);
    	TextOne.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TextOne.getText();
				callBack.SuccessText(TextOne.getText().toString());
//				callBackBoolean.getBooleanLast(false);
				Log.i(TAG, "==-->callBack.SuccessText(TextOne.getText().toString())执行了");
//				close();
				Intent intent = new Intent(getActivity(), JumpActivity.class);
				startActivity(intent);
				getActivity().finish();
			}
		});
    	TextTwo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callBack.SuccessText(TextTwo.getText().toString());
//				callBackBoolean.getBooleanLast(true);
				Intent intent = new Intent(getActivity(), JumpActivity2.class);
				startActivity(intent);
				getActivity().finish();
				close();
			}

			
		});
    	TextThr.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callBack.SuccessText(TextThr.getText().toString());
				close();
			}
		});
    	TextFour.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callBack.SuccessText(TextFour.getText().toString());
				close();
			}
		});
    	TextFive.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callBack.SuccessText(TextFive.getText().toString());
				close();
			}
		});
        return view;
    }
    private void close() {
		if (getActivity() instanceof MainActivity) {
			MainActivity activity  = (MainActivity) getActivity(); 
			activity.setToggle();
		}
	}
}
