package com.example.user4.sietzeberendspset4;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
        EditText editText;
        ListView listView;

        int completed;
        CheckBox checkBox;

        private TodoAdapter todoAdapter;
        private TodoDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = TodoDatabase.getInstance(getApplicationContext());
        todoAdapter = new TodoAdapter(getApplicationContext(), db.selectAll());

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(todoAdapter);


        OnItemClickListener clickListener = new OnItemClickListener();
        listView.setOnItemClickListener(clickListener);
        OnItemLongClickListener longClickListener = new OnItemLongClickListener();
        listView.setOnItemLongClickListener(longClickListener);
    }

    public void addItem(View v) {
        editText = (EditText) findViewById(R.id.editText);
        String title = editText.getText().toString();
        db = TodoDatabase.getInstance(getApplicationContext());
        db.insert(title, 0);
        editText.setText("");
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        updateData();
    }

    private void updateData() {
        todoAdapter.swapCursor(db.selectAll());
        todoAdapter.notifyDataSetChanged();
    }

    private class OnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            db = TodoDatabase.getInstance((getApplicationContext()));
            checkBox = (CheckBox) view.findViewById(R.id.checkBox);

            if(checkBox.isChecked()) {
                completed = 0;
                checkBox.setPaintFlags(checkBox.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
            }
            else {
                completed = 1;
                checkBox.setPaintFlags(checkBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            db.update(id, completed);
            updateData();
        }
    }

    private class OnItemLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            db = TodoDatabase.getInstance((getApplicationContext()));
            db.delete(id);
            updateData();
            return true;
        }
    }
}
