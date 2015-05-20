package com.badr.infodota.api.cosmetics.store;

import java.util.List;

/**
 * User: ABadretdinov
 * Date: 31.03.14
 * Time: 17:58
 */
public class Result {
    private int status;
    private String items_game_url;
    /*private Map<String,Integer> qualities;
    private List<Origin> originNames;*/
    private List<CosmeticItem> items;
    private List<ItemSet> item_sets;

    public Result() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getItems_game_url() {
        return items_game_url;
    }

    public void setItems_game_url(String items_game_url) {
        this.items_game_url = items_game_url;
    }

	/*public Map<String, Integer> getQualities()
    {
		return qualities;
	}

	public void setQualities(Map<String, Integer> qualities)
	{
		this.qualities = qualities;
	}

	public List<Origin> getOriginNames()
	{
		return originNames;
	}

	public void setOriginNames(List<Origin> originNames)
	{
		this.originNames = originNames;
	}*/

    public List<CosmeticItem> getItems() {
        return items;
    }

    public void setItems(List<CosmeticItem> items) {
        this.items = items;
    }

    public List<ItemSet> getItem_sets() {
        return item_sets;
    }

    public void setItem_sets(List<ItemSet> item_sets) {
        this.item_sets = item_sets;
    }
}
