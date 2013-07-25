package reader.src;

import java.util.ArrayList;
import java.util.List;

import reader.src.td;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class tdcustomAdapter extends ArrayAdapter<td> {

	private ArrayList<td> entries;
	private Activity activity;

	public tdcustomAdapter(Activity a, int txtvrid,
			ArrayList<td> entries) {
		super(a, txtvrid, entries);
		// TODO Auto-generated constructor stub
		this.activity=a;
		this.entries=entries;
	}
	
	public static class ViewHolder{
		public TextView title;
		public TextView description;
	}
	
	@Override
	public View getView(int position,View convertView,ViewGroup parent){
		View v=convertView;
		ViewHolder vholder;
		if(v==null){
			LayoutInflater vi=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v=vi.inflate( R.layout.rowlayout, null);
			vholder=new ViewHolder();
			vholder.title=(TextView) v.findViewById(R.id.title);
			vholder.description=(TextView)v.findViewById(R.id.description);
			v.setTag(vholder);
		
		}
		else
			vholder=(ViewHolder)v.getTag();
		
		final td custom=entries.get(position);
		if(custom!=null){
			
			vholder.description.setTypeface(null,2);
			vholder.title.setText(custom.gettitle());
			vholder.description.setText(custom.getdetail());
		}
		return v;	
		}
	
	

}
