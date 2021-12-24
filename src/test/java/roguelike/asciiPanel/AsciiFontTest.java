package roguelike.asciiPanel;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AsciiFontTest {
    @Test
    public void testGetFontFilename() {
        AsciiFont font=new AsciiFont("images/cp437_32x32.png",32 , 32);
        assertTrue("Error in AsciiFont.getFontFilename",font.getFontFilename()=="images/cp437_32x32.png");
    }

    @Test
    public void testGetHeight() {
        AsciiFont font=new AsciiFont("images/cp437_32x32.png",32 , 32);
        assertTrue("Error in AsciiFont.getHeight",font.getHeight()==32);

    }

    @Test
    public void testGetWidth() {
        AsciiFont font=new AsciiFont("images/cp437_32x32.png",32 , 32);
        assertTrue("Error in AsciiFont.getWidth",font.getWidth()==32);
    }
}
