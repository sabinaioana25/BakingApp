package com.example.android.bakingapp.Tests;

import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingapp.model.Step;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class StepParcelableSimpleTest {

    private static final int STEP_ID = 0;
    private static final String INTRODUCTION = "Introduction";
    private static final String ADD_WATER = "Add water";
    private static final String WWW_YOUTUBE_COM = "www.youtube.com";
    private static final String WWW_GOOGLE_COM = "www.google.com";

    @Test
    public void testCorrectStepParcelableImplementation() {
        Step step = new Step(STEP_ID, INTRODUCTION, ADD_WATER, WWW_YOUTUBE_COM, WWW_GOOGLE_COM);

        Parcel parcel = Parcel.obtain();
        step.writeToParcel(parcel, step.describeContents());
        parcel.setDataPosition(0);

        Step createdFromParcel = Step.CREATOR.createFromParcel(parcel);

        assertThat(createdFromParcel.getStepId(), is(STEP_ID));
        assertThat(createdFromParcel.getShortDescription(), is(INTRODUCTION));
        assertThat(createdFromParcel.getDescription(), is(ADD_WATER));
        assertThat(createdFromParcel.getVideoUrl(), is(WWW_YOUTUBE_COM));
        assertThat(createdFromParcel.getThumbnailUrl(), is(WWW_GOOGLE_COM));
    }
}
