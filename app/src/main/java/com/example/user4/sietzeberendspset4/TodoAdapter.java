package com.example.user4.sietzeberendspset4;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by User4 on 11/20/2017.
 */

public class TodoAdapter extends ResourceCursorAdapter {
    public TodoAdapter(Context context, Cursor cursor){
        super(context, R.layout.adapter, cursor);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // get the values that belong to a todoitem
        int columnIndexTitle = cursor.getColumnIndex("title");
        int columnIndexCompleted = (cursor.getColumnIndex("completed"));

        // load the values and get the checkbox
        String title = cursor.getString(columnIndexTitle);
        int completedInt = cursor.getInt(columnIndexCompleted);
        CheckBox checkBox = view.findViewById(R.id.checkBox);

        // check the checkbox and strike through the text
        if (completedInt == 1) {
            checkBox.setChecked(true);
            checkBox.setPaintFlags(checkBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        // or don't
        else {
            checkBox.setChecked(false);
            checkBox.setPaintFlags(checkBox.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }
        checkBox.setText(title);

        // alternate backgroundcolor
        if (cursor.getPosition() % 2 == 0) {
            checkBox.setBackgroundColor(Color.parseColor("#e6f7ff"));
        }
    }
}
