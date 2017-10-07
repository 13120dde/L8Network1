package mahlabs.l8network1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class LoadBitmap extends Thread{
	private final String url;
	private BitmapListener listener;
	
	public LoadBitmap(BitmapListener listener, String url) {
		this.url = url;
		if(listener!=null) {
		    this.listener = listener;
		    start();
		}
	}

	@Override
	public void run() {
		Bitmap result = null;
		URL url;
		URLConnection connection;
		InputStream inputStream  = null;
		try {
			url = new URL(this.url);
			connection = url.openConnection();
			inputStream = connection.getInputStream();
			result = BitmapFactory.decodeStream(inputStream);
			listener.bitmapLoaded(result);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {

			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public interface BitmapListener {
		public void bitmapLoaded(Bitmap bitmap);
	}
}
