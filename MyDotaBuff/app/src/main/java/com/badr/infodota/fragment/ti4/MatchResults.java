package com.badr.infodota.fragment.ti4;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.service.match.MatchService;
import com.badr.infodota.service.team.TeamService;
/*import com.handmark.pulltorefresh.library.PinnedSectionListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshPinnedSectionListView;*/

/**
 * todo need to replace pullToRefreshPinnedSection like in Tournament games
 * User: ABadretdinov
 * Date: 15.05.14
 * Time: 15:58
 */

public class MatchResults extends Fragment {
    public static final int MATCH_DETAILS = 123;
    private Long leagueId;
    //private LeagueMatchResultsAdapter adapter;
    /*private PullToRefreshPinnedSectionListView listView;*/
    private long total = 1;
    private BeanContainer container = BeanContainer.getInstance();
    private MatchService matchService = container.getMatchService();
    private TeamService teamService = container.getTeamService();
    private AsyncTask updaterTask = null;

    public static MatchResults newInstance(Long leagueId) {
        MatchResults fragment = new MatchResults();
        fragment.leagueId = leagueId;
        return fragment;
    }
/*

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        MenuItem refresh = menu.add(1, 1001, 1, R.string.refresh);
        refresh.setIcon(R.drawable.ic_menu_refresh);
        MenuItemCompat.setShowAsAction(refresh, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 1001) {
            adapter = null;
            loadHistory(0);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.league_match_results, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        listView = (PullToRefreshPinnedSectionListView) getView().findViewById(R.id.pullToRefreshList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Match match = adapter.getItem(position);
                Intent intent = new Intent(getActivity(), MatchInfoActivity.class);
                if (!(match instanceof Result)) {
                    intent.putExtra("matchId", String.valueOf(match.getMatch_id()));
                    startActivity(intent);
                } else {
                    if (!((Result) match).isSection()) {
                        intent.putExtra("match", match);
                        startActivity(intent);
                    }
                }
            }
        });
        loadHistory(0);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<PinnedSectionListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<PinnedSectionListView> refreshView) {
                long lastMatchId = 0;
                if (adapter.getCount() > 0) {
                    Match last = adapter.getItem(adapter.getCount() - 1);
                    lastMatchId = last.getMatch_id() - 1;
                }
                loadHistory(lastMatchId);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Match match = adapter.getItem(position - 1);
                Intent intent = new Intent(getActivity(), MatchInfoActivity.class);
                if (!(match instanceof Result)) {
                    if (updaterTask != null) {
                        updaterTask.cancel(true);
                        updaterTask = null;
                    }
                    intent.putExtra("matchId", String.valueOf(match.getMatch_id()));
                    startActivityForResult(intent, MATCH_DETAILS);
                } else {
                    if (!((Result) match).isSection()) {
                        if (updaterTask != null) {
                            updaterTask.cancel(true);
                            updaterTask = null;
                        }
                        intent.putExtra("match", match);
                        startActivityForResult(intent, MATCH_DETAILS);
                    }
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MATCH_DETAILS) {
            updateResults();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private synchronized void loadHistory(final long fromMatchId) {
        final BaseActivity activity = (BaseActivity) getActivity();
        activity.setSupportProgressBarIndeterminateVisibility(true);
        if (adapter == null) {
            adapter = new LeagueMatchResultsAdapter(activity, new ArrayList<Match>());
            listView.setAdapter(adapter);
            total = 1;
        }
        if (total > adapter.getCount()) {
            new LoaderProgressTask<Pair<ResultResponse, String>>(new ProgressTask<Pair<ResultResponse, String>>() {
                @Override
                public Pair<ResultResponse, String> doTask(OnPublishProgressListener listener) {
                    return matchService.getMatches(activity, fromMatchId, Constants.History.LEAGUE_ID + String.valueOf(leagueId));
                }

                @Override
                public void doAfterTask(Pair<ResultResponse, String> resultResponse) {
                    if (resultResponse.first != null) {
                        com.badr.infodota.api.matchhistory.Result result = resultResponse.first.getResult();
                        total = result.getTotal_results();
                        adapter.addMatches(result.getMatches());
                        if (result.getStatus() == 15 || !TextUtils.isEmpty(result.getStatusDetail())) {
                            Toast.makeText(activity, getString(R.string.match_history_closed),
                                    Toast.LENGTH_LONG).show();
                        }
                        updateResults();
                    } else if (!TextUtils.isEmpty(resultResponse.second)) {
                        Toast.makeText(activity, resultResponse.second, Toast.LENGTH_LONG).show();

                    }
                    listView.onRefreshComplete();
                    activity.setSupportProgressBarIndeterminateVisibility(false);
                }

                @Override
                public void handleError(String error) {
                    Toast.makeText(activity, error, Toast.LENGTH_LONG).show();
                    listView.onRefreshComplete();
                    activity.setSupportProgressBarIndeterminateVisibility(false);
                }

                @Override
                public String getName() {
                    return null;
                }
            }, null).execute();
        } else {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    activity.setSupportProgressBarIndeterminateVisibility(false);
                    listView.onRefreshComplete();
                }
            }, 1000);
        }
    }

    private void updateResults() {
        if (updaterTask != null) {
            updaterTask.cancel(true);
        }
        if (adapter != null) {
            List<Match> matches = adapter.getMatches();
            updaterTask = new MatchDetailsLoader().execute(matches.toArray(new Match[matches.size()]));
        }
    }

    @Override
    public void onDestroy() {
        ((AppCompatActivity) getActivity()).setSupportProgressBarIndeterminateVisibility(false);
        if (updaterTask != null) {
            updaterTask.cancel(true);
            updaterTask = null;
        }
        super.onDestroy();
    }

    private class MatchDetailsLoader extends AsyncTask<Match, Object, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            adapter.setCancel(false);
        }

        @Override
        protected String doInBackground(Match... params) {
            Activity activity = getActivity();
            if (activity != null) {
                for (Match match : params) {
                    if (isCancelled()) {
                        return "";
                    }
                    if (!(match instanceof Result)) {
                        try {
                            Pair<MatchDetails, String> matchResult = matchService.getMatchDetails(activity, String.valueOf(match.getMatch_id()));
                            if (matchResult.first != null) {
                                Result result = matchResult.first.getResult();
                                Team radiant = teamService.getTeamById(activity, result.getRadiant_team_id());
                                if (radiant == null) {
                                    Pair<String, String> radiantTeamLogo = teamService.getTeamLogo(activity,
                                            result.getRadiant_logo());
                                    if (radiantTeamLogo.first != null) {
                                        Team team = new Team();
                                        team.setId(result.getRadiant_team_id());
                                        team.setTeamLogoId(result.getRadiant_logo());
                                        team.setLogo(radiantTeamLogo.first);
                                        teamService.saveTeam(activity, team);
                                    }
                                }
                                Team dire = teamService.getTeamById(activity, result.getDire_team_id());
                                if (dire == null) {
                                    Pair<String, String> direTeamLogo = teamService.getTeamLogo(activity,
                                            result.getDire_logo());
                                    if (direTeamLogo.first != null) {
                                        Team team = new Team();
                                        team.setId(result.getDire_team_id());
                                        team.setTeamLogoId(result.getDire_logo());
                                        team.setLogo(direTeamLogo.first);
                                        teamService.saveTeam(activity, team);
                                    }
                                }
                                publishProgress(match, result);
                            }
                        } catch (Exception e) {
                            Log.e(MatchResults.class.getName(), e.getMessage(), e);
                        }
                    }
                }
            }
            return "";
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            if (values.length == 2) {
                Match match = (Match) values[0];
                Result matchResult = (Result) values[1];
                if (matchResult != null) {
                    adapter.setResultMatch(match, matchResult);
                }
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            adapter.setCancel(true);
        }
    }
*/

}
