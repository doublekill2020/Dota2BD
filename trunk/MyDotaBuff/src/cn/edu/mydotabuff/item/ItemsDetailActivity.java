package cn.edu.mydotabuff.item;

import java.io.IOException;

import org.json2.JSONException;

import cn.edu.mydotabuff.DataManager;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.adapter.DBAdapter;
import cn.edu.mydotabuff.adapter.HeroImagesAdapter;
import cn.edu.mydotabuff.adapter.ItemsImagesAdapter;
import cn.edu.mydotabuff.base.SwipeBackAppCompatFragmentActivity;
import cn.edu.mydotabuff.entity.FavoriteItem;
import cn.edu.mydotabuff.entity.HeroItem;
import cn.edu.mydotabuff.entity.ItemsItem;
import cn.edu.mydotabuff.util.Utils;
import cn.edu.mydotabuff.view.SimpleGridView;
import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;

/**
 * 物品详细 Activity
 * 
 * @author tupunco
 */
public class ItemsDetailActivity extends SwipeBackAppCompatFragmentActivity {
	private static final String TAG = "ItemsDetailActivity";
	/**
	 * 物品名称 Intent 参数
	 */
	public final static String KEY_ITEMS_DETAIL_KEY_NAME = "KEY_ITEMS_DETAIL_KEY_NAME";
	/**
	 * 父物品名称(合成卷轴使用) Intent 参数
	 */
	public final static String KEY_ITEMS_DETAIL_PARENT_KEY_NAME = "KEY_ITEMS_DETAIL_PARENT_KEY_NAME";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		Utils.fillFragment(this, ItemsDetailFragment.newInstance(this
				.getIntent().getStringExtra(KEY_ITEMS_DETAIL_KEY_NAME), this
				.getIntent().getStringExtra(KEY_ITEMS_DETAIL_PARENT_KEY_NAME)));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// NavUtils.navigateUpFromSameTask(this);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 物品详细 Fragment
	 */
	public static class ItemsDetailFragment extends Fragment implements
			SimpleGridView.OnItemClickListener {
		private DisplayImageOptions mImageLoadOptions;
		private ItemsItem mItemsItem;
		private MenuItem mMenuCheckAddCollection;

		static ItemsDetailFragment newInstance(String items_keyName) {
			return newInstance(items_keyName, null);
		}

		/**
		 * 
		 * @param items_keyName
		 * @param items_parent_keyName
		 * @return
		 */
		static ItemsDetailFragment newInstance(String items_keyName,
				String items_parent_keyName) {
			final ItemsDetailFragment f = new ItemsDetailFragment();
			final Bundle b = new Bundle();
			b.putString(KEY_ITEMS_DETAIL_KEY_NAME, items_keyName);
			if (!TextUtils.isEmpty(items_parent_keyName)) {
				b.putString(KEY_ITEMS_DETAIL_PARENT_KEY_NAME,
						items_parent_keyName);
			}
			f.setArguments(b);
			return f;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			setHasOptionsMenu(true);
			mImageLoadOptions = Utils.createDisplayImageOptions();

			final Bundle arg = this.getArguments();
			final String items_keyName = arg
					.getString(KEY_ITEMS_DETAIL_KEY_NAME);
			final String items_parent_keyName = arg
					.containsKey(KEY_ITEMS_DETAIL_PARENT_KEY_NAME) ? arg
					.getString(KEY_ITEMS_DETAIL_PARENT_KEY_NAME) : null;

			// Log.v(TAG, "arg.items_keyName=" + items_keyName
			// + " arg.items_parent_keyName" + items_parent_keyName);

			if (!TextUtils.isEmpty(items_keyName)) {
				Utils.executeAsyncTask(mLoaderTask, items_keyName,
						items_parent_keyName);
			}
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			return inflater.inflate(R.layout.fragment_itemsdetail, container,
					false);
		}

		/**
		 * fill MenuItem Check AddCollection
		 */
		private void tryFillMenuCheckAddCollection() {
			if (mMenuCheckAddCollection == null || mItemsItem == null) {
				return;
			}

			final MenuItem check = mMenuCheckAddCollection;
			check.setChecked(mItemsItem.hasFavorite == 1);
			Utils.configureStarredMenuItem(check, mItemsItem.isrecipe);
			if (mItemsItem.isrecipe) {
				return;
			}

			check.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
				@Override
				public boolean onMenuItemClick(MenuItem item) {
					final boolean isChecked = !item.isChecked();
					final ItemsItem items = mItemsItem;
					item.setChecked(isChecked);

					Utils.configureStarredMenuItem(item, items.isrecipe);
					items.hasFavorite = isChecked ? 1 : 0;
					if (isChecked) {
						final FavoriteItem c = new FavoriteItem();
						c.keyName = items.keyName;
						c.type = FavoriteItem.KEY_TYPE_ITEMS;
						DBAdapter.getInstance().addFavorite(c);
					} else {
						DBAdapter.getInstance().deleteFavorite(items.keyName);
					}
					return true;
				}
			});

		}

		/**
		 * 绑定物品视图
		 * 
		 * @param cItem
		 */
		@SuppressLint("NewApi")
		private void bindItemsItemView(ItemsItem cItem) {
			if (cItem == null) {
				return;
			}

			mItemsItem = cItem;
			final FragmentActivity cContext = this.getActivity();
			if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.HONEYCOMB) {
				cContext.invalidateOptionsMenu();
			} else {
				tryFillMenuCheckAddCollection();
			}
			cContext.setTitle(cItem.dname_l);
			final View v = this.getView();
			bindItemsItemSimpleView(v, cItem, mImageLoadOptions);

			// 合成卷轴处理
			if (cItem.isrecipe) {
				final View layout_items_desc = v
						.findViewById(R.id.layout_items_desc);
				layout_items_desc.setVisibility(View.GONE);

				// final View layout_items_desc1 =
				// v.findViewById(R.id.layout_items_desc1);
				// if (layout_items_desc1 != null) {
				// layout_items_desc1.setVisibility(View.GONE);
				// }

				return;
			}

			((TextView) v.findViewById(R.id.text_items_desc)).setText(Html
					.fromHtml(cItem.desc));
			((TextView) v.findViewById(R.id.text_items_lore))
					.setText(cItem.lore);
			((TextView) v.findViewById(R.id.text_items_attrib)).setText(Html
					.fromHtml(cItem.attrib));
			// mc
			if (!TextUtils.isEmpty(cItem.mc)) {
				((TextView) v.findViewById(R.id.text_items_mana))
						.setText(cItem.mc);
			} else {
				v.findViewById(R.id.rlayout_items_mana)
						.setVisibility(View.GONE);
			}
			// cd
			if (cItem.cd > 0) {
				((TextView) v.findViewById(R.id.text_items_cd)).setText(String
						.valueOf(cItem.cd));
			} else {
				v.findViewById(R.id.rlayout_items_cd).setVisibility(View.GONE);
			}
			// components
			if (cItem.components != null && cItem.components.length > 0) {
				final ItemsImagesAdapter adapter = new ItemsImagesAdapter(
						cContext, mImageLoadOptions, cItem.components_i);

				final SimpleGridView grid = (SimpleGridView) v
						.findViewById(R.id.grid_items_components);
				grid.setAdapter(adapter);
				grid.setOnItemClickListener(this);
			} else {
				v.findViewById(R.id.llayout_items_components).setVisibility(
						View.GONE);
			}
			// tocomponents
			if (cItem.tocomponents != null && cItem.tocomponents.length > 0) {
				final ItemsImagesAdapter adapter = new ItemsImagesAdapter(
						cContext, mImageLoadOptions, cItem.tocomponents_i);

				final SimpleGridView grid = (SimpleGridView) v
						.findViewById(R.id.grid_items_tocomponents);
				grid.setAdapter(adapter);
				grid.setOnItemClickListener(this);
			} else {
				v.findViewById(R.id.llayout_items_tocomponents).setVisibility(
						View.GONE);
			}
			// toheros
			if (cItem.toheros != null && cItem.toheros.length > 0) {
				final HeroImagesAdapter adapter = new HeroImagesAdapter(
						cContext, mImageLoadOptions, cItem.toheros_i);
				final SimpleGridView grid = (SimpleGridView) v
						.findViewById(R.id.grid_items_toheros);
				grid.setAdapter(adapter);
				grid.setOnItemClickListener(this);
			} else {
				v.findViewById(R.id.llayout_items_toheros).setVisibility(
						View.GONE);
			}
		}

		/**
		 * 绑定视图-物品简单数据信息
		 * 
		 * @param v
		 * @param cItem
		 * @param cImageLoadOptions
		 */
		public static void bindItemsItemSimpleView(final View v,
				final ItemsItem cItem,
				final DisplayImageOptions cImageLoadOptions) {
			if (v == null || cItem == null || cImageLoadOptions == null) {
				return;
			}

			ImageLoader.getInstance().displayImage(
					Utils.getItemsImageUri(cItem.keyName),
					((ImageView) v.findViewById(R.id.image_items)),
					cImageLoadOptions);

			((TextView) v.findViewById(R.id.text_items_dname))
					.setText(cItem.dname);
			((TextView) v.findViewById(R.id.text_items_dname_l))
					.setText(cItem.dname_l);
			((TextView) v.findViewById(R.id.text_items_cost)).setText(String
					.valueOf(cItem.cost));
		}

		/**
		 * 物品详细 LoaderTask
		 */
		private final AsyncTask<String, Void, ItemsItem> mLoaderTask = new AsyncTask<String, Void, ItemsItem>() {
			@Override
			protected void onPreExecute() {
				ItemsDetailFragment.this.getActivity()
						.setProgressBarIndeterminateVisibility(true);

				super.onPreExecute();
			}

			@Override
			protected void onCancelled() {
				super.onCancelled();

				ItemsDetailFragment.this.getActivity()
						.setProgressBarIndeterminateVisibility(false);
			}

			@Override
			protected ItemsItem doInBackground(String... params) {
				try {
					String keyName = params[0];
					final boolean isrecipe = keyName
							.equals(DataManager.KEY_NAME_RECIPE_ITEMS_KEYNAME);
					if (isrecipe) {
						keyName = params[1];
					}
					final ItemsItem cItem = DataManager.getItemsItem(
							ItemsDetailFragment.this.getActivity(), keyName);
					if (!isrecipe && cItem != null && cItem.hasFavorite < 0) {
						final boolean has = DBAdapter.getInstance()
								.hasFavorite(keyName);
						cItem.hasFavorite = has ? 1 : 0;
					}
					// 合成卷轴数据合并
					if (isrecipe) {
						final ItemsItem recipeItem = cItem.components_i
								.get(cItem.components_i.size() - 1);
						final ItemsItem resRecipeItem = new ItemsItem();
						resRecipeItem.cost = recipeItem.cost;
						resRecipeItem.dname = cItem.dname + " "
								+ recipeItem.dname;
						resRecipeItem.dname_l = cItem.dname_l
								+ recipeItem.dname_l;
						resRecipeItem.isrecipe = true;
						resRecipeItem.keyName = recipeItem.keyName;
						resRecipeItem.parent_keyName = recipeItem.parent_keyName;
						return resRecipeItem;
					}
					return cItem;
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(ItemsItem result) {
				super.onPostExecute(result);

				ItemsDetailFragment.this.bindItemsItemView(result);
				ItemsDetailFragment.this.getActivity()
						.setProgressBarIndeterminateVisibility(false);
			}
		};

		@Override
		public void onItemClick(ListAdapter parent, View view, int position,
				long id) {
			if (parent instanceof ItemsImagesAdapter) {
				Utils.startItemsDetailActivity(this.getActivity(),
						(ItemsItem) parent.getItem(position));
			} else if (parent instanceof HeroImagesAdapter) {
				Utils.startHeroDetailActivity(this.getActivity(),
						(HeroItem) parent.getItem(position));
			}
		};
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
