package com.example.teamaapp.polly;

import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.services.polly.AmazonPollyPresigningClient;
import com.amazonaws.services.polly.model.DescribeVoicesRequest;
import com.amazonaws.services.polly.model.DescribeVoicesResult;
import com.amazonaws.services.polly.model.Voice;
import com.example.teamaapp.polly.Language;

import java.util.ArrayList;
import java.util.List;

public class VoicesTask extends AsyncTask<CognitoCachingCredentialsProvider, Void, ArrayList<Voice>> {

    @Override
    protected ArrayList<Voice> doInBackground(CognitoCachingCredentialsProvider... cognitoCachingCredentialsProviders) {
        AmazonPollyPresigningClient client = new AmazonPollyPresigningClient(cognitoCachingCredentialsProviders[0]);
        DescribeVoicesRequest describeVoicesRequest = new DescribeVoicesRequest();
        DescribeVoicesResult describeVoicesResult = client.describeVoices(describeVoicesRequest);
        return new ArrayList<>(describeVoicesResult.getVoices());
    }

    public static Voice chooseVoice(Language language, String TAG, List<Voice> voices) {
        for (Voice voice : voices) {
            Log.i(TAG, "voice name " + voice.getName());
            Log.i(TAG, "country code " + language.countryCode);
            switch (language) {
                case PL:
                    if (voice.getName().equals("Ewa"))
                        return voice;
                    break;
                case EN:
                    if (voice.getName().equals("Joanna"))
                        return voice;
                    break;
                case FR:
                    if (voice.getName().equals("Mathieu"))
                        return voice;
                    break;
                case DE:
                    if (voice.getName().equals("Vicki"))
                        return voice;
                    break;
                case ES:
                    if (voice.getName().equals("Lucia"))
                        return voice;
                    break;
                case RU:
                    if (voice.getName().equals("Tatyana"))
                        return voice;
                    break;
                case TR:
                    if (voice.getName().equals("Filiz"))
                        return voice;
                    break;
                case ZH:
                    if (voice.getName().equals("Zhiyu"))
                        return voice;
                    break;
                case JA:
                    if (voice.getName().equals("Mizuki"))
                        return voice;
                    break;
            }
            Log.i(TAG, "read text - chosen speaker: " + voice.getName());
        }
        throw new IllegalArgumentException("Voice doesn't exist");
    }
}
