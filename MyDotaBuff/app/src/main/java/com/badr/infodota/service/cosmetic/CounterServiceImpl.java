package com.badr.infodota.service.cosmetic;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.InitializingBean;
import com.badr.infodota.api.heroes.TruepickerHero;
import com.badr.infodota.api.truepicker.Counter;
import com.badr.infodota.remote.counterpicker.CounterRemoteEntityService;
import com.badr.infodota.service.hero.HeroService;

import java.util.List;

import cn.edu.mydotabuff.R;

/**
 * User: ABadretdinov
 * Date: 02.04.14
 * Time: 14:54
 */
public class CounterServiceImpl implements CounterService, InitializingBean {
    private CounterRemoteEntityService service;
    private HeroService heroService;

    @Override
    public TruepickerHero.List getCounters(Context context, List<Integer> allies, List<Integer> enemies,
                                                int roleCodes) {
        try {
            Pair<List<Counter>, String> serviceResult = service.getCounters(context, allies, enemies, roleCodes);
            if (serviceResult.first == null) {
                String message;
                if (serviceResult.second.contains("\"controller\":\"pick\"")) {
                    message = context.getString(R.string.empty_truepicker);
                } else {
                    message = serviceResult.second;
                }
                Log.e(CounterServiceImpl.class.getName(), message);
                return null;
            } else {
                TruepickerHero.List heroes = new TruepickerHero.List();
                for (Counter counter : serviceResult.first) {
                    TruepickerHero hero = heroService.getTruepickerHero(context, Integer.valueOf(counter.getHero()));
                    if (hero != null) {
                        heroes.add(hero);
                    }
                }
                return heroes;
            }
        } catch (Exception e) {
            String message = "Failed to get counters, cause: " + e.getMessage();
            Log.e(CounterServiceImpl.class.getName(), message, e);
            return null;
        }
    }

    @Override
    public void initialize() {
        BeanContainer container = BeanContainer.getInstance();
        service = container.getCounterRemoteEntityService();
        heroService = container.getHeroService();
    }
}
