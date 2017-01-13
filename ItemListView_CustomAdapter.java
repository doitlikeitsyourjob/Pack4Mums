package com.doitlikeitsyourjob.pack4mums;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by ndRandall on 09/01/2017.
 */
public class ItemListView_CustomAdapter extends SimpleCursorAdapter {

    private Context mContext;
    private Context appContext;
    private int layout;
    private Cursor cr;
    private final LayoutInflater inflater;

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{

        public TextView itemcode;
        public TextView itemname;
        public TextView itembuylink;
        public CheckBox item_chkbx;
    }

    public ItemListView_CustomAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int i) {
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

        holder.itemcode = (TextView) vi.findViewById(R.id.code);
        holder.itemname = (TextView) vi.findViewById(R.id.txtitemname);
        holder.itembuylink = (TextView) vi.findViewById(R.id.txtitembuylink);
        holder.item_chkbx = (CheckBox) vi.findViewById(R.id.item_chkbx);

        vi.setTag( holder );

        return vi;

        //ViewHolder holder;
        //View convertView = mInflater.inflate(R.layout.custom, parent,false);
        //holder = new ViewHolder(convertView);
        //convertView.setTag(holder);
        //return convertView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);

        ViewHolder holder  =   (ViewHolder)    view.getTag();
        //holder.itemname.setText((Html.fromHtml("<h2>Sleeping Bag</h2><br><p>Description here</p>")));
        //holder.itemname.setAutoLinkMask(Linkify.WEB_URLS);

        //holder.itembuylink.setText((Html.fromHtml("https://www.amazon.co.uk/gp/search?ie=UTF8&camp=1634&creative=6738&index=baby&keywords=Sleeping%20Bag&linkCode=ur2&tag=doitlikeitsyo-21")));
        //holder.itembuylink.setAutoLinkMask(Linkify.WEB_URLS);

        holder.itembuylink.setMovementMethod(LinkMovementMethod.getInstance());
        //String text = "<a href=https://www.amazon.co.uk/gp/search?ie=UTF8&camp=1634&creative=6738&index=baby&keywords=Sleeping%20Bag&linkCode=ur2&tag=doitlikeitsyo-21>buy</a>";;
        String text = String.valueOf(holder.itembuylink.getText());
        holder.itembuylink.setText(Html.fromHtml(text));

        String check = (String) holder.item_chkbx.getText();
        if (check.equals("1")) {
            holder.item_chkbx.setChecked(true);
            //holder.itemname.setPaintFlags(holder.itemname.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else
        {
            holder.item_chkbx.setChecked(false);
            //holder.itemname.setPaintFlags(holder.itemname.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
        holder.item_chkbx.setText("");

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