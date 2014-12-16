package com.qiao.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qiao.callback.CallBack;
import com.qiao.viewpageranimation.MainActivity;
import com.qiao.viewpageranimation.R;

public class MenuFragment2 extends Fragment {
	private CallBack callBack;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		callBack = (CallBack) getActivity();
	}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.layout_menu2, null);
    	final TextView TextRight = (TextView) view.findViewById(R.id.TextRight);
    	TextRight.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callBack.SuccessText(TextRight.getText().toString());
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
