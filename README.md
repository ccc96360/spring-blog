# Spring Boot + Thymeleaf를 이용한 개인 블로그

---


## 사용법
### 1. application-oauth.properties 작성
```properties
spring.security.oauth2.client.registration.github.client-id=클라이언트ID
spring.security.oauth2.client.registration.github.client-secret=클라이언트Secret
```
* 서버 내부에 [GitHub Developer Setting](https://github.com/settings/developers) 에서 만든 OAuth App의 정보를 위와 같이 작성합니다.

### 2. application-real-db.properties 작성

```properties
spring.jpa.hibernate.ddl-auto=none
spring.datasource.url=jdbc:mariadb://localhost:DB포트(보통은 3306)/스키마명
spring.datasource.username=유저name
spring.datasource.password=password
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
```
* 서버 내부에 위와 같은 properties파일을 작성한다.

### 3. SQL 디렉토리에 있는 쿼리들을 서버 DB에 적용 시킨다.
* Post 테이블, User테이블, Tag테이블, DB 세션을 사용하기 때문에 세션 테이블이 있다.
  
### 4. Utterance 코드 변경

* Utterance는 내 특정 리포지토리의 Issue기능으로 댓글기능을 구현한다.
* 따라서, 댓글을 위한 Public 리포지토리를 하나 생성한다.
  
![이미지](https://raw.githubusercontent.com/ccc96360/ccc96360/main/images/spring-blog/readme/Utterance_%EC%82%AC%EC%9A%A9%EB%B2%95_1_%EB%A0%88%ED%8F%AC%EC%A7%80%ED%86%A0%EB%A6%AC_%EC%9E%85%EB%A0%A5..PNG)
    
* 위 그림과 같이 [Utterance 홈페이지](https://utteranc.es/) 에서 빨간 박스 부분에 위에서 생성한 리포지토리를 입력한다.
  
![이미지](https://raw.githubusercontent.com/ccc96360/ccc96360/main/images/spring-blog/readme/Utterance_%EC%82%AC%EC%9A%A9%EB%B2%95_2_%EC%BD%94%EB%93%9C_%EC%B9%B4%ED%94%BC.PNG)
    
* 그러면 위와 같은 코드가 나오는데 빨간 부분은 위에서 입력해준 리포지토리로 변경돼있다.
*```src/main/resources/templates/contents/post.html``` 의 아래와 같은 ```38~45라인```을 복사한 코드로 변경한다.
```html
<script src="https://utteranc.es/client.js"
        repo="ccc96360/blog-comment"
        issue-term="pathname"
        label="comments"
        theme="github-light"
        crossorigin="anonymous"
        async>
</script>
```

### 5. 실행
* 빌드된 jar파일을 다음과 같은 형식으로 실행한다.
```shell
nohup java -jar \
-Dspring.config.location=classpath:/application.properties,\
경로/application-oauth.properties,\
경로/application-real-db.properties \
$JAR_NAME>$REPOSITORY/nohup.log 2>&1 &
```
* 만약 real1, real2를 활용해 매번 다른 포트로 실행시킨다면 다음과 같은 방식으로 실행한다.
```shell
nohup java -jar \
-Dspring.config.location=classpath:/application.properties,\
classpath:/application-$IDLE_PROFILE.properties,\
경로/application-oauth.properties,\
경로/application-real-db.properties \
-Dspring.profiles.active=$IDLE_PROFILE \
$JAR_NAME>$REPOSITORY/nohup.log 2>&1 &
```
* 이떄 $IDLE_PROFILE은 쉬고 있는 포트의 Profile값이다.
* 예를들어 8082포트에서 어플리케이션이 서비스되고 있지 않으면 real1 프로필이 IDLE_PROFILE이다.
### 6. 참고
* 프로젝트 내부에 application-real1, real2 프로퍼티 파일은 Nginx를 이용한 무중단 배포를 위해 존재한다.
* 다음과 같은 스크립트로 IDLE_PROFILE을 설정 할 수 있다.
```shell
#!/usr/bin/env bash
# 쉬고 있는 profile 찾기
function find_idle_profile()
{
    RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)
    if [ ${RESPONSE_CODE} -ge 400 ] # 400 보다 크면(40x, 50x)에러
    then
        CURRENT_PROFILE=real2
    else
        CURRENT_PROFILE=$(curl -s http://localhost/profile)
    fi

    if [ ${CURRENT_PROFILE} == real1 ]
    then
        IDLE_PROFILE=real2
    else
        IDLE_PROFILE=real1
    fi
    echo "${IDLE_PROFILE}"
}

# 쉬고 있는 profile의 port찾기
function find_idle_port()
{
    IDLE_PROFILE=$(find_idle_profile)
    if [ ${IDLE_PROFILE} == real1 ]
    then
        echo "8082"
    else
        echo "8083"
    fi
}
```
* 게시글 작성, 수정, 삭제를 위해선 ADMIN 권한이 필요하다.
* DB에서 ADMIN권한을 부여하고하는 user의 role을 'ADMIN'로 변경하면 된다.
---


## 개발 환경
1. 자바 버전: openjdk 11
2. 스프링 부트 2.5.0
3. DB: 개발: h2db 1.4.199 서버: Mariadb 15.1
4. 개발 OS : Window 10
5. 서버 OS: Ubuntu 20.04.2 LTS (라즈베리파이 aarch64)
6. Jenkins 서버: Ubuntu 18.04.05 LTS (x86_64) (AWS EC2 인스턴스 활용)
7. IDE: Intellij community
8. 블로그 테마는 [Start Bootstrap](https://startbootstrap.com/theme/clean-blog) 의 Clean Blog 테마를 활용했다.