<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <title>Delete an Audit Record</title>
    </head>
    <body>
        <h2>Delete Audit Web Flow</h2>
            <form:form method="post">

                <!-- When a view state is entered, the flow pauses and waits for the user to take some action. The flow execution key
                    is given to the view as a sort of "claim ticket" for the flow. When the user submits the form, the flow execution key
                    is sent along with it in the _flowExecutionKey field and the flow resumes where it left off -->
                <input type="hidden" name="_flowExecutionKey" value="${flowExecutionKey}"/>

                <!-- The _eventIf_ portion of the button's name is a clue to Spring Web Flow that what follows is an evenet that should
                    be fired. When the form is submitted by clicking that button, a deleteSingle or deleteAll Audit event will be fired,
                    triggering a transition to the relevant action state -->

                <input type="submit" name="_eventId_deleteSingleAudit" value="Delete Single Audit" />
            </form:form>

    </body>
</html>