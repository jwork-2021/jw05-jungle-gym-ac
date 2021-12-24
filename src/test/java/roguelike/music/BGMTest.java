package roguelike.music;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.Test;

import roguelike.mainWindow.MainWindow;

public class BGMTest {
    
    @Test
    public void testStop() {
        try {
            MainWindow mainWindow=new MainWindow();
            BGM bgm=new BGM(mainWindow);
            bgm.setAudioStream();
            bgm.play();
            bgm.stop();
            assertTrue("Error in BGM.play",!bgm.getClip().isRunning());
            assertTrue("Error in BGM.play",!bgm.getClip().isOpen());
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }        
    }
}
