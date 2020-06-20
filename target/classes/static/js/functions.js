
function findFunction() {
    // Declare variables
    //alert("xddd");
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("myInput");
    filter = input.value.toUpperCase();
    table = document.getElementById("myTable");
    tr = table.getElementsByTagName("tr");

    var sumaLepsza = document.getElementById("sumaLepsza");
    var suma = 0;

    // Loop through all table rows, and hide those who don't match the search query
    for (i = 0; i < tr.length; i++) {
        suma++;
        td = tr[i].getElementsByTagName("td")[1];
        if (td) {
            txtValue = td.textContent || td.innerText;
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }

    sumaLepsza.innerHTML = suma.toString();
}


function findFunction2() {
    // Declare variables
    //alert("xddd");
    var input, filter, table, tr, i, txtValue;
    input = document.getElementById("myInput2");
    filter = input.value.toUpperCase();
    table = document.getElementById("myTable2");
    //var tableChilds = table.getElementsByTagName("tr");

    tr = table.getElementsByTagName("tr");

    var wynikSumy= document.getElementById("wynikSumy");

    // wynikSumy.write("xdddd");

    var suma = 0;
    // Loop through all table rows, and hide those who don't match the search query
    //wynikSumy.innerHTML = suma.toString();
    /*for (i = 0; i < tr.length; i++) {
        suma++;
        /*var td2,table2,tr2,j;
        td2 = tableChilds[i].getElementsByTagName("td")[2];
        table2 = td2.getElementsByTagName("table");
        tr2 = table2.getElementsByTagName("tr");
        //suma = suma + tr.length;
        for(j=0;j<tr2.length;j++){
            suma++;
            var td3 = tr2[j].getElementsByTagName("td");
            if (td3) {
                txtValue = td3.textContent || td3.innerText;
                if (txtValue.toUpperCase().indexOf(filter) > -1) {
                    tr2[i].style.display = "";
                } else {
                    tr2[i].style.display = "none";
                }
            }
        }

    }*/
    var tdnapis = tr[1].getElementsByTagName("td")[0];
    wynikSumy.innerHTML = tdnapis.textContent;


}
