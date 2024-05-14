#!/bin/bash -l

PACKAGE_PATH=$(cd $(dirname $0) && pwd )
LOG_DIR=${PACKAGE_PATH}/running.log
JAR_NAME=${PACKAGE_PATH}/*.jar
SECRET_KEY=6lCS3AXHGA88eVD2UB

if [ "$1" = "" ];
then
    echo -e "未输入操作名"
    echo [start]
    echo [stop]
    echo [restart]
    echo [status]
    exit 1
fi

function start(){
  if [ ! -f ${JAR_NAME} ]
  then
      echo "jar package does not exist!"
      exit 1
  fi

  PID=$( ps -ef | grep "${PACKAGE_PATH}" | grep 'java' | awk '{print $2}')
  [ -n "$PID" ] && echo "java program already exist!!!" && exit 1
  echo "-----start java program-----"
  nohup java -Xmx1024m -Xms1024m -XX:MetaspaceSize=512m -XX:MaxMetaspaceSize=512m  -jar ${JAR_NAME} --jasypt.encryptor.password=${SECRET_KEY} > ${LOG_DIR}
  2>&1 &
}

function stop(){
  COUNT=$(ps -ef |grep ${JAR_NAME} |grep -v "grep" |wc -l)
  if [ ${COUNT} -gt 0 ]
  then
      echo "已存在${COUNT}个${JAR_NAME}程序在运行"
      PID=$(ps -ef | grep java | grep ${JAR_NAME} | grep -v grep | awk '{print $2}')
      echo ${PID}
      kill -9 ${PID}
      OUTPUT=`echo "正在关闭${JAR_NAME}程序,进程PID:${PID}"`
      echo ${OUTPUT}
  else
      echo "没有对应的程序在运行"
  fi
}

function restart(){
  stop
  sleep 6s
  start
}

function status(){
  PID=`ps -ef | grep java | grep ${JAR_NAME} | grep -v grep | wc -l`
  if [ ${PID} != 0 ]
  then
      echo "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
      echo "${JAR_NAME} 服务正在运行中..."
      echo "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
  else
      echo "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
      echo "${JAR_NAME} 服务已停止运行..."
      echo "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
  fi
}

case $1 in
  start)
  start;;
  stop)
  stop;;
  restart)
  restart;;
  status)
  status;;
  *)

esac
