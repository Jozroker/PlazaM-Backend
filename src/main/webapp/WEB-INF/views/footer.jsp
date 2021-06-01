<%--
  Created by IntelliJ IDEA.
  User: Illamurialis
  Date: 30.05.2021
  Time: 0:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Footer</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css"
          integrity="sha512-NhSC1YmyruXifcj/KFRWoC561YpHpc5Jtzgvbuzx5VozKpWvQ+4nXhPdFgmx8xqexRcpAglTj9sIBWINXa8x5w=="
          crossorigin="anonymous"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/simplebar/5.3.0/simplebar.min.css"
          integrity="sha512-uZTwaYYhJLFXaXYm1jdNiH6JZ1wLCTVnarJza7iZ1OKQmvi6prtk85NMvicoSobylP5K4FCdGEc4vk1AYT8b9Q=="
          crossorigin="anonymous"/>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Baumans&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${contextPath}/resources/scss/footer.css">
</head>
<body>
<footer id="footer">
    <div class="content">
        <div id="about">
            <div class="title"><spring:message code="footer.about"/></div>
            <div class="scroll info">
                <spring:message code="footer.about.info"/>
            </div>
        </div>
        <div id="contacts">
            <div class="title"><spring:message code="footer.contacts.title"/></div>
            <div class="info">
                <a target="_blank"
                   href="https://mail.google.com/mail/u/0/#inbox?compose=CllgCHrkVwbTchvBbQjhDPMtlwqmvxczbqmXXqpGCVrMLMRntQDtzFrwSrzrtdpGJchkZMpnsVq">
                    <div class="email">
                        <span class="title-2"><spring:message code="email.title.default"/>:&nbsp;</span><span>info@plazam.com</span>
                    </div>
                </a>
                <div class="phone">
                    <div class="title-2"><spring:message code="phone.title.default"/>:</div>
                    <div>
                        <ul>
                            <li>
                                <div class="flag ua-flag"></div>
                                <div class="number">(+380) 800-444-200</div>
                            </li>
                            <li>
                                <div class="flag uk-flag"></div>
                                <div class="number">(+380) 800-444-200</div>
                            </li>
                            <li>
                                <div class="flag pl-flag"></div>
                                <div class="number">(+380) 800-444-200</div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div id="footer-location">
            <div class="title"><spring:message code="footer.location.title"/></div>
            <div class="content"></div>
        </div>
        <div id="links">
            <div class="title"><spring:message code="footer.follow.us.title"/></div>
            <div class="icons">
                <a href="#" class="icon icon-facebook"></a>
                <a href="#" class="icon icon-instagram"></a>
                <a href="#" class="icon icon-youtube"></a>
                <a href="#" class="icon icon-googleplay"></a>
            </div>
        </div>
    </div>
    <div class="copyright">
        <span>Copyright PlazaM © 2021: </span><span><spring:message code="footer.rights"/></span>
    </div>
</footer>
</body>
<%--<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"--%>
<%--        integrity="sha512-bLT0Qm9VnAYZDflyKcBaQ2gg0hSYNQrJ8RilYldYQ1FxQYoCLtUjuuRuZo+fjqhx/qtq/1itJ0C2ejDxltZVFg=="--%>
<%--        crossorigin="anonymous"></script>--%>
<%--<script src="https://cdnjs.cloudflare.com/ajax/libs/simplebar/5.3.0/simplebar.min.js"--%>
<%--        integrity="sha512-AS9rZZDdb+y4W2lcmkNGwf4swm6607XJYpNST1mkNBUfBBka8btA6mgRmhoFQ9Umy8Nj/fg5444+SglLHbowuA=="--%>
<%--        crossorigin="anonymous"></script>--%>
<%--<script src="${contextPath}/resources/js/footer.js"></script>--%>
</html>
