<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>스토리 등록</title>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;400;700&display=swap" rel="stylesheet">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Sunflower:wght@300&display=swap" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        //선택 input:file 태그 연결
        function fnFileChange(input) {
            $(input).closest('div').find('input:file').click();
        }

        //파일읽어서 등록이미지 뿌려주기
        function readURL(input, id) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                reader.onload = function (e) {
                    $("#" + id).attr('src', e.target.result);
                }
                reader.readAsDataURL(input.files[0]);
            }
        }
        function fnValidate() {
            return false;
        }
        //등록
        function fnSave(type) {
            //제출일 경우 필수값 체크필요
            if (type == 'A') {
                $("input[name='StorySave']").val("1");
                $("input[name='storyNo']").val();
                if (fnValidate()) return;
            }
            $("#test").submit();
        }
    </script>
    <script>
        $(document).ready(function () {
        	$("#headerMain").load("/html/comm/header.jsp");
            $("#footerMain").load("/html/comm/footer.html");
        });
    </script>
<style>
*{margin:0;padding:0;}
body {font-family: 'Noto Sans KR', sans-serif;}
.back-con {width: 100%;hight:1500px;}
.contents {width: 600px;border: 1px solid lightgrey;border-radius: 30px;padding: 20px;margin:0 auto;}
.container{width: 1000px;margin:0 auto;}
img:hover {cursor: pointer;}
table tr {line-height: 3em;}
table th {font-size: 20px;text-align: left;}
table td input {width: 170px;height: 30px;}
.img_title {border: 1px solid rgb(219, 219, 219);border-radius: 20px;margin-right: 20px;height: 300px;}
.preview {width:550px;height:250px;margin: 20px;margin-left: 35px;}
p {font-weight: normal;font-size: 20px;padding-top: 10px;}
textarea {resize: none;vertical-align:text-bottom;border: 1px solid rgb(219, 219, 219);border-radius: 5px;background-color: rgb(246, 246, 246);}
textarea:focus {outline: none;}
#taginput {width: 600px;height: 35px;margin: 3px;border: 1px solid rgb(219, 219, 219);border-radius: 5px;background-color: rgb(246, 246, 246);margin-bottom:20px;}
.btn_color {border: 1px solid rgba(182, 181, 181, 0.849);cursor: pointer;color: black;display: inline-block;width: 130px;height: 40px;border-radius: 5px;font-size: 14px;background-color:transparent;}	
.btn_color:hover {background-color: #c2be5c;}
</style>
</head>

<body>
<div id="headerMain"></div>
<br>
<hr style="border: 0.2px solid rgb(236, 236, 236);">
<br>
<div class="back-con">
	<div class="container">
    	<div style="text-align:center; margin:20px;">
	    	<span class="h1text" style="font-family:'Sunflower', sans-serif;font-size:3rem;font-weight:bold;">스토리 수정</span>
	    </div>
	    <div class="contents">
	        <form action="/story/modify" method="post" id="test"enctype="multipart/form-data">
	         <input type="hidden" name="storySave" value="0" />
	         <input type="hidden" name="storyNo" value="${storyOne.storyNo }" /> 
	         <input type="hidden" name="fileNo" value="${storyOne.fileNo }" /> 
	         <table style="">
	             <thead></thead>
	             <tbody>
	                 <tr>
                         <td width="150px" class="imgtd">
	                     	<div>
	                             <div class="img_title">
	                                 <input id="storyFile" name="storyFile" type="file" style="display: none;"onchange="readURL(this, 'mainImg')">
	                                 <img class="preview" id="mainImg" src="
	                                 <c:if test="${empty storyOne.fileName }">/img/photoImg.png</c:if><c:if test="${not empty storyOne.fileName }"></c:if>/story-upload/${storyOne.fileName}"onclick="javascript: $('#storyFile').click();">
	                             </div>
	                     	</div>
                         </td>
	                 </tr>
	                 <tr>
	                     <td colspan="2">
	                         <div id="process">
	                                 <b>내용</b>
	                             <div id="process1" style="height: 150px; margin-bottom: 1em;">
	                                 <textarea placeholder="내용을 입력해주세요." name="story-contents" style="resize:none;vertical-align: middle; width: 600px; height: 150px;" value="${storyOne.storyContents }"></textarea>
	                             </div>
	                         </div>
	                     </td>
	                 </tr>
	             </tbody>
	         </table>
	
	         <div class="tag">
	             <h4>태그</h4>
	             <input type="text" name="story-tag" id="taginput" placeholder="태그를 입력해주세요.(#ㅈㅈ)" value="${storyOne.storyTag }" />
	         </div>
	         <div id="buttonArea" align="center">
	             <!-- <input type="button" value="삭제"> -->
	             <input class="btn_color" type="reset" value="취소">
	             <input class="btn_color" type="button" onclick="fnSave('A');" value="등록">
	            </div>
	        </form>
	    </div>
    </div>
</div>
<div id="footerMain"></div>
</body>
</html>