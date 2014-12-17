package net.mobctrl.saveloadobjects;

import net.mobctrl.saveloadobjects.test.Car;
import net.mobctrl.saveloadobjects.utils.FileUtils;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author Zheng Haibo
 *
 * @Web http://www.mobctrl.net
 */
public class MainActivity extends Activity implements OnClickListener {

	TextView tvContent = null;
	TextView tvTimes = null;
	LinearLayout llPanel = null;
	Button btnSaveEncode = null;
	Button btnLoadDecode = null;
	Button btnLoad = null;
	Button btnSave = null;

	private Car car;

	private String rootDir = Environment.getExternalStorageDirectory()
			.getPath() + "/mobctrl/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initUi();
		initTestDatas();
	}

	private void initUi() {
		tvContent = (TextView) findViewById(R.id.tv_content);
		tvTimes = (TextView) findViewById(R.id.tv_times);
		llPanel = (LinearLayout) findViewById(R.id.ll_panel);
		btnSaveEncode = (Button) findViewById(R.id.btn_save_encode);
		btnLoadDecode = (Button) findViewById(R.id.btn_load_decode);
		btnLoad = (Button) findViewById(R.id.btn_load);
		btnSave = (Button) findViewById(R.id.btn_save);
		btnSaveEncode.setOnClickListener(this);
		btnLoadDecode.setOnClickListener(this);
		btnLoad.setOnClickListener(this);
		btnSave.setOnClickListener(this);
	}

	/**
	 * init the testDatas
	 */
	private void initTestDatas() {
		String[] wheels = new String[20];
		for (int i = 0; i < wheels.length; i++) {
			wheels[i] = "circle" + i;
		}
		String[] colors = new String[20];
		for (int i = 0; i < colors.length; i++) {
			colors[i] = "test" + i * i;
		}
		car = new Car(1023, "BWM", wheels, colors, 10000);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_save:
			saveBtnClick();
			break;
		case R.id.btn_load:
			loadBtnClick();
			break;
		case R.id.btn_save_encode:
			saveEncodeBtnClick();
			break;
		case R.id.btn_load_decode:
			loadDecodeBtnClick();
			break;
		default:
			break;
		}
	}

	/**
	 * 不加密,将对象保存到文件
	 */
	private void saveBtnClick() {
		long startTime = System.currentTimeMillis();
		boolean isSucc = FileUtils.getInstance().saveObject(car,
				rootDir + "car.json", false);
		long times = System.currentTimeMillis() - startTime;
		tvTimes.setText(isSucc + "," + times + "ms");
		System.out.println("debug:saveBtnClick times = " + times);
	}

	/**
	 * 不加密，将文件中的文本保存加载到内存
	 */
	private void loadBtnClick() {
		long startTime = System.currentTimeMillis();

		Car car = (Car) FileUtils.getInstance().loadObject(
				rootDir + "car.json", Car.class, false);

		long times = System.currentTimeMillis() - startTime;
		tvTimes.setText(times + "ms");

		System.out.println("debug:loadBtnClick times = " + times + ",car = "
				+ car.toString());
	}

	/**
	 * 加密保存
	 */
	private void saveEncodeBtnClick() {
		long startTime = System.currentTimeMillis();

		boolean isSucc = FileUtils.getInstance().saveObject(car,
				rootDir + "car_edc.json", true);

		long times = System.currentTimeMillis() - startTime;
		tvTimes.setText(isSucc + "," + times + "ms");
		System.out.println("debug:saveEncodeBtnClick times = " + times);
	}

	/**
	 * 解密加载: 注意，文件如果有损坏，将会解密失败
	 */
	private void loadDecodeBtnClick() {
		long startTime = System.currentTimeMillis();

		Car car = (Car) FileUtils.getInstance().loadObject(
				rootDir + "car_edc.json", Car.class, true);

		long times = System.currentTimeMillis() - startTime;
		tvTimes.setText(times + "ms");
		System.out.println("debug:loadDecodeBtnClick times = " + times
				+ "car = " + car.toString());
	}

}
