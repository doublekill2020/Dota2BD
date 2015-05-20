package com.badr.infodota.fragment.guide.edit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.api.abilities.Ability;
import com.badr.infodota.api.guide.custom.AbilityBuild;
import com.badr.infodota.api.guide.custom.Guide;
import com.badr.infodota.api.heroes.Hero;
import com.badr.infodota.fragment.guide.GuideHolder;
import com.badr.infodota.service.hero.HeroService;
import com.badr.infodota.util.FileUtils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.edu.mydotabuff.R;

/**
 * User: Histler
 * Date: 14.02.14
 */
public class AbilityBuildPartEdit extends Fragment implements GuideHolder, OnAfterEditListener {
    LayoutInflater inflater;
    Hero hero;
    private Guide guide;
    private int grayColor;
    private int textColor;

    public static AbilityBuildPartEdit newInstance(long heroId, Guide guide) {
        AbilityBuildPartEdit fragment = new AbilityBuildPartEdit();
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
    public void updateWith(Guide guide) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.guide_skillbuild, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        HeroService heroService = BeanContainer.getInstance().getHeroService();
        hero = heroService.getHeroById(getActivity(), getArguments().getLong("id"));
        grayColor = getResources().getColor(R.color.dota_gray);
        textColor = getResources().getColor(R.color.primary);
        inflater = getActivity().getLayoutInflater();
        initAbilitiesHolder();
    }

    private void initAbilitiesHolder() {
        View root = getView();
        ViewGroup abilityHolder = (ViewGroup) root.findViewById(R.id.ability_holder);
        abilityHolder.removeAllViews();
        final AbilityBuild abilityBuild = guide.getAbilityBuild();
        if (abilityBuild.getAbilityOrder() == null) {
            abilityBuild.setAbilityOrder(new TreeMap<String, String>());
        }
        Map<String, String> abilityUpgrades = abilityBuild.getAbilityOrder();

        Activity activity = getActivity();
        HeroService heroService = BeanContainer.getInstance().getHeroService();
        final List<Ability> abilities = heroService.getHeroAbilities(activity, hero.getId());
        final Map<Ability, String> tags = new HashMap<Ability, String>();
        for (int i = 0; i < abilities.size(); i++) {
            final Ability ability = abilities.get(i);
            ImageView currentAbilityHeader = (ImageView) root.findViewWithTag(String.valueOf(i));
            currentAbilityHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(getActivity().getString(R.string.skill_tooltip));
                    if (abilityBuild.getAbilityTooltips() == null) {
                        abilityBuild.setAbilityTooltips(new HashMap<String, String>());
                    }
                    final EditText editText = new EditText(getActivity());
                    if (abilityBuild.getAbilityTooltips().containsKey(ability.getName())) {
                        editText.setText(abilityBuild.getAbilityTooltips().get(ability.getName()));
                    }
                    builder.setView(editText);
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton(R.string.add_tooltip, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (abilityBuild.getAbilityTooltips() == null) {
                                abilityBuild.setAbilityTooltips(new HashMap<String, String>());
                            }
                            abilityBuild.getAbilityTooltips().put(ability.getName(), editText.getText().toString());
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            });
            currentAbilityHeader.setImageBitmap(
                    FileUtils.getBitmapFromAsset(getActivity(), heroService.getAbilityPath(activity, ability.getId())));
            tags.put(ability, String.valueOf(i));
        }
        for (int level = 1; level <= 25; level++) {
            final View row = inflater.inflate(R.layout.skillbuild_attribute_table_row, null, false);
            final String finalLevel = String.valueOf(level);
            for (int j = 0; j < 5; j++) {
                TextView possibleUpgrade = (TextView) row.findViewWithTag(String.valueOf(j));
                possibleUpgrade.setText(String.valueOf(level));
                setCommon(possibleUpgrade);
                possibleUpgrade.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView textView = (TextView) v;
                        for (int abilityId = 0; abilityId < 5; abilityId++) {
                            TextView posup = (TextView) row.findViewWithTag(String.valueOf(abilityId));
                            setCommon(posup);
                        }
                        setUpgraded(textView);
                        String abilityStr = (String) textView.getTag();
                        Map<String, String> abilityOrder = guide.getAbilityBuild().getAbilityOrder();
                        if (abilityOrder.containsKey(finalLevel)) {
                            abilityOrder.remove(finalLevel);
                        }
                        Ability current = abilities.get(Integer.valueOf(abilityStr));
                        abilityOrder.put(finalLevel, current.getName());
                    }
                });
            }
            row.setTag(level);
            abilityHolder.addView(row);
            if (abilityUpgrades.containsKey(String.valueOf(level))) {
                Ability tekAbility = new Ability();
                tekAbility.setName(abilityUpgrades.get(String.valueOf(level)));
                String tag = tags.get(tekAbility);
                row.findViewWithTag(tag).performClick();
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void setCommon(TextView possibleUpgrade) {
        possibleUpgrade.setTextColor(grayColor);
        possibleUpgrade.setBackgroundDrawable(getResources().getDrawable(R.drawable.attribute_not_choosed_bkg));
    }

    @SuppressWarnings("deprecation")
    private void setUpgraded(TextView possibleUpgrade) {
        possibleUpgrade.setTextColor(textColor);
        possibleUpgrade.setBackgroundDrawable(getResources().getDrawable(R.drawable.attribute_selected_bkg));
    }

    @Override
    public void onSave() {
        AbilityBuild abilityBuild = guide.getAbilityBuild();
        Map<String, String> map = abilityBuild.getAbilityOrder();
        abilityBuild.setAbilityOrder(new TreeMap<String, String>(new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                if (lhs.length() > rhs.length()) {
                    return 1;
                } else if (lhs.length() < rhs.length()) {
                    return -1;
                }
                return lhs.compareTo(rhs);
            }
        }));
        abilityBuild.getAbilityOrder().putAll(map);
    }
}
