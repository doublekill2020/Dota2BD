package com.badr.infodota.fragment.guide.edit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.api.guide.custom.Guide;
import com.badr.infodota.api.guide.custom.ItemBuild;
import com.badr.infodota.api.heroes.Hero;
import com.badr.infodota.api.items.Item;
import com.badr.infodota.fragment.guide.GuideHolder;
import com.badr.infodota.service.hero.HeroService;
import com.badr.infodota.service.item.ItemService;
import com.badr.infodota.util.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.view.FlowLayout;

/**
 * User: Histler
 * Date: 14.02.14
 */
public class ItemPartEdit extends Fragment implements GuideHolder, OnAfterEditListener {
    LinearLayout itemHolder;
    LayoutInflater inflater;
    Hero hero;
    private Guide guide;
    private FlowLayout currentFlow;

    public static ItemPartEdit newInstance(long heroId, Guide guide) {
        ItemPartEdit fragment = new ItemPartEdit();
        Bundle bundle = new Bundle();
        bundle.putLong("id", heroId);
        fragment.setGuide(guide);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setGuide(Guide guide) {
        this.guide = guide;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.guide_creator_items, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        HeroService heroService = BeanContainer.getInstance().getHeroService();

        hero = heroService.getHeroById(getActivity(), getArguments().getLong("id"));

        inflater = getActivity().getLayoutInflater();
        itemHolder = (LinearLayout) getView().findViewById(R.id.parts_holder);
        getView().findViewById(R.id.addPart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initNewPart(null, null);
            }
        });
        ItemBuild itemBuild = guide.getItemBuild();
        if (itemBuild.getItems() != null) {
            for (String part : itemBuild.getItems().keySet()) {
                List<String> items = itemBuild.getItems().get(part);
                initNewPart(part, items);
            }
            itemBuild.setItems(null);
        }
    }

    private void initNewPart(String title, List<String> items) {
        final LinearLayout part = (LinearLayout) inflater.inflate(R.layout.guide_creator_item_part, null, false);
        itemHolder.addView(part);
        final FlowLayout flow = (FlowLayout) part.findViewById(R.id.items);
        LinearLayout addRow = (LinearLayout) inflater.inflate(R.layout.item_recept_row, null);
        addRow.findViewById(R.id.name).setVisibility(View.GONE);
        addRow.findViewById(R.id.cost).setVisibility(View.GONE);
        part.findViewById(R.id.delete_part).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.attention);
                builder.setMessage(getActivity().getString(R.string.sure_delete_part));
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        itemHolder.removeView(part);
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
        flow.addView(addRow);
        if (title != null) {
            ((EditText) part.findViewById(R.id.part_header)).setText(title);
        }
        Activity activity = getActivity();
        ItemService itemService = BeanContainer.getInstance().getItemService();
        if (items != null && items.size() > 0) {
            for (String itemName : items) {
                final Item item = itemService.getItemByDotaId(activity, itemName);
                final LinearLayout row = (LinearLayout) inflater.inflate(R.layout.item_recept_row, null);
                row.setTag(item.getDotaId());
                FileUtils.setDrawableFromAsset((ImageView) row.findViewById(R.id.img),
                        "items/" + item.getDotaId() + ".png");
                ((TextView) row.findViewById(R.id.name)).setText(item.getDname());
                ((TextView) row.findViewById(R.id.cost)).setText(String.valueOf(item.getCost()));

                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle(getActivity().getString(R.string.select_action));
                        String[] actions = getResources().getStringArray(R.array.item_actions);
                        builder.setItems(actions, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        //todo change this shit
                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                                        builder1.setTitle(getActivity().getString(R.string.add_tooltip));
                                        final EditText editText1 = new EditText(getActivity());
                                        editText1.setText(getItemTooltip(item.getDotaId()));
                                        builder1.setView(editText1);
                                        builder1.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                        builder1.setPositiveButton(R.string.add_tooltip, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                String tooltip = editText1.getText().toString();
                                                if (!TextUtils.isEmpty(tooltip)) {
                                                    addTooltip(item.getDotaId(), tooltip);
                                                }
                                            }
                                        });
                                        builder1.show();
                                        break;
                                    default:
                                        //removeItem()
                                        flow.removeView(row);
                                        break;
                                }
                                dialog.dismiss();
                            }
                        });
                        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                });
                flow.addView(row, flow.getChildCount() - 1);
            }
        }
    }

    @Override
    public void updateWith(Guide guide) {
        this.guide = guide;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            final long id = data.getLongExtra("id", 0);
            ItemService itemService = BeanContainer.getInstance().getItemService();
            final Item item = itemService.getItemById(getActivity(), id);
            final LinearLayout row = (LinearLayout) inflater.inflate(R.layout.item_recept_row, null);
            row.setTag(item.getDotaId());
            FileUtils.setDrawableFromAsset((ImageView) row.findViewById(R.id.img),
                    "items/" + item.getDotaId() + ".png");
            ((TextView) row.findViewById(R.id.name)).setText(item.getDname());
            ((TextView) row.findViewById(R.id.cost)).setText(String.valueOf(item.getCost()));

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(getActivity().getString(R.string.select_action));
                    String[] actions = getResources().getStringArray(R.array.item_actions);
                    builder.setItems(actions, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    //todo change this shit
                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                                    builder1.setTitle(getActivity().getString(R.string.add_tooltip));
                                    final EditText editText1 = new EditText(getActivity());
                                    editText1.setText(getItemTooltip(item.getDotaId()));
                                    builder1.setView(editText1);
                                    builder1.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    builder1.setPositiveButton(R.string.add_tooltip, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            String tooltip = editText1.getText().toString();
                                            if (!TextUtils.isEmpty(tooltip)) {
                                                addTooltip(item.getDotaId(), tooltip);
                                            }
                                        }
                                    });
                                    builder1.show();
                                    break;
                                default:
                                    //removeItem()
                                    currentFlow.removeView(row);
                                    break;
                            }
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            });
            currentFlow.addView(row, currentFlow.getChildCount() - 1);
            /*AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(getActivity().getString(R.string.add_tooltip));
            final EditText editText = new EditText(getActivity());
            builder.setView(editText);
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    String tooltip=editText.getText().toString();
                    if(!TextUtils.isEmpty(tooltip)){
                        addTooltip(item.getDotaId(),tooltip);
                    }
                }
            });
            builder.show();*/
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private String getItemTooltip(String dotaId) {
        ItemBuild itemBuild = guide.getItemBuild();
        if (itemBuild.getItemTooltips() == null) {
            itemBuild.setItemTooltips(new HashMap<String, String>());
        }
        return itemBuild.getItemTooltips().get(dotaId);
    }

    private void addTooltip(String dotaId, String tooltip) {
        ItemBuild itemBuild = guide.getItemBuild();
        if (itemBuild.getItemTooltips() == null) {
            itemBuild.setItemTooltips(new HashMap<String, String>());
        }
        itemBuild.getItemTooltips().put(dotaId, tooltip);
    }

    @Override
    public void onSave() {
        ItemBuild itemBuild = guide.getItemBuild();
        itemBuild.setItems(new LinkedHashMap<String, List<String>>());
        Map<String, List<String>> itemParts = itemBuild.getItems();
        int partsSize = itemHolder.getChildCount();
        for (int i = 0; i < partsSize; i++) {
            LinearLayout part = (LinearLayout) itemHolder.getChildAt(i);
            String title = ((TextView) part.findViewById(R.id.part_header)).getText().toString();
            FlowLayout flow = (FlowLayout) part.findViewById(R.id.items);
            List<String> currentPart = new ArrayList<String>();
            int flowSize = flow.getChildCount();
            for (int j = 0; j < flowSize - 1; j++) {
                View itemView = flow.getChildAt(j);
                currentPart.add((String) itemView.getTag());
            }
            itemParts.put(title, currentPart);
        }
    }
}
