package com.wingnity.jsonparsingtutorial;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity {
	
	ArrayList<Actors> actorsList;
	Button refresh;
	ActorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		refresh = (Button) findViewById(R.id.Refresh);
		actorsList = new ArrayList<Actors>();
		new JSONAsyncTask().execute("https://dl.dropboxusercontent.com/u/746330/facts.json");		
		ListView listview = (ListView)findViewById(R.id.list);
		adapter = new ActorAdapter(getApplicationContext(), R.layout.row, actorsList);
		listview.setAdapter(adapter);
		//adapter.notifyDataSetChanged();
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
									long id) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), actorsList.get(position).getDescription(), Toast.LENGTH_LONG).show();

			}
		});
		   refresh.setOnClickListener(new Button.OnClickListener() {
			   public void onClick(View v) {
				   //for refresh the listview
				   adapter.notifyDataSetChanged();
			   }
		   });

	}

	class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {
		
		ProgressDialog dialog;
		// on start of application when connecting to server
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(MainActivity.this);
			dialog.setMessage("Loading, please wait");
			dialog.setTitle("Connecting server");
			dialog.show();
			dialog.setCancelable(false);
		}
		
		@Override
		protected Boolean doInBackground(String... urls) {
			try {
				
				//------------------>>
				HttpGet httppost = new HttpGet(urls[0]);
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response = httpclient.execute(httppost);

				int status = response.getStatusLine().getStatusCode();

				if (status == 200) {
					HttpEntity entity = response.getEntity();
					String data = EntityUtils.toString(entity);
					
				
					JSONObject jsono = new JSONObject(data);
					JSONArray jarray = jsono.getJSONArray("rows");
					
					for (int i = 0; i < jarray.length(); i++) {
						JSONObject object = jarray.getJSONObject(i);
					//SET THE ITEMS IN ROWS
						Actors actor = new Actors();
						actor.setTitle(object.getString("title"));
						/*String str;
						str=(object.getString("title").toString());
						if(str==null){
							actor.setTitle(object.getString(""));
						}*/
						actor.setDescription(object.getString("description"));
						actor.setImage(object.getString("imageHref"));						
						actorsList.add(actor);
					}
					return true;
				}
				
				//------------------>>
				
			} catch (ParseException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return false;
		}
		//if data not found
		protected void onPostExecute(Boolean result) {
			dialog.cancel();
			adapter.notifyDataSetChanged();
			if(result == false)
				Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

		}
	}
	
	

	
	
	
}
