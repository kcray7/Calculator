package com.xstudio.calculator;

import java.util.regex.Pattern;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Calculator extends Activity implements OnClickListener{
	private Button btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,
	btn_clear,btn_mul,btn_add,btn_equal,btn_div,btn_dot,btn_sub;
	private EditText et_show;
	private StringBuffer str_show = new StringBuffer("");
	private double num1,num2;
	private boolean flag = true;
	private String str_oper = null;
	private String str_result = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}
	
	private void initView() {
		btn0 = (Button)findViewById(R.id.btn0);
		btn1 = (Button)findViewById(R.id.btn1);
		btn2 = (Button)findViewById(R.id.btn2);
		btn3 = (Button)findViewById(R.id.btn3);
		btn4 = (Button)findViewById(R.id.btn4);
		btn5 = (Button)findViewById(R.id.btn5);
		btn6 = (Button)findViewById(R.id.btn6);
		btn7 = (Button)findViewById(R.id.btn7);
		btn8 = (Button)findViewById(R.id.btn8);
		btn9 = (Button)findViewById(R.id.btn9);
		btn_clear = (Button)findViewById(R.id.btn_clear);
		btn_div = (Button)findViewById(R.id.btn_div);
		btn_add = (Button)findViewById(R.id.btn_add);
		btn_mul = (Button)findViewById(R.id.btn_mul);
		btn_equal = (Button)findViewById(R.id.btn_equal);
		btn_dot = (Button)findViewById(R.id.btn_dot);
		btn_sub = (Button)findViewById(R.id.btn_sub);
		et_show = (EditText)findViewById(R.id.et_show);
		btn0.setOnClickListener(this);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		btn6.setOnClickListener(this);
		btn7.setOnClickListener(this);
		btn8.setOnClickListener(this);
		btn9.setOnClickListener(this);
		btn_clear.setOnClickListener(this);
		btn_div.setOnClickListener(this);
		btn_add.setOnClickListener(this);
		btn_mul.setOnClickListener(this);
		btn_equal.setOnClickListener(this);
		btn_dot.setOnClickListener(this);
		btn_sub.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		Button btn = (Button)v;
		switch (v.getId()) {
		case R.id.btn_dot:
			if(str_show.toString() == ""){
				break;
			}else if(flag){
				str_show.append(".");
				et_show.setText(str_show.toString());
				flag=false;
			}
			break;
		case R.id.btn_clear:
			if(!(str_show.toString() == "")){
				if(!flag){
					String lastStr = String.valueOf(str_show.charAt(str_show.length()-1));
					if(lastStr.equals(".")){
						flag = true;
					}
				}
				str_show.deleteCharAt(str_show.length()-1);
				et_show.setText(str_show.toString());
			}else{
				et_show.setText("");
				str_result = null;
				str_show = new StringBuffer("");
				flag = true;
			}
			num1 = 0;
			break;
		case R.id.btn_add:
			getNum1(btn.getText().toString());
			break;
		case R.id.btn_sub:
			getNum1(btn.getText().toString());
			break;
		case R.id.btn_mul:
			getNum1(btn.getText().toString());
			break;
		case R.id.btn_div:
			getNum1(btn.getText().toString());
			break;
		case R.id.btn_equal:
			//点击等号时候就是计算的时候，那我需要知道num1,num2,str_oper，所有先判断是否存在。
			if(str_oper == null) break;
			if(str_show.toString() == "") break;
			calculate();
			if(str_oper!=null){
				str_oper = null;
			}
			break;
		default:
			str_show.append(btn.getText().toString());
			et_show.setText(str_show.toString());
			break;
		}
	}
	
	private void getNum1(String oper) {
		// TODO Auto-generated method stub
		if(str_oper != null){
			calculate();
		}
		str_oper = oper;
		if(!(str_show.toString() == "")){
			num1 = Double.parseDouble(str_show.toString());
			str_show = new StringBuffer("");
			et_show.setText(String.valueOf(num1));
		}else if(str_result != null){
			num1 = Double.parseDouble(str_result);
			str_result = null;			
			et_show.setText(String.valueOf(num1));
		}
	}
	
	//TODO 大数据的计算需要用到bignumber
	private void calculate() {
		if(str_show.toString().equals("")){
			return;
		}
		num2 = Double.parseDouble(str_show.toString());
		if(str_oper.equals("+")){
			str_result = String.valueOf(num1+num2);
		}
		if(str_oper.equals("-")){
			str_result = String.valueOf(num1-num2);
		}
		if(str_oper.equals("*")){
			str_result = String.valueOf(num1*num2);
		}
		if(str_oper.equals("/")){
			if(num2 != 0){
				str_result = String.valueOf(num1/num2);
			}else{
				Toast.makeText(Calculator.this, "除数不能为零！", Toast.LENGTH_LONG).show();
				str_show = new StringBuffer("");
				et_show.setText(str_show);
				return;
			}
		}
		et_show.setText(str_result);
		str_show = new StringBuffer("");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
}
