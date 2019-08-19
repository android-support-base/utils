# android-support-utils
- android utils box

## 发布
* 最新版本

| groupId | artifactId | version |
| -------- | -------- | -------- |
| com.amlzq.android | utils | [ ![Download](https://api.bintray.com/packages/amlzq/android-support-base/utils/images/download.svg) ](https://bintray.com/amlzq/android-support-base/utils/_latestVersion) |

* 使用
```
dependencies{
    ...
    implementation 'com.amlzq.android:utils:latest.integration'
}
```

## 工程结构
* ./utils
> utils library
```
package:
com.amlzq.android.util
```

* ./sample
> sample application
```
package: com.amlzq.asb
appName: Utils支持库
applicationId: com.amlzq.asb.utils
```

- 关于Util/Utils的命名规范
```
Android API是Utils，比如TextUtils.java。
amlzq android api是Util，有两点，首先防止与系统类命名冲突，其次简洁命名。
```

- [easypermissions](https://github.com/googlesamples/easypermissions)
* [PackageStats.aidl](https://android.googlesource.com/platform/frameworks/base/+/master/core/java/android/content/pm/PackageStats.aidl)
* [IPackageStatsObserver.aidl](https://android.googlesource.com/platform/frameworks/base/+/master/core/java/android/content/pm/IPackageStatsObserver.aidl)
* [编码和加密之间的区别](https://stackoverflow.com/questions/4657416/difference-between-encoding-and-encryption)
- [Android 应用中跳转到应用市场评分](https://www.jianshu.com/p/dc5f40b5466f)
- [@xml/file_paths详解](https://www.jianshu.com/p/26e253210942)
- [android设置以太网静态IP](https://github.com/qidashi/EthernetDemo)

### 其他优秀的库
- [Blankj/AndroidUtilCode](https://github.com/Blankj/AndroidUtilCode)
