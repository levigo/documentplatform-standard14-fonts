package com.levigo.jadice.fonts;


import org.junit.Test;

import com.levigo.jadice.document.AbstractTypeCompositionRendererTest;
import com.levigo.jadice.document.io.MemoryInputStream;
import com.levigo.jadice.format.truetype.internal.TrueTypeFaces;

public class TrueTypeRendererTest extends AbstractTypeCompositionRendererTest {

    @Test
    public void testPlainTypeComposition() throws Exception {
        // nothing to do
    }

    public void testFont(String fontResource) throws Exception {
        typeface = TrueTypeFaces.create(new MemoryInputStream(TrueTypeRendererTest.class.getResourceAsStream(fontResource)), null);
        renderStraightTypeComposition(fontResource);
    }

    @Test
    public void testCousineRegular() throws Exception {
        testFont("mono/Cousine-Regular.ttf");
    }

    @Test
    public void testCousineBold() throws Exception {
        testFont("mono/Cousine-Bold.ttf");
    }

    @Test
    public void testCousineItalic() throws Exception {
        testFont("mono/Cousine-Italic.ttf");
    }

    @Test
    public void testCousineBoldItalic() throws Exception {
        testFont("mono/Cousine-BoldItalic.ttf");
    }

    @Test
    public void testArimoRegular() throws Exception {
        testFont("sans/Arimo-Regular.ttf");
    }

    @Test
    public void testArimoBold() throws Exception {
        testFont("sans/Arimo-Bold.ttf");
    }

    @Test
    public void testArimoItalic() throws Exception {
        testFont("sans/Arimo-Italic.ttf");
    }

    @Test
    public void testArimoBoldItalic() throws Exception {
        testFont("sans/Arimo-BoldItalic.ttf");
    }

    @Test
    public void testTinosRegular() throws Exception {
        testFont("serif/Tinos-Regular.ttf");
    }

    @Test
    public void testTinosBold() throws Exception {
        testFont("serif/Tinos-Bold.ttf");
    }

    @Test
    public void testTinosItalic() throws Exception {
        testFont("serif/Tinos-Italic.ttf");
    }

    @Test
    public void testTinosBoldItalic() throws Exception {
        testFont("serif/Tinos-BoldItalic.ttf");
    }

    @Test
    public void testLevibatsRegular() throws Exception {
        testFont("symbol/Levibats-Regular.ttf");
    }
}
