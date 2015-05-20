package com.badr.infodota.remote.player;

import com.badr.infodota.api.dotabuff.Unit;

import java.util.List;

/**
 * User: ABadretdinov
 * Date: 02.04.14
 * Time: 15:49
 */
public interface PlayerRemoteService{

    Unit.List getAccounts(List<Long> ids) throws Exception;

    Unit.List getAccounts(String name) throws Exception;

    Unit.List getFriends(long id) throws Exception;
}
