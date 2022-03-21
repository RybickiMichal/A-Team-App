package com.example.teamaapp;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.amazonaws.handlers.AsyncHandler;
import com.amazonaws.services.polly.AmazonPollyPresigningClient;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SynthesizeSpeechPresignRequest;
import com.amazonaws.services.polly.model.Voice;
import com.amazonaws.services.translate.AmazonTranslateAsyncClient;
import com.amazonaws.services.translate.model.TranslateTextRequest;
import com.amazonaws.services.translate.model.TranslateTextResult;
import com.example.teamaapp.credentials.AmazonCredentials;
import com.example.teamaapp.credentials.CognitoCredentials;
import com.example.teamaapp.polly.Language;
import com.example.teamaapp.polly.VoicesTask;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends Activity {
    private static final String TAG = "Team A App";

    private AmazonCredentials awsCredentials = new AmazonCredentials();
    private CognitoCredentials cognitoCredentials = new CognitoCredentials();
    private List<Voice> voices;
    private EditText textEditText;
    private EditText countryCodeEditText;
    private String countryCode = "en";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");

        textEditText = findViewById(R.id.text);
        countryCodeEditText = findViewById(R.id.countryCodeText);

        VoicesTask voicesTask = new VoicesTask();
        try {
            voices = voicesTask.execute(cognitoCredentials.getCognitoCredentialsProvider(getApplicationContext())).get();
        } catch (ExecutionException | InterruptedException error) {
            Log.e(TAG, "Error during execute voiceTask" + error);
        }
    }

    public void translateText(View view) {
        AmazonTranslateAsyncClient translateAsyncClient = new AmazonTranslateAsyncClient(awsCredentials);
        TranslateTextRequest translateTextRequest = new TranslateTextRequest()
                .withText(textEditText.getText().toString())
                .withSourceLanguageCode(countryCode)
                .withTargetLanguageCode(countryCodeEditText.getText().toString().toLowerCase());
        translateAsyncClient.translateTextAsync(translateTextRequest, new AsyncHandler<TranslateTextRequest, TranslateTextResult>() {
            @Override
            public void onError(Exception e) {
                Log.e(TAG, "Error occurred in translating the text: " + e.getLocalizedMessage());
                textEditText.setText("Error occurred in translating the text: " + e.getLocalizedMessage());
            }

            @Override
            public void onSuccess(TranslateTextRequest request, TranslateTextResult translateTextResult) {
                Log.d(TAG, "Original Text: " + request.getText());
                Log.d(TAG, "Translated Text: " + translateTextResult.getTranslatedText());
                textEditText.setText(translateTextResult.getTranslatedText());
                countryCode = countryCodeEditText.getText().toString().toLowerCase();
            }
        });
        view.postInvalidate();
    }

    public void readText(View view) {
        Log.i(TAG, "read text - actual country code: " + countryCode);
        Language language = Language.valueOf(countryCode.toUpperCase());
        Voice voice = VoicesTask.chooseVoice(language, TAG, voices);
        URL presignedSynthesizeSpeechUrl = createSynthesizeSpeechPresignRequest(textEditText.getText().toString(), voice);
        playSpeech(presignedSynthesizeSpeechUrl);
    }

    private URL createSynthesizeSpeechPresignRequest(String text, Voice voice) {
        SynthesizeSpeechPresignRequest synthesizeSpeechPresignRequest = new SynthesizeSpeechPresignRequest()
                .withText(text)
                .withVoiceId(voice.getId())
                .withOutputFormat(OutputFormat.Mp3);
        return new AmazonPollyPresigningClient(cognitoCredentials.getCognitoCredentialsProvider(getApplicationContext()))
                .getPresignedSynthesizeSpeechUrl(synthesizeSpeechPresignRequest);
    }

    private void playSpeech(URL presignedSynthesizeSpeechUrl) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.setDataSource(presignedSynthesizeSpeechUrl.toString());
        } catch (IOException e) {
            Log.e(TAG, "Unable to set data source for the media player! " + e.getMessage());
        }
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(mp -> mp.start());
        mediaPlayer.setOnCompletionListener(mp -> mp.release());
    }
}
