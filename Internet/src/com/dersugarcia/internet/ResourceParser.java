package com.dersugarcia.internet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class ResourceParser {

	public static JSONObject getJSONObject(String path) {

		try {
			URL url = new URL(path);
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream in = httpConnection.getInputStream();
				return inputStream2JSON(in);

			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static JSONObject inputStream2JSON(InputStream in)
			throws JSONException {
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(
				in));
		StringBuilder responseStrBuilder = new StringBuilder();

		String inputStr;
		try {
			while ((inputStr = streamReader.readLine()) != null) {
				responseStrBuilder.append(inputStr);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (new JSONObject(responseStrBuilder.toString()));
	}
}
