package id.ac.its.fox.audio;

import javax.sound.sampled.*;

public class AudioPlayer {
    private Clip clip;
	private FloatControl fc;

    public AudioPlayer(String path){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(path));
            AudioFormat baseFormat = ais.getFormat();

            AudioFormat decodeFormat = new AudioFormat(
                                                AudioFormat.Encoding.PCM_SIGNED,
                                                baseFormat.getSampleRate(),
                                                16,
                                                baseFormat.getChannels(),
                                                baseFormat.getChannels() * 2,
                                                baseFormat.getSampleRate(),
                                                false
                                            );
            
            AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
            clip = AudioSystem.getClip();
            clip.open(dais);
            fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
