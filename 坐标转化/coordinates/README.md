#### 国内常用坐标系介绍
* GCJ-02，火星坐标， 中国坐标系偏移标准，GoogleMap、高德、腾讯使用该坐标系
* WGS-84，国际坐标，国际标准，GPS原始坐标系，Google Earth使用、或者GPS模块使用
* BD-09，百度坐标，百度坐标偏移标准，百度地图使用

#### 工具类
##### 坐标转换工具类-CoordinateConvertUtil
###### 介绍
CoordinateConvertUtil是针对国内常用经纬度坐标转换方法的封装。
###### 方法
* gcj02ToBd09 方法，将GCJ-02坐标转换为BD-09坐标
* bd09ToGcj02方法，将BD09坐标转换为GCJ-02坐标
* wgs84ToGcj02方法，将WGS-84坐标转换为GCJ-02坐标
* gcj02ToWgs84方法，将GCJ-02坐标转换为WGS-84坐标
* outOfChina方法，判断坐标是否在国内

例子：
```
        Double lng = 108.872493;
        Double lat = 34.396981;
        CoordinateConvertUtil.Point point = CoordinateConvertUtil.gcj02ToBd09(lng, lat);

        System.out.println(point);// lng=108.87899162273379, lat=34.402933807867065
```
##### 经纬度格式转换工具类-CoordinateFormatUtil
* DmsTurnDD方法，将度分秒格式的坐标转换度格式的坐标
* DmTurnDD方法，将度分格式的坐标转换度格式的坐标
* getDistance方法，获取两点之间的距离
 
例子：
```
        String dms = "108°13′21";
        String dd = CoordinateFormatUtil.DmsTurnDD(dms);

        System.out.println(dd); // 108.21666666666667
```

#### 经纬度转换工具服务接口介绍
包含以下4部分：
1. 经纬度坐标转换Controller
    * BD-09    转 GCJ-02
    * GC-J02  转 BD-09
    * GC-J02  转 WGS-84
    * WGS-84 转 GCJ-02
2. 坐标格式转换Controller
    * 度分秒格式坐标转度度格式坐标，example：108°13′21″= 108.2225
    * 度分格式坐标转度度格式坐标，example：112°30.4128 = 112.50688
3. 坐标工具Controller
     * 根据两坐标计算两点之间的距离 单位：米
     * 判断坐标是否在国外
4. Excel批量转换坐标Controller
     1. Excel 批量导入模块下载
     2. Excel批量导入坐标，WGS-84坐标转GCJ-02坐标
         Excel表格中支持度分秒(108°13′21″)、度分(12°30.4128)、度(112.50688)三种格式，后台统一转换度的格式
     3. 转换后的坐标Excel导出
         如果坐标转换失败，导出Excel会提示转换失败原因。
![image.png](https://upload-images.jianshu.io/upload_images/1669182-78313076ae3d21c2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


![image.png](https://upload-images.jianshu.io/upload_images/1669182-d5ff237af75ebe77.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



