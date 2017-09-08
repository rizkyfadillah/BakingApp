package com.rizkyfadillah.bakingapp.ui.stepdetail;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.util.Util;
import com.rizkyfadillah.bakingapp.BakingApp;
import com.rizkyfadillah.bakingapp.EventLogger;
import com.rizkyfadillah.bakingapp.R;

import timber.log.Timber;

/**
 * @author rizkyfadillah on 30/07/2017.
 */

public class StepDetailFragment extends Fragment implements ExoPlayer.EventListener,
        PlaybackControlView.VisibilityListener {

    public static final String STEP_DESCRIPTION = "step_description";
    public static final String STEP_VIDEO_URL = "step_video_url";

    private TextView textStepDescription;

    private ProgressBar progressVideo;
    private TextView textLabelVideoNotAvailable;

    private SimpleExoPlayer player;
    private SimpleExoPlayerView playerView;

    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();

    private DataSource.Factory mediaDataSourceFactory;

    private DefaultTrackSelector trackSelector;

    private Handler mainHandler;

    private EventLogger eventLogger;

    private String stepDescription;
    private String stepVideoUrl;

    private OnNavigationClickListener callback;

    public interface OnNavigationClickListener {
        void onClickNext();
        void onClickPrevious();
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        stepDescription = args.getString(STEP_DESCRIPTION);
        stepVideoUrl = args.getString(STEP_VIDEO_URL);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.step_detail_fragment, container, false);

        Log.d("StepDetailFragment", "StepDetailFragment");

        playerView = rootview.findViewById(R.id.player_view);

        progressVideo = rootview.findViewById(R.id.progress_video);
        textLabelVideoNotAvailable = rootview.findViewById(R.id.text_label_video_not_available);

        if (rootview.findViewById(R.id.text_step_description) != null) {
            textStepDescription = rootview.findViewById(R.id.text_step_description);
            textStepDescription.setText(stepDescription);
        }

        Button buttonNext = rootview.findViewById(R.id.button_next);
        Button buttonPrevious = rootview.findViewById(R.id.button_previous);

        if (buttonNext != null) {
            buttonNext.setOnClickListener(view -> {
                callback.onClickNext();
            });
        }
        if (buttonPrevious != null) {
            buttonPrevious.setOnClickListener(view -> {
                callback.onClickPrevious();
            });
        }

        mainHandler = new Handler();
        mediaDataSourceFactory = buildDataSourceFactory(true);

        return rootview;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            progressVideo.setVisibility(View.VISIBLE);
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            progressVideo.setVisibility(View.VISIBLE);
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void initializePlayer() {
        boolean needNewPlayer = player == null;
        if (needNewPlayer) {
            TrackSelection.Factory adaptiveTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
            trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory);
            eventLogger = new EventLogger(trackSelector);

            DrmSessionManager<FrameworkMediaCrypto> drmSessionManager = null;

            @DefaultRenderersFactory.ExtensionRendererMode
            int extensionRendererMode = DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF;
            DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(getContext(),
                    drmSessionManager, extensionRendererMode);

            LoadControl loadControl = new DefaultLoadControl();

            player = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControl);
            player.addListener(this);
            player.addListener(eventLogger);
            player.setAudioDebugListener(eventLogger);
            player.setVideoDebugListener(eventLogger);
            player.setMetadataOutput(eventLogger);

            playerView.setPlayer(player);
            player.setPlayWhenReady(true);
        }
        if (needNewPlayer) {
            if (stepVideoUrl != null) {
                if (!stepVideoUrl.isEmpty()) {
                    Timber.d("masuk sini");
                    MediaSource mediaSource = buildMediaSource(Uri.parse(stepVideoUrl));
                    player.prepare(mediaSource, false, false);
                } else {
                    Timber.d("masuk sini 1");
                    player.stop();
                    textLabelVideoNotAvailable.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        int type = Util.inferContentType(uri);
        switch (type) {
            case C.TYPE_SS:
                return new SsMediaSource(uri, buildDataSourceFactory(false),
                        new DefaultSsChunkSource.Factory(mediaDataSourceFactory), mainHandler, eventLogger);
            case C.TYPE_DASH:
                return new DashMediaSource(uri, buildDataSourceFactory(false),
                        new DefaultDashChunkSource.Factory(mediaDataSourceFactory), mainHandler, eventLogger);
            case C.TYPE_HLS:
                return new HlsMediaSource(uri, mediaDataSourceFactory, mainHandler, eventLogger);
            case C.TYPE_OTHER:
                return new ExtractorMediaSource(uri, mediaDataSourceFactory, new DefaultExtractorsFactory(),
                        mainHandler, eventLogger);
            default: {
                throw new IllegalStateException("Unsupported type: " + type);
            }
        }
    }

    private DataSource.Factory buildDataSourceFactory(boolean useBandwidthMeter) {
        return ((BakingApp) getActivity().getApplication())
                .buildDataSourceFactory(useBandwidthMeter ? BANDWIDTH_METER : null);
    }

    private void releasePlayer() {
        player.stop();
        player.release();
        player = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            releasePlayer();
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
//        if (playWhenReady) {
//            progressVideo.setVisibility(View.GONE);
//        } else {
//            progressVideo.setVisibility(View.VISIBLE);
//        }
        switch (playbackState) {
            case ExoPlayer.STATE_BUFFERING:
                progressVideo.setVisibility(View.VISIBLE);
                break;
//            case ExoPlayer.STATE_ENDED:
//                return "E";
//            case ExoPlayer.STATE_IDLE:
//                return "I";
            case ExoPlayer.STATE_READY:
                progressVideo.setVisibility(View.GONE);
                break;
            default:
                progressVideo.setVisibility(View.GONE);
                break;
        }
    }

    private static String getStateString(int state) {
        switch (state) {
            case ExoPlayer.STATE_BUFFERING:
                return "B";
            case ExoPlayer.STATE_ENDED:
                return "E";
            case ExoPlayer.STATE_IDLE:
                return "I";
            case ExoPlayer.STATE_READY:
                return "R";
            default:
                return "?";
        }
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException e) {
        String errorString = null;
        if (e.type == ExoPlaybackException.TYPE_RENDERER) {
            Exception cause = e.getRendererException();
            if (cause instanceof MediaCodecRenderer.DecoderInitializationException) {
                // Special case for decoder initialization failures.
                MediaCodecRenderer.DecoderInitializationException decoderInitializationException =
                        (MediaCodecRenderer.DecoderInitializationException) cause;
                if (decoderInitializationException.decoderName == null) {
                    if (decoderInitializationException.getCause() instanceof MediaCodecUtil.DecoderQueryException) {
                        errorString = getString(R.string.error_querying_decoders);
                    } else if (decoderInitializationException.secureDecoderRequired) {
                        errorString = getString(R.string.error_no_secure_decoder,
                                decoderInitializationException.mimeType);
                    } else {
                        errorString = getString(R.string.error_no_decoder,
                                decoderInitializationException.mimeType);
                    }
                } else {
                    errorString = getString(R.string.error_instantiating_decoder,
                            decoderInitializationException.decoderName);
                }
            }
        }
        if (errorString != null) {
            showToast(errorString);
        }
//        needRetrySource = true;
//        if (isBehindLiveWindow(e)) {
//            clearResumePosition();
//            initializePlayer();
//        } else {
//            updateResumePosition();
//            updateButtonVisibilities();
//            showControls();
//        }
    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onVisibilityChange(int visibility) {

    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    private static boolean isBehindLiveWindow(ExoPlaybackException e) {
        if (e.type != ExoPlaybackException.TYPE_SOURCE) {
            return false;
        }
        Throwable cause = e.getSourceException();
        while (cause != null) {
            if (cause instanceof BehindLiveWindowException) {
                return true;
            }
            cause = cause.getCause();
        }
        return false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (StepDetailFragment.OnNavigationClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnNavigationClickListener");
        }
    }

    public String getStepDescription() {
        return stepDescription;
    }

    public String getStepVideoUrl() {
        return stepVideoUrl;
    }
}
