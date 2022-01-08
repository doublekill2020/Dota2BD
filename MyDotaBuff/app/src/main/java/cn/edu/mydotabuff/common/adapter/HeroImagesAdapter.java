package cn.edu.mydotabuff.common.adapter;

import java.util.List;

import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.model.HeroItem;
import cn.edu.mydotabuff.util.Utils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


/**
 * 推荐使用英雄 Adapter
 * 
 * @author tupunco
 */
public final class HeroImagesAdapter extends BaseAdapter {
    private final class ViewHolder {
        public TextView text;
        public ImageView image;
    }

    private final LayoutInflater mInflater;
    private final List<HeroItem> mComponents;
    private Context context;

    public HeroImagesAdapter(Context context, List<HeroItem> items) {
        super();

        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mComponents = items;
    }

    @Override
    public int getCount() {
        return mComponents.size();
    }

    @Override
    public Object getItem(int position) {
        return mComponents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        final ViewHolder holder;
        if (convertView == null) {
            view = mInflater.inflate(R.layout.frag_itemsdetail_hero_grid_item, parent, false);

            holder = new ViewHolder();
            holder.text = (TextView) view.findViewById(R.id.text_hero_name);
            holder.image = (ImageView) view.findViewById(R.id.image_hero);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final HeroItem item = (HeroItem) getItem(position);
        Glide.with(context).load(Utils.getHeroImageUriForGlide(item.keyName)).into(holder.image);
        holder.text.setText(item.name_l);

        return view;
    }
}
