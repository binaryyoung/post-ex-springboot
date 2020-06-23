#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh
source ${ABSDIR}/switch.sh

IDLE_PORT=$(find_idle_port)

echo "> Health Check Start!"
echo "> IDLE_PORT: $IDLE_PORT"
echo "> curl -s http://localhost:$IDLE_PORT/profile)"
# curl 은 http요청을 보내고 응답을 받는 명령

for RETRY_COUNT in {1..10}
do
    RESPONSE=$(curl -s http://localhost:${IDLE_PORT}/profile)
    UP_COUNT=$(echo ${RESPONSE} | grep 'real' | wc -l)
    # wc는 문자열 검색 명령 : l 옵션은 라인 수를 출력)
    if [ ${UP_COUNT} -ge 1 ]
    then # $up_count >= 1 ("real" 문자열이 있는지 검증)
        echo "> Health check 성공"
        switch_proxy
        break
    else
        echo "> Health check의 응답을 알 수 없거나 혹은 실행상태가 아닙니다."
        echo "> Health check: ${RESPONSE}"
    fi

    if [ ${RETRY_COUNT} -eq 10 ]
    then
        echo "> Health Check 실패."
        echo "> 엔진엑스에 연결하지 않고 배포를 종료합니다."
        exit 1
    fi
    echo "> Health check 연결 실패. 재시도..."
    sleep 10
done

# 엔진엑스와 연결되지않은포트로 스프링부트가 잘 수행되었는지 체크한다.
# 잘떳는지 확인이 되면 엔진엑스 프록시 설정을 변경(switch_proxy)를 수행한다.
# 엔진엑스 프록시 설정 변경은 swich.sh에서 수행
