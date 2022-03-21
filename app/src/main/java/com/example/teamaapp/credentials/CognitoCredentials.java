package com.example.teamaapp.credentials;

import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;

public class CognitoCredentials {

    public CognitoCachingCredentialsProvider getCognitoCredentialsProvider(Context context) {
        return new CognitoCachingCredentialsProvider(
                context,
                "tu wpisz identity pool id",
                Regions.EU_CENTRAL_1);
    }
}