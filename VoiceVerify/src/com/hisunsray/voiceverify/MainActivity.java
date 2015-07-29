package com.hisunsray.voiceverify;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText mEditText;
	private Button mBtnYanzheng;
	private Button mButton;
	private MyPhoneStateListenner mListenner;
	private TelephonyManager _TelephonyManager;
	private InputMethodManager _InputMethodManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		showSoftInput();
	}
	
	private void showSoftInput() {
		// TODO Auto-generated method stub
		Timer _Timer = new Timer();
		_Timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				_InputMethodManager.showSoftInput(mEditText, 0);
			}
		}, 300);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
	
	
	

	private void initView() {
		// TODO Auto-generated method stub
			mButton = (Button)findViewById(R.id.yanzheng);
			mBtnYanzheng = (Button)findViewById(R.id.button1);
			mEditText = (EditText)findViewById(R.id.editText1);
			_InputMethodManager = (InputMethodManager)mEditText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			mButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stubs
					Intent _Intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:125904030106"));
					MainActivity.this.startActivity(_Intent);
					_TelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
					mListenner = new MyPhoneStateListenner();
					_TelephonyManager.listen(mListenner, PhoneStateListener.LISTEN_CALL_STATE);
				}
			});
			mBtnYanzheng.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(mEditText.getText().toString().equals("1234")){
						showDialog(1);
					}else{
						showDialog(2);
					}
				}
			});
			
	}
	
	private class MyPhoneStateListenner extends PhoneStateListener{
		private boolean bphonecalling = false;
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// TODO Auto-generated method stub
			super.onCallStateChanged(state, incomingNumber);
			
			if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                bphonecalling = true;
            } 
			if(state == TelephonyManager.CALL_STATE_IDLE && bphonecalling){
				if (_TelephonyManager != null) {
					_TelephonyManager.listen(mListenner,
                            PhoneStateListener.LISTEN_NONE);
                }
				
				final Intent _Intent = new Intent(MainActivity.this,MainActivity.class);
				_Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(_Intent);
				bphonecalling = false;
			}
			
		}
	}
	
	
	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		
		if(id==1){
			new AlertDialog.Builder(MainActivity.this).setIcon(R.drawable.ic_launcher)
					.setMessage("验证成功,退出 ?")
					.setCancelable(false)
					.setNegativeButton("No", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							arg0.cancel()  ;
						}
					})  
					.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							MainActivity.this.finish() ;
						}
					})
					.show()  ;
		}else{
			new AlertDialog.Builder(MainActivity.this).setIcon(R.drawable.ic_launcher)
			.setMessage("验证失败,退出 ?")
			.setCancelable(false)
			.setNegativeButton("No", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					arg0.cancel()  ;
				}
			})  
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					MainActivity.this.finish() ;
				}
			})
			.show()  ;
		}
		return super.onCreateDialog(id);
	}
	
}
