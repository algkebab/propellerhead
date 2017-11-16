<html>
    <head>
        <title>Note edit page - Propellerhead</title>
    </head>
    <body>
        <#if note??>
            ID: ${note.id}<br/>
            Created: ${note.created?string("dd-MM-yyyy HH:mm:ss")}<br/>
            Modified: ${note.modified?string("dd-MM-yyyy HH:mm:ss")}<br/>
            <form action="/propellerhead/notes/edit" method="post">
                <input type="hidden" name="idNote" value="${note.id}">
                <input type="hidden" name="idCustomer", value="${idCustomer}">
                <textarea name="note">${note.note}</textarea>
                <input type="submit" value="Save">
            </form>
        <#else>
            No Note Found
        </#if>
    </body>
</html>