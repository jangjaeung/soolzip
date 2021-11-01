# SOOL.zip 프로젝트
kh정보교육원 세미프로젝트
# 실행 화면 설명
https://fierce-form-86b.notion.site/SOOL-zip-dce2c54213e24bf88bb9551e9ee4d9c5
# 설계 및 구현기간
+ 기간 21/08/16 ~ 21/10/11
+ 발표 21/10/11
# 목차
1. [개발목적 및 목표](#개발목적-및-목표)<br>
2. [개발환경](#개발환경)<br>
3. [DB구조](#DB구조)<br>
4. [기능](#기능)<br>
5. [느낀점 및 배운것들](#느낀점)<br>

# 개발목적 및 목표
**목적**<br>
+ 코로나 시국에 따른 혼술족이 많음에 따라 술을 좋아하는 애주가들이 소통과 자신만의 레시피도 공유할수 있는 사이트를 만들어보자! 해서 만들게 되었습니다<br>
**목표**<br>
+ 사용자가 레시피를 간단하게 올리고 쉽고 편하게  사용자 친화적인 사이트 만들기

# 개발환경
**운영체제**
+ Window10

**개발도구**
+ Eclipse
+ Visual Studio Code

**사용기술**
+ Java
+ JSP/Servlet
+ JavaScript
+ OracleDB
+ HTML5/CSS
+ JQuery

**버전관리 및 협업도구**
+ Git https://github.com/jangjaeung/soolzip

# DB구조
![erd](https://user-images.githubusercontent.com/90733948/137696131-5b836c4c-e374-4e93-8aa2-441be348b1d7.jpg)
https://www.erdcloud.com/d/uPmxAyJdK3Xsbur9X

# 기능
**1. 회원가입 / 로그인**<br>
-유효성 검사를 통해 무차별적인 가입을 방지하고 중복데이터를 방지하였습니다<br>
**2.정보찾기**<br>
-비밀번호 찾기시 회원가입중 입력한 이메일로 임시 비밀번호를 발급하여 password를 변경합니다<br>
**3.검색**<br>
-술이름, 칵테일 검색시 메인주류 검색을 할수 있습니다<br>
**4.스토리**<br>
-사진과 함께 글을 등록하여 회원들끼리 소통할수 있습니다<br>
-자신의 스토리가 아닌 타인의 스토리에 좋아요를 표시할수 있습니다<br>
**5.레시피**<br>
-레시피 등록시 가이드폼을 주어 사용자가 편리하게 등록할수 있습니다.<br>
-등록한 레시피에 추천을 할수있고 스크랩을 할수있습니다.<br>
-스크랩시 마이페이지에서 따로 볼수 있습니다.<br>
**6.투표**<br>
-관리자페이지에 해당 월에 좋아요수가 많은 레시피 10개를 선택할수 있으며, 선택시 투표페이지를 오픈할수 있습니다.<br>
-관리자가 투표를 닫기전까지 사용자는 투표를 할수있으며 투표는 1인당 1레시피만 투표가능합니다(투표취소후 다른 레시피 투표가능).<br>
-투표를 닫으면 1위 레시피는 명예의 전당에 등록되어 테두리와 왕관 아이콘이 생깁니다<br>
**7.쪽지**<br>
-회원은 레시피 혹은 스토리 게시물을 올린 사용자에게 쪽지를 보낼수 있습니다<br>
**8.관리자페이지**<br>
-관리자는 회원을 강제탈퇴 시킬수 있습니다<br>
-회원은 관리자에게 문의를 할수있고 관리자는 그 문의에 답변이 가능합니다<br>
-자주하는 질문을 수정할수 있습니다<br>
**9.마이페이지**<br>
-자신이 올린 레시피, 스토리 게시물을 모아볼수 있습니다<br>
-받은 쪽지를 볼수 있고, 보낸 쪽지를 확인할수 있습니다<br>
-회원정보 수정을 할수 있습니다 회원정보 수정시 유효성검사를 거쳐야 합니다<br>
<br>
# 느낀점
+ 문서의 소중함을 알았습니다. 회의를 통해 말로만 정한후 시간이 지나면 까먹어버리고 팀원들끼리 이해한것도 다를수 있기때문에 글이나 문서로 반드시 정리를 해놔야 한단것을 느꼇습니다.
+ 소통의 중요성을 알았습니다. 개인 프로젝트가 아닌 팀프로젝트 이기 때문에 안된 부분이 있으면 빠르게 공유하고 해결해야 시간단축과 퀄리티 상승으로 이어지고 서로 이해한것이 다를수 있기때문에 항상 소통해야 한단것을 알았습니다
+ DB설계의 소중함을 알았습니다. 설계단계를 대충 거치면 구현단계에서 '아.. 데이터 못넣는데'가 발생해버려 시간과 퀄리티의 타격을 줫습니다. DB를 효율적이고 신중하게 만들어야 데이터를 쉽고 빠르게 얻을수 있다는 것을 알았습니다.