<%--
  Created by IntelliJ IDEA.
  User: Illamurialis
  Date: 31.05.2021
  Time: 14:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Authorization</title>
    <link crossorigin="anonymous" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css"
          integrity="sha512-NhSC1YmyruXifcj/KFRWoC561YpHpc5Jtzgvbuzx5VozKpWvQ+4nXhPdFgmx8xqexRcpAglTj9sIBWINXa8x5w=="
          rel="stylesheet"/>
    <link crossorigin="anonymous" href="https://cdnjs.cloudflare.com/ajax/libs/simplebar/5.3.0/simplebar.min.css"
          integrity="sha512-uZTwaYYhJLFXaXYm1jdNiH6JZ1wLCTVnarJza7iZ1OKQmvi6prtk85NMvicoSobylP5K4FCdGEc4vk1AYT8b9Q=="
          rel="stylesheet"/>
    <link href="${contextPath}/resources/scss/header.css" rel="stylesheet">
    <link href="${contextPath}/resources/scss/login.css" rel="stylesheet">
</head>
<body>
<div id="header-container"></div>
<img alt="" id="back" src="${contextPath}/resources/img/jpg/wide/hall.jpg">
<div id="sign-in-panel">
    <springForm:form method="POST" action="/login" modelAttribute="loginUser" id="login-form">
        <div id="sign-in">
            <div class="navbar">
                <div class="nav-item"></div>
                <div class="nav-item selected"><spring:message code="authorization.sign.in"/></div>
                <div class="nav-item registration">
                    <div><spring:message code="authorization.registration"/></div>
                    <div class="triangle"></div>
                </div>
            </div>
            <div class="fields">
                <div class="email-username">
                    <div class="title"><span><spring:message code="authorization.email.or.username.title"/></span><span class="error empty">*
                        <spring:message code="error.empty.field"/></span></div>
                    <div class="field">
                        <div>
                            <springForm:input path="emailOrUsername" id="email-or-username" placeholder="example@gmail.com" type="text"/>
                                <%--                            <input id="email-or-username" placeholder="example@gmail.com" type="text">--%>
                        </div>
                    </div>
                </div>
                <div class="password">
                    <div
                            class="title"><span><spring:message code="authorization.password.title"/></span><span class="error empty">*
                        <spring:message code="error.empty.field"/></span></div>
                    <div class="field">
                        <div>
                            <springForm:password path="loginPassword" id="password-login" placeholder="********"/>
                                <%--                            <input id="password-login" placeholder="********" type="password">--%>
                        </div>
                    </div>
                </div>
            </div>
            <div class="button" onclick="return false" onsubmit="return false">
                <div class="sign-in-btn"><spring:message code="authorization.sign.in"/></div>
            </div>
        </div>
    </springForm:form>
