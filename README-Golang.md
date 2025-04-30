# Golang Study

## Day 1

### Common commands
- go run [file name] // 执行go文件
- for i in `seq 1 10`; do go run xxx.go; done // 循环执行xxx.go文件

### Installment 安装
- Mac
> brew install go --- 安装
> brew upgrade go --- 升级go
- Linux
> yum install go --- 安装
> yum upgrade go --- 升级go


### 查看环境变量
> go env
> go env | grep GOPATH

- 临时改变环境变量
> export GOPATH=/root/golang
- 持久改变环境变量
> echo "export GOPATH=/root/golang" >> ~/.bash_profile
> source ~/.bash_profile --- 刷新加载系统配置/属性

### package download
> https://pkg.go.dev/

### Go 注释
- // 单行注释  ---  推荐做法， 即使时多行 也建议用这种注释， 因为源码中 都是单行注释
- /**/ 多行注释
- \\\ --- 两个反斜杠用来转义

### Define fields 变量声明
- var a = 10
- var a int = 10
- a := 10

### 批量声明
var (
 name string
 age int
 available bool
)

### 多重赋值
var i, j = 10, 5
i, j = j, i // i,j 的值被交换了， i = 5, j = 10


### 短变量声明 --- 函数内部使用
name := "zhagnsan"

### 全局变量声明 --- 函数外部/内部 都可以使用
var name = "Tom"

### 匿名变量 ‘_’下划线表示
x,_ := foo()


### 常量 Const --- 值不会变的变量
const a = 100
const ( // 同时声明多个常量
    name = "Tom"
    age = 18
    age2 // 不赋值则会 与第一个常量值相同 即18
    age3 // 同上 18
)


### iota 常量计数器
```golang
const(
    n1 = iota //0
    n2 //1
    n3 //2
    n4 //3
)

const (
    m1, m2 = iota, iota // 0
    m3, m4 // m3 = 1, m4 = 1  在同一行的值是相等的
    m5, m6 // m5 = 2, m6 = 2  都为 2
	)
```

### Printer
#### Common
```text
  %v	值的默认格式表示
  %+v	类似%v，但输出结构体时会添加字段名
  %#v	值的Go语法表示
  %T	值的类型的Go语法表示
  %%	百分号
```

#### Boolean
```text
  %t	单词true或false
```

## 占位符
### 整数
```text
    - %b 二进制
    - %c convert chart to Unicode
    - %d 十进制
    - %o 八进制
    - %q 单引号围绕字符面值
    - %x 十六进制，字母小写[a-z]
    - %X 十六进制，字母大写[A-Z]
    - %U Unicode
```

### 浮点数与复数的两个组分
```text
%b	无小数部分、二进制指数的科学计数法，如-123456p-78；参见strconv.FormatFloat
%e	科学计数法，如-1234.456e+78
%E	科学计数法，如-1234.456E+78
%f	有小数部分但无指数部分，如123.456
%F	等价于%f
%g	根据实际情况采用%e或%f格式（以获得更简洁、准确的输出）
%G	根据实际情况采用%E或%F格式（以获得更简洁、准确的输出）
```

### 字符串和[]byte
```text
%s	直接输出字符串或者[]byte
%q	该值对应的双引号括起来的go语法字符串字面值，必要时会采用安全的转义表示
%x	每个字节用两字符十六进制数表示（使用a-f）
%X	每个字节用两字符十六进制数表示（使用A-F） 
```

#### 指针
```text
%p	表示为十六进制，并加上前导的0x 
```

#### 宽度
```text
%f:    默认宽度，默认精度
%9f    宽度9，默认精度
%.2f   默认宽度，精度2
%9.2f  宽度9，精度2
%9.f   宽度9，精度0 
```


### 短变量声明法， 只能在函数内部使用, 不能用来声明全局变量
```go
package main

import "fmt"
func main() {
	username := "zhagnsan"
	fmt.Println(username)
	
	a,b,c := 1,true, "Tom"
	fmt.Println(a, b, c)
	
}
```


### Format codes, 格式化代码

```shell
go fmt ./file.go
```


## 数据类型

### 基本数据类型
- 整型
- 浮点型
- bool
- 字符串
### 复合数据类型
- 数组
- 切片
- 结构体
- 函数
- map
- 通道（chanel）
- 接口

### 整型类型 int
- 有符号整型 int8, int16, int32, int64
- 无符号整型 uint8, uint16, uint32, uint64

| 类型分类  | 具体类型   | 长度(字节)             | 默认值 | 范围             |
|-------|--------|--------------------|-----|----------------|
| 有符号整型 | int8   | 1                  | 0   | -128 ~ 127     |
|       | int16  | 2                  | 0   | -2^15 ~ 2^15-1 |
|       | int32  | 4                  | 0   | -2^31 ~ 2^31-1 |
|       | int64  | 8                  | 0   | -2^63 ~ 2^63-1 |
|       | int    | 32位系统:4<br>64位系统:8 | 0   |                |
| 无符号整型 | uint8  | 1                  | 0   | 0 ~ 2^8-1      |
|       | uint16 | 2                  | 0   | 0 ~ 2^16-1     |
|       | uint32 | 4                  | 0   | 0 ~ 2^32-1     |
|       | uint64 | 8                  | 0   | 0 ~ 2^64-1     |
|       | uint   | 32位系统:4<br>64位系统:8 | 0   |                |

