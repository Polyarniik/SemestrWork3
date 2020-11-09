<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="${pageContext.request.contextPath}/static/js/jquery-3.5.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/scripts.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/fontawesome/icons/all.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css" type="text/css">
</head>
<body>
<div class="wrapper">
    <header>
        <div class="logo">
            <a href="#">
                <c:url var="logo" value="/static/img/logoWithoutBack.png"/>
                <img src="${logo}" alt="Logo" width="60" height="60"/>
            </a>
        </div>
        <nav class="menu">
            <p>Wallet</p>
        </nav>
        <div class="profile">
            <a>${cookie.userName.value}</a>
            <c:url value="" var="quitURI">
                <c:param name="quit" value="1"/>
            </c:url>
            <a href="${quitURI}">Выход</a>
        </div>
    </header>
    <div class="banks flex-wrap flex">
        <c:url var="coins" value="/static/img/coins.png"/>
        <c:forEach items="${requestScope.banksList}" var="bank">
            <div class="account col-md-3" style="background-color: ${bank.color}">
                <img src="${coins}" alt="">
                <div class="account-data">
                    <div class="account-data-title"><b>${bank.name}</b></div>
                    <div class="account-data-value">${bank.amount}</div>
                </div>
            </div>
        </c:forEach>
        <c:if test="${requestScope.addBanksList.size() > 0}">
            <!-- Button trigger modal -->
            <button type="button" class="modal-btn btn btn-primary col-md-3" data-toggle="modal"
                    data-target="#staticBackdrop">
                Добавить банк +
            </button>
        </c:if>
        <!-- Modal -->
        <div class="modal fade" id="staticBackdrop" data-backdrop="static" data-keyboard="false" tabindex="-1"
             aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="staticBackdropLabel">Новый банк</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form method="post">
                            <input type="hidden" name="form" value="form1"/>
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <div class="form-group">
                                        <select class="bank-select form-control" name="bank-add" required="required">
                                            <c:forEach var="bank" items="${requestScope.addBanksList}">
                                                <option value="${bank.id}">${bank.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <input class="record-input  form-control" name="bank-add-amount" type=number
                                           placeholder="0"
                                           min="0"
                                           max="1000000000"
                                           step="0.01"
                                           pattern="([0-9]+\.?[0-9]{1,2})" value="0">
                                </div>
                            </div>
                            <button type="submit" class="btn btn-primary">Добавить банк</button>
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Отмена</button>
                        </form>
                    </div>
                    <%--                    <div class="modal-footer justify-content-between">--%>
                    <%--                    </div>--%>
                </div>
            </div>
        </div>
    </div>
    <div class="record-add-container">
        <form method="post">
            <input type="hidden" name="form" value="form2"/>
            <div class="form-row">
                <div class="form-group col-md-2">
                    <select class="bank-select form-control" name="bank-select">
                        <c:forEach items="${requestScope.banksList}" var="bank">
                            <option value="${bank.id}" <c:if
                                    test="${not empty requestScope.editRecord and requestScope.editRecord.category.id == bank.id}">
                                selected="selected"</c:if>>${bank.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group col-md-2">
                    <select class="record-type-select form-control" name="record-type-select" required="required">
                        <option value="0" <c:if
                                test="${not empty requestScope.editRecord and requestScope.editRecord.sum < 0}"> selected="selected" </c:if>>
                            Расход
                        </option>
                        <option value="1" <c:if
                                test="${not empty requestScope.editRecord and requestScope.editRecord.sum > 0}"> selected="selected" </c:if>>
                            Доход
                        </option>
                    </select>
                </div>
                <div class="form-group col-md-2">
                    <input class="record-input  form-control" id="record-input" name="record-input" type=number
                           placeholder="0"
                    <c:if test="${not empty requestScope.editRecord}">
                           value="<c:out value="${requestScope.editRecord.sum < 0 ? -requestScope.editRecord.sum : requestScope.editRecord.sum}"/>"
                    </c:if>
                           min="0"
                           max="1000000000"
                           step="0.01"
                           pattern="([0-9]+\.?[0-9]{1,2})"
                           required="required">
                </div>
                <div class="form-group col-md-2">
                    <select class="category-select form-control" name="category-select">
                        <c:forEach var="category" items="${requestScope.categoryList}">
                            <option value="${category.id}"
                                    <c:if test="${not empty requestScope.editRecord and requestScope.editRecord.category.id == category.id}">
                                        selected="selected"</c:if>>
                                    ${category.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group col-md-2">
                    <input type="date" class="form-control" id="date" name="date" placeholder="Дата"
                    <c:if test="${not empty requestScope.editRecordDate}">
                           value="<c:out value="${requestScope.editRecordDate}"/>"
                    </c:if> required>
                </div>
                <div class="form-group col-md-2">
                    <input class="description  form-control" name="description" type="text"
                           placeholder="Введите описание" maxlength="100"

                    <c:if test="${not empty requestScope.editRecord}">
                           value="${requestScope.editRecord.description}" </c:if>>
                    <div class="account">
                    </div>
                </div>
            </div>
            <div class="form-row">
                <div class="col-md-4">
                    <button type="submit" class="add-record btn"><c:if test="${not empty requestScope.editRecord}">
                        <c:out value="Изменить"/> </c:if>
                        <c:if test="${empty requestScope.editRecord}"> <c:out value="Добавить"/> </c:if></button>
                </div>
            </div>
        </form>
    </div>
    <div class="record-list-container">
<%--        <form style="display:flex; justify-content: center;">--%>
<%--            <div class="form-group col-md-3">--%>
<%--                <select class="select-record-date form-control">--%>
<%--                    <option>Последняя неделя</option>--%>
<%--                    <option>Последний месяц</option>--%>
<%--                    <option>Последний год</option>--%>
<%--                </select>--%>
<%--            </div>--%>
<%--        </form>--%>
        <div class="records-list">
            <div class="table-responsive">
                <table class="table">
                    <thead>
                    <tr>
                        <th></th>
                        <th scope="col">
                            <div class="record-category">
                                <div class="record-icon"><i class=" fas fa-shopping-bag mr-4"></i></div>
                                Категория
                            </div>
                        </th>
                        <th scope="col" class="record-account text-center">Банк</th>
                        <th scope="col" class="record-description text-center">Заметка</th>
                        <th scope="col" class="record-sum text-center">Сумма</th>
                        <th scope="col" class="record-date text-right">Дата</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="record" items="${requestScope.recordsList}">
                        <tr>
                            <td class="edit-icon">
                                <c:url value="" var="editURI">
                                    <c:param name="rec" value="${record.id}"/>
                                </c:url>
                                <a href="${editURI}"><i
                                        class="fas fa-edit" style="color: ${record.category.color}"></i></a>
                            </td>
                            <td>
                                <div class="record-category">
                                    <div class="record-icon"><i class=" fas ${record.category.iconName} mr-4"></i></div>
                                        ${record.category.name}
                                </div>
                            </td>
                            <td class="record-account text-center">${record.bank.name}</td>
                            <td class="record-description text-center">${record.description}</td>
                            <td class="record-sum text-center">${record.sum}</td>
                            <td class="record-date text-right">${record.date}</td>
                            <td class="delete-icon">
                                <c:url value="" var="delURI">
                                    <c:param name="del" value="${record.id}"/>
                                </c:url>
                                <a href="${delURI}"><i class="fas fa-trash"></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>