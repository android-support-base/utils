# android-support-utils
* dependencies JDK
* dependencies Android Platform API
* dependencies Android Support API

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

##
* 
| 功能 | 类 | 替代方案 |
| -------- | -------- | -------- |
| 时间 | DataUtil | null |
| 剪切板 | ClipboardUtil | null |
| 运行时权限 | PermissionChecker | [easypermissions](https://github.com/googlesamples/easypermissions) |

## 工程结构
* ./utils
> utils library
```
package:
com.amlzq.android.util
class package:
com.amlzq.android.checker
com.amlzq.android.context
com.amlzq.android.crash
com.amlzq.android.errorprone
com.amlzq.android.guava
com.amlzq.android.security
com.amlzq.android.util
```

* ./sample
> sample application
```
package: com.amlzq.asb
appName: Utils支持库
applicationId: com.amlzq.asb.utils
```

## debug.keystore
```
密钥库类型: JKS
密钥库提供方: SUN

您的密钥库包含 1 个条目

别名: androiddebugkey
创建日期: 2016-4-8
条目类型: PrivateKeyEntry
证书链长度: 1
证书[1]:
所有者: CN=Android Debug, O=Android, C=US
发布者: CN=Android Debug, O=Android, C=US
序列号: 75c76b4d
有效期开始日期: Fri Apr 08 15:03:55 CST 2016, 截止日期: Sun Apr 01 15:03:55 CST 2046
证书指纹:
         MD5: D1:91:78:57:57:FD:45:42:C0:FE:DD:7C:E0:A2:DD:26
         SHA1: 6E:1A:9A:60:20:26:22:1D:DB:03:0A:5E:9E:BB:F6:57:9F:A0:23:61
         SHA256: AC:30:C8:63:B7:90:5D:BF:64:28:0D:9B:EE:83:09:7A:AB:AD:44:69:CC:91:4D:11:71:67:41:3A:14:B1:98:78
         签名算法名称: SHA256withRSA
         版本: 3

扩展:

#1: ObjectId: 2.5.29.14 Criticality=false
SubjectKeyIdentifier [
KeyIdentifier [
0000: 61 26 31 CA EA 07 A8 A6   1F 7F 41 BE 26 64 97 EB  a&1.......A.&d..
0010: A7 EA D9 47                                        ...G
]
]



*******************************************
*******************************************



```

- 关于Util/Utils的命名规范
```
android api规范是Utils
amlzq android api规范是Util，有两点，首先防止与系统类命名冲突，其次简洁命名。
```

* [PackageStats.aidl](https://android.googlesource.com/platform/frameworks/base/+/master/core/java/android/content/pm/PackageStats.aidl)
* [IPackageStatsObserver.aidl](https://android.googlesource.com/platform/frameworks/base/+/master/core/java/android/content/pm/IPackageStatsObserver.aidl)
* [编码和加密之间的区别](https://stackoverflow.com/questions/4657416/difference-between-encoding-and-encryption)
- [Android 应用中跳转到应用市场评分](https://www.jianshu.com/p/dc5f40b5466f)
- [@xml/file_paths详解](https://www.jianshu.com/p/26e253210942)

- [android设置以太网静态IP](https://github.com/qidashi/EthernetDemo)


- 注解开发
```
```
- [Java 注解 （Annotation）你可以这样学](https://blog.csdn.net/briblue/article/details/73824058)

- 文件存储问题
```
Android 外部存储目录问题，图片文件夹
```
