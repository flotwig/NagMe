package us.chary.nagme;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class NewTaskActivity extends AppCompatActivity {
    private SQLiteDatabase d;
    private LinearLayout root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        findViewById(R.id.save).setOnClickListener(saveClickListener);
        TaskDBOpenHelper t = new TaskDBOpenHelper(this);
        d = t.getWritableDatabase();
        root = (LinearLayout) findViewById(R.id.form);
     }

    View.OnClickListener saveClickListener = new View.OnClickListener() {
        public void onClick(View v){
            Log.d("save", "Saving.");
            ContentValues values = new ContentValues();
            values.put("description", ((EditText) root.findViewById(R.id.description)).getText().toString());
            values.put("due", ((EditText) root.findViewById(R.id.due)).getText().toString());
            values.put("difficulty", ((EditText) root.findViewById(R.id.difficulty)).getText().toString());
            if(values.getAsString("description").isEmpty() ||
                    values.getAsString("due").isEmpty() ||
                    values.getAsString("difficulty").isEmpty()) {
                Toast.makeText(getApplicationContext(),"Please fill out all fields.",Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                d.insertOrThrow("tasks",null,values);
            } catch (SQLException e) {
                Toast.makeText(getApplicationContext(),"Could not save to database!",Toast.LENGTH_SHORT).show();
                Log.d("save", "SQLException while saving. " + e.getMessage());
                return;
            }
            Intent i = new Intent(getApplicationContext(), TaskListActivity.class);
            startActivity(i);
        }
    };
}
