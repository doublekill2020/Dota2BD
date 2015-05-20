package com.badr.infodota.remote.joindota;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.badr.infodota.api.joindota.LiveStream;
import com.badr.infodota.api.joindota.MatchItem;
import com.badr.infodota.api.joindota.SubmatchItem;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * User: ABadretdinov
 * Date: 22.04.14
 * Time: 14:34
 */
public class JoinDotaRemoteServiceImpl implements JoinDotaRemoteService {
    private final static SimpleDateFormat COMPLETE_DATE_FORMAT = new SimpleDateFormat("EEE',' dd MMM yyyy HH:mm:ss Z ", Locale.ENGLISH);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy", Locale.ENGLISH);
    private static SimpleDateFormat CESTDateTimeFormat = new SimpleDateFormat("dd.MM.yyyy, HH:mm", Locale.US);

    static {
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+2"));
        CESTDateTimeFormat.setTimeZone(TimeZone.getTimeZone("GMT+2"));
    }

    @Override
    public MatchItem.List getMatchItems(Context context, int page, String extraParams) throws Exception {
        StringBuilder url = new StringBuilder("http://www.joindota.com/en/matches");
        url.append("/").append(extraParams != null ? extraParams : "");
        if (page > 0) {
            url.append("&archiv_page=").append(page);
        }
        MatchItem.List matchItems = new MatchItem.List();
        Elements matchElements = Jsoup.connect(url.toString()).get().select("div.item");
        for (Element matchElement : matchElements) {
            MatchItem matchItem = new MatchItem();
            Elements aElements = matchElement.select("a");
            Iterator<Element> aIterator = aElements.iterator();
            while (TextUtils.isEmpty(matchItem.getLink()) && aIterator.hasNext()) {
                Element aElement = aIterator.next();
                String link = aElement.attr("href");
                if (!TextUtils.isEmpty(link) && link.contains("matches")) {
                    matchItem.setLink(link);
                }
            }
            Elements localElements = matchElement.select(".sub");//todo искать в "a"

            int size = localElements.size();
            for (int i = 0; i < size; i++) {
                Element element = localElements.get(i);
                switch (i) {
                    //todo fix
                    case 3:
                        String time = element.attr("title");
                        if (!TextUtils.isEmpty(time)) {
                            time = time.substring(0, time.indexOf('('));
                            matchItem.setDate(COMPLETE_DATE_FORMAT.parse(time));
                            //System.out.println(matchItem.getLink());
                        }
                        matchItem.setMiddleText(element.text());
                        /*if(matchItem.getMiddleText().endsWith("h")){
                            Calendar calendar=Calendar.getInstance();
                            calendar.setTimeZone(TimeZone.getTimeZone("GMT+2"));
                            calendar.setTime(matchItem.getDate());
                            String[] timeParts=matchItem.getMiddleText().split(":");
                            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeParts[0]));
                            calendar.set(Calendar.MINUTE,Integer.parseInt(timeParts[1].substring(0,2)));
                            matchItem.setMiddleText(DateUtils.TIME_FORMAT.format(calendar.getTime()));
                            calendar.set(Calendar.MINUTE,0);
                            matchItem.setDate(calendar.getTime());
                            matchItem.setMatchType(MatchItem.MatchType.FUTURE);
                        }*/
                        break;
                    case 1:
                        matchItem.setTeam1name(element.text());
                        Elements imgs1 = element.select("img");
                        if (imgs1.size() > 0) {
                            matchItem.setTeam1flagLink(imgs1.first().attr("src"));
                        }
                        break;
                    case 2:
                        String name2 = element.text();
                        if (!TextUtils.isEmpty(name2) && name2.contains("vs.")) {
                            name2 = name2.substring(3);
                        }
                        matchItem.setTeam2name(name2);
                        Elements imgs2 = element.select("img");
                        if (imgs2.size() > 0) {
                            matchItem.setTeam2flagLink(imgs2.first().attr("src"));
                        }
                        break;
                }
            }
            matchItem.setStreams(new ArrayList<LiveStream>());
            Elements liveStreamsElements = matchElement.select(".live");
            if (liveStreamsElements.size() > 0) {
                liveStreamsElements = liveStreamsElements.select(".stream");
                for (Element streamElement : liveStreamsElements) {
                    LiveStream stream = new LiveStream();
                    stream.setLanguage(streamElement.select(".lang").first().text());
                    Element streamUrlElement = streamElement.select("a[class=caster]").first();
                    stream.setName(streamUrlElement.ownText());
                    stream.setUrl(streamUrlElement.attr("href"));
                    Elements viewersElement = streamElement.select(".viewer");
                    if (viewersElement.size() > 0) {
                        stream.setViewers(viewersElement.first().text());
                    } else {
                        Elements statusElement = streamElement.select(".offline");
                        if (statusElement.size() > 0) {
                            stream.setStatus(statusElement.first().text());
                        }
                    }
                    matchItem.getStreams().add(stream);
                }
            }
            matchItems.add(matchItem);
        }
        return matchItems;
    }

