package com.xstudio.calculator;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
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
	private boolean flag_dot = true;
	private boolean flag_num1 = false;
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
			}else if(flag_dot){
				str_show.append(".");
				et_show.setText(str_show.toString());
				flag_dot=false;
			}
			break;
		case R.id.btn_clear:
			if(!(str_show.toString() == "")){
				if(!flag_dot){
					String lastStr = String.valueOf(str_show.charAt(str_show.length()-1));
					if(lastStr.equals(".")){
						flag_dot = true;
					}
				}
				str_show.deleteCharAt(str_show.length()-1);
				et_show.setText(str_show.toString());
			}else{
				et_show.setText("");
				str_result = null;
				str_show = new StringBuffer("");
				flag_dot = true;
			}
			flag_num1 = false;
			break;
		case R.id.btn_add:
			setNum1(btn.getText().toString());
			break;
		case R.id.btn_sub:
			setNum1(btn.getText().toString());
			break;
		case R.id.btn_mul:
			setNum1(btn.getText().toString());
			break;
		case R.id.btn_div:
			setNum1(btn.getText().toString());
			break;
		case R.id.btn_equal:
			if(str_oper == null) break;
			if(str_show.toString().equals("")) break;
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
	
	private void setNum1(String oper) {
		if(str_oper != null && !str_show.toString().equals("") && flag_num1){
			calculate();
		}
		str_oper = oper;
		if(!(str_show.toString() == "")){
			num1 = Double.parseDouble(str_show.toString());
			et_show.setText(str_show.toString());
			str_show = new StringBuffer("");
			flag_num1 = true;
		}else if(str_result != null){
			num1 = Double.parseDouble(str_result);
			et_show.setText(str_result);
			str_result = null;			
			flag_num1 = true;
		}
	}
	
	//TODO 大数据的计算需要用到bignumber
	//TODO 支持负数运算
	private void calculate() {
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
				str_oper = null;
				flag_num1 = false;
				flag_dot = true;
				return;
			}
		}
		//保留4位小数
		String[] resultArray = str_result.split("\\.");
		String decimals = resultArray[1];
		if(decimals.length() > 4){
			str_result = resultArray[0] + "." + decimals.substring(0,4);
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
