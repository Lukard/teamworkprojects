package com.rubenabad.teamworkprojects.db.service.utils

import android.os.AsyncTask
import android.support.test.runner.AndroidJUnitRunner
import io.reactivex.Scheduler
import io.reactivex.functions.Function
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers


/**
 * This class will inject the AsyncTask.THREAD_POOL_EXECUTOR to the RxJava schedulers so Espresso will automatically
 * wait for rx calls
 */
class EspressoRxJUnitRunner : AndroidJUnitRunner() {

    override fun onStart() {
        val asyncTaskScheduler = Function<Scheduler, Scheduler> { Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR) }

        RxJavaPlugins.setIoSchedulerHandler(asyncTaskScheduler)
        RxJavaPlugins.setComputationSchedulerHandler(asyncTaskScheduler)
        RxJavaPlugins.setNewThreadSchedulerHandler(asyncTaskScheduler)

        super.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        RxJavaPlugins.reset()
    }
}