    @Override
    public MatchItem updateMatchItem(MatchItem item) throws Exception {
        MatchItem resultItem = item.clone();
        Document document = Jsoup.connect(resultItem.getLink()).get();
        Element localElement1 = document.select("div.pad").get(1);
        Elements teamLinks = localElement1.select(".match_names a");
        resultItem.setTeam1detailsLink(teamLinks.first().attr("href"));
        resultItem.setTeam2detailsLink(teamLinks.last().attr("href"));
        resultItem.setTitle(localElement1.select(".match_head .left").first().text());

        String detailedDate = localElement1.select(".match_head .right").first().text();
        if (!"TBA".equals(detailedDate)) {
            Date cestDate = CESTDateTimeFormat.parse(detailedDate);
            resultItem.setCestDate(cestDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(TimeZone.getTimeZone("GMT+2"));
            calendar.setTime(cestDate);
            resultItem.setDetailedDate(calendar.getTime());
        }
        Elements teamLogoElements = localElement1.select(".match_logos img");
        resultItem.setTeam1logoLink(teamLogoElements.first().attr("src"));
        resultItem.setTeam2logoLink(teamLogoElements.last().attr("src"));
        Elements submatchElements = document.select(".submatch");
        resultItem.setSubmatches(new ArrayList<SubmatchItem>());
        for (Element submatchElement : submatchElements) {
            SubmatchItem submatchItem = new SubmatchItem();
            submatchItem.setScore(submatchElement.select(".score").text());
            Elements bans1Elements = submatchElement.select(".ban_left span");
            submatchItem.setTeam1bans(new ArrayList<String>());
            for (Element banElement : bans1Elements) {
                submatchItem.getTeam1bans().add(banElement.attr("title"));
            }
            //heroName= attr("title")
            Elements bans2Elements = submatchElement.select(".ban_right span");
            submatchItem.setTeam2bans(new ArrayList<String>());
            for (Element banElement : bans2Elements) {
                submatchItem.getTeam2bans().add(banElement.attr("title"));
            }
            Elements picks1Elements = submatchElement.select(".pick_left img");
            submatchItem.setTeam1picks(new ArrayList<String>());
            for (Element pickElement : picks1Elements) {
                submatchItem.getTeam1picks().add(pickElement.attr("title"));
            }
            //heroName= attr("title")
            Elements picks2Elements = submatchElement.select(".pick_right img");
            submatchItem.setTeam2picks(new ArrayList<String>());
            for (Element pickElement : picks2Elements) {
                submatchItem.getTeam2picks().add(pickElement.attr("title"));
            }
            Elements players1Elements = submatchElement.select(".pick_left a");
            submatchItem.setTeam1playerNames(new ArrayList<String>());
            for (Element playerElement : players1Elements) {
                submatchItem.getTeam1playerNames().add(playerElement.text());
            }
            // attr("href") - ссылка, text() - имя
            Elements players2Elements = submatchElement.select(".pick_right a");
            submatchItem.setTeam2playerNames(new ArrayList<String>());
            for (Element playerElement : players2Elements) {
                submatchItem.getTeam2playerNames().add(playerElement.text());
            }
            resultItem.getSubmatches().add(submatchItem);
        }
        return resultItem;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void getChannelsNames(List<LiveStream> streams) throws Exception {
        for (LiveStream stream : streams) {
            try {
                Document document = Jsoup.connect(stream.getUrl()).get();
                Elements pad = document.select(".pad");
                if (pad != null && pad.size() > 0) {
                    Element streamElement = pad.first();
                    String urlParams = streamElement.select("param[name=flashvars]").first().attr("value");
                    List<NameValuePair> parameters = URLEncodedUtils.parse(new URI("http://twitch.tv/page?" + urlParams), "UTF-8");
                    boolean found = false;
                    for (int i = 0; !found && i < parameters.size(); i++) {
                        NameValuePair p = parameters.get(i);
                        if ("channel".equals(p.getName())) {
                            stream.setChannelName(p.getValue());
                            found = true;
                        }
                    }
                }
            } catch (Exception e) {
                //ignore
                Log.e(JoinDotaRemoteServiceImpl.class.getName(), e.getMessage(), e);
            }
        }
    }
}
