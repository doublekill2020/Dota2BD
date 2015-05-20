package com.badr.infodota.service.player;

import android.content.Context;

import com.badr.infodota.InitializingBean;
import com.badr.infodota.api.dotabuff.Unit;

import java.util.List;

/**
 * User: ABadretdinov
 * Date: 02.04.14
 * Time: 16:01
 */
public interface PlayerService extends InitializingBean {
    Unit.List loadAccounts(List<Long> ids);

    Unit.List loadAccounts(String name);

    Unit.List loadFriends(long id);

    void saveAccount(Context context, Unit unit);

    Unit getAccountById(Context context, long id);

    void deleteAccount(Context context, Unit unit);

    Unit.List getSearchedAccounts(Context context);

    Unit.List getAccountsByGroup(Context context, Unit.Groups group);
}
