package cn.edu.mydotabuff.base;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by nevermore on 2017/6/30 0030.
 */

public abstract class BaseListAdapter<T> extends RecyclerView.Adapter<BaseListHolder> {

    protected List<T> beans;
    protected final int resourceId;
    private int clickTag;
    private int longClickTag;

    /**
     * 不传tag 代表不处理item点击事件
     *
     * @param beans
     * @param resourceId
     */
    public BaseListAdapter(List<T> beans, int resourceId) {
        this(beans, resourceId, -1);
    }

    public BaseListAdapter(List<T> beans, int resourceId, int clickTag) {
        this(beans, resourceId, clickTag, -1);
    }

    /**
     * 传tag，指定BaseClickEvent的tag，代表处理item点击事件
     *
     * @param beans
     * @param resourceId
     * @param clickTag
     */
    public BaseListAdapter(List<T> beans, int resourceId, int clickTag, int longClickTag) {
        this.beans = beans;
        this.resourceId = resourceId;
        this.clickTag = clickTag;
        this.longClickTag = longClickTag;
    }

    @Override
    public BaseListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(resourceId, parent, false);
        return new BaseListHolder(itemView, clickTag, longClickTag);
    }

    @Override
    public void onBindViewHolder(BaseListHolder holder, int position) {
        T bean = beans.get(position);
        getView(holder, bean, position);
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

    public abstract void getView(BaseListHolder holder, T bean, int pos);

    public void setData(List<T> beans) {
        this.beans = beans;
    }

    public List<T> getData() {
        return beans;
    }

    public void removedData(T bean) {
        int pos = beans.indexOf(bean);
        beans.remove(pos);
        notifyItemRemoved(pos);
    }
}