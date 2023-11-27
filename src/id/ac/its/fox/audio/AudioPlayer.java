package id.ac.its.fox.audio;

import javax.sound.sampled.*;

public class AudioPlayer {
    private Clip clip;
	private float previousVolume = 0;
	private float currentVolume = 0;
	private FloatControl fc;
	private boolean mute = false;

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

    public void clipPlay(){
        if(clip == null) return;
        clipStop();
        clip.setFramePosition(0);
        while(!clip.isRunning()){
            clip.start();
        }
    }

    public void clipStop(){
        if(clip.isRunning()) clip.stop();
    }

    public void bgplay() {
		clipPlay();
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

    public void clipClose(){
		clipStop();
		clip.close();
	}

    public void volumeUp() {
		if(mute)
			mute = false;
		currentVolume += 5.0f;
		if(currentVolume > 6.0f)
			currentVolume = 6.0f;
		fc.setValue(currentVolume);
	}
	
	public void volumeDown() {
		if(mute)
			mute = false;
		currentVolume -= 5.0f;
		if(currentVolume < -50.0f) {
			currentVolume = -50.0f;
			mute = true;
		}
		fc.setValue(currentVolume);
	}
	
	public void volumeMute() {
		if(!mute) {
			previousVolume = currentVolume;
			currentVolume = -50.0f;
			fc.setValue(currentVolume);
			mute = true;
		}
		else if(mute) {
			currentVolume = previousVolume;
			fc.setValue(currentVolume);
			mute = false;
		}
	}
}
