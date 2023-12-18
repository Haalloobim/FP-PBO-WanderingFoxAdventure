package id.ac.its.fox.audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class AudioPlayer {
    private Clip clip;
	private float previousVolume = 0;
	private float currentVolume = 0;
	private FloatControl fc;
	private boolean mute = false;

    public AudioPlayer(String s) {
        try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(s));
			AudioFormat baseFormat = ais.getFormat();
			
			AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 
														baseFormat.getSampleRate(), 
														16,
														baseFormat.getChannels(),
														baseFormat.getChannels() * 2,
														baseFormat.getSampleRate(),
														false);
			
			AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
			fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		}
		catch(Exception e){
			e.printStackTrace();
		}
    }
    
    public void clipPlay() {
		if(clip == null) return;
		clipStop();
		clip.setFramePosition(0);
		clip.start();
	}
	
	public void bgplay() {
		clipPlay();
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void clipStop() {
		if(clip.isRunning()) clip.stop();
	}
	
	public void close(){
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

	public boolean isMuted() {
		return mute;
	}
}
