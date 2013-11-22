Setup
=====
These are the exact steps to build the app using Android Studio
or Gradle.


Setup in Android Studio
-----------------------
1. Download and install Android Studio:
   http://developer.android.com/sdk/installing/studio.html

2. If you don't have the SDK yet, copy the SDK dir from Android Studio
   to somewhere else. It's good to have a separate SDK dir, because
   some upgrades of Android Studio need a clean reinstall. By using a
   separate SDK dir outside of Android Studio you will save time.

3. Set environment variables:

        - `ANDROID_HOME`: the SDK dir

        - `PATH`: add `$ANDROID_HOME/tools`, `$ANDROID_HOME/plaform-tools`

4. Reconfigure Android Studio to use your custom SDK path

        a. Start Android Studio

        b. If you have a project open, close it with **File | Close Project**

        c. Click on **Configure | Project Defaults | Project Structure**

        d. Click on **SDKs** in the left column

        e. Remove all existing Android SDKs

        f. Add your custom Android SDK path

5. Install *Android SDK Build-tools* revision 19

        a. Open **Tools | Android | SDK Manager**
           Note: to have this menu option, you will need a project open,
           any project, it doesn't matter. If you have any existing project,
           you can use it, or create a dummy one.

        b. Install or upgrade *Android SDK Tools* to at least revision 22,
           and *Android SDK Platform-tools* to at least revision 18.

        c. Quit and restart SDK Manager

        d. Install *Android SDK Build-tools* revision 19

6. Click **Import project...**

        - Select `settings.gradle` in the project root

        - Select **Use customizable gradle wrapper**

    Note: the first time you do this it may take a long time.
    At the bottom-right corner of Android Studio you should see a
    progress indicator saying something like "3 processes running...".
    Click on it to see details. You will have to wait for this to complete.

    When the import is finished, a warning may pop-up in the top-right
    corner about *Unregistered Git root detected...*. Click on it to jump
    to the settings to fix it. There, you'll see a box with red background,
    at the right side a **Add root** link. Click on it and click OK.

7. Connect an Android device via USB
   (See **Troubleshooting USB connections** below for help)

8. Edit the run configurations

        1. Go to **Run | Edit Configurations...**
           There should be a run configurations already: winenotes-lite

        2. Edit each configuration, change **Target Device** to *USB device*
           Note: the emulator is useless anyway. Usually it's so slow it's
           prohibiting to development.

9. Launch the run configuration to install and start the app on your device.
   Note: if you have previously installed the app form Google Play,
   you will have to uninstall it first, because its signature won't match
   the signing key used by your Android Studio.
   An easy way to uninstall is with the command:

        adb uninstall com.winenotes.lite

   Actually, modern versions of Android Studio should offer to uninstall.

   A pop-up may also ask to enable ADB integration, it's good to say **Yes**


Troubleshooting USB connections
-------------------------------
1. Enable the **Developer options** menu on your Android device.
   On modern phones this might be a bit hidden. For example on Nexus 4-5,
   you have to go to **About phone**, and click on **Build number** 5 times
   to activate.

2. Switch on **Developer options**

3. Switch on **USB debugging**

4. Note: the **Stay awake** option is useful during active development

5. Plug in USB: a message might pop up on the device to authorize access

6. Run `adb devices`: the device should appear in the list.
   In Linux you might have permission issues. In that case you may have
   to create a new udev rules file to get permissions correctly assigned
   to the plugged in Android device.
   TODO


Creating a run configuration in Android Studio
----------------------------------------------
1. Go to **Run | Edit Configurations...**

2. Select **Android Application** from the list and click on the + icon

3. Give it a name, for example "winenotes-lite"

4. Adjust the settings:

        + Package: **Deploy default APK**

        + Activity: **Launch default Activity**

        + Target Device: **USB device**

5. Click OK to make this configuration the default, and it should
   appear in a drop-down menu in the top toolbar.
   Click on the play button next to the drop-down to launch the configuration.


Building on the command line
----------------------------
To build the projects on the command line we use Gradle.
A pre-requisite is that you already have a working Android Studio setup.

To build the debug apk, run in the project root:

        ./gradlew assembleDebug


Building in Eclipse
-------------------
1. Import the following projects:

        * winenotes/ -- common logic and layouts

        * winenotes-lite/ -- the Wine Notes LITE application

2. Run the Wine Notes LITE project as Android application


Project layout
--------------
+ winenotes/

    An Android Library project that contains most of the
    functionality and layout elements.

+ winenotes-lite/

    An Android Application project that corresponds to the
    Wine Notes LITE application on Google Play.
    It depends on the "winenotes" project.
    Pretty much empty for now.

+ django-winenotes/

    A Django project to make it easier to edit the database
    of questions.


Gradle
------
- User guide: http://tools.android.com/tech-docs/new-build-system/user-guide

- Overview video: http://tools.android.com/tech-docs/new-build-system

- Tips for Android Studio: http://developer.android.com/sdk/installing/studio-tips.html


