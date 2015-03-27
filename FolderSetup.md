# Introduction #

Katalabaino relies on the user to have a specific folder directory in order to process data from Access files.  This directory is not only needed for the program to run, but it is also just good practice for project maintenance.

# Details #

Katalabaino assumes that you have created your directory with folders to store individual subjects' data.  It also assumes that you have a folder just for sessions.  Both the session and results folders should be contained within a parent experiment folder.  Your folder structure should look something like this:
  * experiment\_name
    * phase
      * bird\_1
      * bird\_2
    * sessions

Any given analysis class in Katalabaino will likely use the structure to build/load DBO files:
```
String dir = "C:/information/mtsofs/6item/";
String bird = "leo";
String workDir = dir + bird + "/";

File zipFile = new File(workDir + bird + ".dbo");

//Create a vector of Session objects from the dbo
Vector<Session> sessions = SessionFactory.BuildSessions(new MTSOFSSession(), zipFile);

```

Here, "information" is the folder that holds all of my experiments.  "Mtsofs" is the name of the experiment.  "6item" is a subset of this experiment, and I have selected to look at our subject Leo's data.  It may be important to point out that "Leo" is its own folder as well.  This directory looks like:
  * C:/information/mtsofs/6item/leo
  * C:/information/mtsofs/6item/mike
  * C:/information/mtsofs/6item/sessions

Within the 6item folder, I will need to have an additional folder named "sessions".  This folder will hold all of the Access files that served as sessions within the experiment.

## Bonus ##
The Katalabaino GUI tries to open file dialog windows according to the following preferences (first available will be chosen)
  1. Y:/warehouse/
  1. C:/warehouse/
  1. C:/information/
  1. C:/
  1. D:/