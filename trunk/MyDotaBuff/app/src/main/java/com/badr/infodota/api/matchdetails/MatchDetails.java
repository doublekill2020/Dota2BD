package com.badr.infodota.api.matchdetails;

/**
 * User: ABadretdinov
 * Date: 28.08.13
 * Time: 15:15
 */
public class MatchDetails {
    private Result result;

    public MatchDetails(Result result) {
        this.result = result;
    }

    public MatchDetails() {
        super();
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
