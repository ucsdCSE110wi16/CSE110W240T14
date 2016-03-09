package edu.etduongucsd.dopeshit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by Collin on 3/6/16.
 */
public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> picturesToConvert;

    public GridViewAdapter(Context context, ArrayList<String> picturesToConvert) {
        this.context = context;
        this.picturesToConvert = picturesToConvert;
    }

    @Override
    public int getCount() {
        return picturesToConvert.size();
    }

    @Override
    public Object getItem(int pos) {
        return picturesToConvert.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View view, ViewGroup parentGroup) {
        View gridView;
        LayoutInflater inf =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            gridView = inf.inflate(R.layout.grid_item_layout, null);
            ImageView iView = (ImageView) gridView.findViewById(R.id.gridImage);
            Bitmap bmap = s2bmap(picturesToConvert.get(pos));
            iView.setImageBitmap(bmap);
        }
        else {
            gridView = (View) view;
        }
        return gridView;
    }

    private Bitmap s2bmap(String unconverted) {
        Bitmap bmap = null;
        try {
            bmap = BitmapFactory.decodeStream(context.openFileInput(unconverted));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bmap;
    }
}