说明：
- rune 是 int32的别名， 常用于表示Unicode码点
- byte 是 unit8的别名，常用于处理ASCII字符或原始字节数据

## unsafe.Sizeof() 计算占用内存的字节数

## uint无符号整型， int有符号整型

## Float 浮点型
| 类型分类 | 具体类型                        | 长度(字节) | 默认值 | 范围                           |
|------|-----------------------------|--------|-----|------------------------------|
| 浮点型  | float32<br>(等价于C语言中的float)  | 4      | 0.0 | ±1.18*10^(-38) ~ 3.4*10^38   |
|      | float64<br>(等价于C语言中的Double) | 8      | 0.0 | ±2.23*10^(-308) ~ 1.8*10^308 |


### 浮点型数据算术计算精读丢失
```go
package main
import (
	"fmt"
	"github.com/shopspring/decimal"
)
func main() {
	m2 := 8.2
	m3 := 3.7
	// 精度丢失
	fmt.Println((m2 - m3), (m2 + m3)) //4.499999999999999, 11.899999999999999

	// decimal包 保证精度
	var newM = decimal.NewFromFloat(m2).Add(decimal.NewFromFloat(m3))
	fmt.Println(newM) // 11.9
}
```
### 浮点型的比较
> 在Go语言中 由于浮点数存储存在精度问题， <font color=orange>***不能直接使用==来判断***</font>两个浮点数是否相等， <br>
> 通常设定一个较小的误差范围 也叫精度， 当两个浮点数差的绝对值小于这个误差范围时<br>
> 就认为它们相等。<br>
```golang
const epsilon = 1e-9 // 0.000000001

func isEqual(f1, f2 float64) bool {
	return math.Abs(f1-f2) < epsilon
}
```


- Add 加法
- Sub 减法
- Mul 乘法
- Div 除法

## bool 布尔类型
- 布尔类型的变量默认值是false
- Go语言中不允许将整型强制转换成bool类型
- 布尔类型无法参与数值运算，也无法与其他类型进行转换

```go
package main

import "fmt"
func main() {
    var b1 bool
    fmt.Printf("b1 = %v, b1 is %T\n", b1, b1) // b1 = false, b1 is bool
}
```

### 转移字符 \
eg:
- str1 := "This is \"Tom\"" // This is "Tom"
- str2 := "C:\\Go\\Workspace\\Day01" // C:\Go\Workspace\Day01

## String 包

### 多行字符串
```go
package main

import "fmt"
func main()  {
    str1 := `
this is goog
girl,
hello worls
`
	fmt.Println(str1)
}
```

### 字符串拼接
- 直接相加 ‘+’
- 使用fmt.Fprint("%v%v") 赋新值

### 数组分割 
strings.Split(str13, "-")

### 数组拼接 
strings.Join(arr, "*")

### 是否包含
strings.contains(str1, str2)

### 前缀/后缀
strings.HasPrefix(str1, fix)
strings.HasSuffix(str1, fix)

### Golang中字符属于int类型

### Golang中字符有两种类型
- unit8类型，或者叫byte型， 代表了ASCII码的一个字符
- rune类型，代表一个UTF-8字符

```go
package main

import "fmt"

func main() {
	str31 := "中国 is a great country"
	for i := 0; i < len(str31); i++ {
		fmt.Printf("%c - %v - %T\n", str31[i], str31[i], str31[i]) // 输出乱码
	}
	fmt.Println("----------------------------")
	// 字符串遍历
	for _, v := range str31 { // 中文/日文/其他特殊字符 正确循环方式
		fmt.Printf("%c - %v - %T\n", v, v, v) //输出正常
	}
}
```

### 修改字符串
> for 循环进行遍历时，实际上你是在<b><font color='red'>逐字节地遍历</font></b>字符串，这可能会导致非 ASCII字符显示为乱码。
```go
package main

import "fmt"

func main() {

	str32 := "hello world!"
	arr3 := []byte(str32) // 普通字符 直接转换成byte切片
	arr3[0] = 'H'
	arr3[6] = 'W'
	fmt.Println(string(arr3)) // Hello World!

	str33 := "中国 how are you!"
	arr4 := []rune(str33) // 包含中文/日文/其他特殊文字 转换成rune切片
	fmt.Println(string(arr4)) // 泰国 how are you!
}
```

### 字符转换
>
- ff11 := strconv.FormatInt(int64(ff1), 10)
- ff22 := strconv.FormatBool(ff2)
- ff33 := strconv.FormatUint(uint64(ff3), 10)
- ff44 := strconv.FormatFloat(float64(ff4), 'f', 2, 64)

### 字符串转换成（int/float/bool）
- strconv.ParseInt(str, 10, 64)
- strconv.ParseFloat(str, 64)

### switch case
- fallthrough <b>向下穿透，并且只能穿透一层</b>

## 标签 Label
用于<font color=orange>***标识代码位置的标识符***</font>， 通常与goto/break 和 continue语句配合使用
注意事项
- 标签作用域： <font color=orange>***仅限于当前函数，不能跨函数使用***</font>
- 避免滥用
### continue + label
```go
package main

import "fmt"

func main() {
lable2: // 这是一个label标识
	for i := 0; i < 2; i++ {
		for j := 0; j < 10; j++ {
			if j == 3 {
				continue lable2
			}
			fmt.Printf("i = %v, j = %v\n", i, j)
		}
	}
	fmt.Println("-----------------------------")
}
```

