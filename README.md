
[![](https://jitpack.io/v/baowenqing/MvvmHelper.svg)](https://jitpack.io/v/baowenqing/MvvmHelper)
[![api](https://img.shields.io/badge/API-21+-brightgreen.svg)](https://android-arsenal.com/api?level=21)
[![Issue](https://img.shields.io/github/issues/baowenqing/MvvmHelper.svg)](https://github.com/baowenqing/MvvmHelper/issues)
[![Star](https://img.shields.io/github/stars/baowenqing/MvvmHelper.svg)](https://github.com/baowenqing/MvvmHelper)

```
//集成该框架   例如：

allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}

dependencies {
		implementation 'com.github.baowenqing:MvvmHelper:1.0.0'
	}
```

> jdk8编译期间会报错，选到jdk11运行

### 打包相关

```shell

//打包正式环境
gradlew assembleRelease

//打包测试环境
gradlew assembleDebug


关闭应用
adb shell am force-stop xxx
重启
adb  reboot

adb connect  192.168.20.41:5559

开始抓logcat日志  ctrl+C 停止抓取
adb  logcat > log.txt
adb  logcat  BaseSingEngine:I -s

获取设备dp
adb shell getprop ro.sf.lcd_density
创建文件夹以及文件
mkdir temp
touch log.txt

查看到系统的启动app信息  可以用来隐式或者显式打开app
adb  shell
logcat | grep START

//卸载应用
adb uninstall xxx
 

连接夜神模拟器
adb  connect  127.0.0.1:62001
连接海马玩模拟器
adb connect 127.0.0.1:53001
连接木木模拟器
adb  connect  127.0.0.1:7555


清单文件异常
gradlew processPadDebugManifest --stacktrace

//打开开发助手
adb shell am start -n   cn.trinea.android.developertools/c.b.a
adb shell am start -a android.media.action.STILL_IMAGE_CAMERA  启动camera
//打开设置
adb shell am start com.android.settings/com.android.settings.Settings
//打开录音机
adb shell am  start  com.android.soundrecorder/.SoundRecorder
//浏览器
adb shell  am start   com.android.browser/com.android.browser.BrowserActivity

//本地储存卡补丁路径 【需要把jar放进去】
adb push  sophix-patch.jar  /storage/emulated/0/sophix-patch.jar
adb shell input text  "123"
  

查看md5 和 sha1  输入密码就可以获取了
keytool -list -v -keystore keystore/release_keystore.keystore

```




