#!/bin/bash
# 来源：《高性能MySQL》
INTERVAL=5
PREFIX=$INTERVAL-sec-status
RUNFILE=/tmp/collect.sh.running
USER=$2
read -s -p "请输入密码：" PASSWORD
printf "\n"
HOST=$1
mysql -e 'show global variables' --host=$HOST --user=$USER --password=$PASSWORD >> mysql-variables
while test -e $RUNFILE; do
	file=$(date +%F_%I)
	sleep=$(date +%s.%N | awk "{print $INTERVAL - (\$1 % $INTERVAL)}")
	sleep $sleep
	ts="$(date +"TS %s.%N %F %T")"
	loadavg="$(uptime)"
	echo "$ts $loadavg" >> $PREFIX-${file}-status
	mysql -e 'show global status' --host=$HOST --user=$USER --password=$PASSWORD >> $PREFIX-${file}-status &
	echo "$ts $loadavg" >> $PREFIX-${file}-innodbstatus
	mysql -e 'show engine innodb status\G' --host=$HOST --user=$USER --password=$PASSWORD >> $PREFIX-${file}-innodbstatus &
	echo "$ts $loadavg" >> $PREFIX-${file}-processlist
	mysql -e 'show full processlist\G' --host=$HOST --user=$USER --password=$PASSWORD >> $PREFIX-${file}-processlist &
	echo $ts
	
done
echo Exiting because $RUNFILE does not exist.
