package com.doitlikeitsyourjob.pack4mums;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by ndRandall on 09/01/2017.
 */
public class AddItemView_CustomAdapter extends SimpleCursorAdapter {

    private Context mContext;
    private Context appContext;
    private int layout;
    private Cursor cr;
    private final LayoutInflater inflater;

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{

        public TextView itemid;
        public TextView itemname;
        public Button itembuylink;
    }

    public AddItemView_CustomAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int i) {
        super(context,layout,c,from,to);
        this.layout=layout;
        this.mContext = context;
        this.inflater=LayoutInflater.from(context);
        this.cr=c;
    }

    @Override
    public View newView (Context context, Cursor cursor, ViewGroup parent) {

        View vi = inflater.inflate(layout, null);
        ViewHolder holder;
        holder = new ViewHolder();

        holder.itemid = (TextView) vi.findViewById(R.id.txtitemaddname);
        holder.itemname = (TextView) vi.findViewById(R.id.txtitemaddbuylink);
        holder.itembuylink = (Button) vi.findViewById(R.id.btnAddItem);

        vi.setTag( holder );

        return vi;

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);

        ViewHolder holder  =   (ViewHolder)    view.getTag();

        String btn = (String) holder.itembuylink.getText();

        if (btn.equals("0") ) {
            holder.itembuylink.setText("+ADD");
            holder.itembuylink.setEnabled(true);
            holder.itembuylink.setTextColor(Color.parseColor("#ff5c74"));
        }
            else
        {
            holder.itembuylink.setText("added");
            holder.itembuylink.setEnabled(false);
            holder.itembuylink.setTextColor(Color.GRAY);
        }

        //holder.itembuylink.setMovemen
        //(Html.fromHtml(text));

        //String check = (String) holder.item_chkbx.getText();
        //if (check.equals("1")) {
        //    holder.item_chkbx.setChecked(true);
        //    //holder.itemname.setPaintFlags(holder.itemname.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        //}
        //else
        // {
        //    holder.item_chkbx.setChecked(false);
        //   //holder.itemname.setPaintFlags(holder.itemname.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        //}
        //holder.item_chkbx.setText("");

        //CheckBox cb = (CheckBox) view.findViewById(R.id.item_chkbx);
        //TextView titleS=(TextView)view.findViewById(R.id.TitleSong);
        //TextView artistS=(TextView)view.findViewById(R.id.Artist);
        //int Title_index=cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
        //int Artist_index=cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);

        //titleS.setText(cursor.getString(Title_index));
        //artistS.setText(cursor.getString(Artist_index));

        //if(cursor.getString(cursor.getColumnIndex("checkbox")).compareTo("1")==0)
        //{
        //    cb.setChecked(true);
        // }
        //else
        //{
        //    cb.setChecked(false);
        //}

    }



}