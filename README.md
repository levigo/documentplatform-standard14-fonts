[![Actions Status](https://github.com/levigo/documentplatform-standard14-fonts/workflows/Continuous%20Delivery/badge.svg)](https://github.com/levigo/documentplatform-standard14-fonts/actions)
[![Docker Hub](https://img.shields.io/badge/MADE%20with-JAVA-RED.svg)](#JAVA)
[![Generic badge](https://img.shields.io/badge/current%20version-2.3.2-1abc9c.svg)](https://github.com/levigo/documentplatform-standard14-fonts/releases/tag/documentplatform-standard14-fonts-2.3)

# Standard 14 Font Resources

This module provides font resources which are the best matching substitutes for PDF's standard 14 fonts. Due to missing free substitute this module can not provide the Symbol typeface at this moment.

The following table shows the resources and substitutions:

| Original Standard 14 Font  | Substitute (Postscript name)      | Resource path (in /src/main/resources)               |
| -------------------------- | --------------------------------  | ---------------------------------------------------- |
| Times-Roman                | Tinos                             | /com/levigo/jadice/fonts/serif/tinos-v15-latin-regular.ttf     |
| Helvetica                  | Arimo                             | /com/levigo/jadice/fonts/sans/arimo-v16-latin-regular.ttf      |
| Courier                    | Cousine                           | /com/levigo/jadice/fonts/mono/cousine-v16-latin-regular.ttf    |
| Times-Bold                 | Tinos-Bold                        | /com/levigo/jadice/fonts/serif/tinos-v15-latin-700.ttf         |
| Helvetica-Bold             | Arimo-Bold                        | /com/levigo/jadice/fonts/sans/arimo-v16-latin-700.ttf          |
| Courier-Bold               | Cousine-Bold                      | /com/levigo/jadice/fonts/mono/cousine-v16-latin-700.ttf        |
| Times-Italic               | Tinos-Italic                      | /com/levigo/jadice/fonts/serif/tinos-v15-latin-italic.ttf      |
| Helvetica-Oblique          | Arimo-Italic                      | /com/levigo/jadice/fonts/sans/arimo-v16-latin-italic.ttf       |
| Courier-Oblique            | Cousine-Italic                    | /com/levigo/jadice/fonts/mono/cousine-v16-latin-italic.ttf     |
| Times-BoldItalic           | Tinos-BoldItalic                  | /com/levigo/jadice/fonts/serif/tinos-v15-latin-700italic.ttf   |
| Helvetica-BoldOblique      | Arimo-BoldItalic                  | /com/levigo/jadice/fonts/sans/arimo-v16-latin-700italic.ttf    |
| Courier-BoldOblique        | Cousine-BoldItalic                | /com/levigo/jadice/fonts/mono/cousine-v16-latin-700italic.ttf  |
| Symbol                     | NOT INCLUDED                      |                                                                |
| ZapfDingbats               | Levibats-Regular                  | /com/levigo/jadice/fonts/symbol/Levibats-Regular.ttf           |


All fonts are available in different formats:
* TTF (TrueType) - System font and Safari, Android, iOS
* WOFF (Web Open Font Format) - Modern Browsers
* WOFF2 (Web Open Font Format) - Super Modern Browsers

# How to use

Simply clone the project and build via Apache Maven. Execute `mvn install` in the project's root folder (where the `pom.xml` is placed). This should result in a JAR file that will be placed in `target` folder under project's root folder. Include this JAR in your application's classpath and you will be able to use the included font resources.