</div>
<div id="registration-panel">
    <springForm:form method="POST" action="/register" modelAttribute="registerUser" id="register-form">
        <div id="registration">
            <div class="navbar">
                <div class="nav-item sign-in">
                    <div class="triangle"></div>
                    <div><spring:message code="authorization.sign.in"/></div>
                </div>
                <div class="nav-item selected"><spring:message code="authorization.registration"/></div>
                <div class="nav-item"></div>
            </div>
            <div class="fields">
                <div class="username">
                    <div class="title"><span><spring:message code="username.title.default"/></span><span class="error required">*<spring:message
                            code="error.required.field"/></span></div>
                    <div class="field">
                        <div>
                            <spring:message code="authorization.username.placeholder" var="userExample"/>
                            <springForm:input path="username" id="username" placeholder="${userExample}" type="text"/>
                                <%--                            <input id="username" placeholder="User1234" type="text">--%>
                        </div>
                    </div>
                </div>
                <div class="first-name">
                    <div class="title"><spring:message code="first.name.title.default"/></div>
                    <div class="field">
                        <div>
                            <spring:message code="authorization.first.name.placeholder" var="firstNameExample"/>
                            <springForm:input path="firstName" placeholder="${firstNameExample}" type="text"/>
                                <%--                            <input placeholder="Bill" type="text">--%>
                        </div>
                    </div>
                </div>
                <div class="last-name">
                    <div class="title"><spring:message code="last.name.title.default"/></div>
                    <div class="field">
                        <div>
                            <spring:message code="authorization.last.name.placeholder" var="lastNameExample"/>
                            <springForm:input path="lastName" placeholder="${lastNameExample}" type="text"/>
                                <%--                            <input placeholder="Jonson" type="text">--%>
                        </div>
                    </div>
                </div>
                <div class="email">
                    <div class="title"><span><spring:message code="email.title.default"/></span><span class="error required">*<spring:message
                            code="error.required.field"/></span>
                        <span class="error not-email">*<spring:message code="error.not.email.syntax"/></span></div>
                    <div class="field">
                        <div>
                            <springForm:input path="email" placeholder="example@gmail.com" type="email" id="email" regex="^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$"/>
                                <%--                            <input id="email" placeholder="example@gmail.com" regex="^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$"--%>
                                <%--                                   type="email">--%>
                        </div>
                    </div>
                </div>
                <div class="password">
                    <div class="title"><span><spring:message code="authorization.password.title"/></span><span class="error required">*<spring:message
                            code="error.required.field"/></span>
                        <span class="error weak">*<spring:message code="error.weak.password"/></span></div>
                    <div class="field">
                        <div>
                            <springForm:password path="registerPassword" id="password-register" placeholder="********"
                                                 regex="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*[_!#$%^&*-]).{8,}$"/>
                                <%--                            <input id="password-register" placeholder="********"--%>
                                <%--                                   regex="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*[_!#$%^&*-]).{8,}$"--%>
                                <%--                                   type="password">--%>
                        </div>
                    </div>
                </div>
                <div class="password-confirm">
                    <div class="title"><span><spring:message code="authorization.confirm.password.title"/></span><span class="error required">*<spring:message
                            code="error.required.field"/></span>
                        <span class="error not-confirmed">*<spring:message code="error.not.confirmed"/></span></div>
                    <div class="field">
                        <div>
                            <springForm:password path="confirmPassword" id="password-confirm" placeholder="********"/>
                                <%--                            <input id="password-confirm" placeholder="********" type="password">--%>
                        </div>
                    </div>
                </div>
            </div>
            <div class="button" onclick="return false" onsubmit="return false">
                <div class="registration-btn"><spring:message code="button.register.title"/></div>
            </div>
        </div>
    </springForm:form>
</div>
</body>
<script>
    let ukraine = '<spring:message code="ukraine.default"/>';
    let unitedKingdom = '<spring:message code="united.kingdom.default"/>';
    let poland = '<spring:message code="poland.default"/>';
    let createMovieValue = '<spring:message code="button.create.movie.title"/>';
</script>
<script crossorigin="anonymous"
        integrity="sha512-bLT0Qm9VnAYZDflyKcBaQ2gg0hSYNQrJ8RilYldYQ1FxQYoCLtUjuuRuZo+fjqhx/qtq/1itJ0C2ejDxltZVFg=="
        src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script crossorigin="anonymous"
        integrity="sha512-0QbL0ph8Tc8g5bLhfVzSqxe9GERORsKhIn1IrpxDAgUsbBGz/V7iSav2zzW325XGd1OMLdL4UiqRJj702IeqnQ=="
        src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"></script>
<script async
        crossorigin="anonymous"
        defer
        integrity="sha512-AS9rZZDdb+y4W2lcmkNGwf4swm6607XJYpNST1mkNBUfBBka8btA6mgRmhoFQ9Umy8Nj/fg5444+SglLHbowuA=="
        src="https://cdnjs.cloudflare.com/ajax/libs/simplebar/5.3.0/simplebar.min.js"></script>
<script async
        crossorigin="anonymous"
        defer
        integrity="sha512-sR3EKGp4SG8zs7B0MEUxDeq8rw9wsuGVYNfbbO/GLCJ59LBE4baEfQBVsP2Y/h2n8M19YV1mujFANO1yA3ko7Q=="
        src="https://cdnjs.cloudflare.com/ajax/libs/jquery.inputmask/5.0.5/jquery.inputmask.min.js"></script>
<script async
        crossorigin="anonymous" defer
        src="https://cdn.jsdelivr.net/npm/jsbarcode@3.11.4/dist/barcodes/JsBarcode.code128.min.js"></script>
<script src="${contextPath}/resources/js/login.js"></script>
</html>