### goto [label]

```go
package main

import "fmt"

func main() {
	age := 20
	if age >= 18 {
		goto lable3
	}
	fmt.Println("Is teenage")
lable3:
	fmt.Println("Finish")
}
```


### Array 数组
> 声明：<br>
> var arrayName [length]elementType <br>
> 赋值：<br>
> var number1 [5]int = [5]int{1,2,3}<br>
> or<br>
> number2 := [5]int{1,2,3}<br>
- 数组长度也是数组类型的一部分， [3]int 和 [5]int 不是相同的类型
- 长度自行推断，eg: arr3 := [...]float32{1, 2.0, 3.14}
- 数组一旦定义，长度不可改变
- 指定下标，eg: arr4:=[...]int{0:1, 5:100}

省略数组长度
> numbers3 :=[...]int{1,2,3} // 自动推导长度 类型是 [3]int





### Slice 切片
> 切片Slice是一种动态数组， 他是对数组的抽象， <br>
> 提供了更加方便、更灵活且高效的操作方式<br>
> 切片是一个<font color=yellow>***引用类型***</font><br>
> <font color=yellow>***它本身不存储数据， 而是指向底层的数组***</font><br>


### 数组和切片的区别
- arr := [3]int{1,2,3} // 这是数组， 类型是[3]int
- var arr = []int{1,2,3} // 这是切片，类型是[]int

### 切片的本质
> 切片的本质就是对底层数组的封装，<br>
> 包含了三个信息：<br>
> - 底层数组的指针<br>
> - 切片的长度（len）<br>
> - 切片的容量（cap）<br>


```go
package main

import "fmt"

func main() {
	arr10 := [5]int{11, 12, 13, 14, 15}
	arr11 := arr10      // 数组
	arr12 := arr10[:]   // 切片 [11,12,13,14,15]
	arr13 := arr10[2:]  // 切片 截取下标为2之后的所有元素 [13,14,15]
	arr14 := arr10[1:3] // 切片 截取下标为1-3之间的所有元素 左开右闭原则 [12,13]

	fmt.Printf("arr11 = %v, type is %T\n", arr11, arr11)
	fmt.Printf("arr12 = %v, type is %T\n", arr12, arr12)
	fmt.Printf("arr13 = %v, type is %T\n", arr13, arr13)
	fmt.Printf("arr14 = %v, type is %T\n", arr14, arr14)
}
```


### 切片的声明 --- 使用make()函数构造切片
> make([]T, size, capacity)
- T 切片的元素类型
- size 切片中元素的数量
- cap 切片的容量

### 切片的声明 --- var (nil切片)
> var s []int

### 切片的声明 --- 直接初始化
> s := []int{1,2,3}

### 切片的声明 --- 从数组或切片生成 <font color=yellow>***共享底层数组，修改会影响原数据***</font>
> arr := [5]int{1,2,3,4,5}
> s := arr[1,3] // 切片长度是2 值是{2,3}

### 切片的声明 --- 空切片
> s := []int{} // 长度为0的切片

### append() 动态添加元素，合并切片
```go
package main

import "fmt"

func main() {
	slice4 := []string{"One", "Two"}
	slice5 := []string{"Royal", "Grand"}
	// 合并
	slice6 := append(slice4, slice5...) // One, Two, Royal, Grand
	fmt.Printf("%v", slice6)
}
```

### 切片复制 copy() 函数复制切片
```go
package main

import "fmt"

func main() {
	slice7 := []int{1, 2, 55, 66}
	slice8 := make([]int, 4, 4)
	copy(slice8, slice7)
	fmt.Println(slice7) // 1,2,55,66
	fmt.Println(slice8) // 1,2,55,66
}
```

### 值类型
> <b><font color='red'>基本数据类型</font></b>和<b><font color='orange'>数组</font></b>都是值类型

```go
package main

import "fmt"

func main() {
	arr6 := [...]int{1, 2, 3}
	arr7 := arr6
	arr6[0] = 11
	fmt.Println(arr6) // [11 2 3]
	fmt.Println(arr7) // [1,2,3]
}
```

### 引用类型

### Slice使用sort包排序
```go
package main

import "fmt"
import "sort"

func main() {
	arr2 := []int{4, 1, 9, 13, 28, 0, 2}
	// 正序
	sort.Sort(sort.IntSlice(arr2))
	fmt.Println(arr2) // [0 1 2 4 9 13 28]

	fmt.Println("------------------------------------")
	arr3 := []int{4, 1, 9, 13, 28, 0, 2}
	// 倒序
	sort.Sort(sort.Reverse(sort.IntSlice(arr3)))
	fmt.Println(arr3) //[28 13 9 4 2 1 0]
}
```


### Map
> map是一种<font color=yellow>***无序的基于key-value的数据结构***</font><br>
> map是<font color=orange>***引用类型***</font>，必须初始化才能使用<br>
> map[keyType]valueType<br>
> 非线程安全，并发读写需加锁或使用 sync.Map<br>
> <font color=orange>***无序性：遍历顺序不固定***</font>，每次可能不同<br>

