package mahlabs.l8network1;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private ImageView ivMemory;
	private TextView tvText;
	private JsonTable jsonTable;
	private XmlTable xmlTable;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ivMemory = (ImageView)findViewById(R.id.imageView1);
		tvText = (TextView)findViewById(R.id.textView1);
		jsonTable = (JsonTable)getFragmentManager().findFragmentById(R.id.table_fragment);
		xmlTable = (XmlTable)getFragmentManager().findFragmentById(R.id.xml_table);
		if(NetworkStatus.isOnline(this)) {
//            json("utgifter");
//            json("inkomster");
//			jsonWithReader("Reader");
			xml();
			textAndImage();
		}
	}

    private void json(String type) {
        jsonTable.setUrlTypeEncoding("http://ddwap.mah.se/tsroax/expendituresjson.php", type, "UTF-8");
    }

	private void jsonWithReader(String type) {
		jsonTable.setUrlTypeEncoding("https://dl.dropboxusercontent.com/u/1074349/data.txt", type, "UTF-8");
	}

    private void xml() {
        xmlTable.setUrlAndEncoding("http://ddwap.mah.se/tsroax/expendituresxml.xml", "UTF-8");
    }

    private void textAndImage() {
        new LoadBitmap(new BL(),"http://i1.kym-cdn.com/photos/images/newsfeed/000/755/142/348.png");
        new LoadText(new LoadText.TextListener() {
            @Override
            public void textLoaded(String str) {
				final String s =str;
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						tvText.setText(s);

					}
				});
			}

        },"http://ddwap.mah.se/tsroax/memory/Memory.html","ISO-8859-1" );
    }

	private class BL implements LoadBitmap.BitmapListener {
		public void bitmapLoaded(final Bitmap bitmap) {
            final Bitmap bm = bitmap;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ivMemory.setImageBitmap(bm);

                }
            });
		}
	}

}
