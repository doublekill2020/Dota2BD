package cn.edu.mydotabuff.common.adapter;

import java.util.List;

import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.model.ItemsItem;
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
 * 物品 Adapter
 * 
 * @author tupunco
 */
public final class ItemsImagesAdapter extends BaseAdapter {
    private final class ViewHolder {
        public TextView name;
        public TextView cost;
        public ImageView image;
    }

    private final LayoutInflater mInflater;
    private final List<ItemsItem> mComponents;

    public ItemsImagesAdapter(Context context,List<ItemsItem> items) {
        super();

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
            view = mInflater.inflate(R.layout.frag_itemsdetail_components_grid_item,
                    parent, false);

            holder = new ViewHolder();
            holder.name = (TextView) view.findViewById(R.id.text_items_name);
            holder.cost = (TextView) view.findViewById(R.id.text_items_cost);
            holder.image = (ImageView) view.findViewById(R.id.image_items);

            view.setTag(holder);
        } else
            holder = (ViewHolder) view.getTag();

        if (holder != null) {
            final ItemsItem item = (ItemsItem) getItem(position);
            Glide.with(convertView.getContext())
                    .load(Utils.getItemsImageUriGlide(item.keyName))
                    .into(holder.image);
            holder.name.setText(item.dname_l);
            holder.cost.setText(String.valueOf(item.cost));
        }
        return view;
    }
}
