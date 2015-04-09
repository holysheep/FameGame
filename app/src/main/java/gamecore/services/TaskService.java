package gamecore.services;

import java.util.ArrayList;

import gamecore.Logging.L;
import gamecore.callbacks.PCgamesLoadedListener;
import gamecore.pojo.GameCat;
import gamecore.task.TaskLoadGamesPC;
import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;


public class TaskService extends JobService implements PCgamesLoadedListener {
    private JobParameters jobParameters;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        L.t(this, "onStartJob");
        this.jobParameters = jobParameters;
        new TaskLoadGamesPC(this).execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        L.t(this, "onStopJob");
        return false;
    }

    public void onPCgamesLoaded(ArrayList<GameCat> listGames) {
        L.t(this, "onPCgamesLoaded");
        jobFinished(jobParameters, false);
    }
}


