package findyourself.musta.firebasedb;

import android.app.ActionBar;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

	private String versionName = null;
	//ActionBar actionBar = getActionBar();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		/*actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);*/
		int versionCode = BuildConfig.VERSION_CODE;
		versionName = BuildConfig.VERSION_NAME;
		final Button appVersion = (Button) findViewById(R.id.app_version);
		appVersion.setEnabled(false);
		/*try {
			PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			versionName = String.valueOf((float) packageInfo.versionCode);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}*/
		if (versionName!=null)
			appVersion.setText("App Version: "+versionName);
	}
}
