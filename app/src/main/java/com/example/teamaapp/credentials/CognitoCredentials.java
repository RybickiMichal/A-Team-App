package com.example.teamaapp.credentials;

import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;

public class CognitoCredentials {

    public CognitoCachingCredentialsProvider getCognitoCredentialsProvider(Context context) {
        return new CognitoCachingCredentialsProvider(
                context,
                "eu-central-1:882537c6-e64f-4df4-b0fc-9510023a5a0c",
                Regions.EU_CENTRAL_1);
    }
}