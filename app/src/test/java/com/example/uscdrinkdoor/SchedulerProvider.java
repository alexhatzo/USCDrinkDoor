package com.example.uscdrinkdoor;

import com.google.android.datatransport.runtime.scheduling.Scheduler;

public interface SchedulerProvider {
    Scheduler ui();
    Scheduler computation();
    Scheduler io();
    Scheduler special();
    // Other schedulers as required…
}