GameGaSDK SampleProject for Android
========================

* Authentication
* Billing
* Tracking

INSTALLATION
------------

**Download the official version: [click here](https://github.com/itcgosucorp/GamegaSDK-Android-SampleProject/releases)**

#### 1. In your root-level (project-level) Gradle file `<project>/build.gradle`, add more plugins dependency to your `build.gradle` file:

```gradle
allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
dependencies {
    // ...
    // google service (use firebase tracking)
    classpath "com.google.protobuf:protobuf-gradle-plugin:0.9.4"
    classpath 'com.google.gms:google-services:4.4.2'
    classpath 'com.google.firebase:firebase-crashlytics-gradle:2.9.0'
}
```	
#### 2. In your module (app-level) Gradle file `<project>/<app-module>/build.gradle`, add more plugins dependency to your `build.gradle` file:

```gradle
// google service plugin (use firebase tracking)
apply plugin: 'com.google.gms.google-services'
apply plugin: 'com.google.firebase.crashlytics'
dependencies {
    // ...
    //for in app billing
    implementation 'com.android.billingclient:billing:6.1.0'
    //for showLogin facebook sdk
    implementation 'com.facebook.android:facebook-android-sdk:latest.release'
    //for sigin GG SDK
    implementation 'com.google.android.gms:play-services-auth:20.6.0'
    //for firebase
    // Import the BoM for the Firebase platform
    implementation platform('com.google.firebase:firebase-bom:31.1.0')
    implementation 'com.google.guava:guava:31.1-android'
    implementation 'com.google.firebase:firebase-messaging:23.2.1'
    implementation 'com.google.firebase:firebase-analytics'
    implementation("com.google.firebase:firebase-crashlytics")
    // GRPC Deps
    implementation 'io.grpc:grpc-okhttp:1.57.1'
    implementation 'io.grpc:grpc-protobuf-lite:1.57.1'
    implementation 'io.grpc:grpc-stub:1.57.1'
    compileOnly 'org.apache.tomcat:annotations-api:6.0.53'
    // for GaSDK and ItsSDK
    implementation("com.google.android.play:review:2.0.1")
    //
    implementation 'androidx.core:core:1.10.1'
    implementation "net.zetetic:sqlcipher-android:4.5.6@aar"
    implementation "androidx.sqlite:sqlite:2.3.1"
    implementation 'androidx.lifecycle:lifecycle-process:2.6.1'
    implementation 'androidx.lifecycle:lifecycle-common:2.6.1'
    implementation 'androidx.browser:browser:1.8.0'
    implementation 'com.rudderstack.android.sdk:core:1.25.1'
    implementation files('libs/gasdk.aar')
    implementation files('libs/its-sdk.aar')
}
```	
**-Move config file (google-services.json) into the module (app-level) root directory of your app.**
```
app/
  google-services.json
```

**- Add ga-service.json file to folder main/assets**
```json
{
 "client_id": "sample_client_id",
 "its_app_write_key": "sample_write_key",
 "its_app_signing_key": "sample_signing_key"
}
```
#### 4. Edit Your Resources and Manifest
**- Open the /app/res/values/strings.xml file.**
```xml
<string name="facebook_app_id">sample_facebook_app_id</string>
<string name="fb_login_protocol_scheme">sample_fb_login_protocol_scheme</string>
<string name="facebook_client_token">sample_facebook_client_token</string>
```
**-Open the /app/manifest/AndroidManifest.xml file.**
```xml
Merge XML manifest
<application
        tools:replace = "android:fullBackupContent"
        android:allowBackup = "true"
        android:fullBackupContent = "true"/>
<!-- ============ PERMISSION ============== -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<!-- use for Push GSM -->
<uses-permission android:name="android.permission.WAKE_LOCK" />
<uses-permission android:name="com.google.android.c2dm.permission.RECEIVE" />
<!-- use for iab -->
<uses-permission android:name="com.android.vending.BILLING" />
<uses-permission android:name="com.google.android.gms.permission.AD_ID" />

<!-- ============ Facebook META config ============== -->
<meta-data
    android:name="com.facebook.sdk.ApplicationId"
    android:value="@string/facebook_app_id"/>
<meta-data
    android:name="com.facebook.sdk.ClientToken"
    android:value="@string/facebook_client_token" />
<provider android:authorities="com.facebook.app.FacebookContentProvider116350609033094"
    android:name="com.facebook.FacebookContentProvider"
    android:exported="true"/>
````
API
--------------------
1. Initialize configuration for GaSDK
---
```java
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        GaSDK.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void onCreate(Bundle savedInstanceState)()
    {
        // ...
        //Initialize SDK
        GaSDK.sdkInitialize(this,  new IGameInitListener() {
            @Override
            public void onSuccess() { //invoked when login successfully
                onLogin();
            }
            @Override
            public void onError(GameException exception) {
                exception.printStackTrace();
            }
        });
    }
    private void onLogin() {
        GaSDK.onLogin(new IGameOauthListener() {
            @Override
            public void onLoginSuccess(String UserId, String UserName, String accesstoken) {
                //invoked when login successfully
            }
            @Override
            public void onLogout() {
                 //invoked when logout successfully
                GaSDK.showLogin();
            }

            @Override
            public void onError() {
                //invoked when login failed
            }
        });
}
```
**NOTE**
* Login with Google: You send SHA-1 us [click here](https://developers.google.com/android/guides/client-auth)
* Login with Facebook: You send hash key us [more here](https://developers.facebook.com/docs/facebook-login/android)

2. GaSDK Basic Functions
---
```java
//login
GaSDK.showLogin();
//Logout
GaSDK.logout();

```
3. Payment
---
```java
public void call_billing()
{
    String serverID       = "sample_serverId";
    String characterID    = "sample_chareacer_id";
    String characterName    = "sample_character_name";

    GaSDK.showTopUp(serverID, characterID, characterName);

    /**
     * serverID: ID of the server
     * characterID: ID of the character
     * characterName: Name of the character
     */
}
```
USAGE TRACKING
--------------------
The SDK supports tracking in-app events. To use it, you need to implement the `GItsTrackingManager` module. For detailed information, refer to the code example below.
```java
GTrackingManger.getInstance().completeRegistration("User_id");
GTrackingManger.getInstance().completeTutorial();
```
For detailed information on tracking events, please refer to the [Tracking Guide](./TRACKING_GUIDE.md).