### map的声明方式 --- var (nil map)
> var m map[keyType]valueType 
> var m map[string]int // 声明一个nil map 底层为分配内存

### map的声明方式 --- make 函数
> - var m = make(map[keyType]valueType) // 初始化一个空map 非nil<br>
> - var m = make(map[keyType]valueType, 10) // 初始容量为10<br>

### map的声明方式 --- 直接初始化(字面量)
// 初始化 并 赋值， 支持动态添加
m := map[string]int{
 "apple": 1,
 "banana": 2,
}
// 

```go
package main

import "fmt"

func main() {
	// 1 使用make创建map类型数组
	var map1 = make(map[string]string)
	map1["name"] = "Tom"
	map1["gender"] = "male"
	fmt.Println(map1)
	fmt.Println(map1["name"])

	fmt.Println("-----------------------")

	// 2 声明时填充数据
	map2 := map[string]string{
		"name":   "Lili",
		"gender": "female",
		"age":    "18",
	}
	fmt.Println(map2)
	fmt.Println(map2["age"])
}
```
### Map 添加/修改元素
> m[key] = value // 键存在 则修改， 否则 添加

### Map中某个键是否存在 / 查询元素
> v, exists = map[key]

### Map 删除元素
> delete(map, key)

### Map 遍历元素
> for k, v := range m {
    // to something
 }

## 函数
> func sumFn1(x int, y int) int {}<br>
> func sumFn2(x, y int) int {}<br>
> <b><font color = orange>func speak(s Student) string {}</font></b> // 值传递<br>
> <b><font color = red>func speak(s *Student) string {}</font></b> // 指针传递<br>

### 函数-可变参数(切片)
> func sumFn3(x ...int) int {}

### 函数-返回值命名 / 多返回值
> func calc(x int, y int) (sum int, sub int) {} // 返回多个值
```go
package main
func calc(x int, y int) (sum int, sub int) {
	sum = x + y
	sub = x - y
	return
}
```


### 函数类型
> 使用type关键字来定义一个函数类型
> type calculation func(int, int) int

### 函数作为参数传入方法
```go
package main

import "fmt"
func main() {

	num8 := mycalc(10, 5, add)
	fmt.Printf("num8=%v, num8 is %T\n", num8, num8)
}
// 自定义一个方法类型
type myType func(int, int) int
// 方法类型作为参数
func mycalc(x, y int, cb myType) int {
    return cb(x, y)
}
// 计算方法
func add(x, y int) int {
	return x + y
}
```

### 函数作为返回值
```go
package main

import "fmt"
func main() {
	f := do("-")
	num9 := f(10, 7)
	fmt.Printf("f is %T\n", f)
	fmt.Printf("num9=%v, num9 is %T\n", num9, num9)
}

func sub(x, y int) (su int) {
	su = x - y
	return
}

func add(x, y int) int {
	return x + y
}

type doType func(x, y int) int

func do(d string) doType {
	switch d {
	case "+":
		return add
	case "-":
		return sub
	case "*":
		return func(x, y int) int {
			return x * y
		}
	default:
		return func(x, y int) int {
			return x / y
		}
	}
}
```

### 匿名自执行函数
```go
package main

import "fmt"

func main() {

	// 匿名自执行函数
	func() {
		fmt.Println("这是一个匿名自执行函数")
	}()
}
```
### 匿名函数
```go
package main

import "fmt"

func main() {
	// 匿名函数
	var fn = func(x, y int) int {
		return x + y
	}
	num11 := fn(10, 4)
	fmt.Println(num11)
}
```

### 函数闭包
- 可以让一个变量常驻内存
- 可以让一个变量不污染全局
```go
package main

import "fmt"

func main() {
	var num2 = fn2()
	fmt.Println(num2(10)) // 20
	fmt.Println(num2(10)) // 30
	fmt.Println(num2(10)) // 40
}
func fn2() func(y int) int {
	var x = 10
	return func(y int) int {
		x += y
		return x
	}
}
```

## 错误处理
显式的错误返回值方式来处理错误

## Defer
### 函数-defer
> defer语句会将其后边<font color=yellow>***跟随的语句延迟处理***</font>，<br>
> 在defer归属的函数即将返回时，将其延迟处理<br>
> defer注册要延迟执行的函数时，该函数所有的参数都要<br>
> <b><font color='orange'>确定其值</font></b><br>

### defer 应用场景
- 文件操作
- 网络连接
- 数据库连接操作 后使用defer来保证 资源在函数结束时被正确释放， 避免资源泄露

```go
package main

import "fmt"

func main() {
	num4 := fun4() //  5
	fmt.Println(num4)
}
func fun4() (x int) {
	defer func(x int) {
		fmt.Printf("x = %v\n", x)
		x++
	}(x)
	return 5
}
```

```go
package main

import "fmt"

func main() {

	fun5()
}
/*
注册顺序
defer calc2("AA", x, calc2("A", x, y))
defer calc2("BB", x, calc2("B", x, y))

执行顺序
defer calc2("BB", x, calc2("B", x, y))
defer calc2("AA", x, calc2("A", x, y))

确定值
1. calc2("A", x, y) // A 1 2 3
2. calc2("B", x, y) // B 10 2 12
延迟执行
3. calc2("BB", x, calc2("B", x, y)) // BB 10 12 22
4. calc2("AA", x, calc2("A", x, y)) // AA 1 3 4
 */
func fun5() {
	x := 1
	y := 2
	defer calc2("AA", x, calc2("A", x, y))
	x = 10
	defer calc2("BB", x, calc2("B", x, y))
	y = 20
}

func calc2(str string, x, y int) (sum int) {
	sum = x + y
	fmt.Println(str, x, y, sum)
	return
}
```

