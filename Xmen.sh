﻿#!/bin/bash
function xfile(){
for file in ` ls $1`;do
    if [ -d $1"/"$file ];then
        local dirPath=${1}
        local dirName=${file}
        xfile $1"/"$file
        `echo $dirName |grep -q $INIT_RE_A`
        if [ $? -eq 0 ]
                then
                        log ${dirPath}"/"${dirName}
            mv -f ${dirPath}"/"${dirName} ${dirPath}"/"${dirName/${INIT_RE_A}/${INIT_RE_B}}
                fi
    else
        local path=$1
        local name=$file    
        `echo $name |grep -q $INIT_RE_A`
        if [ $? -eq 0 ]
        then
                #echo "include"
            log  ${path}"/"${name}
            mv -f ${path}"/"${name} ${path}"/"${name/${INIT_RE_A}/${INIT_RE_B}}
        fi
    fi
    _X_F_count=`expr $_X_F_count + 1`
    nbar $_X_F_count $_X_F_sum
done
}
function xcode(){
    if [ ! -d $INIT_LOG_PATH ];then
                mkdir -p $INIT_LOG_PATH
        fi
    local para="s/"$1"/"$2"/g"
    local fromStr=`echo "$1" | sed "s/ /_/g"`
    local toStr=`echo "$2" | sed "s/ /_/g"`
    local logfile=${INIT_LOG_PATH}"/text-"$fromStr"-"$toStr".log"
    grep "$1" -rl $INIT_PATH >>$logfile
    if [ $? -eq 0 ]
        then   
        local counter=0
        local sum=`cat $logfile |wc -l`
        for i in `cat ${logfile}`
        do
            sed -i "$para" "$i"
            counter=`expr $counter + 1`
            nbar $counter $sum
        done
        #sed -i $para `grep $1 -rl $INIT_PATH`
    else
        local j=0
        while [ $j -lt 101 ]
        do
                nbar $j  100
                j=`expr $j + 1`
        done
    fi
}
function log(){
    if [ ! -d $INIT_LOG_PATH ];then
        mkdir -p $INIT_LOG_PATH
    fi
    echo $1 >> $INIT_LOG_PATH"/file-"${INIT_RE_A}"-"${INIT_RE_B}".log"
}
 
xbar(){
        local counter=$1
        local _PROCEC=$2
        tput cup $_PROCEC 0
        if [ $counter -eq 1 ];then
                printf "["
        fi
        tput cup $_PROCEC $counter
        printf "=>"
        tput cup $_PROCEC 101
        printf "]%d%%" $counter
}
nbar(){
        local idx=$1
        local sum=$2;
        local counter=`echo "${idx}*100/${sum}" |bc`
        #echo $counter
        xbar $counter $_P
}
if [ $# -lt 4 ]; then
    echo "Usage: $0 source-dir to-replace-string replacement log-path [-Xcode]"
    echo ""
    echo "For example:"
    echo " $0 /nnit/glusterfs-3.3.1 gluster refungo /nnit/log -Xcode"
    exit
fi
INIT_LOG_PATH=$4
INIT_RE_A="$2"
INIT_RE_B="$3"
INIT_PATH=$1
INIT_X_CODE=$5
_X_F_sum=`ls -lR $INIT_PATH|grep "^[-d]"|wc -l`
_X_F_count=0
 
echo  "--------------------------------------------------------------------"     
echo  "--------o-----o--o-------o--oooooooo--o-------o---------------------"      
echo  "---------o---o---o-o---o-o--o---------o-o-----o---------------------"        
echo  "----------o-o----o--o-o--o--o---------o--o----o---------------------"       
echo  "-----------o-----o---o---o--oooooooo--o---o---o---------------------"       
echo  "----------o-o----o-------o--o---------o----o--o---------------------"       
echo  "---------o---o---o-------o--o---------o-----o-o---------------------"       
echo  "--------o-----o--o-------o--oooooooo--o-------o---------------------"       
echo  "--------------------------------------------------------------------"
printf "\n"
echo  "Rename file and directory:"
_P=`tput cols`
echo $5
if [ ! "$INIT_X_CODE" = "-Xcode" ]; then
    xfile $INIT_PATH
fi
printf "\n"
echo  "Replace text"
_P=`tput cols`
xcode "$INIT_RE_A" "$INIT_RE_B"
printf "\n"
echo  "Xmen has complete the task , bye bye !"
printf "\n"
echo  "--------------------------------------------------------------------"        
echo  "---------@@@@@@@@@@-------------------------@-------@---------------"    
echo  "---------@--------@-------------------------@-----@-----------------"    
echo  "---------@--------@-------------------------@---@-------------------"    
echo  "---------@--------@-------------------------@-@---------------------"    
echo  "---------@--------@-------------------------@---@-------------------"    
echo  "---------@--------@-------------------------@-----@-----------------"    
echo  "---------@@@@@@@@@@-------------------------@-------@---------------"    
echo  "--------------------------------------------------------------------"   