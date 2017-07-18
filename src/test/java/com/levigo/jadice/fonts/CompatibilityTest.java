package com.levigo.jadice.fonts;

import static com.levigo.jadice.document.io.IOUtils.wrap;
import static com.levigo.jadice.format.truetype.internal.FSType.FSTYPE_EMBEDDING_INSTALLABLE;
import static com.levigo.jadice.format.truetype.internal.structure.TrueTypeConstants.PLATFORM_ID_MS;
import static com.levigo.jadice.format.truetype.internal.structure.TrueTypeConstantsMS.ENCODING_ID_MS_UNICODE_BMP;
import static java.awt.geom.AffineTransform.getScaleInstance;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.levigo.jadice.document.ImageComparisonTest;
import com.levigo.jadice.document.JadiceException;
import com.levigo.jadice.document.internal.model.font.JadiceGlyphVector;
import com.levigo.jadice.document.internal.render.font.GraphicsContextOutlineFontRenderer;
import com.levigo.jadice.document.internal.render.j2d.Graphics2DBridge;
import com.levigo.jadice.document.io.SeekableInputStream;
import com.levigo.jadice.format.truetype.internal.TrueTypeFont;
import com.levigo.jadice.format.truetype.internal.TrueTypeFontLoader;
import com.levigo.jadice.format.truetype.internal.structure.CMap;

@RunWith(Parameterized.class)
public class CompatibilityTest extends ImageComparisonTest {

  private static final String SAMPLE_TEXT = "ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜ " + "abcdefghijklmnopqrstuvwxyzäöüß "
      + "1234567890 " + "@€!\"§$%&/()=?{}[]\\<>|,;.:-_#'+*~´`µ°^²³";

  private static final int FONT_SIZE = 40;

  private static final int MARKER_SIZE = 3;

  @Parameterized.Parameters(name = "{index} - {0}")
  public static Object[] parameters() {
    return new Object[]{
        "mono/Cousine-Regular.ttf", "mono/Cousine-Bold.ttf", "mono/Cousine-Italic.ttf", "mono/Cousine-BoldItalic.ttf",
        "sans/Arimo-Regular.ttf", "sans/Arimo-Bold.ttf", "sans/Arimo-Italic.ttf", "sans/Arimo-BoldItalic.ttf",
        "serif/Tinos-Regular.ttf", "serif/Tinos-Bold.ttf", "serif/Tinos-Italic.ttf", "serif/Tinos-BoldItalic.ttf",
        "symbol/Levibats-Regular.ttf"
    };
  }

  @Parameterized.Parameter(0)
  public String fontResource;

  public TrueTypeFont font;

  @Before
  public void setUpTrueTypeFont() throws IOException, JadiceException {
    final SeekableInputStream inputStream = wrap(getClass().getResourceAsStream(fontResource));
    final TrueTypeFontLoader fontLoader = new TrueTypeFontLoader();
    font = (TrueTypeFont) fontLoader.load(inputStream);
  }

  @Test
  public void assertThat_fsType_allowsEditing() {
    assertThat(font.getFsType(), is(FSTYPE_EMBEDDING_INSTALLABLE.getMask()));
  }

  @Test
  public void assertThat_renderedGlyphVector_hasExpectedVisualOutput() throws Exception {
    final CMap microsoftUnicodeCMap = font.getCMap(PLATFORM_ID_MS, ENCODING_ID_MS_UNICODE_BMP);
    final int[] codePoints = new int[SAMPLE_TEXT.length()];
    final char[] sampleTextChars = SAMPLE_TEXT.toCharArray();
    for (int i = 0; i < sampleTextChars.length; i++) {
      codePoints[i] = microsoftUnicodeCMap.map(sampleTextChars[i]);
    }

    final JadiceGlyphVector glyphVector = new JadiceGlyphVector(0, 0, font, codePoints, sampleTextChars);
    glyphVector.setTransform(getScaleInstance(FONT_SIZE, FONT_SIZE));

    final BufferedImage renderedGlyphVector = render(glyphVector);
    compareWithExpected(renderedGlyphVector, fontResource);
  }

  private BufferedImage render(final JadiceGlyphVector glv) {
    final GraphicsContextOutlineFontRenderer renderer = new GraphicsContextOutlineFontRenderer();

    final Rectangle2D b = glv.getGlyphLogicalBounds(0).getBounds2D();
    b.add(glv.getGlyphLogicalBounds(glv.getNumGlyphs() - 1).getBounds2D());
    b.add(0, 0); // make sure to include origin!

    final Rectangle bounds = b.getBounds();
    final int gap = bounds.height / 2;
    final BufferedImage img = new BufferedImage(bounds.width + gap, bounds.height + gap, BufferedImage.TYPE_INT_RGB);
    final Graphics2D g = img.createGraphics();
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, img.getWidth(), img.getHeight());

    g.setColor(Color.BLACK);

    final Graphics2DBridge gc = new Graphics2DBridge(g);
    gc.scale(1, -1);
    gc.translate(gap / 2, -(bounds.height + bounds.y + gap / 2));

    // draw bounding box
    g.setColor(Color.RED);
    g.draw(glv.getVisualBounds());

    // draw glyph info
    final float p1[] = new float[2];
    final float p0[] = new float[2];
    for (int i = 0; i < glv.getNumGlyphs(); i++) {
      g.setColor(Color.ORANGE);
      g.draw(glv.getGlyphVisualBounds(i));

      g.setColor(Color.BLUE);
      g.draw(glv.getGlyphLogicalBounds(i));

      // position
      g.setColor(Color.GREEN.darker());
      glv.getGlyphPosition(i, p1);
      p1[0] += glv.getPosX();
      p1[1] += glv.getPosY();
      drawX(g, (int) p1[0], (int) p1[1]);

      // advance
      if (i > 0) {
        glv.getGlyphPosition(i - 1, p0);
        p0[0] += glv.getPosX();
        p0[1] += glv.getPosY();
        g.drawLine((int) p0[0], (int) p0[1], (int) p1[0], (int) p1[1]);
      }
    }

    // draw origin
    g.setColor(Color.MAGENTA);
    drawX(g, 0, 0);

    // draw outline
    final com.levigo.jadice.document.internal.model.Color color = com.levigo.jadice.document.internal.model.Color.BLACK;
    gc.setColor(color);
    final Color awtColor = color.toAWTColor();
    renderer.render(glv, gc, null, null, awtColor, awtColor);

    return img;
  }

  private void drawX(final Graphics2D g, int x, int y) {
    g.drawLine(x - MARKER_SIZE, y - MARKER_SIZE, x + MARKER_SIZE, y + MARKER_SIZE);
    g.drawLine(x - MARKER_SIZE, y + MARKER_SIZE, x + MARKER_SIZE, y - MARKER_SIZE);
  }

}
