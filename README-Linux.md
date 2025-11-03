# Linux Study 

## 1 基本命令

- 删除多行
> vi xx.txt
> :23,144d --- linux文件编辑器中删除多行(23-144行)



- uname -a  内核名称和版本
- lsb_release -a 系统发行版的ID、描述和版本号
- hostnamectl 操作系统、内核、架构
- lscpu 显示CPU架构信息，如CPU型号、核心数、线程数等
- 


## 1.0 常用命令
vi | vim 启动Linux中的文本编辑器，vim是vi的增强版
- a 追加模式
- i 插入模式
- o 在光标所在位置之下插入一行
- O 在光标所在位置之上插入一行
- dd 删除当前行
- :w 保存 
- :q 退出
- :wq 保存并退出
- :q! 强制退出
- :set nu 显示行号
- /keywords 搜索   eg: /man   及搜索而后光标定位到man的位置， ?man向上搜索
- G 光标跳到文件末尾
- gg 光标跳到文件头

系统命令
- getconf LONG_BIT 查看系统位数

## 1.0 压缩与解压缩
1. tar 命令
- -c 创建压缩文件
- -C 指定压缩文件存放位置
- <font color=orange><b>-x 解压</b></font>
- -t 列出所有文件
- -z 有gzip属性
- -v 显示所有过程
- <font color=red>-f 使用压缩或者解压缩<b>文件的名字</b>，这个参数是最后一个参数， 后边只能接文件名</font>

eg: tar -cvf a.tar.gz a.txt  将a.txt压缩成a.tar.gz
eg: tar -xvf a.tar.gz 解压a.tar.gz到当前目录下
eg: tar -xvf a.tar.gz -C /home/user/  解压到指定目录下

2. unzip 命令
- -d 指定解压存放位置
- -n 解压缩时不要覆盖原有文件
- -v 执行时显示详细信息

eg: unzip a.zip -d /home/user/ 解压a.zip到/home/user/



## 常用配置
- date 查看当前时间
- date -s '20251103 21:54:32'  设置系统时间
- nmcli c up ens33 启动网络 -- 临时启动（重启系统后 失效）
- vi /etc/sysconfig/network-scripts/ens33  <font color=red>修改文件 onboot=yes 即可</font>
- ifconfig 查看网络地址

### 1.1 启动/停止/重启/查看/自启动   服务

```bash
# 启动服务
sudo systemctl start 服务名

# 停止服务
sudo systemctl stop 服务名

# 重启服务
sudo systemctl restart 服务名

# 查看服务状态
sudo systemctl status 服务名

# 设置开机自启
sudo systemctl enable 服务名

# 禁用开机自启
sudo systemctl disable 服务名

# 查看是否启用开机启动
sudo systemctl is-enabled 服务名
```



**Java服务**

download java-8-openjdk-devel.tar.gz and
tar -xvf java-8-openjdk-devel.tar.gz -C /usr/local/jdk
vi /etc/profile -- 修改环境变量， 目的是将java安装目录添加到环境变量中
```
export JAVA_HOME=/usr/local/jdk/jdk1.8.0_181
export CLASSPATH=$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
export PATH=$PATH:$JAVA_HOME/bin
```
source /etc/profile --- 使配置文件 立即生效

```bash
# 启动Spring Boot jar包
java -jar your-application.jar

# 后台运行并输出日志
nohup java -jar your-application.jar > app.log 2>&1 &

# 带JVM参数启动
java -Xms512m -Xmx1024m -jar your-application.jar
```



Nginx

```bash
# 启动
sudo systemctl start nginx

# 测试配置
sudo nginx -t

# 重载配置（不中断服务）
sudo nginx -s reload
```



### 1.2 **查看所有服务状态**

```bash
sudo systemctl list-units --type=service
```

### 1.3 **查找服务名**

```bash
sudo systemctl list-unit-files | grep 关键字
```

### 1.4 **查看服务日志**

```bash
sudo journalctl -u 服务名 -f  # 实时查看
sudo journalctl -u 服务名 --since "2023-01-01" --until "2023-01-02"
```

### 1.5 **服务启动失败排查**

```bash
sudo systemctl status 服务名
sudo journalctl -xe
```







### 1.6 SCP文件上传/下载



```
# 文件上传到远程服务器
scp /path/to/local/file username@remote_host:/path/to/remote/directory
eg:
scp ~/document.txt user@192.168.1.100:/home/user/


# 从远程服务器下载文件
scp username@remote_host:/path/to/remote/file /path/to/local/directory
eg:
scp user@192.168.1.100:/home/user/document.txt ~/Downloads/




```



### 1.7 **`rsync`（增量同步，高效）**

- **上传文件/目录**

  ```bash
  rsync -avz /path/to/local/file_or_folder user@remote_host:/path/to/remote/
  ```

- **下载文件/目录**

  ```bash
  rsync -avz user@remote_host:/path/to/remote/file_or_folder /path/to/local/
  ```



**参数说明**：

- `-a`：归档模式（保留权限、时间戳等）。
- `-v`：显示详细过程。
- `-z`：压缩传输。





### 1.8 压缩文件/目录 .tar   .tar.gz   .zip

```
# 打包为 .tar
tar -cvf archive.tar /path/to/folder

# 压缩为 .tar.gz
tar -czvf archive.tar.gz /path/to/folder

# 压缩为 .zip
zip -r archive.zip /path/to/folder
```





### 1.9 解压文件  .tar   .tar.gz   .zip



```
# 解压 .tar
tar -xvf archive.tar

# 解压 .tar.gz
tar -xzvf archive.tar.gz
tar -zxf 'your_file.tar.gz' -C '/xxxxx'   // -C 参数,指定解压到 /xxxxx 目录

# 解压 .zip
unzip archive.zip
```



参数说明

- `-C`: 解压到 指定目录
- `-v`: 输出解压每个文件列表， 输出解压明细
- `-x`: **解压**模式 (e**x**tract)

- `-z`告诉 `tar`: 此归档文件是**经过 gzip 压缩的** (`gzip`)，需要先解压
- `-f`: 指定下一个参数是**文件名**



### **总结表格**

| 场景                 | 命令             | 示例                                    |
| :------------------- | :--------------- | :-------------------------------------- |
| **上传到远程服务器** | `scp`            | `scp file.txt user@host:/remote/path/`  |
| **从远程下载文件**   | `scp`            | `scp user@host:/remote/file.txt ~/`     |
| **增量同步文件**     | `rsync`          | `rsync -avz /local/ user@host:/remote/` |
| **HTTP 下载**        | `wget` 或 `curl` | `wget https://example.com/file.zip`     |
| **压缩文件**         | `tar -czvf`      | `tar -czvf archive.tar.gz /folder/`     |
| **解压文件**         | `tar -xzvf`      | `tar -xzvf archive.tar.gz`              |

根据需求选择合适的工具，`scp`/`rsync` 适合远程传输，`wget`/`curl` 适合下载网络资源。




## MySQL
```
grant all privileges on *.* to 'test-user'@'%' identified by 'myp@ssw0rd.' with grant option;
```
其中 'test-user'@'%' 表示 test-user 这个用户哪里都可以连接mysql服务
如果是 'test-user'@'localhost' 表示只能在本地连接





















