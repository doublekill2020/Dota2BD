package com.badr.infodota.remote.counterpicker;

import android.content.Context;
import android.util.Pair;

import com.badr.infodota.api.Constants;
import com.badr.infodota.api.truepicker.Counter;
import com.badr.infodota.api.truepicker.TruepickerResponse;
import com.badr.infodota.remote.BaseRemoteServiceImpl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ABadretdinov
 * Date: 02.04.14
 * Time: 14:46
 */
public class CounterRemoteEntityServiceImpl extends BaseRemoteServiceImpl implements CounterRemoteEntityService {
    @Override
    public Pair<List<Counter>, String> getCounters(Context context, List<Integer> allies, List<Integer> enemies,
                                                   int roleCode) throws Exception {
        //&CaptainV4[tm_a_h1_id]=&CaptainV4[tm_a_h1_rl]=1&CaptainV4[tm_a_h2_id]=&CaptainV4[tm_a_h2_rl]=&CaptainV4[tm_a_h3_id]=&CaptainV4[tm_a_h3_rl]=&CaptainV4[tm_a_h4_id]=&CaptainV4[tm_a_h4_rl]=&CaptainV4[tm_a_h5_id]=&CaptainV4[tm_a_h5_rl]=&hero_name=&CaptainV4[tm_b_h1_id]=44&CaptainV4[tm_b_h2_id]=33&CaptainV4[tm_b_h2_rl]=10&CaptainV4[tm_b_h3_id]=55&CaptainV4[tm_b_h3_rl]=3&CaptainV4[tm_b_h5_id]=&CaptainV4[tm_b_h5_rl]=
        StringBuilder url = new StringBuilder(Constants.TruePicker.SUBURL);
        //url.append("&allies=");
        int alliesSize = allies.size();
        url.append("&CaptainV4[tm_a_h1_id]=");
        url.append("&CaptainV4[tm_a_h1_rl]=");
        //url.append(roleCode);
        int i;
        for (i = 1; i <= alliesSize; i++) {
            url.append("&CaptainV4[tm_a_h");
            url.append(i + 1);
            /*if(i!=alliesSize-1)
			{
				url.append("%7C");
			}*/
            url.append("_id]=");
            url.append(allies.get(i - 1));
            url.append("&CaptainV4[tm_a_h");
            url.append(i + 1);
            url.append("_rl]=");
        }
        while (i < 5) {
            url.append("&CaptainV4[tm_a_h");
            url.append(i + 1);
            url.append("_id]=");
            url.append("&CaptainV4[tm_a_h");
            url.append(i + 1);
            url.append("_rl]=");
            i++;
        }

        url.append("&hero_name=");
		/*url.append("&enemies=");*/
        int enemiesSize = enemies.size();
        for (i = 0; i < enemiesSize; i++) {
            url.append("&CaptainV4[tm_b_h");
            url.append(i + 1);
			/*if(i!=alliesSize-1)
			{
				url.append("%7C");
			}*/
            url.append("_id]=");
            url.append(enemies.get(i));
            url.append("&CaptainV4[tm_b_h");
            url.append(i + 1);
            url.append("_rl]=");
        }
        while (i < 5) {
            url.append("&CaptainV4[tm_b_h");
            url.append(i + 1);
            url.append("_id]=");
            url.append("&CaptainV4[tm_b_h");
            url.append(i + 1);
            url.append("_rl]=");
            i++;
        }
        //url.append("&info=0&count=5&dota2=1&static=1&gtabflat=1");
        //url.append("&roles=");
		/*int rolesSize=roleCodes.size();
		for(int i=0;i<rolesSize;i++)
		{
			url.append(roleCodes.get(i));
			if(i!=rolesSize-1)
			{
				url.append("%7C");
			}
		}*/
        //url.append(level);
        Pair<TruepickerResponse, String> response = basicRequestSend(context, url.toString(), TruepickerResponse.class);
        List<Counter> counters = new ArrayList<Counter>();
        if (response.first != null) {
            String firstTeam = response.first.getRecommendsforteama();
            Document doc = Jsoup.parse(firstTeam);
            Elements elements = doc.select("a[class=advice-hero-pick]");
            if (elements != null && elements.size() != 0) {
                for (Element element : elements) {
                    String heroId = element.attr("data-hero-id");
                    Counter counter = new Counter();
                    counter.setHero(heroId);
                    counters.add(counter);
                }
            }
            //a[class=advice-hero-pick].data-hero-id
        }
        return Pair.create(counters, response.second);
    }
}
