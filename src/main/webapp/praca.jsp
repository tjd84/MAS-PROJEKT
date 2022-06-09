<%@ page import="tjd.mas.projectman.model.Projekt" %>
<%@ page import="java.util.Map" %>
<%@ page import="tjd.mas.projectman.model.Osoba" %>
<%@ page import="tjd.mas.projectman.model.ProjektWewnetrzny" %>
<%@ page import="tjd.mas.projectman.model.ProjektDlaKlienta" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    Map<Integer, Osoba> pracownicy = (Map<Integer, Osoba>) request.getAttribute("pracownicy");
    String projectIdStr = (String) request.getAttribute("projectIdStr");
    String zadanieIdStr = (String) request.getAttribute("zadanieIdStr");

    String oldOpisCzynnosci = (String) request.getAttribute("oldOpisCzynnosci");
    String oldDataWykonania = (String) request.getAttribute("oldDataWykonania");
    String oldCzasPracy = (String) request.getAttribute("oldCzasPracy");
    String oldPracownik = (String) request.getAttribute("oldPracownik");

    String error = (String) request.getAttribute("error");
%>

<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="css/tjd.css">
    <!-- CSS only -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
    <!-- JavaScript Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

    <script src="jquery-ui/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="jquery-ui/jquery-ui.css">

    <title>projectMan</title>
</head>
<body>
<div class="container-fluid" style="background: dodgerblue;">
    <div class="container">
        <div class="row">
            <div class="col-md-7">
                <div class="mt-2 mb-2" style="color: white; font-size: 32px;">
                    <a style="color: white !important;" href="projekt?projectId=<%= projectIdStr %>">projectMan</a>
                </div>
            </div>
            <div class="col-md-5">
                <div class="mt-2 mb-2">
                    <input type="text" class="form-control" id="search" placeholder="znajdź zadanie">
                </div>
            </div>
        </div>
    </div>
</div>
<div class="container mt-5 border" style="border-radius: 6px;">

    <div class="row">
        <div class="col-12 p-3">
            <h1>Wprowadź dane wykonanej pracy</h1>

            <% if(error!=null){ %>
                <div class="alert alert-danger" role="alert">
                    <%=error%>
                </div>
            <%}%>

            <form action="praca?projectId=<%= projectIdStr %>&zadanieId=<%= zadanieIdStr %>" method="POST">
                <div class="form-group mt-5">
                    <label for="opisCzynnosci">Opis czynnosci</label>
                    <input type="text" class="form-control" id="opisCzynnosci" name="opisCzynnosci" value="<%= oldOpisCzynnosci!=null?oldOpisCzynnosci:"" %>">
                </div>

                <div class="form-group mt-3">
                    <label for="dataWykonania">Data wykonania</label>
                    <input type="text" class="form-control" id="dataWykonania" name="dataWykonania" value="<%= oldDataWykonania!=null?oldDataWykonania:"" %>">
                    <script>
                        $(function() {
                            $( "#dataWykonania" ).datepicker({"dateFormat": "yy-mm-dd"});
                        });
                    </script>
                </div>

                <div class="form-group mt-3">
                    <label for="pracownik">Pracownik</label>
                    <select class="form-control" id="pracownik" name="pracownik">
                        <option value="-1">--wybierz z listy--</option>
                        <% for(Map.Entry<Integer, Osoba> pracownik : pracownicy.entrySet()) {
                            boolean selected=false;
                            if(oldPracownik!=null && oldPracownik.equals(pracownik.getKey().toString()))
                                { selected=true; }
                        %>
                            <option <%=selected?"selected":""%> value="<%=pracownik.getKey().toString()%>"><%=pracownik.getValue().getImie()%> <%=pracownik.getValue().getNazwisko()%></option>
                        <%}%>
                    </select>
                </div>

                <div class="form-group mt-3">
                    <label for="czasPracy">Czas pracy</label>
                    <input type="number" class="form-control" id="czasPracy" name="czasPracy" value="<%= oldCzasPracy!=null?oldCzasPracy:"" %>">
                </div>

                <button type="submit" class="btn btn-primary mt-5">Zapisz</button>
            </form>
        </div>

    </div>

</div>

</body>
</html>