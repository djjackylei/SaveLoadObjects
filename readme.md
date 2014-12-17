Android Save Object to file and Load Object from file
===================

This is utils class to save java object to file and load Object from file

----------
Feature
-------------
```
1. Encryption disable model

1)the java object will toJson using Jackson ObjectMapper,and save the json string in the file
2)load Object:read the string from the file and transfer the string(json format) to Object

2.Encryption enable model

In order to protect the file, encryption the file is need.
```

----------
Usage

```
1.Save a object without encryption

boolean isSucc = FileUtils.getInstance().saveObject(car,filePath, false);

if the isSucc is true,the the object 'car' save on the file.
```
```
2.Load a object without encryption

Car car = (Car) FileUtils.getInstance().loadObject(filePath, Car.class, false);

```
```
3.Save a object with encryption

boolean isSucc = FileUtils.getInstance().saveObject(car,filePath, true);

if the isSucc is true,the the object 'car' save on the file.
```
```
4.Load a object with encryption

Car car = (Car) FileUtils.getInstance().loadObject(filePath, Car.class, true);

```
```
5.Conclusion

you can open the file(filePath) with a text tools to compare the encryption and without it.

```

License
-------------

Copyright 2014  [Zheng Haibo](https://github.com/nuptboyzhb/)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License. 