## Panic
panic 是一个内置函数


### panic recover
recover 也是一个内置函数， 用于从panic状态中恢复。
它智能在defer函数中使用。 因为只有在defer函数中调用Recover
才能捕获到当前函数或其他调用栈中的panic

> ***谨慎使用panic和recover***， <br>
> Go中更推荐使用返回错误值的方式处理可预期的错误<br>

```go
package main

import "fmt"

func main() {

	fmt.Println("-----start-----")
	panicTest()
	fmt.Println("-----end-----")
}

func panicTest() {
// 匿名函数来捕获 recover 中的错误
	defer func() {
		var a = recover()
		if a != nil {
			fmt.Printf("err = %v, err is %T\n", a, a) //err = 抛出一个异常, err is string
		}
	}()

	panic("抛出一个异常")
}
```


### time

### time format
> format: 2006-01-02 03:04:06
> 2006      year
> 01        month
> 02        day
> 03/15     hour 03(12小时制) 15(24小时制)
> 04        minute
> 05        second

### time 时间戳日期互转
```go
package main

import (
	"time"
	"fmt"
)
func main() {
	unixTime := 1739799164000
	timeObj := time.UnixMilli(int64(unixTime))
	timeStr := timeObj.Format("2006-01-02 15:04:05")
	fmt.Printf("timeObj=%v, type is %T\n", timeObj, timeObj) //timeObj=2025-02-17 17:32:44 +0400 +04, type is time.Time
	fmt.Println(timeStr) //2025-02-17 17:32:44
}
```

### time 日期字符串 转换为 时间对象
> time.ParseInLocation()
```go
package main

import (
	"time"
	"fmt"
)
func main() {
	dateStr := "2025/02/17 18:00:23"
	format := "2006/01/02 15:04:05"
	newDateObj, _ := time.ParseInLocation(format, dateStr, time.Local)
	fmt.Println(newDateObj) //2025-02-17 18:00:23 +0400 +04
}
```

### time 增加2小时
>  time2.Add(time.Hour * 2)
```go
package main

import (
	"time"
	"fmt"
)
func main() {
	time2 := time.Now()
	fmt.Println(time2) // 2025-02-17 17:55:31.3679043 +0400 +04 m=+0.006893701
	//增加两小时
	time3 := time2.Add(time.Hour * 2)
	fmt.Println(time3) // 2025-02-17 19:55:31.3679043 +0400 +04 m=+7200.006893701
}
```

### time ticker
```go
package main

import (
	"fmt"
	"time"
)

func main() {
	//实例一个定时器
	ticker := time.NewTicker(time.Second)
	fmt.Printf("ticker = %v, type is %T\n", ticker, ticker)//ticker = &{0xc000076000 true}, type is *time.Ticker

	fmt.Println("-----------------------")
	n := 5
	for v := range ticker.C {
		fmt.Printf("v = %v, type is %T\n", v, v)
		n--
		// 终止定时器 并跳出
		if n <= 0 {
			ticker.Stop()
			break
		}
	}
	fmt.Println("------------end-----------")
}

```


### pointer 指针地址
> var a = new(int) 指针是引用数据类型 
> 指针是一个变量， 但是它是一种特殊的变量，他存储的数据不是一个普通的值，而是另一个变量的内存地址
```go
package main

import "fmt"

func main() {
	num1 := 10
	num2 := &num1
	//num1 = 10, type is int, pointer is 0xc00000a0e8
	fmt.Printf("num1 = %v, type is %T, pointer is %p\n", num1, num1, &num1)
	
	// num2 = 0xc00000a0e8, type is *int 
	fmt.Printf("num2 = %v, type is %T\n", num2, num2)

	fmt.Println("-----------------------------")

	// 局部变量
	num3 := 20
	// 指针变量
	// num4 的值 是 num3的内存地址
	num4 := &num3
	// 通过*num4, 取出num4这个变量对应的内存地址的值
	fmt.Println(*num4) // 20
	// 改变num4这个变量对应的内存地址的值 即num3的值
	*num4 = 30
	fmt.Println(num3) // 30
}
```

> num1 = 10
> p := &num1 p是指针变量
> *int 指针类型
> *p = 20 //则 修改了 num1=20

### 指针变量实例
> new方法给指针变量分配存储空间
```go
package main

import "fmt"

func main() {
	var num5 *int
	num5 = new(int)
	*num5 = 100
	fmt.Println(num5, *num5) // 0xc00000a138 100
}
```


### 自定义类型 与 类型别名
```go
package main

import "fmt"

func main() {
	// 自定义类型
	type myInt int
	// 类型别名
	type myFloat = float64

	var a myInt = 10

	var b myFloat = 12.3

	fmt.Printf("%v %T\n", a, a) // 10 main.myInt
	fmt.Printf("%v %T\n", b, b) // 12.3 float64
}
```

## struct

