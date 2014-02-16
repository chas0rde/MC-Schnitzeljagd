package de.hsb.kss.mc_schnitzeljagd.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hsb.kss.mc_schnitzeljagd.R;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Hint;
 
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class LazyAdapter extends BaseAdapter {
 
    private Activity activity;
    private List<Hint> listOfHints;
    private static LayoutInflater inflater=null;
    //public ImageLoader imageLoader; 
 
    public LazyAdapter(Activity a, List<Hint> hintList) {
        activity = a;
        listOfHints=hintList;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


	public int getCount() {
        return listOfHints.size();
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
        vi = inflater.inflate(R.layout.schnitzeljagd_listview, null);
 
        TextView title = (TextView)vi.findViewById(R.id.list_item_element); // title
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.hints_list_image_view_preview); // thumb image
 
        Hint hint = listOfHints.get(position);

        // Setting all values in listview
        if(hint.getHintType().equals("TEXT")) {
        	title.setText(hint.getHintType() + ": " +hint.getDescription());
        	thumb_image.setVisibility(View.GONE);
        } else {
        	byte[] imageAsBytes = Base64.decode(hint.getImage(), 0);
        	thumb_image.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        	title.setText(hint.getHintType());
        	thumb_image.setVisibility(View.VISIBLE);
        }
        return vi;
    }
    
    
    
}