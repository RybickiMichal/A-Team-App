package com.example.teamaapp.task;

import android.os.AsyncTask;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.services.polly.AmazonPollyPresigningClient;
import com.amazonaws.services.polly.model.DescribeVoicesRequest;
import com.amazonaws.services.polly.model.DescribeVoicesResult;
import com.amazonaws.services.polly.model.Voice;

import java.util.ArrayList;

public class VoicesTask extends AsyncTask<CognitoCachingCredentialsProvider, Void, ArrayList<Voice>> {

    @Override
    protected ArrayList<Voice> doInBackground(CognitoCachingCredentialsProvider... cognitoCachingCredentialsProviders) {
        AmazonPollyPresigningClient client = new AmazonPollyPresigningClient(cognitoCachingCredentialsProviders[0]);
        DescribeVoicesRequest describeVoicesRequest = new DescribeVoicesRequest();
        DescribeVoicesResult describeVoicesResult = client.describeVoices(describeVoicesRequest);
        return new ArrayList<>(describeVoicesResult.getVoices());
    }
}
