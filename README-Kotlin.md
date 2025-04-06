# Kotlin 

## 基础语法介绍

- 变量声明
var [var_name] : [type] 
or 
var [var_name] = value
```kotlin
var name : String
var age = 10
```

- 


### 数据类型介绍
| 类型    | 大小(位) | 最小值    | 最大值    |
|:------|:-----:|--------|--------|
 | Byte  |   8   | -128   | 127    |
| Short |  16   | -32768 | 32767  |
| Int   |  32   | -2^31  | 2^31-1 |
| Long  |  64   | -2^63  | 2^63-1 |


> 一个字节byte占8位
> var num1 = 1_000_000 等同于 1000000

### 各类进制的表示
- 二进制 var num = 0b1111 ---- 15
- 十六进制 var num = 0x10 ---- 16
暂时不支持8进制的直接表示。
- 无符号整数： 
> - var num : UInt = 256u
> - var num : UByte = 10u
> - var num : ULong = 100u
> - var num : UShort = 10u


### 浮点型类型介绍
| 类型     | 大小(位) | 符号与尾数位数 | 阶码位数 | 小数位数  |
|:-------|:-----:|---------|------|-------|
| Float  |  32   | 24      | 8    | 6-7   |
| Double |  64   | 53      | 11   | 15-16 |


### 位运算
- 与运算(and)
> - eg: 9 and 3 = ?
> - 9 --> 1001
> - 3 --> 0011
> - 结果-->0001 --> 9 and 3 = 1

- 或运算 or
- 按位异或 xor
- inv() --- 取反
- shl --- 有符号左移
- shr --- 有符号右移



### 字符类型 单引号囊括 ‘a’
var c : Char = 'a'

### 字符串类型 双引号 囊括 “Hello”
var str : String = "Hello world!!!"

字符串拼接 ${var}
eg: var result = "${str}, hello everyone."

