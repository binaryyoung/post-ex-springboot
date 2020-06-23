#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
# readlink -f 심볼릭 링크의 절대경로를 추출한다. $0 현재 실행파일을 의미한다.
ABSDIR=$(dirname $ABSPATH)
# dirname 은 파일 이름을 제외한 path 부분만 알려준다.
source ${ABSDIR}/profile.sh
# source command 사용시 해당 파일의 변수를 불러온다. (변수,메소드 활용가능)

function switch_proxy() {
    IDLE_PORT=$(find_idle_port)

    echo "> 전환될 port: $IDLE_PORT"
    echo "> Port 전환"
    echo 'set \$service_url http://127.0.0.1:${IDLE_PORT};'|sudo tee /etc/nginx/conf.d/service-url.inc
    # $servie_url을 설정해서 파이프라인으로 넘겨준다.

    echo "> 엔진엑스 reload"
    sudo service nginx reload
    # reload, restart
}