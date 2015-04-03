package net.netm.apps.libs.teaseMe.utils;

import java.io.InputStream;

import net.netm.apps.libs.teaseMe.TeaseMe;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicHeader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by ahoban on 26.03.15.
 */

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    private final ImageView bmImage;

    private final double aspectRatio;

    public DownloadImageTask(ImageView bmImage, double aspectRatio) {
        this.bmImage = bmImage;
        this.aspectRatio = aspectRatio;
    }


    protected Bitmap doInBackground(String... urls) {


        String urldisplay = urls[0];

        return downloadBitmap(urldisplay);
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }


    private Bitmap downloadBitmap(String url) {
        // initilize the default HTTP client object
        final HttpClient client = TeaseMe.getInstance().getHttpClient();


        //forming a HttoGet request
        final HttpGet getRequest = new HttpGet(url);

        getRequest.setHeader(new BasicHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));

        try {

            HttpResponse response = client.execute(getRequest);

            //check 200 OK for success
            final int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode != HttpStatus.SC_OK) {
                Log.w("ImageDownloader", "Error " + statusCode +
                        " while retrieving bitmap from " + url);


            }

            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inputStream = null;
                try {
                    // getting contents from the stream
                    inputStream = entity.getContent();

                    // decoding stream data back into image Bitmap that android understands

                    return BitmapFactory.decodeStream(inputStream);

                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    entity.consumeContent();

                }
            }
        } catch (Exception e) {
            // You Could provide a more explicit error message for IOException
            getRequest.abort();
            Log.e("ImageDownloader", "Something went wrong while" +
                    " retrieving bitmap from " + url + e.toString());
        }

        return null;
    }


}