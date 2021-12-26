package client.music;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import client.mainWindow.MainWindow;

public class BGM {
    
    // to store current position
    private Long currentFrame;
    private Clip clip;
      
    // current status of clip
    private String status;
    //MainWindoe
    MainWindow mainWindow;
    
    AudioInputStream audioInputStream;
    public static final String[] WorldScreenBGMPath={"Stage1","Stage2","BossFight"};
    public static final String RestartScreenBGMPath="Select" ;

    public BGM(MainWindow mainWindow)throws LineUnavailableException{
        this.mainWindow=mainWindow;
        // create clip reference
        clip = AudioSystem.getClip();
    }
    /*public void setAudioStream()throws UnsupportedAudioFileException,
    IOException, LineUnavailableException {
        stop();
        // create AudioInputStream object
        if(mainWindow.getScreen() instanceof ContinueScreen)
            audioInputStream = AudioSystem.getAudioInputStream(
                    getClass().getClassLoader().getResource(
                        "BGM/"+RestartScreenBGMPath+".wav"
                    ));
        else
            audioInputStream = AudioSystem.getAudioInputStream(
                    getClass().getClassLoader().getResource(
                        "BGM/"+WorldScreenBGMPath[mainWindow.getScreen().getGameStage()%WorldScreenBGMPath.length]
                        +".wav"
                    ));
  
        // open audioInputStream to the clip
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        play();
    }*/
    public void play(){
        clip.start();
    }
    public void stop() throws UnsupportedAudioFileException,
    IOException, LineUnavailableException 
    {
        //currentFrame = 0L;
        if(clip.isRunning()){
            clip.stop();
        }
        if(clip.isOpen()){
            clip.close();
        }
    }
    public Clip getClip(){
        return clip;
    }
}
