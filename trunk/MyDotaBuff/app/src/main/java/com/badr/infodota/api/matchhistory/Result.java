package com.badr.infodota.api.matchhistory;

import java.util.List;

/**
 * User: ABadretdinov
 * Date: 28.08.13
 * Time: 15:03
 */
public class Result {
    private int status;
    private String statusDetail;
    private long num_results;
    private long total_results;
    private long results_remaining;
    private List<Match> matches;

    public Result(int status, long num_results, long total_results, long results_remaining, List<Match> matches) {
        this.status = status;
        this.num_results = num_results;
        this.total_results = total_results;
        this.results_remaining = results_remaining;
        this.matches = matches;
    }

    public Result() {
        super();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusDetail() {
        return statusDetail;
    }

    public void setStatusDetail(String statusDetail) {
        this.statusDetail = statusDetail;
    }

    public long getNum_results() {
        return num_results;
    }

    public void setNum_results(long num_results) {
        this.num_results = num_results;
    }

    public long getTotal_results() {
        return total_results;
    }

    public void setTotal_results(long total_results) {
        this.total_results = total_results;
    }

    public long getResults_remaining() {
        return results_remaining;
    }

    public void setResults_remaining(long results_remaining) {
        this.results_remaining = results_remaining;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }
}
