package findyourself.musta.firebasedb;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

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
	private String username, roomName;
	private DatabaseReference root;
	private String temp_unique_key;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goppo);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		/*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});*/

		etSingleText = (EditText) findViewById(R.id.et_type_single_message);
		tvSingleText = (TextView) findViewById(R.id.tv_single_text);
		messageScroller = (ScrollView) findViewById(R.id.message_scroller);
		username = getIntent().getExtras().getString("username").toString();
		roomName = getIntent().getExtras().getString("room_name").toString();
		setTitle(getString(R.string.title_activity_goppo)+" - "+roomName);
		btnSend = (Button) findViewById(R.id.btn_send_single_message);
		etSingleText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(!etSingleText.getText().toString().isEmpty())
					btnSend.setEnabled(true);
				else
					btnSend.setEnabled(false);
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
				if(!username.isEmpty())
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
			public void onChildAdded(DataSnapshot dataSnapshot, String s) {
				updateChatConversation(dataSnapshot);
			}

			@Override
			public void onChildChanged(DataSnapshot dataSnapshot, String s) {
				updateChatConversation(dataSnapshot);
			}

			@Override
			public void onChildRemoved(DataSnapshot dataSnapshot) {

			}

			@Override
			public void onChildMoved(DataSnapshot dataSnapshot, String s) {

			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});
	}

	private String messageUpdate, usernameUpdate;
	private void updateChatConversation(DataSnapshot dataSnapshot) {
		Iterator iterator = dataSnapshot.getChildren().iterator();
		while (iterator.hasNext()){
			messageUpdate = (String) ((DataSnapshot)iterator.next()).getValue();
			usernameUpdate = (String) ((DataSnapshot)iterator.next()).getValue();
			tvSingleText.append(usernameUpdate+": "+messageUpdate+"\n");
		}
	}


}
