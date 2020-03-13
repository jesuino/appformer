package org.dashbuilder.common.client.widgets;

import java.util.function.Consumer;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.constants.IconType;

public class AnimationControlPanel {

    Button btnPlayPause;
    Button btnStop;
    boolean isPlaying = false;

    Runnable playPauseCallback;
    Runnable stopCallback;

    AnimationControlPanel() {
        btnPlayPause = new Button("", IconType.PLAY, e -> {
            if (isPlaying) {
                btnPlayPause.setIcon(IconType.PLAY);
            } else {
                btnPlayPause.setIcon(IconType.PAUSE);
            }
            isPlaying = !isPlaying;
            playPauseCallback.run();
        });

        btnStop = new Button("", IconType.STOP, e -> stopCallback.run());

    }

}
