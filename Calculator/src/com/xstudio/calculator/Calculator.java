package com.xstudio.calculator;

import android.os.Bundle;
import android.app.Activity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * <p>Title: Calculator.java<／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2014<／p>
 * @author Kevin Xu
 * @date Jan 7, 2014
 * @version 1.6
 */
public class Calculator extends Activity implements OnClickListener {
	private EditText et_show;
	private StringBuffer str_show = new StringBuffer("");
	private double num1, num2;
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

	// TODO editText显示很长一段数字时应该是先入先出的规则显示。
	private void initView() {
		findViewById(R.id.btn0).setOnClickListener(this);
		findViewById(R.id.btn1).setOnClickListener(this);
		findViewById(R.id.btn2).setOnClickListener(this);
		findViewById(R.id.btn3).setOnClickListener(this);
		findViewById(R.id.btn4).setOnClickListener(this);
		findViewById(R.id.btn5).setOnClickListener(this);
		findViewById(R.id.btn6).setOnClickListener(this);
		findViewById(R.id.btn7).setOnClickListener(this);
		findViewById(R.id.btn8).setOnClickListener(this);
		findViewById(R.id.btn9).setOnClickListener(this);
		findViewById(R.id.btn_clear).setOnClickListener(this);
		findViewById(R.id.btn_div).setOnClickListener(this);
		findViewById(R.id.btn_add).setOnClickListener(this);
		findViewById(R.id.btn_mul).setOnClickListener(this);
		findViewById(R.id.btn_equal).setOnClickListener(this);
		findViewById(R.id.btn_dot).setOnClickListener(this);
		findViewById(R.id.btn_sub).setOnClickListener(this);
		et_show = (EditText) findViewById(R.id.et_show);
	}

	@Override
	public void onClick(View v) {
		Button btn = (Button) v;
		switch (v.getId()) {
		case R.id.btn_dot:
			if (str_show.toString() == "") {
				break;
			} else if (flag_dot) {
				str_show.append(".");
				showInEditText(str_show.toString());
				flag_dot = false;
			}
			break;
		case R.id.btn_clear:
			if (!(str_show.toString() == "")) {
				if (!flag_dot) {
					String lastStr = String.valueOf(str_show.charAt(str_show
							.length() - 1));
					if (lastStr.equals(".")) {
						flag_dot = true;
					}
				}
				str_show.deleteCharAt(str_show.length() - 1);
				showInEditText(str_show.toString());
			} else {
				showInEditText("");
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
			if (str_oper == null)
				break;
			if (str_show.toString().equals(""))
				break;
			calculate();
			break;
		default:
			//当第一个数字是0的时候，后面输入0则重置为一个0，后面输入为数字的时候清除前面的0.
			if(et_show.getText().toString().equals("0")){
				if(btn.getText().toString().equals("0")){
					break;
				}else{
					str_show.deleteCharAt(0);
				}
			}
			str_show.append(btn.getText().toString());
			showInEditText(str_show.toString());
			break;
		}
	}

	private void setNum1(String oper) {
		if (str_oper != null && !str_show.toString().equals("") && flag_num1) {
			calculate();
		}
		str_oper = oper;
		if (!(str_show.toString() == "")) {
			num1 = Double.parseDouble(str_show.toString());
			showInEditText(str_show.toString());
			str_show = new StringBuffer("");
			flag_num1 = true;
		} else if (str_result != null) {
			num1 = Double.parseDouble(str_result);
			showInEditText(str_result);
			str_result = null;
			flag_num1 = true;
		}
	}

	// TODO 大数据的计算需要用到bigDecimal
	// TODO 支持负数运算
	// TODO issue:当一个结果是很大的数据时，保留小数后显示错误。
	// TODO 实现四舍五入
	private void calculate() {
		num2 = Double.parseDouble(str_show.toString());
		if (str_oper.equals("+")) {
			str_result = String.valueOf(num1 + num2);
		}
		if (str_oper.equals("-")) {
			str_result = String.valueOf(num1 - num2);
		}
		if (str_oper.equals("*")) {
			str_result = String.valueOf(num1 * num2);
		}
		if (str_oper.equals("/")) {
			if (num2 != 0) {
				str_result = String.valueOf(num1 / num2);
			} else {
				Toast.makeText(Calculator.this, "除数不能为零！", Toast.LENGTH_LONG)
						.show();
				str_show = new StringBuffer("");
				showInEditText(str_show.toString());
				str_oper = null;
				flag_num1 = false;
				flag_dot = true;
				return;
			}
		}
		// 保留4位小数，当小数只有0时去掉这个0.
		String[] resultArray = str_result.split("\\.");
		String decimals = resultArray[1];
		if (decimals.equals("0")) {
			str_result = resultArray[0];
		}
		if (decimals.length() > 4) {
			str_result = resultArray[0] + "." + decimals.substring(0, 4);
		}
		showInEditText(str_result);
		str_show = new StringBuffer("");
		flag_dot = true;
		str_oper = null;
	}

	private void showInEditText(String str) {
		et_show.setText(str);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
