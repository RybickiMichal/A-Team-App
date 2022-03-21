package com.example.teamaapp.credentials;

import com.amazonaws.auth.AWSCredentials;

public class AmazonCredentials implements AWSCredentials {
    @Override
    public String getAWSAccessKeyId() {
        return "tu wpisz access key";
    }

    @Override
    public String getAWSSecretKey() {
        return "tu wpisz secret key";
    }
}