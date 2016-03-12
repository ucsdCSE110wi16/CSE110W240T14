package edu.etduongucsd.dopeshit;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;

/**
 * Created by Collin on 3/10/16.
 */
public class ProgressBarAnimation extends Animation {
    private ProgressBar progress_bar;
    private int start;
    private int end;
    private long stepDur;


    public ProgressBarAnimation(ProgressBar pbar, long totalDur) {
        super();
        progress_bar = pbar;
        stepDur = totalDur / pbar.getMax();
    }


    public void setProgress(int progress) {
        if (progress < 0) {
            progress = 0;
        }

        if (progress > progress_bar.getMax()) {
            progress = progress_bar.getMax();
        }

        end = progress;

        start = progress_bar.getProgress();
        setDuration(Math.abs(end - start) * stepDur);
        progress_bar.startAnimation(this);
    }

    @Override
    protected void applyTransformation(float iTime, Transformation t) {
        float progressVal = start + (end - start) * iTime;
        progress_bar.setProgress((int) progressVal);
    }
}