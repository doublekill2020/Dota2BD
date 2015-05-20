package com.badr.infodota.fragment.match.details;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.api.items.Item;
import com.badr.infodota.api.matchdetails.AdditionalUnit;
import com.badr.infodota.api.matchdetails.Player;
import com.badr.infodota.service.item.ItemService;
import com.badr.infodota.util.TrackUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json2.JSONException;

import java.io.IOException;

import cn.edu.mydotabuff.DataManager;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.common.Common;
import cn.edu.mydotabuff.util.Utils;

/**
 * User: Histler
 * Date: 23.01.14
 */
public class MatchPlayerSummary extends Fragment {
    private static final int PHONE = 0;
    private static final int TABLET_PORTRAIT = 1;
    private static final int TABLET_LANDSCAPE = 2;
    private Player player;
    private DisplayImageOptions options;
    private ImageLoader imageLoader;

    public static MatchPlayerSummary newInstance(Player player) {
        MatchPlayerSummary fragment = new MatchPlayerSummary();
        fragment.setPlayer(player);
        return fragment;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.match_player_summary, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        orientationChanged();
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.default_pic)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        imageLoader = ImageLoader.getInstance();
        initBasicInfo();
    }

    private void initBasicInfo() {
        View root = getView();
        final Activity activity = getActivity();
        if(root!=null&&activity!=null) {
            ItemService itemService = BeanContainer.getInstance().getItemService();
            final Item current = itemService.getItemById(activity, player.getItem0());
            ImageView item0 = (ImageView) root.findViewById(R.id.item0);
            if (current != null) {
                imageLoader.displayImage(
                        TrackUtils.getItemImage(current.getDotaId()),
                        item0,
                        options);
                item0.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        try {
                            Utils.startItemsDetailActivity(
                                    MatchPlayerSummary.this.getActivity(),
                                    DataManager.getItemsItem(MatchPlayerSummary.this.getActivity(),
                                            Common.getItemName(new Long(current.getId()).intValue())));
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                item0.setImageResource(R.drawable.default_pic);
            }
            final Item current2 = itemService.getItemById(activity, player.getItem1());
            ImageView item1 = (ImageView) root.findViewById(R.id.item1);
            if (current != null) {
                imageLoader.displayImage(
                        TrackUtils.getItemImage(current.getDotaId()),
                        item1,
                        options);
                item1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        try {
                            Utils.startItemsDetailActivity(
                                    MatchPlayerSummary.this.getActivity(),
                                    DataManager.getItemsItem(MatchPlayerSummary.this.getActivity(),
                                            Common.getItemName(new Long(current2.getId()).intValue())));
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                item1.setImageResource(R.drawable.default_pic);
            }
            final Item current3 = itemService.getItemById(activity, player.getItem2());
            ImageView item2 = (ImageView) root.findViewById(R.id.item2);
            if (current != null) {
                imageLoader.displayImage(
                        TrackUtils.getItemImage(current.getDotaId()),
                        item2,
                        options);
                item2.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        try {
                            Utils.startItemsDetailActivity(
                                    MatchPlayerSummary.this.getActivity(),
                                    DataManager.getItemsItem(MatchPlayerSummary.this.getActivity(),
                                            Common.getItemName(new Long(current3.getId()).intValue())));
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                item2.setImageResource(R.drawable.default_pic);
            }
            final Item current4 = itemService.getItemById(activity, player.getItem3());
            ImageView item3 = (ImageView) root.findViewById(R.id.item3);
            if (current != null) {
                imageLoader.displayImage(
                        TrackUtils.getItemImage(current.getDotaId()),
                        item3,
                        options);
                item3.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        try {
                            Utils.startItemsDetailActivity(
                                    MatchPlayerSummary.this.getActivity(),
                                    DataManager.getItemsItem(MatchPlayerSummary.this.getActivity(),
                                            Common.getItemName(new Long(current4.getId()).intValue())));
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                item3.setImageResource(R.drawable.default_pic);
            }
            final Item current5 = itemService.getItemById(activity, player.getItem4());
            ImageView item4 = (ImageView) root.findViewById(R.id.item4);
            if (current != null) {
                imageLoader.displayImage(
                        TrackUtils.getItemImage(current.getDotaId()),
                        item4,
                        options);
                item4.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        try {
                            Utils.startItemsDetailActivity(
                                    MatchPlayerSummary.this.getActivity(),
                                    DataManager.getItemsItem(MatchPlayerSummary.this.getActivity(),
                                            Common.getItemName(new Long(current5.getId()).intValue())));
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                item4.setImageResource(R.drawable.default_pic);
            }
            final Item current6 = itemService.getItemById(activity, player.getItem5());
            ImageView item5 = (ImageView) root.findViewById(R.id.item5);
            if (current != null) {
                imageLoader.displayImage(
                        TrackUtils.getItemImage(current.getDotaId()),
                        item5,
                        options);
                item5.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        try {
                            Utils.startItemsDetailActivity(
                                    MatchPlayerSummary.this.getActivity(),
                                    DataManager.getItemsItem(MatchPlayerSummary.this.getActivity(),
                                            Common.getItemName(new Long(current6.getId()).intValue())));
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                item5.setImageResource(R.drawable.default_pic);
            }
            View additionalUnitHolder = root.findViewById(R.id.additional_unit_holder);
            if (player.getAdditionalUnits() != null && player.getAdditionalUnits().size() > 0) {
                AdditionalUnit unit = player.getAdditionalUnits().get(0);
                additionalUnitHolder.setVisibility(View.VISIBLE);
                final Item current7 = itemService.getItemById(activity, unit.getItem0());
                ImageView additionalUnitItem0 = (ImageView) root.findViewById(R.id.additional_unit_item0);
                if (current != null) {
                    imageLoader.displayImage(
                            TrackUtils.getItemImage(current.getDotaId()),
                            additionalUnitItem0,
                            options);
                    additionalUnitItem0.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            try {
                                Utils.startItemsDetailActivity(
                                        MatchPlayerSummary.this.getActivity(),
                                        DataManager.getItemsItem(MatchPlayerSummary.this.getActivity(),
                                                Common.getItemName(new Long(current7.getId()).intValue())));
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    additionalUnitItem0.setImageResource(R.drawable.default_pic);
                }
                final Item current8 = itemService.getItemById(activity, unit.getItem1());
                ImageView additionalUnitItem1 = (ImageView) root.findViewById(R.id.additional_unit_item1);
                if (current != null) {
                    imageLoader.displayImage(
                            TrackUtils.getItemImage(current.getDotaId()),
                            additionalUnitItem1,
                            options);
                    additionalUnitItem1.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            try {
                                Utils.startItemsDetailActivity(
                                        MatchPlayerSummary.this.getActivity(),
                                        DataManager.getItemsItem(MatchPlayerSummary.this.getActivity(),
                                                Common.getItemName(new Long(current8.getId()).intValue())));
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    additionalUnitItem1.setImageResource(R.drawable.default_pic);
                }
                final Item current9 = itemService.getItemById(activity, unit.getItem2());
                ImageView additionalUnitItem2 = (ImageView) root.findViewById(R.id.additional_unit_item2);
                if (current != null) {
                    imageLoader.displayImage(
                            TrackUtils.getItemImage(current.getDotaId()),
                            additionalUnitItem2,
                            options);
                    additionalUnitItem2.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            try {
                                Utils.startItemsDetailActivity(
                                        MatchPlayerSummary.this.getActivity(),
                                        DataManager.getItemsItem(MatchPlayerSummary.this.getActivity(),
                                                Common.getItemName(new Long(current9.getId()).intValue())));
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    additionalUnitItem2.setImageResource(R.drawable.default_pic);
                }
                final Item current10 = itemService.getItemById(activity, unit.getItem3());
                ImageView additionalUnitItem3 = (ImageView) root.findViewById(R.id.additional_unit_item3);
                if (current != null) {
                    imageLoader.displayImage(
                            TrackUtils.getItemImage(current.getDotaId()),
                            additionalUnitItem3,
                            options);
                    additionalUnitItem3.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            try {
                                Utils.startItemsDetailActivity(
                                        MatchPlayerSummary.this.getActivity(),
                                        DataManager.getItemsItem(MatchPlayerSummary.this.getActivity(),
                                                Common.getItemName(new Long(current10.getId()).intValue())));
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    additionalUnitItem3.setImageResource(R.drawable.default_pic);
                }
                final Item current11 = itemService.getItemById(activity, unit.getItem4());
                ImageView additionalUnitItem4 = (ImageView) root.findViewById(R.id.additional_unit_item4);
                if (current != null) {
                    imageLoader.displayImage(
                            TrackUtils.getItemImage(current.getDotaId()),
                            additionalUnitItem4,
                            options);
                    additionalUnitItem4.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            try {
                                Utils.startItemsDetailActivity(
                                        MatchPlayerSummary.this.getActivity(),
                                        DataManager.getItemsItem(MatchPlayerSummary.this.getActivity(),
                                                Common.getItemName(new Long(current11.getId()).intValue())));
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    additionalUnitItem4.setImageResource(R.drawable.default_pic);
                }
                final Item  current12 = itemService.getItemById(activity, unit.getItem5());
                ImageView additionalUnitItem5 = (ImageView) root.findViewById(R.id.additional_unit_item5);
                if (current != null) {
                    imageLoader.displayImage(
                            TrackUtils.getItemImage(current.getDotaId()),
                            additionalUnitItem5,
                            options);
                    additionalUnitItem5.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            try {
                                Utils.startItemsDetailActivity(
                                        MatchPlayerSummary.this.getActivity(),
                                        DataManager.getItemsItem(MatchPlayerSummary.this.getActivity(),
                                                Common.getItemName(new Long(current12.getId()).intValue())));
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    additionalUnitItem5.setImageResource(R.drawable.default_pic);
                }
            } else {
                additionalUnitHolder.setVisibility(View.GONE);
            }

            ((TextView) root.findViewById(R.id.kills)).setText(Html.fromHtml(getString(R.string.kills) + " " + player.getKills()));
            ((TextView) root.findViewById(R.id.death)).setText(Html.fromHtml(getString(R.string.deaths) + " " + player.getDeaths()));
            ((TextView) root.findViewById(R.id.assists)).setText(
                    Html.fromHtml(getString(R.string.assists) + " " + player.getAssists()));
            ((TextView) root.findViewById(R.id.gold)).setText(Html.fromHtml(getString(R.string.gold) + " " + player.getGold()));
            ((TextView) root.findViewById(R.id.last_hits)).setText(
                    Html.fromHtml(getString(R.string.last_hits) + " " + player.getLastHits()));
            ((TextView) root.findViewById(R.id.denies)).setText(
                    Html.fromHtml(getString(R.string.denies) + " " + player.getDenies()));
            ((TextView) root.findViewById(R.id.gpm)).setText(
                    Html.fromHtml(getString(R.string.gpm) + " " + player.getGoldPerMin()));
            ((TextView) root.findViewById(R.id.xpm)).setText(
                    Html.fromHtml(getString(R.string.xpm) + " " + player.getXpPerMin()));
            if(player.getNetWorth()==null) {
                ((TextView) root.findViewById(R.id.hero_damage)).setText(
                        Html.fromHtml(getString(R.string.hero_damage) + " " + player.getHeroDamage()));
                ((TextView) root.findViewById(R.id.hero_healing)).setText(
                        Html.fromHtml(getString(R.string.hero_healing) + " " + player.getHeroHealing()));
                ((TextView) root.findViewById(R.id.tower_damage)).setText(
                        Html.fromHtml(getString(R.string.tower_damage) + " " + player.getTowerDamage()));
            }
            else {
                TextView netWorth= (TextView) root.findViewById(R.id.net_worth);
                netWorth.setVisibility(View.VISIBLE);
                netWorth.setText(Html.fromHtml(getString(R.string.net_worth) + " " + player.getNetWorth()));
                root.findViewById(R.id.hero_damage).setVisibility(View.GONE);
                root.findViewById(R.id.hero_healing).setVisibility(View.GONE);
                root.findViewById(R.id.tower_damage).setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        orientationChanged();
    }

    private int getState() {
        Resources resources = getResources();
        if (!resources.getBoolean(R.bool.is_tablet)) {
            if (resources.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                return TABLET_PORTRAIT;
            } else {
                return PHONE;
            }
        } else {
            if (resources.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                return TABLET_LANDSCAPE;
            } else {
                return TABLET_PORTRAIT;
            }
        }
    }

    private void orientationChanged() {
        View root = getView();
        if(root!=null) {
            switch (getState()) {
                case TABLET_LANDSCAPE:
                    ((LinearLayout) root.findViewById(R.id.unit_holder)).setOrientation(LinearLayout.VERTICAL);
                    ((LinearLayout) root.findViewById(R.id.additional_unit_holder)).setOrientation(LinearLayout.VERTICAL);
                    ((LinearLayout) root.findViewById(R.id.item_holder)).setOrientation(LinearLayout.HORIZONTAL);
                    ((LinearLayout) root.findViewById(R.id.main_holder)).setOrientation(LinearLayout.HORIZONTAL);
                    break;
                case TABLET_PORTRAIT:
                    ((LinearLayout) root.findViewById(R.id.unit_holder)).setOrientation(LinearLayout.VERTICAL);
                    ((LinearLayout) root.findViewById(R.id.additional_unit_holder)).setOrientation(LinearLayout.VERTICAL);
                    ((LinearLayout) root.findViewById(R.id.item_holder)).setOrientation(LinearLayout.VERTICAL);
                    ((LinearLayout) root.findViewById(R.id.main_holder)).setOrientation(LinearLayout.HORIZONTAL);
                    break;
                case PHONE:
                    ((LinearLayout) root.findViewById(R.id.unit_holder)).setOrientation(LinearLayout.HORIZONTAL);
                    ((LinearLayout) root.findViewById(R.id.additional_unit_holder)).setOrientation(LinearLayout.HORIZONTAL);
                    ((LinearLayout) root.findViewById(R.id.item_holder)).setOrientation(LinearLayout.VERTICAL);
                    ((LinearLayout) root.findViewById(R.id.main_holder)).setOrientation(LinearLayout.VERTICAL);
                    break;
            }
        }
    }
}
