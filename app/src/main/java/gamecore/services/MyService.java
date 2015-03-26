package gamecore.services;

import gamecore.Logging.L;
import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;


public class MyService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
