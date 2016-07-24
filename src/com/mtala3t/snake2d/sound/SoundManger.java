package com.mtala3t.snake2d.sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Mohammed.Talaat (mtala3t)
 * @version 1.0
 */
public class SoundManger {

	private PlayThread playThread;

	public SoundManger(String soundFilePath) {
		playThread = new PlayThread(soundFilePath);

	}

	@SuppressWarnings("deprecation")
	public void startSound() {
		if (playThread.isAlive())
			playThread.resume();
		else
			playThread.start();
	}

	@SuppressWarnings("deprecation")
	public void pauseSound() {
		playThread.suspend();
	}

	@SuppressWarnings("deprecation")
	public void stopSound() {
		playThread.stop();
	}

	class PlayThread extends Thread {

		private AudioFormat audioFormat;
		private AudioInputStream audioInputStream;
		private SourceDataLine sourceDataLine;

		private String filePath = null;

		public PlayThread(String filePath) {
			this.filePath = filePath;
		}

		byte tempBuffer[] = new byte[10000];

		public void run() {

			File soundFile = new File(filePath);

			while (true) {
				try {
					audioInputStream = AudioSystem
							.getAudioInputStream(soundFile);
					audioFormat = audioInputStream.getFormat();

					DataLine.Info dataLineInfo = new DataLine.Info(
							SourceDataLine.class, audioFormat);

					sourceDataLine = (SourceDataLine) AudioSystem
							.getLine(dataLineInfo);
					sourceDataLine.open(audioFormat);
				} catch (LineUnavailableException ex) {
					ex.printStackTrace();
				} catch (UnsupportedAudioFileException ex) {
					ex.printStackTrace();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				sourceDataLine.start();
				try {
					int cnt;
					while ((cnt = audioInputStream.read(tempBuffer, 0,
							tempBuffer.length)) != -1) {
						if (cnt > 0) {
							sourceDataLine.write(tempBuffer, 0, cnt);
						}
					}
					sourceDataLine.flush();
					sourceDataLine.close();
					Thread.sleep(2000);
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(0);
				}
			}
		}

	}
}
