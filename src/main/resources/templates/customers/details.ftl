<html>
    <head>
        <link rel="stylesheet" type="text/css" href="../../static/css/global.css">
    </head>
    <body>
        <#if customer??>
            ID: ${customer.id}<br/>
            Name: ${customer.name}<br/>
            Phone: ${customer.phone}<br/>
            Created: ${customer.created?string("dd-MM-yyyy HH:mm:ss")}<br/>
            Modified: ${customer.modified?string("dd-MM-yyyy HH:mm:ss")}<br/>
            <br/>
            <#if customer.notes?? && customer.notes?has_content>
            Notes: <br/>
                <table style="border-collapse: collapse;width: 100%;text-align: left;">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Created</th>
                            <th>Modified</th>
                            <th>Content</th>
                            <th>Edit</th>
                            <th>Delete</th>
                        </tr>
                    </thead>
                    <tbody>
                        <#list customer.notes as n>
                            <tr>
                                <td>${n.id}</td>
                                <td>${n.created?string("dd-MM-yyyy HH:mm:ss")}</td>
                                <td>${n.modified?string("dd-MM-yyyy HH:mm:ss")}</td>
                                <td>${n.note}</td>
                                <td><a href="/propellerhead/notes/edit/${customer.id}/${n.id}">Edit</a></td>
                                <td><a href="/propellerhead/notes/delete/${n.id}">Delete</a></td>
                            </tr>
                        </#list>
                    </tbody>
                </table>
            <#else>
                Customer doesn't have notes
            </#if>

            <br/>
            <br/>

            Add new note:<br/>
            <form action="/propellerhead/notes/add" method="post">
                <input type="text" name="note">
                <input type="hidden" name="idCustomer" value="${customer.id}">
                <input type="submit" value="Save">
            </form>

        <#else>
            No Customer found
        </#if>
    </body>
</html>