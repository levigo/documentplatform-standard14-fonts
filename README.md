# Standard 14 Font Resources

This module provides font resources which are the best matching substitutes for PDF's standard 14 fonts. Due to missing free substitute this module can not provide the Symbol typeface at this moment.

The following table shows the resources and substitutions:

| Original Standard 14 Font  | Substitute (Postscript name)      | Resource path (in /src/main/resources)               |
| -------------------------- | --------------------------------  | ---------------------------------------------------- |
| Times-Roman                | Tinos                             | /com/levigo/jadice/fonts/serif/Tinos-Regular.ttf     |
| Helvetica                  | Arimo                             | /com/levigo/jadice/fonts/sans/Arimo-Regular.ttf      |
| Courier                    | Cousine                           | /com/levigo/jadice/fonts/mono/Cousine-Regular.ttf    |
| Times-Bold                 | Tinos-Bold                        | /com/levigo/jadice/fonts/serif/Tinos-Bold.ttf        |
| Helvetica-Bold             | Arimo-Bold                        | /com/levigo/jadice/fonts/sans/Arimo-Bold.ttf         |
| Courier-Bold               | Cousine-Bold                      | /com/levigo/jadice/fonts/mono/Cousine-Bold.ttf       |
| Times-Italic               | Tinos-Italic                      | /com/levigo/jadice/fonts/serif/Tinos-Italic.ttf      |
| Helvetica-Oblique          | Arimo-Italic                      | /com/levigo/jadice/fonts/sans/Arimo-Italic.ttf       |
| Courier-Oblique            | Cousine-Italic                    | /com/levigo/jadice/fonts/mono/Cousine-Italic.ttf     |
| Times-BoldItalic           | Tinos-BoldItalic                  | /com/levigo/jadice/fonts/serif/Tinos-BoldItalic.ttf  |
| Helvetica-BoldOblique      | Arimo-BoldItalic                  | /com/levigo/jadice/fonts/sans/Arimo-BoldItalic.ttf   |
| Courier-BoldOblique        | Cousine-BoldItalic                | /com/levigo/jadice/fonts/mono/Cousine-BoldItalic.ttf |
| Symbol                     | NOT INCLUDED                      |                                                      |
| ZapfDingbats               | Levibats-Regular                  | /com/levigo/jadice/fonts/symbol/Levibats-Regular.ttf |

## How to use

Simply clone the project and build via Apache Maven. Execute `mvn install` in the project's root folder (where the `pom.xml` is placed). This should result in a JAR file that will be placed in `target` folder under project's root folder. Include this JAR in your application's classpath and you will be able to use the included font resources.

## License

The individual fonts come with their own license each. The according license files are placed inside the folder of the
font. This project and the fonts all use the Apache 2.0 license.

