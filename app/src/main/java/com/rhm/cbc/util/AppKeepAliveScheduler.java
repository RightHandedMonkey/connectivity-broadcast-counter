package com.rhm.cbc.util;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.support.annotation.WorkerThread;
import android.util.Log;

/**
 * Created by sambo on 11/17/17.
 */

public class AppKeepAliveScheduler extends JobService {
        public static long DELAY_IN_SEC = 60*60*24;

        @Override
        public boolean onStartJob(JobParameters jobParameters) {
            scheduleJob(this, (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE), DELAY_IN_SEC);
            return true;
        }

        @WorkerThread
        public static void scheduleJob(Context context, JobScheduler js, Long delayInSec) {
            js.cancelAll();
            ComponentName mServiceComponent = new ComponentName(context, AppKeepAliveScheduler.class);
            JobInfo.Builder builder = new JobInfo.Builder(0, mServiceComponent);
            long multiplier = 1000; //sec to milli
            long minStart = delayInSec - delayInSec / 10;
            long maxStart = delayInSec + delayInSec / 10;
            long minLatency = Math.max(1000, minStart * multiplier);
            long deadline = Math.max(15000, maxStart * multiplier);
            builder.setMinimumLatency(minLatency); // wait - at least 1 second
            builder.setOverrideDeadline(deadline); // maximum delay - at least 15 sec
            builder.setRequiresCharging(false); // we don't care if the device is charging or no
            builder.setRequiresDeviceIdle(false);

            js.schedule(builder.build());

            Log.d("SAMB", AppKeepAliveScheduler.class.getName() + " - Job queued to start in " + delayInSec + " seconds");
        }

        @Override
        public void onCreate() {
            Log.d("SAMB", this.getClass().getName() + " onCreate() called");
            super.onCreate();
        }

        @Override
        public void onDestroy() {
            Log.d("SAMB", this.getClass().getName() + " onDestroy() called");
            super.onDestroy();
        }

        @Override
        public boolean onStopJob(JobParameters jobParameters) {
            Log.d("SAMB", this.getClass().getName() + " onStopJob() called");
            return true;
        }
    }
