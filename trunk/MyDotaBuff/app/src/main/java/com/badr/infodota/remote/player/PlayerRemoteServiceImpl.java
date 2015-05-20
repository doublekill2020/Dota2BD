package com.badr.infodota.remote.player;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.api.Constants;
import com.badr.infodota.api.dotabuff.Unit;
import com.badr.infodota.api.playersummaries.Player;
import com.badr.infodota.api.playersummaries.PlayerResponse;
import com.badr.infodota.api.playersummaries.PlayersResult;
import com.badr.infodota.api.playersummaries.friends.Friend;
import com.badr.infodota.api.playersummaries.friends.FriendsList;
import com.badr.infodota.api.playersummaries.friends.FriendsResult;
import com.badr.infodota.util.TrackUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: ABadretdinov
 * Date: 02.04.14
 * Time: 15:51
 */
public class PlayerRemoteServiceImpl implements PlayerRemoteService {

    @Override
    public Unit.List getAccounts(List<Long> ids) throws Exception {
        StringBuilder steamIds = new StringBuilder("");
        for (int i = 0; i < ids.size(); i++) {
            if (i != 0) {
                steamIds.append(",");
            }
            Long steam64id = TrackUtils.steam32to64(ids.get(i));
            steamIds.append(String.valueOf(steam64id));
        }
        PlayersResult result= BeanContainer.getInstance().getSteamService().getPlayers(steamIds.toString());
        if (result!= null && result.getResponse() != null) {
            PlayerResponse response = result.getResponse();
            List<Player> players = response.getPlayers();
            Unit.List units = new Unit.List();
            if (players != null && players.size() > 0) {
                for (Player player : players) {
                    Unit unit = new Unit();
                    unit.setName(player.getPersonaname());
                    Long steam64id = Long.valueOf(player.getSteamid());
                    unit.setAccountId(TrackUtils.steam64to32(steam64id));
                    unit.setIcon(player.getAvatarfull());
                    units.add(unit);
                }
            }
            return units;
        }
        return null;
    }

    @Override
    public Unit.List getAccounts(String name) throws Exception {
        String url = Constants.DotaBuff.SEARCH_URL + URLEncoder.encode(name, "UTF-8");
        Document document = Jsoup.connect(url).get();    //document.location()
        String location = document.location();
        if (!url.equals(location)) {
            String[] parts = location.split("/");
            Long accountId = Long.valueOf(parts[parts.length - 1]);
            return getAccounts(Arrays.asList(accountId));
        } else {
            Elements elements = document.select("div[class=record player]");
            Unit.List units = new Unit.List();
            for (Element element : elements) {
                Element img = element.select("img").first();
                Unit unit = new Unit();
                unit.setIcon(img.attr("src"));
                Element nameElement = element.select("div[class=name]").first();
                Element dotaBuffUrl = nameElement.select("a").first();
                String accountUrl = dotaBuffUrl.attr("href");
                unit.setUrl(accountUrl);
                unit.setName(dotaBuffUrl.html());
                unit.setType("Player");
                units.add(unit);
            }
            return units;
        }
    }

    @Override
    public Unit.List getFriends(long id) throws Exception {
        long steam64id = TrackUtils.steam32to64(id);
        FriendsResult result= BeanContainer.getInstance().getSteamService().getFriends(String.valueOf(steam64id));
        if (result != null && result.getFriendslist() != null) {
            FriendsList response = result.getFriendslist();
            List<Friend> friends = response.getFriends();
            if (friends != null && friends.size() > 0) {
                List<Long> ids = new ArrayList<Long>();
                for (Friend friend : friends) {
                    ids.add(TrackUtils.steam64to32(Long.valueOf(friend.getSteamid())));
                }
                return getAccounts(ids);
            }
        }
        return null;
    }
}
