package net.netm.apps.libs.teaseme.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;

import net.netm.apps.libs.teaseme.listview.services.impl.ListViewScreenConfiguration;
import net.netm.apps.libs.teaseme.models.FilteredScreen;
import net.netm.apps.libs.teaseme.models.Teaser;
import net.netm.apps.libs.teaseme.services.ItemLayoutMapper;

import java.util.List;

/**
 * Created by ahoban on 26.03.15.
 * <p>
 * ListAdapter for Teasers. For the item layouts, you can pass either a List of
 * layout id's, one layout id (in that case each item will have the same
 * layout), or an implementation of {@link net.netm.apps.libs.teaseme.services.ItemLayoutMapper itemLayoutMapper}</p>
 */
public class TeaserListAdapter extends BaseAdapter {

    private final FilteredScreen screen;
    private final LayoutInflater inflater;
    private final ItemLayoutMapper layoutMapper;
    private final AbsListView listView;
    private final List<? extends Transformation> transformers;
    private final Context context;

    public TeaserListAdapter(final FilteredScreen screen, final ItemLayoutMapper layoutMapper, final ListViewScreenConfiguration configuration, final List<? extends Transformation> transformer) {
        this.context = configuration.getContext();
        this.inflater = LayoutInflater.from(context);
        this.layoutMapper = layoutMapper;
        this.listView = (AbsListView) configuration.getView();
        this.screen = screen;
        this.transformers = transformer;
    }

    public FilteredScreen getScreen() {
        return screen;
    }

    @Override
    public int getCount() {
        return !hasContent(screen) ? 0 : screen.getTeasers().size();
    }

    @Override
    public Teaser getItem(int position) {
        return !hasContent(screen) ? null : screen.getTeasers().get(position);
    }

    @Override
    public long getItemId(int position) {
        return !hasContent(screen) ? -1 : screen.getTeasers().get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Teaser item = this.getItem(position);

        if (item.getImageTeaser()) {

            String teaserImage = item.getImage();

            if (teaserImage == null) {
                Log.w(this.getClass().getPackage().getName(), "TeaserImage or Url is null! " + teaserImage);
                return convertView;
            }

            convertView = inflateAndReturnItemView(convertView, layoutMapper.getItemLayoutId(item, position));

            ImageView image = getImageView(position, convertView, item);

            processImage(item, image, layoutMapper.getImageTransformationFor(item, position));


        } else {

            processContent(position, convertView, item);
        }

        return convertView;
    }

    private ImageView getImageView(int position, View convertView, Teaser item) {

        Integer imageViewId = layoutMapper.getImageViewId(item, position);

        ImageView image;

        if (imageViewId != null) {
            image = (ImageView) convertView.findViewById(imageViewId);
        } else {
            image = (ImageView) ((ViewGroup) convertView).getChildAt(0);
        }

        if (item.getImageAlt() != null) {
            image.setContentDescription(item.getImageAlt());

        }

        Log.e(this.getClass().getPackage().getName(), "RETURNING IMAGE VIEW " + image + " " + (image == null));
        return image;
    }

    private void processContent(int position, View convertView, Teaser item) {
        TextView textView = (TextView) convertView.findViewById(layoutMapper.getContentViewId(item, position));

        if (textView == null)
            ((ViewGroup) convertView).getChildAt(1);

        textView.setText(item.getContent());
    }

    private void processImage(Teaser item, ImageView image, Transformation transformation) {


        Picasso picasso = new Picasso.Builder(context).listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                Log.e("PICASSSO errored", exception.getCause() + "\n" + exception.getMessage() + "\n"
                );
                exception.printStackTrace();
            }
        }).indicatorsEnabled(true).loggingEnabled(true).build();

        RequestCreator creator =
                picasso
                        .load(item.getImage());

        if (transformation != null)
            creator.transform(transformation);
        else if (transformers != null) {

            for (Transformation t : transformers) {
                creator.transform(t);
            }
        }

        //new DownloadImageTask(image, item.getImageAspectRatio()).execute(item.getImage());
        creator.into(image, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Log.e(this.getClass().getPackage().getName(), "PICASSO ERROR");

            }
        });
    }


    private View inflateAndReturnItemView(View convertView, int itemLayoutId) {

        if (convertView == null)
            convertView = inflater.inflate(itemLayoutId, null);

        return convertView;

    }

    /**
     *
     * @param screen the filteredScreen instance
     * @return true if has teasers
     */
    public boolean hasContent(FilteredScreen screen) {
        return screen != null && !screen.getTeasers().isEmpty();
    }

}
