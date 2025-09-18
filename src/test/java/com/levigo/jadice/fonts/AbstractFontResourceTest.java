package com.levigo.jadice.fonts;


import static com.levigo.jadice.document.io.IOUtils.wrap;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import com.levigo.jadice.document.ImageComparisonTest;
import com.levigo.jadice.document.io.SeekableInputStream;
import com.levigo.jadice.format.truetype.internal.TrueTypeFont;
import com.levigo.jadice.format.truetype.internal.TrueTypeFontLoader;

@ExtendWith(ParameterizedTestProvider.class)
public abstract class AbstractFontResourceTest {

  static final class PublicImageComparisonTest extends ImageComparisonTest {
    @Override
    public void compareWithExpected(BufferedImage bufferedImage, String s) throws IOException, ClassNotFoundException {
      super.compareWithExpected(bufferedImage, s);
    }
  }
  protected String fontResourceName;
  protected SeekableInputStream fontStream;
  protected TrueTypeFont fontResource;
  protected PublicImageComparisonTest imageComparisonTest;

  public void initializeResource() throws Exception {
    fontStream = wrap(getClass().getResourceAsStream(fontResourceName));
    final TrueTypeFontLoader fontLoader = new TrueTypeFontLoader();
    fontResource = (TrueTypeFont) fontLoader.load(fontStream);
    imageComparisonTest = new PublicImageComparisonTest();
    System.err.println("Initialize Resource: " + fontResourceName);
  }

  public void freeResource() throws Exception {
    System.err.println("Free Resource: " + fontResourceName);
    fontStream.close();
    fontStream = null;
    fontResource = null;
    imageComparisonTest = null;
  }

  public abstract Iterable<String> fontResourceNames();


  @TestTemplate
  public void runAllTests() throws Exception {
    for (Method method : this.getClass().getDeclaredMethods()) {
      if (method.isAnnotationPresent(FontResourceTestCase.class)) {
        System.err.println("→ Start: " + method.getName() + " with " + fontResourceName);
        method.setAccessible(true);
        method.invoke(this);
      }
    }
  }
}