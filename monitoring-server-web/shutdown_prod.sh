#!/bin/bash
#先通过管理端口关闭进程
curl -i -X POST http://localhost:16000/monitoring-server-web/actuator/shutdown

#再次通过名称检查进程是否被成功停止
count=$(ps -ef | grep monitoring-server-web | grep -v "grep" | wc -l)
if [ $count -gt 0 ]; then
  if [ -f "$pid_file" ]; then
    #如果存在进程ID文件，则读取进程ID使用信号量通知方式关闭进程
    pid=$(cat $pid_file)
    kill -15 $pid
    echo "killed $pid"
  else
    #通过名称方式查找到进程ID，使用信号量通知方式关闭进程
    pid=$(ps -ef | grep monitoring-server-web | grep -v "grep" | awk '{print $2}')
    kill -15 $pid
    echo "killed $pid"
  fi
fi
