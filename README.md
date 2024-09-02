<h2 align="center">(BE) 개인 PR 홈페이지 (Mrlee-Story)</h2>
개인의 공중 관계(Public Relationship)을 3D로 지원하여, <br/> 페이지 내에서 개인 소개부터 포트폴리오, 커뮤니케이션 등을 시각적으로 이용할 수 있습니다.

## 목차
  - [개요](#개요)
  - [페이지 설명](#페이지-설명)
    - [홈 화면](#1-홈화면)
    - [About 메뉴](#2-about-메뉴)
    - [Portfolio 메뉴](#3-portfolio-메뉴)
    - [Library 메뉴](#4-library-메뉴)
    - [Contact 메뉴](#5-contact-메뉴)
  - [첨언](#첨언)

## 개요
- <b>프로젝트 이름</b> : Mrlee-Story
- <b>개발 기간</b> : 2024.06.01 - 2024.08.31
- <b>주요 기술 스택</b> 
    - <b>FE</b> : 
        <img src="https://img.shields.io/badge/React-61DAFB?style=flat-square&logo=React&logoColor=black"/>
        <img src="https://img.shields.io/badge/Typescript-3178C6?style=flat-square&amp;logo=Typescript&amp;logoColor=white">
        <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat-square&amp;logo=javascript&amp;logoColor=black">
        <img src="https://img.shields.io/badge/HTML5-E34F26?style=flat-square&amp;logo=html5&amp;logoColor=white">
        <img src="https://img.shields.io/badge/CSS3-1572B6?style=flat-square&amp;logo=css3&amp;logoColor=white">
        <img src="https://img.shields.io/badge/R3F-1572B6?style=flat-square&amp;logo=threedotjs&amp;logoColor=white">
    - <b>BE</b> : 
        <img src="https://img.shields.io/badge/Java-1572B6?style=flat-square&amp;logo=&amp;logoColor=white">
        <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&amp;logo=springboot&amp;logoColor=white">
        <img src="https://img.shields.io/badge/Node.js-339933?style=flat-square&amp;logo=Node.js&amp;logoColor=white">
        <img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=flat-square&amp;logo=postgresql&amp;logoColor=white">
        <img src="https://img.shields.io/badge/Gradle-02303A?style=flat-square&amp;logo=gradle&amp;logoColor=white">

- 멤버 : 이민성(1인 개발)
- 링크 : http://144.24.70.238

## 페이지 설명


### 1. 홈화면
1. 데스크톱 화면 구성

    - 총 5개의 섹션으로 구성되며, 스크롤 시 1개의 섹션씩 이동합니다.
    - 3D와 텍스트 모션(애니메이션 효과)를 제공합니다.

|이름|이미지|설명
|:---:|:---:|:---:|
|로딩 화면|<img src='https://github.com/user-attachments/assets/cee0afbe-f63b-45ce-960a-8ad6cb0c5653' width=256 />|- 정적 Resource가 모두 로딩될 때까지 메인 로고를 화면에 배치합니다.<br/>- 로고는 blur 효과로 나타나며, 진행률만큼 좌측부터 로고가 선명해져서 대기 예상 시간을 유추할 수 있습니다.|
|1번 섹션|<img src='https://github.com/user-attachments/assets/bd6db86c-40f8-4edf-a514-9aea3e01eb14' width=256/>|- 첫 화면 진입 시 개인 PR의 핵심 키워드와 3D 아바타를 통해 사용자에게 초두 효과를 제시합니다.|
|2번 섹션|<img src='https://github.com/user-attachments/assets/1cee2105-b429-4afc-9605-6ac17b29c6a3' width=256/>|- 개인의 가치관을 알릴 수 있는 3D 캐릭터와 함께, 전반적인 개발 기술 스택을 제시합니다.<br/>- 더 자세히 보기 위해 About 페이지로의 버튼을 제공합니다.|
|3번 섹션|<img src='https://github.com/user-attachments/assets/66862b71-7904-489e-8a9a-d298345a9f73' width=256/>|- 참여 프로젝트를 간단히 볼 수 있습니다.<br/>- 더 자세히 보기 위해 Portfolio 페이지로의 버튼을 제공합니다.|
|4번 섹션|<img src='https://github.com/user-attachments/assets/62710461-6da4-4327-adab-12abed1a3bf1' width=256/>|- 3D 배경과 함께, 토이 프로젝트 게시판을 안내하고 버튼을 제공합니다.<br/>- 추후 다양한 개인 프로젝트들을 배치할 수 있는 플랫폼을 제공합니다.|
|5번 섹션|<img src='https://github.com/user-attachments/assets/d0d21639-217e-4595-8425-ffe0f97179ce' width=256/>|- 문의사항을 게시물로 작성할 수 있는 Form을 제공합니다.<br/>- 모바일 환경에서는 게시판으로 이동할 수 있는 버튼을 제공합니다.<br/>- Footer에서 노션/이메일/SNS 등의 링크를 제공합니다. |
|Nav|<img src='https://github.com/user-attachments/assets/3122a184-ed50-4ff3-ba06-ce3e001a6716' width=256 />|- 상세 페이지로의 탐색 메뉴를 표시하며, 클릭 링크를 포함합니다.<br />- 첫 화면인 Main과 About, Protfolio, Library, Contact 메뉴가 포함됩니다.
<br/>
2. 모바일 화면 구성

|섹션1|섹션2|섹션3|섹션4|섹션5|
|:--:|:--:|:--:|:--:|:--:|
|<img src='https://github.com/user-attachments/assets/81e8a545-f062-433f-a97c-48f87ac0d730' />|<img src='https://github.com/user-attachments/assets/629abbe7-44e8-4457-85c5-a6f3cb7aaeac' />|<img src='https://github.com/user-attachments/assets/45246d29-30d0-44d6-a144-e4e602e6f8b0' />|<img src='https://github.com/user-attachments/assets/4b3f8ad0-67a2-4f8a-affa-58f4077f9aaf' />|<img src='https://github.com/user-attachments/assets/99ffab32-06a9-44b4-a498-a60fbfada2f4' />
---
<br/>

### 2. About 메뉴
- 개인의 전반적인 정보를 제공합니다.
- 3D 이미지와 텍스트로 정보를 제공하며, 섹션으로 구분됩니다.
- 스크롤 시 다채로운 모션 효과를 접할 수 있으며, 이미지에 마우스를 올리면 확대 효과를 접할 수 있습니다.

|이름|이미지|설명|
|:--:|:--:|:--:|
|소개글|<img src='https://github.com/user-attachments/assets/39c1ce5d-653c-4455-b38f-98356a5562d7' width=256 />|- 이름, 나이 등과 경력 및 교육/자격사항 등의 기본 정보를 제공합니다.|
|기술 스택|<img src='https://github.com/user-attachments/assets/96900b81-f6c0-4746-8a9a-93b31348b814' width=256 />|- 개발 스킬을 그래프와 툴팁 등으로 경험할 수 있습니다.|
|경험|<img src='https://github.com/user-attachments/assets/5ed93a2a-55aa-497e-8ecc-ce59e6fe8fa1' width=256 />|- 개발 경험과 서버 구축/커뮤니케이션 등의 역량을 사진과 함께 볼 수 있습니다.|

---
<br/>

### 3. Portfolio 메뉴
- Notion API를 연동하여, 노션 Database에서의 최종 포트폴리오 데이터를 볼 수 있습니다.
- 노션 API 라이브러리 특성 상 로딩 속도 지연 현상이 존재하여, 응답 시까지 로딩 화면을 배치하였습니다.

|이름|이미지|설명|
|:--:|:--:|:--:|
|참여<br/>프로젝트|<img src='https://github.com/user-attachments/assets/4afe245f-3d81-479a-a920-7043fc097f3b' width=256 />|- 좌측에는 노션에 저장된 참여 프로젝트가 표시되며, 클릭하면 본문에 프로젝트 상세 내역이 표시됩니다. <br/> - 본문에서 '주요 업무'에 표시되는 데이터베이스를 클릭하면, 해당 상세 개발내역을 모달 창으로 볼 수 있습니다.
|상세 업무|<img src='https://github.com/user-attachments/assets/9e7119b2-01c0-4310-bcf1-da1ec311a685' width=256 />|- 개발 내역에 대한 상세 내용을 모달 창으로 확인 가능합니다. <br/>- 우상단의 닫기 버튼 또는 모달 외측 빈 공간을 클릭 시 모달 창이 닫힙니다.

---
<br/>

### 4. Library 메뉴
- 업무 외 개인적으로 진행한 토이 프로젝트 목록을 볼 수 있습니다.
- 현재(2024-08) 기준 본 프로젝트만 업로드된 상태로, 추후 개발되는 모든 아이템에 대한 플랫폼 역할을 수행합니다.

|이름|이미지|설명|
|:--:|:--:|:--:|
|App 리스트|<img src='https://github.com/user-attachments/assets/e11a1d77-20d8-4108-9350-6126a7d18b59' width=256>|- 개발된 애플리케이션들의 썸네일과 이름, 요약을 갤러리 형태로 보여줍니다. <br/>- 클릭하면 해당 페이지의 링크와 상세 설명을 보여주는 모달 창이 표시됩니다.
|상세 보기|<img src='https://github.com/user-attachments/assets/e49a2287-d3f6-41f4-901c-d907a8b1e1cf' width=256>|- 모달 창으로 진행한 토이 프로젝트의 상세 내용과 링크 등을 보여줍니다.

---
<br/>

### 5. Contact 메뉴
- 개발자(관리자)와의 공중 관계를 위하여 공지사항과 게시물/댓글을 작성할 수 있는 페이지입니다.
- 회원/비회원 이용 가능하며, 게시물 조회/검색/수정/삭제를 지원합니다.

|이름|이미지|설명|
|:--:|:--:|:--:|
|게시물<br/>목록/검색|<img src='https://github.com/user-attachments/assets/ac616d39-0dc2-40c1-9d3c-732af77675a9' width=256 />|- 상단에는 검색과 글쓰기 버튼을 제공하며, 공지사항과 게시판을 나누어 목록을 볼 수 있습니다. <br/>- 공지사항은 아코디언 형태로 기본 접혀있으며, 클릭할 시 펼쳐볼 수 있습니다. <br/>- 게시판은 무한 스크롤 형태로 제공되며, 화면의 70% 아래로 내려가면 최상단으로 이동할 수 있는 버튼이 하단에 나타납니다. <br/>- 비공개 게시물은 내용과 사진이 임의의 사진과 텍스트로 비공개 처리되어 나타납니다. <br/>- 게시물을 클릭하면 상세 보기로 들어갈 수 있습니다.
|게시물<br/>작성|<img src='https://github.com/user-attachments/assets/bd9820ea-9b89-4fe7-833f-3738272e3a98' width=256 />|- 제목과 내용/사진을 입력할 수 있는 인터페이스가 왼편에, 기타 정보를 입력할 수 있는 인터페이스가 오른편에 나타납니다. <br/>- 제목/내용/닉네임/비밀번호를 필수로 입력해야 하며, 이미지 첨부와 휴대폰 번호는 선택사항입니다. <br/>- 휴대폰 번호를 입력 시 개인정보 수집에 대한 안내 문구가 뜨며, 반드시 개인정보 제공 동의를 클릭해야 업로드가 가능합니다. <br/>- 첨부된 이미지는 본문 내용 아래에 표시됩니다. <br/>- 게시물 비공개를 원하면 비밀글로 설정 체크박스를 클릭합니다.
|게시물<br/>조회|<img src='https://github.com/user-attachments/assets/63539ece-c387-489e-b12c-5fcdee2d2606' width=256 />|- 상단에는 게시판 목록으로 이동할 수 있는 버튼이 존재합니다. <br/>- 제목과 닉네임/작성일자가 상단에 나타나며, 수정된 게시물일 경우 수정일자가 작성일자 옆에 나타납니다. <br/>- 비로그인 상태에서 수정/삭제 버튼을 클릭하면 비밀번호 작성을 유도하는 모달 창이 나타납니다. <br/>- 본문 내용 아래에 첨부된 이미지가 차례로 나타납니다. <br/>- 제일 아래에는 댓글 입력 창과 작성된 댓글 목록이 나타납니다.
|댓글<br/>조회/작성|<img src='https://github.com/user-attachments/assets/c8cad8f6-95d9-4a67-b2ce-42a0bd21ef51' width=256 />|- 댓글 댓글 목록을 제공합니다. <br/>- 비로그인 상태에서 댓글 수정/삭제 버튼을 클릭하면 비밀번호 작성을 유도하는 모달 창이 나타납니다. <br/>- 관리자/게시물 작성자일 경우 인증 마크가 표시됩니다. <br/>- 댓글은 무한 스크롤 형태로 나타나며, 화면의 70% 아래로 내려가면 최상단으로 이동할 수 있는 버튼이 하단에 나타납니다.

---
<br/>

## 개발 상세 내역
### 1. 프로젝트 설정
- 본 프로젝트는 타입스크립트-리액트 기반의 FE와 스프링부트-자바 기반의 BE, 그리고 Notion Backend API를 활용하기 위한 Express-NodeJS 기반의 BE로 구성되어 있습니다.
- DB는 PostgreSQL로 이뤄져있으며, 주로 Contact 메뉴(게시판) 사용을 위한 테이블로 구성되어 있습니다.
- FE는 스프링부트와 Express BE API를 이용하며, 노션 API는 각각의 BE 프로젝트에 프록시 API로 내장되어 있습니다.
- 스프링부트 BE는 PostgreSQL과 JPA로 연동되어 있습니다.
- FE(React) 설정 파일 예시(/src/apis/index.ts)
    ```JavaScript
    // API 도메인 설정(약 15라인~25라인 사이)
    const DOMAIN = 'http://{SpringBoot 프로젝트 IP}:{SpringBoot 프로젝트 Port}';
    const NODE_API_DOMANIN = `http://{NodeJS 프로젝트 IP}:NodeJS 프로젝트 Port/api/proxy`;
    ```

- BE(Spring Boot) 설정 파일 예시(/src/main/resources/application.properties)
    ```properties
    spring.application.name={App 이름 입력}

    # 서버
    server.port={포트 입력}
    spring.datasource.driver-class-name=org.postgresql.Driver
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

    # DB 연결
    spring.datasource.url={DB URL 입력}
    spring.datasource.username={DB ID 입력}
    spring.datasource.password={DB 비밀번호 입력}

    # File Service 경로(로컬-개발환경)
    file.path={이미지 등 서버 저장 Resource 경로 입력}
    file.url={DB에 URL 형태로 저장할 파일 서비스 Context Path 입력}
    file.profile.default.prefix={사용자 랜덤 프로필 이미지 경로 입력}


    # 파일 업로드
    spring.servlet.multipart.max-file-size={최대 파일 크기 지정}
    spring.servlet.multipart.max-request-size={최대 Request 파일 크기 지정}

    # 비밀키
    jwt-secret-key={JWT 비밀키 입력}

    # Notion API 관련
    notion.secret-key={노션 API 비밀키 입력}
    notion.version={노션 API 버전(YYYY-MM-DD) 입력}

    notion.request-url-database.format={노션 API-참여 프로젝트 Request URL 포맷 입력}
    notion.request-url-page.format={노션 API-DB 상세 페이지 Request URL 포맷 입력}
    notion.request-url-blocks.format={노션 API-블럭 Request URL 포맷 입력}

    notion.request-url-page-splitbee.format={노션 API-비표준 형태(Spliebee) Request URL 포맷 입력}

    notion.database-id.project={노션 API-참여 프로젝트 Database ID 입력}
    notion.database-id.work={노션 API-상세 개발 내역(주요 업무) Database ID 입력}
    ```

<br/>

### 2. 의존성 설정
- FE(npm)
```bash
# 프로젝트 생성 및 TS 설정
npx create-react-app mrlee-story --template typescript
# three 및 three-fiber 설치
npm i three @react-three/fiber @react-three/drei
# types 설치
npm install --save-dev @types/three
# 라우터 설치
npm i react-router-dom
# 자바스크립트 애니메이션 라이브러리 설치
npm i gsap
# zustand(상태관리 도구) 설치
npm i zustand
 # 모션 도구(framer-motion) 설치
 npm i framer-motion
 npm i framer-motion-3d
# 노션 리액트 설치
npm i react-notion
# 노션 디자인 관련 라이브러리 설치
npm i katex prismjs rc-dropdown
```

- BE-Spring Boot(/build.gradle)
```bash
plugins {
    id 'java'
    id 'war'
    id 'org.springframework.boot' version '3.3.2'
    id 'io.spring.dependency-management' version '1.1.6'
}
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    providedRuntime 'org.springframework.boot:spring-boot-starter-tomcat'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
    
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-validation'

    implementation group: 'io.jsonwebtoken', name: 'jjwt', version: '0.9.1'
    
    implementation group: 'com.googlecode.json-simple', name: 'json-simple', version: '1.1.1'
    implementation group: 'com.github.seratch', name: 'notion-sdk-jvm-core', version: '1.11.1'

    implementation group: 'org.postgresql', name: 'postgresql', version: '42.2.23'
    runtimeOnly 'org.postgresql:postgresql'

    // com.sun.xml.bind
    implementation 'com.sun.xml.bind:jaxb-impl:4.0.1'
    implementation 'com.sun.xml.bind:jaxb-core:4.0.1'
    // javax.xml.bind
    implementation 'javax.xml.bind:jaxb-api:2.4.0-b180830.0359'
}
```

- BE-NodeJS(npm)

```bash
# 노션 클라이언트 설치
npm i notion-client
# express 설치
npm i express
# cors 설치
npm i cors
# http 설치
npm i http
# http-proxy-middleware 설치
npm i http-proxy-middleware
```
<br/>
---
<br/>

## 첨언
### 1. 향후 계획
- 현재 게시판을 제외한 개인 경력 데이터는 전체가 FE에서 Mock(&.ts) 파일로 하드코딩되어 있어, 효율적인 경력 관리를 위해 DBMS로 전환할 예정입니다.
- 현재 방문자 중심의 페이지만 개발되어, 추후 효율적인 경력 관리를 위해 관리자 페이지를 추가 개발하여 웹 페이지에서 개인 경력 관리 CRUD 작업과 회원 관리 등을 수행할 수 있도록 수정할 예정입니다.
- 모바일 등 반응형 페이지에서 버튼/텍스트 등 사용자 인터페이스 해상도가 망가지는 현상이 있어 수정할 계획입니다.
- 비밀글 등으로 인해 게스트의 경우에도 JWT 인증 방식을 활용하는데 로그아웃하는 기능이 없어 추가할 예정입니다.
- About 메뉴에서 모니터 화면에 따라 이미지 크기가 과도하게 크거나 작게 적용되는 현상이 있어, 동적 그래픽 계산 공식을 연구하여 적용할 예정입니다.
- 게시물 작성 시 텍스트 효과와 이미지 중간 첨부 등이 불가능하여, 게시글 작성과 관련된 텍스트 에디터 라이브러리를 적용할 예정입니다.

### 2. 회고
- 경력에서 Backend와 GIS S/W 개발에 치중되어 있었던 만큼, 새로운 기술과 분야를 개발하며 S/W 개발에 대한 전반적인 이해를 넓힐 수 있었습니다.
- 특히 상태 관리와 컴포넌트 중심 개발을 하며 리액트 라이브러리의 유지/보수성에 대하여 깊은 감탄을 했습니다.
- 노션 API와 3D 모델 등 다소 무거운 자원들을 CSR 환경에서 로딩하여 페이지 첫 진입 속도가 느려지는 단점이 있었습니다. 이를 해결하기 위해 전역 store를 활용하여 Background에서 비동기 방식으로 자원을 받아오고, 캐시 형태로 메모리에 저장하여 문제를 해결할 수 있었습니다.
- 평소 Java 개발을 하던 본인에게는 컴파일 단게에서 타입 안정성을 보장받는 게 익숙하여 선택한 TypeScript였지만, 타입 유추가 힘든 라이브러리 등으로 인해 여전히 any 타입으로 불러오는 코드가 많이 남아있어서 아쉬움이 남습니다.
- 저사양 무료 서버 사양으로 인해 최소한의 자원을 활용하고자 Stateless 방식의 JWT 인증/인가 방식을 채택하였지만, 많은 어려움을 겪어서 의미 이해보다 구글링에 의존하여 개발을 하였습니다. 특히 게스트 회원의 경우에도 JWT를 사용하여 테이블 구조와 코드/로직이 난잡해지는 결과를 불러왔습니다. 이는 꾸준히 공부하여 개선 계획에 있습니다.
- 본 프로젝트 개발 기간 동안 브랜치 전략을 공부하여 버전 관리를 할 계획이었으나 시간 부족으로 하지 못했습니다. 특히 개발 도중 Backend 프로젝트가 삭제되는 현상이 발생해 private 공간에 repository를 생성하였으나 효율적인 버전관리는 수행하지 못해 계속해서 공부 예정입니다.
- 무엇보다도 개인의 경력과 경험, 지식에 대한 정리가 필요하여 퇴직 후 진행한 프로젝트에서 그간 정리하지 못했던 많은 부분이 정리되어 값진 경험이었습니다.