### struct 结构体
> <b><font color='red'>Attention !!!</font></b></br>
> 结构体首字母可以是大写(Uppercase)也可以是小写(Lowercase)</br>
> <b><font color='green'>大写(Uppercase)表示结构体是公有的</font></b>，其他包也可以使用</br>
> <b><font color='orange'>小写(Lowercase)表示结构体是私有的</font></b>，只有这个包里才能使用</br>
```go
package main

import "fmt"

func main() {
	// 实例化Person结构体
	var p1 Person 
	p1.name = "Tom"
	p1.sex = "male"
	p1.age = 20
	fmt.Println(p1)         // {Tom 20 male}
	fmt.Printf("%#v\n", p1) // main.Person{name:"Tom", age:20, sex:"male"}
}

// Person 结构体定义
type Person struct {
	name string
	age  int
	sex  string
}
```

### struct 结构体实例化方式
- var p Person // 值类型
- var p = new(Person) // 指针类型
- var p = &Person{} // 指针类型
- var p = Person{..k-v..} // 值类型
- var p = &Person{..k-v..} // 指针类型
- var p = &Person{..v..} // 指针类型 v需要保证顺序

### struct - func 结构体方法
> func (接收者变量，接收者类型) 方法名(参数列表) (返回类型){}
```go
package main

import "fmt"

func main() {
	
}
type Person struct {
	name string
	age  int
	sex  string
}
func (p Person) PrintInfo() {
	fmt.Printf("Name is %v, Age is %v\n", p.name, p.age)
}

func (p Person) PrintFullInfo(age int, gender string) string {
	return fmt.Sprintf("Name = %v, Age = %v, Gender = %v", p.name, age, gender)
}
```

### struct - func 指针传递
```go
package main2

import "fmt"

func main() {
	p4 := Person {
		name: "July",
		age:  14,
    }
    p4.PrintInfo() // Name is July, Age is 14
    p4.SetPersonInfo("Coco", 28)
    p4.PrintInfo() // Name is Coco, Age is 28
}


type Person struct {
	name string
	age  int
	sex  string
}

func (p Person) PrintInfo() {
	fmt.Printf("Name is %v, Age is %v\n", p.name, p.age)
}

// SetPersonInfo 指针传递
func (p *Person) SetPersonInfo(name string, age int) {
	p.name = name
	p.age = age
}

```

### struct 匿名结构体
> 嵌套结构体</br>
> 当访问结构体成员时会先在结构体中寻找该字段，如果找不到回到匿名结构体中寻找</br>
> 但是当多个匿名结构体中都有该字段时，则必须指定结构体类型</br>
```go
package main
type User struct {
	string
	int
}
```

### struct 结构体嵌套 继承
```go
package main

import "fmt"

type Animal struct {
	name string
}

func (a Animal) run() {
	fmt.Printf("%v is running\n", a.name)
}

type Dog struct {
	Age     string
	*Animal // 结构体嵌套 继承
}

func (d Dog) bark() {
	fmt.Printf("%v is barking\n", d.name)
}

func main() {
	var d = Dog{
		Age: "10",
		Animal: &Animal{
			name: "Puppy",
		},
	}
	e := d
	fmt.Printf("%#v\n", d)
	d.run() // Puppy is running
	d.bark() // Puppy is barking
	e.name = "Eldor"
	d.run()  // Eldor is running
	d.bark() // Eldor is barking

}

```


### struct json
> 私有属性不能被json包访问</br>
> JSON序列化是指 吧结构体数据转化为JSON格式字符串</br>
> 序列化方法
```go
 json.Marshal()
 ```
> JSON反序列化就是将JSON字符串转化成结构体对象
```go
json.Unmarshal()
```
### struct tag 结构体标签tag
> 属性： Name string `json:"name"`


## 指针 Pointer
> 指针（Pointer） 是一种直接操作内存地址的数据类型，用于间接访问变量的值。

### 指针的基本概念
- 指针本质：存储变量内存地址的变量。
- 核心作用：通过指针可以直接修改变量的值，<font color=orange>***避免值拷贝，提升性能***</font>。
- 内存安全：Go 的指针设计相对安全（无指针运算），依赖垃圾回收机制（GC）管理内存。

### 指针操作符
- & 取址符：获取变量的内存地址。
- '*' 解引用符：访问指针指向的内存中的值。
```golang
a := 42
ptr := &a      // ptr 存储 a 的地址
fmt.Println(*ptr) // 输出 42（通过指针读取值）
*ptr = 100     // 通过指针修改 a 的值
fmt.Println(a) // 输出 100
```

### 指针的声明 --- 
var pointer *DataType
eg: var intPtr *int


## 流程控制
- if else
- switch case

