package com.badr.infodota.service.hero;

import android.content.Context;

import com.badr.infodota.InitializingBean;
import com.badr.infodota.api.abilities.Ability;
import com.badr.infodota.api.heroes.CarouselHero;
import com.badr.infodota.api.heroes.Hero;
import com.badr.infodota.api.heroes.TruepickerHero;

import java.util.List;

/**
 * Created by ABadretdinov
 * 25.12.2014
 * 14:35
 */
public interface HeroService extends InitializingBean {

    List<Hero> getAllHeroes(Context context);

    Hero.List getFilteredHeroes(Context context, String filter);

    List<Hero> getHeroesByName(Context context, String name);

    List<CarouselHero> getCarouselHeroes(Context context, String filter);

    CarouselHero.List getCarouselHeroes(Context context, String filter, String name);

    TruepickerHero.List getTruepickerHeroes(Context context, String filter);

    Hero getHeroById(Context context, long id);

    TruepickerHero getTruepickerHero(Context context, long tpId);

    TruepickerHero getTruepickerHeroById(Context context, long id);

    Hero getHeroWithStatsById(Context context, long id);

    void saveHero(Context context, Hero hero);

    List<Ability> getHeroAbilities(Context context, long heroId);

    List<Ability> getNotThisHeroAbilities(Context context, long heroId);

    List<Ability> getAbilitiesByList(Context context, List<Long> inGameList);

    String getAbilityPath(Context context, long id);

    void saveAbility(Context context, Ability ability);

}
