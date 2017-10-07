package mahlabs.l8network1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;

public class LoadText implements Runnable{
	private TextListener listener;
	private String url, encoding;
	
	public LoadText(TextListener listener, String url, String encoding) {
		this.url=url;
		this.encoding=encoding;
		if(listener!=null) {
			this.listener = listener;
			Thread t = new Thread(this);
			t.start();
		}
	}

	@Override
	public void run() {
		StringBuilder result = new StringBuilder();
		BufferedReader reader = null;
		try {
			URL url = new URL(this.url);
			URLConnection connection = url.openConnection();
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),encoding));
			String txt;
			while ((txt = reader.readLine())!=null){
				result.append(txt+"\n");
			}
			listener.textLoaded(result.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	@Override
	protected String doInBackground(String... params) {
		StringBuilder result = new StringBuilder();
		BufferedReader reader = null;
		try {
			URL url = new URL(params[0]);
			URLConnection connection = url.openConnection();
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), params[1]));
			String txt;
			while((txt=reader.readLine())!=null)
				result.append(txt+"\n");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch(Exception e) {}
		}
		return result.toString();
	}

	@Override
	protected void onPostExecute(String result) {
		listener.textLoaded(result);
	}
*/
	public interface TextListener {
		public void textLoaded(String str);
	}
}
