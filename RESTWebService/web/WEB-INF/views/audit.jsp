<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>

        <title>Budget WebService - Audit Files</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/audit.css" type="text/css">

    </head>

    <body>

        <h2>Budget Service Audit Files</h2>

        <a href="/audit/deleteAudit.htm?_flowId=audit">Launch Flow</a>

        <table id="audit_table">
            <tr>
                <th id="fileName">File Name</th>
                <th id="yearlyIncome">Yearly Income</th>
                <th id="incomeTax">Income Tax</th>
                <th id="natInsurance">National Insurance Tax</th>
                <th></th>
                <th id="deleteAudit">Delete</th>
            </tr>

            <c:forEach items="${audits}" var="audit">
                <tr>
                    <td><c:out value="${audit.fileName}"/></td>
                    <td>£<c:out value="${audit.yearlyIncome}"/></td>
                    <td>£<c:out value="${audit.incomeTax} "/></td>
                    <td>£<c:out value="${audit.natInsTax} "/></td>
                    <td><c:out value="${audit.path} "/></td>
                    <td><a href="/RESTWebService/audit/deleteAudit/<c:out value="${audit.fileName}"/>">remove audit</a></td>
                </tr>
            </c:forEach>

        </table>


    </body>

</html>