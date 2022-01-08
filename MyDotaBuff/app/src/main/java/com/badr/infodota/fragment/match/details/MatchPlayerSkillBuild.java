package com.badr.infodota.fragment.match.details;

import android.app.Activity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.api.AbilityUpgrade;
import com.badr.infodota.api.abilities.Ability;
import com.badr.infodota.api.matchdetails.Player;
import com.badr.infodota.service.hero.HeroService;
import com.badr.infodota.util.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.mydotabuff.R;

/**
 * User: Histler
 * Date: 23.01.14
 */
public class MatchPlayerSkillBuild extends Fragment {
    private Player player;
    private boolean randomSkills;

    public static MatchPlayerSkillBuild newInstance(boolean isRandomSkills, Player player) {
        MatchPlayerSkillBuild fragment = new MatchPlayerSkillBuild();
        fragment.setPlayer(player);
        fragment.setRandomSkills(isRandomSkills);
        return fragment;
    }

    public void setRandomSkills(boolean randomSkills) {
        this.randomSkills = randomSkills;
    }

    private void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.match_player_skillbuild, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initAbilities();
    }

    @SuppressWarnings("deprecation")
    private void initAbilities() {
        View root = getView();
        if(root!=null) {
            ViewGroup abilityHolder = (ViewGroup) root.findViewById(R.id.ability_holder);
            List<AbilityUpgrade> abilityUpgrades = player.getAbilityUpgrades();
            Activity activity = getActivity();
            if (abilityUpgrades != null && abilityUpgrades.size() > 0 && activity != null) {
                HeroService heroService = BeanContainer.getInstance().getHeroService();
                List<Long> inGameAbilities = new ArrayList<Long>();
                for (AbilityUpgrade abilityUpgrade : abilityUpgrades) {
                    if (!inGameAbilities.contains(abilityUpgrade.getAbility())) {
                        inGameAbilities.add(abilityUpgrade.getAbility());
                    }
                }
                List<Ability> abilities;
                if (!randomSkills) {

                    abilities = heroService.getHeroAbilities(activity, player.getHero_id());
                } else {
                    abilities = heroService.getAbilitiesByList(activity, inGameAbilities);
                }
                Map<Ability, String> tags = new HashMap<Ability, String>();
                for (int i = 0; i < abilities.size(); i++) {
                    Ability ability = abilities.get(i);
                    ImageView currentAbilityHeader = (ImageView) root.findViewWithTag(String.valueOf(i));
                    currentAbilityHeader.setImageBitmap(
                            FileUtils.getBitmapFromAsset(getActivity(), heroService.getAbilityPath(activity, ability.getId())));
                    tags.put(ability, String.valueOf(i));

                }
                LayoutInflater inflater = getActivity().getLayoutInflater();
                for (AbilityUpgrade abilityUpgrade : abilityUpgrades) {
                    View row = inflater.inflate(R.layout.skillbuild_attribute_table_row, null, false);
                    long tekability = abilityUpgrade.getAbility();
                    Ability ability = new Ability();
                    ability.setId(tekability);
                    String tag = tags.get(ability);
                    while (tag == null && tekability > 5002) {
                        tekability--;
                        ability.setId(tekability);
                        tag = tags.get(ability);
                    }
                    if (tag == null) {
                        continue;
                    }
                    TextView upgraded = (TextView) row.findViewWithTag(tag);
                    // System.out.println("dota2 info on ability:" + abilityUpgrade.getAbility() + " on level:" + abilityUpgrade.getLevel());
                    upgraded.setText(String.valueOf(abilityUpgrade.getLevel()));
                    upgraded.setBackgroundDrawable(getResources().getDrawable(R.drawable.attribute_selected_bkg));
                    abilityHolder.addView(row);
                }
            } else {
                abilityHolder.setVisibility(View.GONE);
            }
        }
    }
}
