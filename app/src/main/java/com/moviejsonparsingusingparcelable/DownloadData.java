package com.moviejsonparsingusingparcelable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class DownloadData extends AsyncTask<String, String, List<MovieModel>> {

	private IMovieListener listener;

	public DownloadData(IMovieListener listener){
		this.listener = listener;
	}

	@Override
	protected List<MovieModel> doInBackground(String... params) {
		URL url;
		try {
			url = new URL(params[0]);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			InputStream stream = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			String line = "";
			StringBuffer buffer = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			String finalData = buffer.toString();
			JSONObject jsonObject = new JSONObject(finalData);
			JSONArray jsonArray = jsonObject.getJSONArray("movies");
			List<MovieModel> movieModelList = new ArrayList<>();

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonFinalObject = jsonArray.getJSONObject(i);
				MovieModel movieModel = new MovieModel();
				movieModel.setMovie(jsonFinalObject.getString("movie"));
				movieModel.setYear(jsonFinalObject.getInt("year"));
				movieModel.setRating((float) jsonFinalObject.getDouble("rating"));
				movieModel.setDirector(jsonFinalObject.getString("director"));
				movieModel.setDuration(jsonFinalObject.getString("duration"));
				movieModel.setTagline(jsonFinalObject.getString("tagline"));
				movieModel.setInage(jsonFinalObject.getString("image"));
				movieModel.setStory(jsonFinalObject.getString("story"));
				List<MovieModel.Cast> castList = new ArrayList<>();
				/*for (int j = 0; j < jsonFinalObject.getJSONArray("cast").length(); j++) {
					JSONObject castObject = jsonFinalObject.getJSONArray("cast").getJSONObject(j);
					MovieModel.Cast cast = (Cast) new MovieModel().getCastList();
					cast.setName(castObject.getString("name"));
					castList.add(cast);
				}*/
//				movieModel.setCastList(castList);
				movieModelList.add(movieModel);
			}
			return movieModelList;
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(List<MovieModel> result) {
		if(!result.isEmpty()){
			listener.onMoviesListSuccess(result);
		}else{
			listener.onMoviesListSuccess("No Data Found.");
		}
	}
}
