# 👩‍💻 지원자 : 민승현
<br></br>

![image](https://github.com/seongHyun-Min/wanted-pre-onboarding-backend/assets/112048126/9dd48926-0760-41ea-85a6-989c20a997ee)


<br></br>
# 📌 애플리케이션의 실행 방법

## 1. 기술 스택
<img alt="JAVA" src ="https://img.shields.io/badge/JAVA-C9284D.svg?&style=for-the-badge&logo=java&logoColor=white"/></a> <img alt="Spring" src ="https://img.shields.io/badge/Spring-6DB33F.svg?style=for-the-badge&logo=Spring&logoColor=white"/> <img src="https://img.shields.io/badge/spring boot-6DB33F?style=for-the-badge&logo=spring boot&logoColor=white"> <img alt=" MySQL" src ="https://img.shields.io/badge/MySQL-003545.svg?&style=for-the-badge&logo=MySQL&logoColor=white"/> <img alt="Amazon AWS" src ="https://img.shields.io/badge/Amazon AWS-FF9900.svg?&style=for-the-badge&logo=Amazon AWS&logoColor=white"/>

## 2. 의존성 설치
- 데이터베이스 연결 및 조작: spring-boot-starter-data-jpa와 mysql-connector-j를 사용하여 MySQL 데이터베이스와 연결하고 데이터 조작을 수행합니다.


- 입력 유효성 검사: spring-boot-starter-validation으로 API 요청의 유효성을 검증합니다.


- 웹 애플리케이션 개발: spring-boot-starter-web를 활용하여 웹 애플리케이션을 개발하고 웹 API를 만듭니다.

- 보안 및 시큐리티: spring-boot-starter-security를 활용하여 WebSecurityConfig 및 JWT토큰을 처리 합니다.

- 코드 간소화: lombok을 통해 Getter, Setter, Constructor 등의 메소드를 자동 생성하여 코드를 간소화하고 생산성을 높입니다.


- 테스트: spring-boot-starter-test를 이용하여 애플리케이션을 테스트하고 유닛 테스트를 작성합니다.

## 3. 환경 변수 및 프로파일
### 환경 변수

- ${APPLICATION_PORT}: 애플리케이션의 포트 번호를 지정합니다. 기본값은 8080입니다.

- ${DATABASE_HOST}: 데이터베이스 호스트 주소를 지정합니다. 기본값은 localhost입니다.

- ${DATABASE_PORT}: 데이터베이스 포트 번호를 지정합니다. 기본값은 3306입니다.

- ${DATABASE_NAME}: 데이터베이스 이름을 지정합니다. 기본값은 wanted_board입니다.

- ${DATABASE_USERNAME}: 데이터베이스 사용자 이름을 지정합니다.

- ${DATABASE_PASSWORD}: 데이터베이스 사용자 비밀번호를 지정합니다.

- ${JWT_SECRET_KEY}: JWT 토큰 서명에 사용되는 비밀 키를 지정합니다.

- ${JWT_ACCESS_EXPIRE}: JWT 액세스 토큰의 만료 시간을 지정합니다.

### 프로 파일

dev 프로파일 
- 개발용 환경 설정입니다.
- 데이터베이스 스키마를 자동으로 업데이트하고, SQL 쿼리를 보여줍니다.
- dev 및 datasource 프로파일이 활성화됩니다.

prod 프로파일:
- 프로덕션용 환경 설정입니다.
- 데이터베이스 스키마를 유효성 검사하고, SQL 쿼리를 보여줍니다.
- prod 및 datasource 프로파일이 활성화됩니다

## 4. 데이터베이스 접근 방식
- MySQL 데이터베이스 버전 8.0을 사용하였습니다.
- "board.cmhdg2cgzx06.ap-northeast-2.rds.amazonaws.co:3306/wnated_board로 RDS 데이터베이스 접근이 가능합니다.


## 5. 엔드포인트 호출
- 애플리케이션이 배포된 서버의 IP 주소인 43.202.90.139를 이용하여 엔드포인트에 접속할 수 있습니다.
- 인증된 사용자는 다음과 같은 기능을 수행할 수 있습니다.
- 로그인에 성공하면 JWT 토큰을 받게 됩니다. 이 토큰은 권한이 있는 사용자에게만 부여됩니다.
- API 요청 헤더에 JWT 토큰을 포함하여 게시글 수정 및 삭제와 같은 권한이 필요한 작업을 수행할 수 있습니다.
- API 호출에 대한 자세한 내용은 아래 시연 영상 및 API 명세를 참고해주시기 바랍니다.

<br></br>

# 📌 데이터베이스 테이블 구조 (ERD)

![image](https://github.com/seongHyun-Min/wanted-pre-onboarding-backend/assets/112048126/238015df-5b9f-444b-9bb4-87a634669d40)
- JPA(Entity)를 사용하여 데이터베이스 테이블을 매핑하였습니다.

## 유저 (User)
- users 테이블은 사용자 정보를 저장하는 테이블 입니다.
- user_id: 사용자의 고유 식별자인 아이디 (PK)
- user_email: 사용자 이메일 (Not Null)
- user_password: 사용자 비밀번호 (Not Null)
- posts: 사용자가 작성한 게시글 목록 (One-to-Many 관계, posts 테이블과 연결)

## 게시글 (Post)
- posts 테이블은 게시글 정보를 저장하는 테이블 입니다.
- post_id: 게시글의 고유 식별자인 아이디 (PK)
- post_title: 게시글 제목 (Not Null)
- post_content: 게시글 내용 (Not Null)
- user_id: 게시글을 작성한 사용자의 아이디 (FK, users 테이블과 연결)

## 관계 매핑
- User와 Post의 관계를 1:N(One-to-Many)로 매핑하였습니다.

  
- 게시글 관리 용이성: 1:N 관계를 통해 하나의 사용자가 여러 개의 게시글을 작성할 수 있습니다. 이로 인해 게시글이 여러 사용자에 의해 작성될 때 개별 사용자와 연결하여 쉽게 관리할 수 있습니다.

  
- 유연한 확장성: 사용자가 게시글을 작성하는 행위는 늘어날 수 있습니다. 1:N 관계를 사용하면 새로운 게시글이 추가되더라도 사용자 테이블의 구조를 변경하지 않아도 됩니다.

  
- 효율적인 조회: 사용자가 작성한 게시글을 조회할 때, 1:N 관계를 이용하여 해당 사용자와 연관된 게시글을 효율적으로 조회할 수 있습니다

  
- 권한 관리: 사용자와 게시글 간의 1:N 관계를 활용하여, 특정 사용자가 작성한 게시글에 대한 수정 및 삭제 권한을 효과적으로 관리할 수 있습니다.

  
- 쿼리 최적화: 1:N 관계를 이용하여 한 사용자가 작성한 모든 게시글을 한 번에 로드하거나, 특정 사용자의 게시글 수를 세는 등의 쿼리를 최적화할 수 있습니다.
<br></br>


# 📌 구현한 API의 동작을 촬영한 데모 영상 링크

## https://present.do/documents/64d5fafd10ab9a5ae56822ba

<br></br>


# 📌 구현 방법 및 이유에 대한 간략한 설명

## 인증과 보안 설정
이 프로젝트는 Spring Security를 활용하여 인증과 보안을 관리합니다.

JWT 기반 인증

- JwtUtil, AuthService, JwtAuthenticationFilter 등의 클래스를 활용하여 JWT(JSON Web Token) 기반의 인증 방식을 구현했습니다.
- JWT는 토큰 자체에 정보를 담고 있어서 세션을 서버에 저장하지 않고도 사용자를 식별하고 권한을 부여할 수 있습니다.

인증 및 인가 처리

- AuthService에서 사용자 로그인 처리를 담당하며, 비밀번호 검증과 JWT 토큰 발급을 수행합니다.
- JwtAuthenticationFilter는 인증된 사용자를 위한 필터로서, JWT 토큰을 검증하여 사용자를 인증하고 UserAuthentication을 생성하여 SecurityContextHolder에 저장합니다.

보안 설정

- WebSecurityConfig 클래스를 통해 Spring Security의 설정을 정의했습니다
- 필요한 엔드포인트만 공개하고, 나머지는 권한을 가진 사용자만 접근하도록 제어할 수 있습니다.

예외 처리

- JwtAuthenticationEntryPoint 클래스에서는 인증되지 않은 요청에 대한 예외 처리 로직을 구현합니다.
- 인가되지 않은 접근 시 UnauthorizedUserException과 ForbiddenUserException 예외를 처리하며 해당 예외 발생 시 적절한 상태 코드와 메시지를 반환합니다.

## 사용자 관리

사용자 생성 
- CreateUserRequestDto를 통해 사용자 생성을 처리 합니다. Dto를 통해 사용자가 회원 가입시 입력한 정보가 유효한지 검사하였습니다.
- 사용자가 회원 가입을 요청할 때, 입력된 비밀번호는 passwordEncoder를 사용하여 Bcrypt로 암호화하여 저장됩니다.

중복된 이메일로의 회원 가입 방지
- 중복된 이메일로의 회원 가입을 방지하는 로직이 구현되어있습니다. 이미 존재하는 이메일으로 회원 가입을 시도하면 DuplicateUserException 이 발생하여 중복 가입을 막으며 응답메시지를 전달합니다.

 이메일/아이디로 사용자 조회
- 이메일 또는 아이디를 이용하여 사용자 정보를 조회할 수 있습니다.
- 이를 통해 로그인 시 사용자 정보를 가져오거나, 특정 사용자의 게시글 조회 등의 기능을 제공합니다.

## 게시글 관리

게시글 생성
- CreatePostRequestDto를 통해 게시글 생성을 처리 합니다. 사용자가 새로운 게시글을 작성할 때, 해당 사용자 정보와 함께 게시글 내용이 Post 엔티티에 저장됩니다.

게시글 조회
- 페이징처리를 통해 페이징된 게시글 목록을 조회 할 수 있습니다. 페이징된 데이터 조회를 통해 대량의 게시글을 효율적으로 처리하며 게시글과 작성자 정보를 한 번에 로딩하여(fetch join) 필요한 데이텅를 효율적으로 가져옵니다.

- 게시글 아이디를 통하여 특정 게시글을 조회 할 수 있습니다.

게시글 수정과 삭제
- 사용자는 자신의 게시글을 수정하거나 삭제할 수 있습니다.
- 이때 게시글의 권한을 확인하여 해당 사용자만 수정 또는 삭제할 수 있도록 제한하고 있습니다.
<br></br>

# 📌 API 명세

## 회원(User) API

### 회원 가입(사용자 등록)
- 엔드포인트: POST /users/sign-up
- Request Body: 사용자 정보를 담은 요청 데이터

   - email (문자열): 사용자의 이메일 주소 (반드시 '@' 포함)
   - password (문자열): 사용자의 비밀번호 (최소 8자리 이상)
- Response: 생성된 회원의 ID
  
#### 요청
- Method: POST
- URL: http://43.202.90.139/users/sign-up
- Headers: Content-Type: application/json
- Body:{
  "email": "user@example.com",
  "password": "password"}

#### 응답
- Status: 201 Created
- Body : 생성된 회원의 ID

<img width="849" alt="image" src="https://github.com/seongHyun-Min/wanted-pre-onboarding-backend/assets/112048126/e0af2326-2be3-43c9-8a47-487a017abe80">


### 로그인
- 엔드포인트: POST /users/login
- Request Body: 사용자 정보를 담은 요청 데이터
  - email (문자열): 가입된 사용자의 이메일 주소 (반드시 '@' 포함)
  - password (문자열): 가입된 사용자의 비밀번호 (최소 8자리 이상)
- Response: JWT 토큰

#### 요청
- Method: POST
- URL: http://43.202.90.139/users/login
- Headers:
  - Content-Type: application/json
- Body:{
  "email": "user@example.com",
  "password": "password"}

#### 응답
- Status: 200 OK
- Body: 로그인 성공 시 발급된 JWT 토큰
  

<img width="849" alt="image" src="https://github.com/seongHyun-Min/wanted-pre-onboarding-backend/assets/112048126/fa9c977a-ee56-4ba4-a829-519c532fcd15">

## 게시글(Post) API

### 게시글 생성(등록)
- 엔드포인트: POST /posts
- Request Body: 게시글 생성 요청 데이터
  - title (문자열): 게시글 제목(Not Blank)
  - content (문자열): 게시글 내용(Not Blank)
- Response: 생성된 게시글의 ID

#### 요청
- Method: POST
- URL: http://43.202.90.139/posts
- Headers:
  - Content-Type: application/json
  - Authorization: Bearer {JWT_Token}
- Body:{
  "title": "새로운 게시글",
  "content": "게시글 내용입니다."}

#### 응답
- Status: 201 Created
- Body: 생성된 게시글의 ID

<img width="851" alt="image" src="https://github.com/seongHyun-Min/wanted-pre-onboarding-backend/assets/112048126/41dd1fce-2765-4657-bbf6-4af13dd110bf">

## 게시글 수정
- 엔드포인트: PATCH /posts/{postId}
- Request Body: 게시글 수정 요청 데이터
  - title (문자열): 수정할 게시글 제목
  - content (문자열): 수정할 게시글 내용
  - 동적으로 업데이트 가능(제목만 수정, 내용만 수정)
- Response: 수정된 게시글의 정보

#### 요청
- Method: PATCH
- URL: http://43.202.90.139/posts/{postId}
- Headers:
  - Content-Type: application/json
  - Authorization: Bearer {JWT_Token}
- Body:{
  "title": "수정된 게시글",
  "content": "게시글 내용이 수정되었습니다."}
  

#### 응답
- Status: 200 OK
- Body: 수정된 게시글의 정보 (제목, 내용 포함)

<img width="844" alt="image" src="https://github.com/seongHyun-Min/wanted-pre-onboarding-backend/assets/112048126/7a09007f-0a48-4d34-b11b-08763a4adbee">

## 게시글 목록 조회(페이징)
- 엔드포인트: GET /posts?page=?&size=?
- Response: 페이지별 게시글 목록


#### 요청
- Method: GET
- URL: http://43.202.90.139/posts?page=?&size=?
- Headers:
  - Content-Type: application/json


#### 응답
- Status: 200 OK
- Body: 페이지별 게시글 목록 (제목, 내용, 유저 이메일 포함)

  <img width="892" alt="image" src="https://github.com/seongHyun-Min/wanted-pre-onboarding-backend/assets/112048126/a71d2433-47f3-4fc8-85a2-26a9577a97ff">

## 게시글 조회(단건)
- 엔드포인트: GET /posts/{postId}
- Response: 게시글의 상세 정보


#### 요청
- Method: GET
- URL: http://43.202.90.139/posts/{postId}
- Headers:
  - Content-Type: application/json
 
#### 응답
- Status: 200 OK
- Body: 게시글의 상세 정보 (제목, 내용, 유저 이메일 포함)

<img width="852" alt="image" src="https://github.com/seongHyun-Min/wanted-pre-onboarding-backend/assets/112048126/12bf186a-d653-4343-a1a2-0756056622a9">



## 게시글 삭제
- 엔드포인트: DELETE /posts/{postId}
- Response: 없음


#### 요청
- Method: DELETE
- URL: http://43.202.90.139/posts/{postId}
- Headers:
  - Content-Type: application/json
  - Authorization: Bearer {Your_JWT_Token}
 
#### 응답
- Status: 204 No Content

<img width="853" alt="image" src="https://github.com/seongHyun-Min/wanted-pre-onboarding-backend/assets/112048126/659d610e-292c-4b5d-bb40-a1356fcdc572">

<br></br>

# 📌 Docker-compose

## 1.gradle build 
- DockerFile을 통해 Gradle을 사용하여 프로젝트를 빌드하여 JAR 파일을 생성합니다.

## 2.docker-compose up
### database 서비스

- MySQL 8.0 이미지를 사용하여 데이터베이스 컨테이너를 생성합니다.
- test_board_db라는 이름의 컨테이너가 생성됩니다.
- MYSQL_DATABASE 환경 변수를 설정하여 데이터베이스 이름을 지정합니다.
- MYSQL_ROOT_HOST 환경 변수를 설정하여 (root) 사용자가 어떤 호스트로부터의 연결도 허용하도록 설정합니다.
- MYSQL_ROOT_PASSWORD 환경 변수를 설정하여 (root) 사용자의 암호를 설정합니다.
- 호스트의 3306 포트와 컨테이너의 3306 포트를 매핑하여 외부에서 MySQL에 접근할 수 있도록 합니다.
- ./db/data 디렉터리를 컨테이너 내의 /var/lib/mysql 디렉터리에 마운트하여 데이터를 유지합니다.
- pre_onboarding 네트워크에 연결합니다.
  
### webapp 서비스:
- build 섹션에서 현재 디렉터리의 Dockerfile을 사용하여 이미지를 빌드합니다.
- 항상 컨테이너를 재시작합니다.
- database 서비스가 실행 중일 때만 실행됩니다 (depends_on).
- 호스트의 80 포트와 컨테이너의 8080 포트를 매핑하여 외부에서 어플리케이션에 접근할 수 있도록 합니다.
- 컨테이너 이름을 board_app으로 지정합니다.
- 다양한 환경 변수를 설정하여 Spring Boot 어플리케이션의 설정을 구성합니다.
  - ${APPLICATION_PORT}: 애플리케이션의 포트 번호를 지정합니다.
  - ${DATABASE_HOST}: 데이터베이스 호스트 주소를 지정합니다.
  - ${DATABASE_PORT}: 데이터베이스 포트 번호를 지정합니다. 
  - ${DATABASE_NAME}: 데이터베이스 이름을 지정합니다.
  - ${DATABASE_USERNAME}: 데이터베이스 사용자 이름을 지정합니다.
  - ${DATABASE_PASSWORD}: 데이터베이스 사용자 비밀번호를 지정합니다.
  - ${JWT_SECRET_KEY}: JWT 토큰 서명에 사용되는 비밀 키를 지정합니다
  - ${JWT_ACCESS_EXPIRE}: JWT 액세스 토큰의 만료 시간을 지정합니다.
- pre_onboarding 네트워크에 연결합니다.

- networks 섹션에서 pre_onboarding이라는 이름의 사용자 정의 네트워크를 정의하여 두 서비스가 해당 네트워크에 속하도록 합니다.

<img width="965" alt="image" src="https://github.com/seongHyun-Min/wanted-pre-onboarding-backend/assets/112048126/62b5de19-fd95-42ab-9569-561dd9a69e7f">

<br></br>
# 📌 클라우드 환경(AWS)

## 배포된 API 주소 : 43.202.90.139
![image](https://github.com/seongHyun-Min/wanted-pre-onboarding-backend/assets/112048126/c7e15bc8-7e67-4947-8410-8047a737bcee)










  





