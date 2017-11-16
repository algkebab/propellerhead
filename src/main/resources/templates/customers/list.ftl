<html>
    <head>
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <link rel="stylesheet" href="/resources/demos/style.css">

        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
        <script>
            function update(idCustomer) {
                var status = $('#' + idCustomer + '_status').val();
                $.post("/propellerhead/customers/change/status", {"status":status, "idCustomer":idCustomer}, function(data) {
                    if (data=="success") {
                        alert("Status was successfully updated");
                    } else {
                        alert("Error! Status wasn't updated");
                    }
                });
            }
        </script>

        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script type="text/javascript">
            $(function() {
                var format = "yy-mm-dd";
                $("input[name=createdFrom]").datepicker({ dateFormat: format }).val();
                $("input[name=createdTo]").datepicker({ dateFormat: format }).val();
            });
        </script>

        <title>Customer list - Propellerhead.</title>
    </head>
    <body>
        <form action="/propellerhead/customers/filter" method="post">
            Status:
            <select name="status">
                <option value="" <#if !(status??)> selected</#if>>EMPTY</option>
                <#list statuses as s>
                    <option value="${s}" <#if status?? && status==s> selected</#if>>${s}</option>
                </#list>
            </select>
            <br/>

            Created from:
            <input type="text" name="createdFrom" placeholder="yyyy-mm-dd" value="<#if createdFrom??>${createdFrom?string("yy-MM-dd")}</#if>" />
            <br/>

            Created to:
            <input type="text" name="createdTo" placeholder="yyyy-mm-dd" value="<#if createdTo??>${createdTo?string("yy-MM-dd")}</#if>" />
            <br/>

            <input type="radio" name="hasNotes" value="" <#if !(hasNotes??)> checked</#if>> Both
            <input type="radio" name="hasNotes" value="true" <#if hasNotes?? && hasNotes> checked</#if>> Has Notes
            <input type="radio" name="hasNotes" value="false" <#if hasNotes?? && !hasNotes> checked</#if>> Doesn't have notes
            <br/>

            Name:
            <input type="text" name="name" value="<#if name??>${name}</#if>">
            <br/>

            <input type="submit" value="Filter">
            <br/>
            <a href="/propellerhead/customers/filter/reset">Reset Filters</a>
        </form>
        <br/>

        <form action="/propellerhead/customers/sort" method="post">
            <select name="sort">
                <option value="" >EMPTY</option>
                <option value="created" <#if sort?? && sort=='created'> selected</#if>>By Date Created</option>
                <option value="modified" <#if sort?? && sort=='modified'> selected</#if>>By Date Modified</option>
                <option value="name" <#if sort?? && sort=='name'> selected</#if>>By Name</option>
                <option value="status" <#if sort?? && sort=='status'> selected</#if>>By Status</option>
            </select>
            <select name="order">
                <option value="" >EMPTY</option>
                <option value="ASC" <#if order?? && order=='ASC'> selected</#if>>ASC</option>
                <option value="DESC" <#if order?? && order=='DESC'> selected</#if>>DESC</option>
            </select>
            <input type="submit" value="Sort">
        </form>

        <#if customers??>
        <table style="border-collapse: collapse;width: 100%;text-align: left;">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Phone</th>
                    <th>Status</th>
                    <th>Created</th>
                    <th>Modified</th>
                    <th>Update</th>
                    <th>Details</th>
                </tr>
            </thead>
            <tbody
                <#list customers as c>
                    <tr>
                        <td>${c.id}</td>
                        <td>${c.name}</td>
                        <td>${c.phone}</td>
                        <td>
                            <select id="${c.id}_status">
                                <#list statuses as s>
                                    <option value="${s}" <#if c.status==s> selected</#if>>${s}</option>
                                </#list>
                            </select>
                        </td>
                        <td>${c.created?string("dd-MM-yyyy HH:mm:ss")}</td>
                        <td>${c.modified?string("dd-MM-yyyy HH:mm:ss")}</td>
                        <td>
                            <input type="button" onclick="update('${c.id}')" value="Update Status">
                        </td>
                        <td><a href="/propellerhead/customers/details/${c.id}">Details</a></td>
                    </tr>
                </#list>
            </tbody

        </table>
        <#else>
            No Customers Found
        </#if>
    </body>
</html>