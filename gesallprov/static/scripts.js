function openTab(tabName) {
    var x = document.getElementsByClassName("tab");
    for (var i = 0; i < x.length; i++)
        x[i].style.display = "none";
    document.getElementById(tabName).style.display = "block";
}
function filterFunction() {
  // Declare variables
  var input, filter, table, tr, td, i;
  input = document.getElementById("filterInput");
  filter = input.value.toUpperCase();
  table = document.getElementById("resultTable");
  tr = table.getElementsByTagName("tr");

  // Loop through all table rows, and hide those who don't match the search query
  for (i = 0; i < tr.length; i++) {
    td = tr[i].getElementsByTagName("td")[0];
    if (td) {
      if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
        tr[i].style.display = "";
      } else {
        tr[i].style.display = "none";
      }
    }
  }
}