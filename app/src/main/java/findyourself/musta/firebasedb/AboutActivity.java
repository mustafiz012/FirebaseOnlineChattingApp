package findyourself.musta.firebasedb;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

	//ActionBar actionBar = getActionBar();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		/*actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);*/
		int versionCode = BuildConfig.VERSION_CODE;
		String versionName = BuildConfig.VERSION_NAME;
		final Button appVersion = findViewById(R.id.app_version);
		appVersion.setEnabled(false);
		/*try {
			PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			versionName = String.valueOf((float) packageInfo.versionCode);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}*/
		if (versionName != null)
			appVersion.setText("App Version: " + versionName);
	}
}
