package com.wingnity.jsonparsingtutorial;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

public class ActorAdapter extends ArrayAdapter<Actors> {
	ArrayList<Actors> actorList;
	LayoutInflater vi;
	int Resource;
	ViewHolder holder;

	public ActorAdapter(Context context, int resource, ArrayList<Actors> objects) {
		super(context, resource, objects);
		vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Resource = resource;
		actorList = objects;
	}
 
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// convert view = design
		View v = convertView;
		if (v == null) {
			holder = new ViewHolder();
			v = vi.inflate(Resource, null);
			holder.imageview = (ImageView) v.findViewById(R.id.ivImage);
			holder.tvName = (TextView) v.findViewById(R.id.tvName);
			holder.tvTitle= (TextView) v.findViewById(R.id.tvTitle);
			//holder.tvDescription = (TextView) v.findViewById(R.id.tvDescription);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		holder.imageview.setImageResource(R.drawable.ic_launcher);
		new DownloadImageTask(holder.imageview).execute(actorList.get(position).getImage());
		//holder.tvName.setText(actorList.get(position).getName());
		holder.tvTitle.setText(actorList.get(position).getTitle()+":"+actorList.get(position).getDescription());
		return v;

	}

	static class ViewHolder {
		public ImageView imageview;
		public TextView tvName;
		public TextView tvTitle;
		//public TextView tvDescription;

	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;
//load the image
		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
		}

	}
}