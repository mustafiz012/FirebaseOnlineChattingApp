package findyourself.musta.firebasedb;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GoppoActivity extends AppCompatActivity {

	private Button btnSend = null;
	private EditText etSingleText = null;
	private TextView tvSingleText = null;
	private ScrollView messageScroller = null;
	private String username;
	private DatabaseReference root;
	private String temp_unique_key;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goppo);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		/*actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);*/
		/*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});*/

		etSingleText = findViewById(R.id.et_type_single_message);
		tvSingleText = findViewById(R.id.tv_single_text);
		messageScroller = findViewById(R.id.message_scroller);
		username = getIntent().getExtras().getString("username");
		String roomName = getIntent().getExtras().getString("room_name");
		setTitle(getString(R.string.title_activity_goppo) + " - " + roomName);
		btnSend = findViewById(R.id.btn_send_single_message);
		etSingleText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				btnSend.setEnabled(!etSingleText.getText().toString().isEmpty());
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		root = FirebaseDatabase.getInstance().getReference().child(roomName);
		btnSend.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Map<String, Object> map = new HashMap<String, Object>();
				temp_unique_key = root.push().getKey();
				root.updateChildren(map);
				DatabaseReference message_root = root.child(temp_unique_key);
				Map<String, Object> messagingInfoMapping = new HashMap<String, Object>();
				if (!username.isEmpty())
					messagingInfoMapping.put("name", username);
				else
					messagingInfoMapping.put("name", "Unknown");
				messagingInfoMapping.put("message", etSingleText.getText().toString());
				message_root.updateChildren(messagingInfoMapping);
				etSingleText.setText("");
				messageScroller.fullScroll(View.FOCUS_DOWN);
			}
		});
		root.addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
				updateChatConversation(dataSnapshot);
			}

			@Override
			public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
				updateChatConversation(dataSnapshot);
			}

			@Override
			public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

			}

			@Override
			public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {

			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {

			}
		});
	}

	private void updateChatConversation(DataSnapshot dataSnapshot) {
		Iterator iterator = dataSnapshot.getChildren().iterator();
		while (iterator.hasNext()) {
			String messageUpdate = (String) ((DataSnapshot) iterator.next()).getValue();
			String usernameUpdate = (String) ((DataSnapshot) iterator.next()).getValue();
			tvSingleText.append(usernameUpdate + ": " + messageUpdate + "\n");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_about) {
			startActivity(new Intent(getApplicationContext(), AboutActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
