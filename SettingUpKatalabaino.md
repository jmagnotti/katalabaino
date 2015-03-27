# Introduction #

Before you can analyze your data, there are several prerequisites. This guide will describe the prerequisites in sufficient detail and finish with a quick analysis example.

## Software needed to create and analyze DBO files ##
  * Windows XP or better
  * Microsoft Office XP or better<sup>1</sup>
  * [Eclipse 32-bit](http://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/indigo/SR2/eclipse-java-indigo-SR2-win32.zip) or 64-bit
  * Java version 1.7 OR BELOW (Java 1.8 removed support for accessing MDB files using the current technique)
Note: If you get 64-bit Eclipse, you will need to manually obtain and select a 32-bit JDK. If you don't know how to do this, get 32-bit Eclipse.
## Optional Software ##
  * Sigmaplot
  * MS Excel
  * SPSS

# Software Installation #
  1. [Download Eclipse](http://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/indigo/SR2/eclipse-java-indigo-SR2-win32.zip) if you don't have it and extract the zip file to a preferred directory, e.g., C:/Users/John
  1. Run Eclipse by Clicking on the executable
  1. Install subclipse using the help->Install new software... option in Eclipse
    1. Click the "Add..." button and provide a name (subclipse) and repository URL <pre>http://subclipse.tigris.org/update_1.8.x</pre>
    1. Ensure subclipse is selected in the drop-down box
    1. Choose "Select All"
    1. Choose "Next," then "Next" again
    1. Accept the license agreements, then choose "Finish"
    1. Choose "OK" if you receive any informational pop-up dialogs
    1. Choose the "Restart Now" option (restarting Eclipse, not your computer) once your software is installed
  1. Grab the latest code from the repository<sup>2</sup>
    1. In Eclipse, File->Import->SVN->Checkout Projects from SVN
    1. Choose "Next"
    1. Create New Repository Location: <pre> http://katalabaino.googlecode.com/svn/trunk/ </pre>
    1. Choose "Next"
    1. Select Katalabaino, Then "Finish"
      * Watch all the downloading, maybe grab a coffee if you have a slow connection
# Running Katalabaino #
  1. From Eclipse, Double-click on the Katalabaino project in the left-hand tree-view
  1. Open the folder called <pre>src</pre> and then <pre>analyses</pre>
  1. Double-click the file <pre>GUIAnalyze</pre>
  1. Choose "Run" from the menu bar, and "Run Last Launched"
  1. Select the session type from the drop-down (e.g., SDSession)
  1. Select Create DBO (Windows Only) or Load DBO
  1. Navigate to the directory with your .mdb/.tr files. (See FolderSetup page for directory structure information)
    * For Create DBO, select an .mdb or .tr file from a subject directory
      1. A DBO will be created with subject\_name\_timestamp.dbo
    * For Load DBO, select the DBO you want to load
  1. Choose output as well as grouping and filtering, and then choose Run
  1. Data will fill into the Eclipse console as tab-separated columns. These columns will copy/paste into most major spreadsheet programs.

# Obtaining Sample Data #
  * Send a request to the authors for sample data, or see the relevant wiki for the XML schema to roll your own data.


---

  1. Although the code is written in Java, the code makes use of the windows-only JET Engine to read in MS Access files. It's unclear if there is an easy way to handle MS Access files in OSX and Linux
  1. There is something exhilarating about downloading freshly minted code, although this approach is not without its problem. Once the code reaches a stable point, we should change this to have users download a recent tag, rather than checkout trunk