package net.netm.apps.libs.teaseMe.components;

import net.netm.apps.libs.teaseMe.listview.services.impl.ListViewScreenConfiguration;
import net.netm.apps.libs.teaseMe.models.FilteredScreen;
import net.netm.apps.libs.teaseMe.models.Teaser;
import net.netm.apps.libs.teaseMe.services.ItemLayoutMapper;
import net.netm.apps.libs.teaseMe.utils.DownloadImageTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ahoban on 26.03.15.
 * <p>
 * ListAdapter for Teasers. For the item layouts, you can pass either a List of
 * layout id's, one layout id (in that case each item will have the same
 * layout), or an implementation of {@link net.netm.apps.libs.teaseMe.services.ItemLayoutMapper ItemLayoutMapper}</p>
 */
public class TeaserListAdapter extends BaseAdapter {

	private final FilteredScreen screen;

	private final LayoutInflater inflater;

	private final ItemLayoutMapper layoutMapper;

	private final AbsListView listView;

	public TeaserListAdapter(FilteredScreen screen, ItemLayoutMapper layoutMapper, ListViewScreenConfiguration configuration) {
		this.inflater = LayoutInflater.from(configuration.getContext());
		this.layoutMapper = layoutMapper;
		this.listView = (AbsListView) configuration.getView();
		this.screen = screen;
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

			if (layoutMapper != null)
				convertView = inflateAndReturnItemView(convertView, layoutMapper, position);

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

			if (teaserImage == null) {
				Log.w(this.getClass().getPackage().getName(), "TeaserImage or Url is null! " + teaserImage);
				return convertView;
			}

			DownloadImageTask task = new DownloadImageTask(image, item.getImageAspectRatio());

			task.execute(item.getImage());

		} else {

			TextView textView = (TextView) convertView.findViewById(layoutMapper.getContentViewId(item, position));

			if (textView == null)
				((ViewGroup) convertView).getChildAt(1);

			textView.setText(item.getContent());
		}

		return convertView;
	}

	/**
	 * @param convertView
	 * @param layoutMapper
	 *            used to get the corresponding item layout set by the developer
	 * @param position
	 * @return View inflated item view
	 */
	private View inflateAndReturnItemView(View convertView, ItemLayoutMapper layoutMapper, int position) {

		if (convertView == null)
			convertView = inflater.inflate(layoutMapper.getItemLayoutId(this.getItem(position), position), null);

		return convertView;

	}

	public boolean hasContent(FilteredScreen screen) {
		return screen != null && !screen.getTeasers().isEmpty();
	}

}