### For 循环
- for int i=0;i <100;i ++ { //循环体}
- for i < 10 {//循环体} // 模拟while循环
- for {//循环体} // 无限循环

### for range 循环
用于迭代数组/切片/字符串/map/channel
for index,value := range numbers {//循环体}

## Golang 中包的介绍和定义
- 系统内置包
- 自定义包
- 第三方包

### package 包的定义
- package 语句 只能放在文件的<b>第一行</b>
- 一个文件夹下 直接包含的文件只能归属于一个package
- 包名可以与文件夹名称<b><font color=red>不一致</font></b>
- 包名为main的包为应用程序的入口包，这种包编译后会得到一个可执行文件
- 共一个包下的 所有文件中的<b><font color=red>公共方法名</font></b>不能重复  

#### go mod init 初始化项目
> <b><font color=red>go mod init [path]</font></b>
> eg: go mon init test_02

#### package 包别名
> import (
J "encoding/json"
)

#### package 匿名引入一个包
下划线 作为别名
> import (
_ "encoding/json"
)

#### package 导入一个包
import "fmt"

#### package 导入多个包
import (
    "fmt"
    "math"
)

#### 下载安装包
> go get  url 
> eg: go get github.com/shopspring/decimal
> go install url

### 下载丢失的包 移除未使用的包
> <b><font color=red>go mod tidy</font></b>  :add missing and remove unused modules

### Init() 初始化函数
> GO语言程序执行<b>导入包</b>语句时,会自动触发init()函数的调用。不能在代码中主动调用它。


## Interface 接口
> 接口就是<b>一种数据类型</b>， 不需要显示的实现<br>
> 接口里不能有变量名<br/>
> 接口里的方法，必须通过<b>结构体</b>或者<b>自定义类型</b>实现这个接口<br/>
> 接口是一个规范<br>
> 实现一个接口，必须实现接口内的所有方法<br>
> 空接口，即不定义任何方法的接口，表示没有任何约束, <b><font color=orange>可以表示任何类型</b></font><br>
> 空接口作为<b>方法的参数</b>时，表示该方法可以接受任何类型

### interface 空接口
```go
package main

import "fmt"
func main() {
	var i interface{} // 定义一个空接口
	// 可以赋予任何类型
	i = 10
	i = "This is B"
	i = true
	fmt.Println(i)
}
```

### 空接口作为方法的参数
```go
package main

import "fmt"
func show(a interface{}) {
	fmt.Printf("%v, %T\n", a, a)
}

func main() {
	show(10) //10, int
	show("This is B") // This is B, string
	show(true) // true, bool
	show([]int{10, 3, 7, 11}) // [10 3 7 11], []int
}
```

### 空接口 使 slice/map 类型数据的值多样化
```go
package main

import "fmt"

func show(a interface{}) {
	fmt.Printf("%v, %T\n", a, a)
}

func main() {
    var m = make(map[string]interface{})
	m["name"] = "Tome"
	m["age"] = 18
	m["gender"] = "female"
	m["statue"] = false
	show(m) // map[age:18 gender:female name:Tome statue:false], map[string]interface {}
	fmt.Println("--------------------------")
	s := []interface{}{1, true, 'a', "this is content"} // 可以接受任何类型
	show(s) // [1 true 97 this is content], []interface {}
}
```


### 类型断言
> x.(T)
> X 表示类型为interface{}的变量
> T 表示断言X可能是的类型
```go
package main

import "fmt"

func main() {
	var a interface{}
	a = "Tom"
    if _,ok := a.(string);ok{
		fmt.Println("Is string") // ok
    } else if _,ok := a.(int);ok {
		fmt.Println("Is int")
    }
}
```
### 断言的另一种写法
> <b><font color=red>x.(type) 判断一个变量的类型，只能用在switch语句中</font></b>
```go
package main
//import "fmt"
func printType(x interface{}) {
	// x.(type) 判断一个变量的类型，只能用在switch语句中
	switch x.(type) {
	case int:
		//fmt.Printf("%v is int\n", x)
	case float64, float32:
		//fmt.Printf("%v is float64/float32\n", x)
	case string:
		//fmt.Printf("%v is string\n", x)
	case bool:
		//fmt.Printf("%v is bool\n", x)
	default:
		//fmt.Printf("%v is others\n", x)
	}
}
```

### interface 接口嵌套
```go
package main

type AInterface interface {
	SetName(s string)
}

type BInterface interface {
	GetName() string
}

type Animaler interface {
	AInterface
	BInterface
}
```



## Reflect 反射

### var v = reflect.TypeOf() 获取<b>任意值的类型对象（reflect.Type）</b>

### v.Name() <b>类型名称</b>

### v.Kind() <b>类型种类</b>，是指底层的类型

### 通过反射 reflect.ValueOf()获得原始值
```go
func reflectType3(x interface{}) {
	v := reflect.ValueOf(x)
	//获取种类
	switch v.Kind() {
	case reflect.Int, reflect.Int8, reflect.Int16, reflect.Int32, reflect.Int64:
		fmt.Println(v.Int() + 1)
	case reflect.Float64, reflect.Float32:
		fmt.Println(v.Float() + 1)
	case reflect.String:
		fmt.Println("new string = ", v.String())
	default:
		fmt.Println("---others---")
	}
}
```

### reflect.ValueOf().Elem()
> 用于获取一个指针引用的元素，即指针指向的值

### 通过反射 设置变量的值
> v := reflect.ValueOf(x)
> v.Elem().setInt() / setFloat() / setString() / ...
> 


### 结构体反射

### 根据类型变量(reflect.TypeOf(x))获取结构体字段
- v.Field(0) 
- v.FieldByName("Age")

### 根据类型变量(reflect.TypeOf(x))获取结构体字段树
- v.NumField()

### 根据值类型变量(reflect.ValueOf(x))获取结构体属性对应的值
- vv.FieldByName("Name")
- vv.Field(0)

### 根据类型变量 获取结构体Method信息
- m1 := v.Method(0) // 和结构体方法顺序没有关系， 和结构体方法ASCII有关系

### 通过值变量 执行方法
- v.Method(1).Call(nil)
- params := []reflect.Value{}

### codes
```go
package main

import (
	"fmt"
	"reflect"
)

type Student4 struct {
	Name string `json:"name" wsf:"coocpu"`
	Age  int    `json:"age"`
}

func resetValue3(s interface{}) {
	v := reflect.TypeOf(s)
	fmt.Println(v, v.Kind())
	if reflect.Struct != v.Kind() && v.Elem() != nil && reflect.Struct != v.Elem().Kind() {
		fmt.Println("It isn't struct")
		return
	}
	f := v.Field(0)
	fmt.Println(f)
	fmt.Printf("%#v\n", f)
	// get field's name
	fmt.Println(f.Name)
	// get field's type
	fmt.Println(f.Type)
	// get field's tag
	fmt.Println(f.Tag.Get("json"))
	fmt.Println(f.Tag.Get("wsf"))
	f2, ok := v.FieldByName("Age")
	fmt.Println("-------------------------")
	if ok {
		fmt.Println(f2.Name)
		// get field's type
		fmt.Println(f2.Type)
		// get field's tag
		fmt.Println(f2.Tag.Get("json"))
	}
	fmt.Println("-------------------------")
	num := v.NumField()
	fmt.Println("Field number is ", num)
	fmt.Println("------------根据值类型变量 获取结构体属性对应的值-------------")
	vv := reflect.ValueOf(s)
	fmt.Println(vv.FieldByName("Name"))
	fmt.Println(vv.FieldByName("Age"))
	fmt.Println(vv.Field(0))
	fmt.Println(vv.NumField())
	f3 := vv.FieldByName("Name")
	fmt.Println(f3.Kind())
}

func main() {

	s := Student4{
		"Tom",
		18,
	}
	//resetValue3(1)
	resetValue3(s)
}

```




## 文件读取 --- OS包

> mode:
- os.O_WRONLY 只写
- os.O_CREATE 创建文件
- os.O_RDONLY 只读
- os.O_RDWR 读写
- os.O_TRUNC 清空
- os.O_APPEND 追加
> perm: 文件权限———— 一个八进制数。r(Read)04, w(Write)02, x(Execute)01.

### 读取方式1 file.read(), 流的方式读取
> <b>只读方式</b>打开文件

### 读取方式2 buffer io , bufio.newReader(file), 流的方式读取

### 读取方法3 os.ReadFile(), 一次性读取

```go
package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
)

func writeFile1(path, content string) {
	file, err := os.OpenFile(path, os.O_APPEND, 0666)
	defer file.Close()
	if err != nil {
		fmt.Println("打开文件失败，", err)
		return
	}
	for i := 0; i < 5; i++ {
		_, err := file.WriteString(content + " - " + strconv.Itoa(i+1) + "\n")
		if err != nil {
			fmt.Println("写入失败，", err)
			return
		}
	}

	fmt.Println("Success!!!")
}

func writeFile2(path, content string) {
	file, err := os.OpenFile(path, os.O_RDWR|os.O_CREATE|os.O_TRUNC, 0666)
	defer file.Close()
	if err != nil {
		fmt.Println("打开文件错我,", err)
		return
	}
	writer := bufio.NewWriter(file)
	for i := 0; i < 5; i++ {
		_, e := writer.WriteString(content + strconv.Itoa(i+1) + "\n")
		if e != nil {
			fmt.Println("write error,", err)
			return
		}
	}
	// flush 将缓存中的内容，写入文件
	writer.Flush()
	fmt.Println("Success!!!")

}
func main() {
	filePath := "../files/names.txt"
	writeFile1(filePath, "这是增加的写入内容：abcd")

	filePath2 := "../files/names_2.txt"
	writeFile2(filePath2, "这是增加的写入内容：Have a great time!")

}

```


#### 采用流的方式copy文件 复制文件
```go

func copyFile2(sourcePath, targetPath string) error {

	sourceFile, sErr := os.Open(sourcePath)
	//必须关闭
	defer sourceFile.Close()
	if sErr != nil {
		return sErr
	}
	targetFile, tErr := os.OpenFile(targetPath, os.O_CREATE|os.O_WRONLY|os.O_APPEND, 0666)
	//必须关闭
	defer targetFile.Close()
	if tErr != nil {
		return tErr
	}

	buff := make([]byte, 16)
	for {
		sLen, sE := sourceFile.Read(buff)
		// end of reading
		if sE == io.EOF {
			break
		} else if sE != nil {
			return sE
		} else {
			_, tE := targetFile.Write(buff[:sLen])
			if tE != nil {
				return tE
			}
		}
	}
	return nil
}
```

### 创建目录
- 创建一级目录 os.Mkdir(path, 0666)
- 创建多级目录 os.MkdirAll("../aaa/abc/aa", 0666)
- 删除一级目录 os.Remove("./abc")
- 删除多级目录 os.RemoveAll("./abc/ab/a")


## 标准库
- fmt //fmt.Scan(&name, &age) 获取命令行输入内容
- OS // 文件操作/环境变量访问/进程管理
- IO/ioutil
- Net/http包
- Encoding/json包 // json数据的编码和解码功能


### json
- json.Marshal // 结构体 编码 为json字节切片
- json.Unmarshal // json字节切片解码为Go结构体


